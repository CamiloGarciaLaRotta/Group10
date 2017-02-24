<?php
require_once __DIR__.'\..\Controller\InputValidator.php';
require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';
require_once __DIR__.'\..\Model\ProfileManager.php';
require_once __DIR__.'\..\Model\Profile.php';
require_once __DIR__.'\..\Model\Instructor.php';


class ProfileController{
	private $pt = new PersistenceTAMAS();
	private $pm = $pt->loadProfileManagerFromStore();
	
	public function __construct(){
	}
	
	public function createInstructor($aUsername, $aPassword, $aFirstName, $aLastName) {
		// Validate input
		$error = "";
		$uName = InputValidator::validate_input($aUsername);
		$pass = InputValidator::validate_input($aPassword);
		$fName = InputValidator::validate_input($aFirstName);
		$lName = InputValidator::validate_input($aLastName);

		if($uName==null || strlen($uName) == 0){
			$error .= ("Username name cannot be empty!<br>");
		} 
		if($pass==null || strlen($pass) == 0){
			$error .= ("Password name cannot be empty!<br>");
		} 
		if($fName==null || strlen($fName) == 0){
			$error .= ("First name name cannot be empty!<br>");
		} 
		if($lName==null || strlen($lName) == 0){
			$error .= ("Last name name cannot be empty!<br>");
		} 
		
		if(strlen($error) > 0) {
			throw new Exception($error);
		} else {
			// Add the new profile
			$instructor = new Instructor($uName, $pass, $fName, $lName);
			$this->pm->addInstructor($instructor);

			// Write all the data
			$this->pt->writeProfileDataToStore($this->pm);
		}
	}
}
?>