<?php
require_once __DIR__.'\..\Controller\ProfileController.php';

session_start();

$pc =new ProfileController();

try {

	$_SESSION['errorProfile'] = "";
	$pc->createInstructor($_POST['instructor_username'],$_POST['instructor_password'],
			$_POST['instructor_fName'], $_POST['instructor_lName']);

}
catch (Exception $e) {
	$_SESSION['errorInstructor'] = $e->getMessage();
}
?>

<!
DOCTYPE html>
<html>
	<head>
		<meta http-equiv = "refresh" content = "0; url=/ca.mcgill.ecse321.group10.TAMAS/View/createInstructor.php"/>
	</head>
</html>