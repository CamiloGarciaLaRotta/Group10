<?php
require_once __DIR__.'\ApplicationController.php';

session_start();

$ac =new ApplicationController();

// Different actions depending on the origin form
try {

	$_SESSION['errorJob'] = "";
	$_SESSION['successJob'] = "";

	// validate data
	$job_start = isset($_POST['job_start']) ? $_POST['job_start'] : '';
	$job_end = isset($_POST['job_end']) ? $_POST['job_end'] : '';
	$job_day = isset($_POST['job_day']) ? $_POST['job_day'] : '';
	$job_position = isset($_POST['job_position']) ? $_POST['job_position'] : '';
	$job_salary = isset($_POST['job_salary']) ? $_POST['job_salary'] : '';
	$job_requirements = isset($_POST['job_requirements']) ? $_POST['job_requirements'] : '';
	$job_courseCDN = isset($_POST['job_courseCDN']) ? $_POST['job_courseCDN'] : '';
	$job_instructorUsername = isset($_POST['job_instructorUsername']) ? $_POST['job_instructorUsername'] : '';
	
	
	// switch($_POST['submit']) {
	// 	//case 'Create':
	// 	case 'Publish': 
			$ac->createJob($job_start,$job_end,
					$job_day, $job_position,
					$job_salary, $job_requirements,
					$job_courseCDN, $job_instructorUsername);
			// break;
	
// LEFT EHRE FOR FUTURE IMPLEMENTATIONS
// 		case 'Delete': 
// 			$job = NULL;
// 			if (isset($_POST['jobspinner'])) {
// 				$jobID = $_POST['jobspinner'];
// 			}
			
// 			$ac->deleteJob($jobID);
// 			break;
	
// 		case 'Publish': 
// 			$job = NULL;
// 			if (isset($_POST['jobspinner'])) {
// 				$jobID = $_POST['jobspinner'];
// 			}
				
// 			$ac->publishJob($jobID);
// 			break;
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