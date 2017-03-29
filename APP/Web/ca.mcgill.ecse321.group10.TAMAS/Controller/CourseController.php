<?php
require_once __DIR__.'\..\Controller\InputValidator.php';
require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';
require_once __DIR__.'\..\Model\CourseManager.php';
require_once __DIR__.'\..\Model\Course.php';

class CourseController{
	private $pt;
	private $cm;
				
	public function __construct(){
		$this->pt = new PersistenceTAMAS();
		$this->cm = $this->pt->loadCourseManagerFromStore();
	}
	
	public function createCourse($course_name, $CDN, 
								$graderTimeBudget, $TATimeBudget) {
		// Validate input
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

}
?>