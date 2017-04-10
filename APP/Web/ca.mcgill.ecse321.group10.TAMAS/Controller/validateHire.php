<?php
require_once __DIR__. DIRECTORY_SEPARATOR .'InputValidator.php';
require_once __DIR__. DIRECTORY_SEPARATOR .'applicationController.php';

// validation script for hiring form

session_start();

$ac = new ApplicationController();

$_SESSION['errorHire'] = "";
$_SESSION['successHire'] = "";
		
if($_POST['manageApp'] == 'Hire'){
	try{
		$id = (isset($_POST['application'])) ?  $_POST['application'] : "";
		$ac->hire($id);
	} catch(Exception $e){
		$_SESSION['errorHire'] = $e->getMessage();
	}
	if ($_SESSION['errorHire'] == ""){
		$_SESSION['successHire'] = "Succesfully sent offer";
	}
} else if($_POST['manageApp'] == 'Submit Evaluation'){
	try{
		$eval = (isset($_POST['evaluation'])) ? $_POST['evaluation'] : "";
		$id = (isset($_POST['application'])) ?  $_POST['application'] : "";
		$ac->setEvaluation($eval,$id);
	} catch(Exception $e){
		$_SESSION['errorHire'] = $e->getMessage();
	}
	if ($_SESSION['errorHire'] == ""){
		$_SESSION['successHire'] = "Succesfully submitted Evaluation";
	}
}
?>
<!
DOCTYPE html>
<html>
	<head>
 		<meta http-equiv = "refresh" content = "0; url=/ca.mcgill.ecse321.group10.TAMAS/View/manageApplication.php"/> 
	</head>
</html>