<?php
require_once __DIR__.'\..\Controller\ApplicationController.php';

session_start();

$ac =new ApplicationController();

// Different actions depending on the origin form
try {

	$_SESSION['errorJob'] = "";
	
	switch($_REQUEST['subject']) {
	
		case 'Create': 
			$ac->createJob($_POST['job_start'],$_POST['job_end'],
					$_POST['job_salary'], $_POST['job_requirements'],
					$_POST['job_courseCDN'], $_POST['job_instructorUsername']);
			break;
	
		case 'Delete': 
			$job = NULL;
			if (isset($_POST['jobspinner'])) {
				$jobID = $_POST['jobspinner'];
			}
			
			$ac->deleteJob($jobID);
			break;
	
		case 'Publish': 
			$job = NULL;
			if (isset($_POST['jobspinner'])) {
				$jobID = $_POST['jobspinner'];
			}
				
			$ac->publishJob($jobID);
			break;
	
	}	
} catch (Exception $e) {
	$_SESSION['errorJob'] = $e->getMessage();
}
?>

<!
DOCTYPE html>
<html>
	<head>
		<meta http-equiv = "refresh" content = "0; url=/ca.mcgill.ecse321.group10.TAMAS/View/job.php"/>
	</head>
</html>