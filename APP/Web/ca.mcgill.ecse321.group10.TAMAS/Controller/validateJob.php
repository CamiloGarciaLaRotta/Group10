<?php
require_once __DIR__.'\ApplicationController.php';

// validation script for job creation

session_start();

$ac =new ApplicationController();

try {

	$_SESSION['errorJob'] = "";
	$_SESSION['successJob'] = "";

	// validate data
	$jobTime = isset($_POST['jobTime']) ? $_POST['jobTime'] : '';
	$jobDay = isset($_POST['jobDay']) ? $_POST['jobDay'] : '';
	$jobPosition = isset($_POST['jobPosition']) ? $_POST['jobPosition'] : '';
	$jobSalary = isset($_POST['jobSalary']) ? $_POST['jobSalary'] : '';
	$jobRequirements = isset($_POST['jobRequirements']) ? $_POST['jobRequirements'] : '';
	$jobCourseCDN = isset($_POST['jobCourseCDN']) ? $_POST['jobCourseCDN'] : '';
	$job_instructorUsername = $_SESSION['username'];
	
	$ac->createJob($jobTime, $jobDay, $jobPosition,
			$jobSalary, $jobRequirements,
			$jobCourseCDN, $job_instructorUsername);
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