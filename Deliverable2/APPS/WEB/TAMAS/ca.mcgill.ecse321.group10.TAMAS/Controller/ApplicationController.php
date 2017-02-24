<?php
require_once __DIR__.'\.\InputValidator.php';
require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';
require_once __DIR__.'\..\Model\ApplicationManager.php';
require_once __DIR__.'\..\Model\Application.php';
require_once __DIR__.'\..\Model\ProfileManager.php';
require_once __DIR__.'\..\Model\Profile.php';
require_once __DIR__.'\..\Model\CourseManager.php';
require_once __DIR__.'\..\Model\Course.php';

class ApplicationController{
	private $pt = new PersistenceTAMAS();
	private $am = $pt->loadApplicationManagerFromStore();
	private $pm = $pt->loadProfileManagerFromStore();
	private $cm = $pt->loadCourseManagerFromStore();
	
	public function __construct(){
	}
	
	public function createJob($startTime, $endTime, $aSalary, 
							$aRequirements, $aCourse, $anInstructor) {
		//Validate primitive var input
		$error = "";
// 		$startTime = InputValidator::validate_date($startTime);			//TODO check with harley how he validated date
// 		$endTime = InputValidator::validate_date($endTime);
		$requirements = InputValidator::validate_input($requirements);

		
		if($requirements==null || strlen($requirements) == 0){
			$error .= ("Requirements cannot be empty!<br>");
		} 
		if(!is_numeric($aSalary)) {
			$error .= ("Salary must be a non null Integer!<br>");
		}
		
		if(strlen($error) > 0) {
			throw new Exception($error);
		} else {
			// validate reference var input
			$myIntstuctor = NULL;
			foreach ($this->pm->getInstructors() as $instructor){
				if(strcmp($instructor->getUsername(), $anInstructor)==0){
					$myIntstuctor = $instructor;
					break;
				}
			}
			// Find the event
			$myCourse = NULL;
			foreach ($this->cm->getCourses() as $course){
				if(strcmp($course->getCdn(), $aCourse) ==0){
					$myCourse = $course;
					break;
				}
			}
			
			// Register for the event
			if ($myIntstuctor != NULL && $myCourse != NULL){
				$myJob = new Job($startTime, $endTime, $aSalary, $requirements, $myCourse, $myInstructor);
				$this->am->addJob($job);
				
				// Write all the data
				$this->pt->writeApplicationDataToStore($this->am);
			} else {
				if($myIntstuctor == NULL){
					$error .= "Instructor not found!<br>";
				}
				if ($myCourse == NULL){
					$error .= "Course not found!<br>";
				}
				throw new Exception(trim($error));
			}
		}
	}
	
	public function deleteJob($jobID) {
		$error = "";
		
		$myJob = NULL;
		foreach ($this->am->getJobs() as $job){
			if(strcmp($job->getId(), $jobID) ==0){
				$myjob = $job;
				break;
			}
		}
			
		if ($myjob != NULL){
			// Delete posting
			$this->am->removeJob($myjob);
		
			// Write all the data
			$this->pt->writeApplicationDataToStore($this->am);
		} else {
			$error .= "Job Application not found!<br>";
			throw new Exception(trim($error));
		}
	}
	
	public function publishJob($jobID) {
		$error = "";
		
		$myJob = NULL;
		foreach ($this->am->getJobs() as $job){
			if(strcmp($job->getId(), $jobID) ==0){
				$myjob = $job;
				break;
			}
		}
			
		if ($myjob != NULL){
			// Delete posting
			$this->am->publishJob($myjob);  //TODO MUST IMPLEMENT A FLAG TO PUBLISH
		
			// Write all the data
			$this->pt->writeApplicationDataToStore($this->am);
		} else {
			$error .= "Job Application not found!<br>";
			throw new Exception(trim($error));
		}
	}
}
?>