<?php
// validation script for logout

session_start();

if(session_destroy()) {
	header("location: ../index.php");
}
?>