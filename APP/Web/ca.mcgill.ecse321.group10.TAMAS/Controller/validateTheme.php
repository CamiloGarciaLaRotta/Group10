<?php
session_start();
if(isset($_POST['set'])) {
	$_SESSION['dark'] = $_POST['set'];
}
if(isset($_POST['get'])){
	$out = "false";
	if (isset($_SESSION['dark']) && !empty($_SESSION['dark'])) {
		$out = $_SESSION['dark'];
	}
	echo $out;
}
?>