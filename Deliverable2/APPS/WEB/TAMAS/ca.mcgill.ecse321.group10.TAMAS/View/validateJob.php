<?php
require_once __DIR__.'\..\Controller\ApplicationController.php';

session_start();

$ac =new ApplicationController();

try {

	$_SESSION['errorJob'] = "";
	$ac->createJob($_POST['job_start'],$_POST['job_end'],
			$_POST['job_salary'], $_POST['job_requirements'],
			$_POST['job_courseCDN'], $_POST['job_instructorUsername']);

}
catch (Exception $e) {
	$_SESSION['errorJob'] = $e->getMessage();
}
?>

<!
DOCTYPE html>
<html>
	<head>
		<meta http-equiv = "refresh" content = "0; url=/ca.mcgill.ecse321.group10.TAMAS/View/createJob.php"/>
	</head>
</html>