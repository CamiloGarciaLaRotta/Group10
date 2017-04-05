<?php
require_once __DIR__.'\ProfileController.php';

session_start();

$pc =new ProfileController();

try {
	$_SESSION['errorProfile'] = "";
	$_SESSION['successProfile'] = "";

	// validate <datalist>
	$firstName = isset($_POST['firstName']) ? $_POST['firstName'] : "XXX";
	$lastName = isset($_POST['lastName']) ? $_POST['lastName'] : 'XXX';
	$oldPassword = isset($_POST['oldPassword']) ? $_POST['oldPassword'] : 'XXX';
	$newPassword = isset($_POST['newPassword']) ? $_POST['newPassword'] : 'XXX';

	$pc->updateProfile($_SESSION['username'],$firstName,
			$lastName, $oldPassword, $newPassword);

}
catch (Exception $e) {
	$_SESSION['errorProfile'] = $e->getMessage();
}

if ($_SESSION['errorProfile'] == "") {
	$_SESSION['successProfile'] = "Succesfully modified Profile";
}
?>

<!
DOCTYPE html>
<html>
	<head>
		<meta http-equiv = "refresh" content = "0; url=/ca.mcgill.ecse321.group10.TAMAS/View/modifyProfile.php"/>
	</head>
</html>