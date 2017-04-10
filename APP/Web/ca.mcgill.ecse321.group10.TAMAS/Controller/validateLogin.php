<?php
require_once __DIR__. DIRECTORY_SEPARATOR .'ProfileController.php';

// validation script for login

session_start();

$pc = new ProfileController();

// avoid ilegal input
$username = (isset($_POST['username'])) ? $_POST['username'] : "";
$password = (isset($_POST['password'])) ? $_POST['password'] : "";

if(isset($_POST['login'])) {
	
 	$_SESSION['error'] = "";
	
	try {
		$pc->validate($username,$password);
	} catch (Exception $e) {
		$_SESSION['error'] = $e->getMessage();
		$error = $e->getMessage();
	}
	
	if ($_SESSION['error'] == "") {
		$_SESSION['username'] = $username;
		header("location: home.php");
	}
}
?>
