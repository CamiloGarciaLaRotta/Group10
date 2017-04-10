<?php
require_once __DIR__. DIRECTORY_SEPARATOR .'InputValidator.php';
require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Persistence'. DIRECTORY_SEPARATOR .'PersistenceTAMAS.php';
require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'ApplicationManager.php';
require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'Application.php';
require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'Job.php';
require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'ProfileManager.php';
require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'Profile.php';
require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'Instructor.php';
require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'CourseManager.php';
require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'Course.php';
require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'Student.php';

/**
 *Controller for applications and jobs, handles creating jobs. 
 */
class ApplicationController{
	private $pt;
	private $am;
	private $pm;
	private $cm;
	/**
	 * Constructor for the ApplicationController class
	 */
	public function __construct(){
		$this->pt = new PersistenceTAMAS();
		$this->am = $this->pt->loadApplicationManagerFromStore();
		$this->pm = $this->pt->loadProfileManagerFromStore();
		$this->cm = $this->pt->loadCourseManagerFromStore();
	}
	/**
	 * Checks all inputs for the Job class constructor, if the inputs are valid then
	 * it creates a job and saves the job in persistence.
	 * If any inputs are invalid it throwns an exception.
	 * 
	 * @param integer $job_time 		The amount of time needed for the job.
	 * @param string $aDay				The day of the week the applicant needs to be available.
	 * @param string $aPosition		The position that the applicant will be applying to.
	 * @param integer $aSalary			The hourly salary for the position.
	 * @param string $aRequirements	The requirements of the job.
	 * @param integer $aCDN				The unique course number.
	 * @param Instructor $anInstructor		The instructor repsonsible for the course.
	 * @throws Exception
	 * 
	 */
	public function createJob($job_time, $aDay, $aPosition, $aSalary, 
							$aRequirements, $aCDN, $anInstructor) {
		//Validate primitive var inputs.
		//For all invalid inputs, throw an error to be displayed on the webpage
		$error = "";
		
		if(!is_numeric($job_time)) {
			$error .= ("Time Budget must be a non null Number!<br><br>");
		}
		if($job_time < 0) {
			$error .= ("Time Budget must be a positive Number!<br><br>");
		}
		if($job_time < 45) {
			$error .= ("Time Budget must be at least 45 hours/semester!<br><br>");
		}
		$requirements = InputValidator::validate_input($aRequirements);
		if($requirements==null || strlen($requirements) == 0){
			$error .= ("Requirements cannot be empty!<br><br>");
		} 
		if(!is_numeric($aSalary)) {
			$error .= ("Salary must be a non null Number!<br><br>");
		}
		if($aSalary < 0) {
			$error .= ("Salary must be a positive Number!<br><br>");
		}
		if(!is_numeric($aCDN)) {
			$error .= ("CDN must be a non null Integer!<br><br>");
		}
		if($aCDN < 0) {
			$error .= ("CDN must be a positive Integer!<br><br>");
		}
		$myInstructor = null;
		foreach ($this->pm->getInstructors() as $instructor){
			if(strcmp($instructor->getUsername(), $anInstructor)==0){
				$myInstructor = $instructor;
				break;
			}
		}
		if ($myInstructor == null) $error .= ("Instructor was not found!<br><br>");
		
		// Find the event
		$myCourse = null;
		foreach ($this->cm->getCourses() as $course){
			if(strcmp($course->getCdn(), $aCDN) ==0){
				$myCourse = $course;
				break;
			}
		}
		if ($myCourse == null) $error .= ("Course was not found!<br><br>");
		
		/**
		 * If there is an error then ouput it,
		 * otherwise create the Job and save it in persistence
		 */
		if(strlen($error) > 0) {
			throw new Exception($error);
		} else {
			try {
				$myJob = new Job($job_time, $aDay, 
						$aSalary, $requirements, $myCourse, $myInstructor);
				$myJob->setPosition($aPosition);
				$this->am->addJob($myJob);
			} catch (Exception $e){
				echo $e->getMessage();
			}
						
			// Write all the data to persistence
			$this->pt->writeApplicationDataToStore($this->am);
		}
	}
	
	// retrieve a string version of the applications for a given course
	public function getApplications($cdn) {
		$applications = $this->am->getApplications();

		$matchedApps = array();
		foreach($applications as $app) {
			if($app->getJobs()->getCourse()->getCdn() == $cdn){
				$matchedApps[$app->getId()] = $this->getCleanPosition($app->getJobs())." - ".$this->getCleanDegree($app->getStudent()); 
			}
		}
		
		echo json_encode($matchedApps);
	}
	
	// retrieve a string version of application information for a given application
	public function getApplicationInfo($id) {
		
		// retrieve selected application
		$selectedApp = null;
		$applications = $this->am->getApplications();
		foreach($applications as $app) {
			if($app->getId() == $id){
				$selectedApp = $app;
				break;
			}
		}
		
 		// encode necessary information
		$info = array();
		$info["student"] = $selectedApp->getStudent()->getFirstName()." ".$selectedApp->getStudent()->getLastName();
		$info["experience"] = $selectedApp->getStudent()->getExperience();
		$info["evaluation"] = $selectedApp->getStudentEvaluation();
		
		echo json_encode($info);
	}
	
	// make necessary changes in model and persistence to flag the hiring of a student
	public function hire($id){
		// retrieve selected application
		$selectedApp = null;
		$applications = $this->am->getApplications();
		foreach($applications as $app) {
			if($app->getId() == $id){
				$selectedApp = $app;
				break;
			}
		}
		
		$selectedApp->getJobs()->setOfferSent(TRUE);
		
		// Write all the data to persistence
		$this->pt->writeApplicationDataToStore($this->am);
	}
	
	// setter for the evaluation submitted by an instructor to a students application
	public function setEvaluation($eval, $id){
		// retrieve selected application
		$selectedApp = null;
		$applications = $this->am->getApplications();
		foreach($applications as $app) {
			if($app->getId() == $id){
				$selectedApp = $app;
				break;
			}
		}
		
		$selectedApp->setStudentEvaluation($eval);
		
		// Write all the data to persistence
		$this->pt->writeApplicationDataToStore($this->am);
	}
	
	// return a prettified version of the model's degree
	public function getCleanDegree($student){
		return ($student->getDegree() == "DegreeGRADUATE") ? "Graduate" : "Undergrad";
	}
	
	// return a prettified versions of the model's position
	public function getCleanPosition($job){
		$pos = "";
		switch ($job->getPosition()) {
			case "PositionTUTORIAL":
				$pos = "Tutorial";
				break;
			case "PositionLABORATORY":
				$pos = "Laboratory";
				break;
			case "PositionGRADER":
				$pos = "Grader";
				break;
			default:
				$pos = "UNDEFINED";
		}
		return $pos;
	}
}
?>