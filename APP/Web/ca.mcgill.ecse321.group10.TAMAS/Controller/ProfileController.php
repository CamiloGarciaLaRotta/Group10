<?php
require_once __DIR__. DIRECTORY_SEPARATOR .''. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Controller'. DIRECTORY_SEPARATOR .'InputValidator.php';
require_once __DIR__. DIRECTORY_SEPARATOR .''. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Persistence'. DIRECTORY_SEPARATOR .'PersistenceTAMAS.php';
require_once __DIR__. DIRECTORY_SEPARATOR .''. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'ProfileManager.php';
require_once __DIR__. DIRECTORY_SEPARATOR .''. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'Profile.php';
require_once __DIR__. DIRECTORY_SEPARATOR .''. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'Instructor.php';
require_once __DIR__. DIRECTORY_SEPARATOR .''. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'CourseManager.php';
require_once __DIR__. DIRECTORY_SEPARATOR .''. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'Course.php';

/**
 * Controller for Profiles, handles creation, deletion, modification and validation of instructors
 *
 */
class ProfileController{
	private $pt;
	private $pm;
	private $cm;
	/**
	 * Constructor for ProfileController
	 */
	public function __construct(){
		$this->pt = new PersistenceTAMAS();
		$this->pm = $this->pt->loadProfileManagerFromStore(); 
		$this->cm = $this->pt->loadCourseManagerFromStore();
	}
	/**
	 * Checks all inputs for the instructor class constructor, if the inputs are valid then
	 * it creates an instructor and saves the it in persistence.
	 * If any inputs are invalid it throwns an exception.
	 * 
	 * @param string $aUsername		The username associated with the profile.
	 * @param string $aPassword		The password associated with the profile.
	 * @param string $aFirstName	The first name of the Instructor.
	 * @param string $aLastName		The last name of the Instructor.
	 * @param integer array $cdns	The list of courses associated with the Instructor.		
	 * @throws Exception
	 */
	public function createInstructor($aUsername, $aPassword, $aFirstName, $aLastName, $cdns) {
		// Validate input, generate error if invalid inputs are found
		$error = "";
		$uName = InputValidator::validate_input($aUsername);
		$pass = InputValidator::validate_input($aPassword);
		$fName = InputValidator::validate_input($aFirstName);
		$lName = InputValidator::validate_input($aLastName);

		if($uName==null || strlen($uName) == 0){
			$error .= ("Username name cannot be empty!<br><br>");
		} 
		$Instructors = $this->pm->getInstructors();
		foreach($Instructors as $i) {
			if($i->getUsername() == $aUsername) {
				$error .= ("Username must be unique!<br><br>");
				break;
			}
		}
		if($pass==null || strlen($pass) == 0){
			$error .= ("Password name cannot be empty!<br><br>");
		} 
		if($fName==null || strlen($fName) == 0){
			$error .= ("First name name cannot be empty!<br><br>");
		} 
		if($lName==null || strlen($lName) == 0){
			$error .= ("Last name name cannot be empty!<br><br>");
		} 
		
		if(strlen($error) > 0) {
			throw new Exception($error);
		} else {
			// Add the new profile
			$instructor = new Instructor($uName, $pass, $fName, $lName);
			
			if(!empty($cdns)){
				$courses = $this->cm->getCourses();
				foreach($courses as $c){
					if(in_array($c->getCdn(), $cdns)) $instructor->addCourse($c);
				}	
			}
			
			$this->pm->addInstructor($instructor);			

			// Write all the data to persistence
			$this->pt->writeProfileDataToStore($this->pm);
		}
	}
	/**
	 * Validates login input to determine if an instructor is logging in
	 * by checking existing Instructor profiles
	 * 
	 * @param string $aUsername		A login username
	 * @param string $aPassword		A login password
	 * @throws Exception
	 */
	public function validate($aUsername, $aPassword) {
		// Validate input
		$error = "";
		$uName = InputValidator::validate_input($aUsername);
		$pass = InputValidator::validate_input($aPassword);
		
		if($uName==null || strlen($uName) == 0){
			$error = ("Username name cannot be empty!<br><br>");
		}
		if($pass==null || strlen($pass) == 0){
			$error = ("Password name cannot be empty!<br><br>");
		}
		
		if($uName == "admin" && $pass == "admin") {
			// Do nothing for admin
		} else {		
			$Instructors = $this->pm->getInstructors();
			$found = FALSE;
			foreach($Instructors as $i) {
				if($i->getUsername() == $aUsername && $i->getPassword() == $pass) {
					$found = TRUE;
					break;
				}
			}
			if(!$found) $error = ("Couldn't find username/password!<br><br>");
			if(strlen($error) > 0) {
				throw new Exception($error);
			}
		}
	}
	/**
	 * Updates profile info with new inputs.
	 * Validates input and then changes the profile info in persistence.
	 * 
	 * @param string $aUsername 	The username of the profile.
	 * @param string $aFirstName	The new first name of the Instructor.
	 * @param string $aLastName		Th new last name of the Instructor.
	 * @param string $anOldPassword	The current password associated with the profile.
	 * @param string $aNewPassword	The new password to be associated with the profile.
	 * @throws Exception
	 */
	public function updateProfile($aUsername,$aFirstName,
			$aLastName, $anOldPassword, $aNewPassword) {
		
		// Validate input
		$error = "";
		$uName = InputValidator::validate_input($aUsername);
		$fName = InputValidator::validate_input($aFirstName);
		$lName = InputValidator::validate_input($aLastName);
		$oldPass = InputValidator::validate_input($anOldPassword);
		$newPass = InputValidator::validate_input($aNewPassword);

		if($uName == "admin" && $pass == "admin") {
			// Do nothing for admin
		} else {
			$Instructors = $this->pm->getInstructors();
			$profile = null;
			foreach($Instructors as $i) {
				if($i->getUsername() == $aUsername) {
					$profile = $i;
					break;
				}
			}
			if($profile==null) $error .= ("Invalid password!<br><br>");
			if(strlen($error) > 0) {
				throw new Exception($error);
			} else {
				if($fName != "XXX") $profile->setFirstName($fName);
				if($lName != "XXX") $profile->setLastName($lName);
				if($newPass != "XXX") $profile->setPassword($newPass);
				

				// Write all the data
				$this->pt->writeProfileDataToStore($this->pm);
			}
		}
	}
}
?>