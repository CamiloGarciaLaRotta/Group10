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

class ApplicationController{
	private $pt;
	private $am;
	private $pm;
	private $cm;
	
	public function __construct(){
		$this->pt = new PersistenceTAMAS();
		$this->am = $this->pt->loadApplicationManagerFromStore();
		$this->pm = $this->pt->loadProfileManagerFromStore();
		$this->cm = $this->pt->loadCourseManagerFromStore();
	}
	
	public function createJob($startTime, $endTime, $aDay, $aPosition, $aSalary, 
							$aRequirements, $aCDN, $anInstructor) {
		//Validate primitive var input
		$error = "";
		
		if(strtotime($startTime) > strtotime($endTime)) {
			$error .= ("end time cannot be before event start time!<br><br>");
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
		
		if(strlen($error) > 0) {
			throw new Exception($error);
		} else {
			try {
				$myJob = new Job($startTime, $endTime, $aDay, 
						$aSalary, $requirements, $myCourse, $myInstructor);
				$myJob->setPosition($aPosition);
				$this->am->addJob($myJob);
			} catch (Exception $e){
				echo $e->getMessage();
			}
						
			// Write all the data
			$this->pt->writeApplicationDataToStore($this->am);
		}
	}

// KEPT FOR FUTURE DELIVERABLES
// 	public function deleteJob($jobID) {
// 		$error = "";
		
// 		$myJob = NULL;
// 		foreach ($this->am->getJobs() as $job){
// 			if(strcmp($job->getId(), $jobID) ==0){
// 				$myjob = $job;
// 				break;
// 			}
// 		}
			
// 		if ($myjob != NULL){
// 			// Delete posting
// 			$this->am->removeJob($myjob);
		
// 			// Write all the data
// 			$this->pt->writeApplicationDataToStore($this->am);
// 		} else {
// 			$error .= "Job Application not found!<br>";
// 			throw new Exception(trim($error));
// 		}
// 	}
	
// 	public function publishJob($jobID) {
// 		$error = "";
		
// 		$myJob = NULL;
// 		foreach ($this->am->getJobs() as $job){
// 			if(strcmp($job->getId(), $jobID) ==0){
// 				$myjob = $job;
// 				break;
// 			}
// 		}
			
// 		if ($myjob != NULL){
// 			// Delete posting
// 			$this->am->publishJob($myjob);  //TODO MUST IMPLEMENT A FLAG TO PUBLISH
		
// 			// Write all the data
// 			$this->pt->writeApplicationDataToStore($this->am);
// 		} else {
// 			$error .= "Job Application not found!<br>";
// 			throw new Exception(trim($error));
// 		}
// 	}
}
?>