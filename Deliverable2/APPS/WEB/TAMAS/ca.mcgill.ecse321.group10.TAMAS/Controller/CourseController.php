<?php
require_once __DIR__.'\..\Controller\InputValidator.php';
require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';
require_once __DIR__.'\..\Model\CourseManager.php';
require_once __DIR__.'\..\Model\Course.php';

class CourseController{
	private $pt = new PersistenceTAMAS();
	private $cm = $pt->loadCourseManagerFromStore();
				
	public function __construct(){
	}
	
	public function createCourse($course_name, $CDN, 
								$graderTimeBudget, $TATimeBudget) {
		// Validate input
		$error = "";
		$name = InputValidator::validate_input($course_name);
		if($name==null || strlen($name) == 0){
			$error .= ("Course name cannot be empty!<br> ");
		} 
		if(!is_numeric($CDN)) {
			$error .= ("CDN must be a non null Integer!<br> ");
		} 
		if((!is_numeric($graderTimeBudget)) || (!is_numeric($TATimeBudget))) {
			$error .= ("Time budget must be a non null Integer!<br> ");
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

}
?>