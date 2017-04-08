<?php
require_once __DIR__.'\.\InputValidator.php';
require_once __DIR__.'\.\applicationController.php';

session_start();

$ac = new ApplicationController();

// ROBERT VERIFY THE INPUT HERE
// if its submit evaluation it must not be empty
// its the only thing to check
// then make sure the error/success messages work fine

$_SESSION['errorHire'] = "";
$_SESSION['successHire'] = "";
		
echo $_POST['manageApp'];
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
		// ADD INPUT VERIFICATION HERE'
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