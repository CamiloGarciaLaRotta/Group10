<?php
require_once __DIR__.'\..\Controller\CourseController.php';

session_start();

$cc =new CourseController();

try {

	$_SESSION['errorCourse'] = "";
	$cc->createCourse($_POST['course_name'],$_POST['course_CDN'], 
			$_POST['course_graderBudget'], $_POST['course_TABudget']);

}
catch (Exception $e) {
	$_SESSION['errorCourse'] = $e->getMessage();
}
?>

<!
DOCTYPE html>
<html>
	<head>
		<meta http-equiv = "refresh" content = "0; url=/ca.mcgill.ecse321.group10.TAMAS/View/createCourse.php"/>
	</head>
</html>