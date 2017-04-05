<?php
require_once __DIR__.'\CourseController.php';

session_start();

$cc = new CourseController();

try {
	$_SESSION['errorCourse'] = "";
	$_SESSION['successCourse'] = "";

	// validate data
	$course_name = isset($_POST['course_name']) ? $_POST['course_name'] : '';
	$course_CDN = isset($_POST['course_CDN']) ? $_POST['course_CDN'] : '';
	$course_graderBudget = isset($_POST['course_graderBudget']) ? $_POST['course_graderBudget'] : '';
	$course_TABudget = isset($_POST['course_TABudget']) ? $_POST['course_TABudget'] : '';
	
	$cc->createCourse($course_name,$course_CDN, 
			$course_graderBudget, $course_TABudget);
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