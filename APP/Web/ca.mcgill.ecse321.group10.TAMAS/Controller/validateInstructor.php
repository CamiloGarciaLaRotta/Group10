<?php
require_once __DIR__.'\ProfileController.php';

// validation script for Instructor creation

session_start();

$pc =new ProfileController();

try {
	$_SESSION['errorProfile'] = "";
	$_SESSION['successProfile'] = "";

	// validate <datalist>
	$instructor_username = isset($_POST['instructor_username']) ? $_POST['instructor_username'] : '';
	$instructor_password = isset($_POST['instructor_password']) ? $_POST['instructor_password'] : '';
	$instructor_fName = isset($_POST['instructor_fName']) ? $_POST['instructor_fName'] : '';
	$instructor_lName = isset($_POST['instructor_lName']) ? $_POST['instructor_lName'] : '';

	$cdns = ($_POST['cdn']);
	$instructor = $pc->createInstructor($instructor_username,$instructor_password,
			$instructor_fName, $instructor_lName, $cdns);
}
catch (Exception $e) {
	$_SESSION['errorProfile'] = $e->getMessage();
}

if ($_SESSION['errorProfile'] == "") {
	$_SESSION['successProfile'] = "Succesfully added Profile";
}
?>

<!
DOCTYPE html>
<html>
	<head>
		<meta http-equiv = "refresh" content = "0; url=/ca.mcgill.ecse321.group10.TAMAS/View/createInstructor.php"/>
	</head>
</html>