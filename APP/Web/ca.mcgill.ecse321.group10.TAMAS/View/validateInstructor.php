<?php
require_once __DIR__.'\..\Controller\ProfileController.php';

session_start();

$pc =new ProfileController();

try {
	$_SESSION['errorProfile'] = "";
	$_SESSION['successProfile'] = "";

	// validate <datalist>
	$instructor_name = isset($_POST['instructor_name']) ? $_POST['instructor_name'] : '';
	$instructor_password = isset($_POST['instructor_password']) ? $_POST['instructor_password'] : '';
	$instructor_fName = isset($_POST['instructor_fName']) ? $_POST['instructor_fName'] : '';
	$instructor_lName = isset($_POST['instructor_lName']) ? $_POST['instructor_lName'] : '';

	$pc->createInstructor($instructor_username,$instructor_password,
			$instructor_fName, $instructor_lName);

}
catch (Exception $e) {
	$_SESSION['errorInstructor'] = $e->getMessage();
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