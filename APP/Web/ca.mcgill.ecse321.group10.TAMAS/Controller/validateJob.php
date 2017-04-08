<?php
require_once __DIR__.'\ApplicationController.php';

session_start();

$ac =new ApplicationController();

// Different actions depending on the origin form
try {

	$_SESSION['errorJob'] = "";
	$_SESSION['successJob'] = "";

	// validate data
	$job_time = isset($_POST['job_time']) ? $_POST['job_time'] : '';
	$job_day = isset($_POST['job_day']) ? $_POST['job_day'] : '';
	$job_position = isset($_POST['job_position']) ? $_POST['job_position'] : '';
	$job_salary = isset($_POST['job_salary']) ? $_POST['job_salary'] : '';
	$job_requirements = isset($_POST['job_requirements']) ? $_POST['job_requirements'] : '';
	$job_courseCDN = isset($_POST['job_courseCDN']) ? $_POST['job_courseCDN'] : '';
	$job_instructorUsername = $_SESSION['username'];
	
	$ac->createJob($job_time, $job_day, $job_position,
			$job_salary, $job_requirements,
				$job_courseCDN, $job_instructorUsername);
} catch (Exception $e) {
	$_SESSION['errorJob'] = $e->getMessage();
}

if ($_SESSION['errorJob'] == "") {
	$_SESSION['successJob'] = "Succesfully published Job";
}
?>

<!
DOCTYPE html>
<html>
	<head>
 		<meta http-equiv = "refresh" content = "0; url=/ca.mcgill.ecse321.group10.TAMAS/View/postJob.php"/> 
	</head>
</html>