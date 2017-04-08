<?php
require_once __DIR__.'\CourseController.php';

session_start();

$cc = new CourseController();

try {
	$_SESSION['errorCourse'] = "";
	$_SESSION['successCourse'] = "";

	// validate data
	$courseName = isset($_POST['courseName']) ? $_POST['courseName'] : '';
	$courseCDN = isset($_POST['courseCDN']) ? $_POST['courseCDN'] : '';
	$courseGraderBudget = isset($_POST['courseGraderBudget']) ? $_POST['courseGraderBudget'] : '';
	$courseTutBudget = isset($_POST['courseTutBudget']) ? $_POST['courseTutBudget'] : '';
	$courseLabBudget = isset($_POST['courseLabBudget']) ? $_POST['courseLabBudget'] : '';
	
	$cc->createCourse($courseName,$courseCDN, 
			$courseGraderBudget, $courseTutBudget, $courseLabBudget);
}
catch (Exception $e) {
	$_SESSION['errorCourse'] = $e->getMessage();
}

if ($_SESSION['errorCourse'] == "") {
	$_SESSION['successCourse'] = "Succesfully added Course";
}
?>

<!
DOCTYPE html>
<html>
	<head>
 		<meta http-equiv = "refresh" content = "0; url=/ca.mcgill.ecse321.group10.TAMAS/View/createCourse.php"/> 
	</head>
</html>