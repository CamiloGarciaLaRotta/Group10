<?php
require_once __DIR__.'\.\InputValidator.php';
require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';
require_once __DIR__.'\..\Model\ApplicationManager.php';
require_once __DIR__.'\..\Model\Application.php';
require_once __DIR__.'\..\Model\Job.php';
require_once __DIR__.'\..\Model\ProfileManager.php';
require_once __DIR__.'\..\Model\Profile.php';
require_once __DIR__.'\..\Model\Instructor.php';
require_once __DIR__.'\..\Model\CourseManager.php';
require_once __DIR__.'\..\Model\Course.php';
/**
 *Controller for courses, handles creation, deletion and retrieving time data
 */
class CourseController{
	private $pt;
	private $cm;
	private $am;
	/**
	 * Constructor for the CourseController class
	 */			
	public function __construct(){
		$this->pt = new PersistenceTAMAS();
		$this->cm = $this->pt->loadCourseManagerFromStore();
		$this->am = $this->pt->loadApplicationManagerFromStore();
	}
	/**
	 * Checks all inputs for the Course class constructor, if the inputs are valid then
	 * it creates a course and saves the it in persistence.
	 * If any inputs are invalid it throwns an exception.
	 * 
	 * @param string $course_name		The name of the course.
	 * @param unknown $CDN				The unique course identification number.	
	 * @param unknown $graderTimeBudget	The total amount of time allocated to graders.
	 * @param unknown $TATimeBudget		The total amount of time allocated to teaching assistants.
	 * @throws Exception
	 */
	public function createCourse($course_name, $CDN, 
								$graderTimeBudget, $TATimeBudget) {
		// Validate input, generate error if any inputs are invalid
		$error = "";
		$name = InputValidator::validate_input($course_name);
		if($name==null || strlen($name) == 0){
			$error .= ("Course name cannot be empty!<br><br>");
		} 
		if(!is_numeric($CDN)) {
			$error .= ("CDN must be a non null Integer!<br><br>");
		} 
		if($CDN < 0) {
			$error .= ("CDN must be a positive Integer!<br><br>");
		} 
		$courses = $this->cm->getCourses();
		foreach($courses as $c) {
			if($c->getCdn() == $CDN){
				$error .= ("CDN must be unique!<br><br>");
				break;
			}
		}
		if((!is_numeric($graderTimeBudget)) || (!is_numeric($TATimeBudget))) {
			$error .= ("Time budget must be a non null Integer!<br><br>");
		}
		if(($graderTimeBudget < 0) || ($TATimeBudget < 0)) {
			$error .= ("Time budget must be a positive Integer!<br><br>");
		}
		
		//if all inputs calid, create the course object, 
		//otherwise 
		if(strlen($error) > 0) {
			throw new Exception($error);
		} else {
			// Add the new course
			$course = new Course($name, $CDN, $graderTimeBudget, $TATimeBudget);
			$this->cm->addCourse($course);
				
			//4. Write all the data
			$this->pt->writeCourseDataToStore($this->cm);
		}
	}
	/**
	 * Checks if the course exists given the CDN, if so then it removes
	 * it from persistence, otherwise throws an exception.
	 * 
	 * @param integer $CDN	A unique course identification number.
	 * @throws Exception
	 */
	public function deleteCourse($CDN) {
		
		$error = "";
		if(!is_numeric($CDN)) {
			$error .= ("CDN must be a non null Integer!<br><br>");
		}
		
		if(strlen($error) > 0) {
			throw new Exception($error);
		} else {
			// find course to delete
			$courseToDel = null;
			$courses = $this->cm->getCourses();
			foreach ($courses as $c){
				if($c->getCdn() == $CDN){
					$courseToDel = $c;
					break;
				}
			}
			
			if(!$courseToDel) {
				$error = "No course found with CDN ".$CDN."<br><br>";
				throw new Exception($error);
			} else {
				$this->cm->removeCourse($courseToDel);
			}
		}
	}
	/**
	 * Returns the amount of hours remaining to be allocated for
	 * grader and TA positions for the given course.
	 * 
	 * @param integer $CDN
	 * @return string
	 */
	public function getRemainingBudget($CDN) {

		$remainingTATime = 0;
		$remainingGraderTime = 0;
		
		// get course budget
		$course = null;
		$courses = $this->cm->getCourses();
		foreach ($courses as $c){
			if($c->getCdn() == $CDN){
				$course = $c;
				break;
			}
		}
		
		$remainingTATime = $course->getTaTimeBudget();
		$remainingGraderTime = $course->getGraderTimeBudget();
		
		// get application allocated budget
		$jobs = $this->am->getJobs();
		foreach($jobs as $j){
			if($j->getCourse()->getCdn() == $CDN){
				if($j->getPosition()=="PositionTA") {
					$remainingTATime -= $j->getTimeBudget();
				} else {
					$remainingGraderTime -= $j->getTimeBudget();
				}
			}
		}
		
		return $remainingTATime .",".$remainingGraderTime;
	}

}
?>