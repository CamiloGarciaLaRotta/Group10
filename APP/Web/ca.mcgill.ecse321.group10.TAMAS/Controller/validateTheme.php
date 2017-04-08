<?php
// validation script of Theme choice

session_start();

// setter
if(isset($_POST['set'])) {
	$_SESSION['dark'] = $_POST['set'];
}

// getter
if(isset($_POST['get'])){
	$out = "false";
	if (isset($_SESSION['dark']) && !empty($_SESSION['dark'])) {
		$out = $_SESSION['dark'];
	}
	echo $out;
}
?>