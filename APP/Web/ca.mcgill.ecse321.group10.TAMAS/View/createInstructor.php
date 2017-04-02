<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title> Create Instructor </title>
	<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
    <link href="../style.css" rel="stylesheet">
</head>
<body>
<?php

$timezone = date_default_timezone_set('America/New_York');

require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';
require_once __DIR__.'\..\Model\ProfileManager.php';
require_once __DIR__.'\..\Model\Profile.php';
require_once __DIR__.'\..\Model\Instructor.php';

session_start();

//Retrieve the data from the model
$pt = new PersistenceTAMAS();
$pm = $pt->loadProfileManagerFromStore();
?>

<main class="instructor">
	<span class="intro">
		<h3>Instructor Form</h3>
		<br>
		<p class="error">
			<?php
			if(isset($_SESSION['errorProfile']) && !empty($_SESSION['errorProfile'])){
				echo $_SESSION["errorProfile"];
			}
			?>
		</p>
		<br>
		<p class="success">
			<?php
			if(isset($_SESSION['successProfile']) && !empty($_SESSION['successProfile'])){
				echo $_SESSION["successProfile"];
			}
			?>
		</p>
	</span>
	<div class="actions">
		<form action='validateInstructor.php' method='post'>
			Username<input type ="text" name="instructor_username" required/><br><br>
			Password<input type="password" name="instructor_password" required/><br><br>
			First Name<input type ="text" name="instructor_fName" required/><br><br>
			Last Name<input type ="text" name="instructor_lName" required/><br><br>
			<br><br><input type= "submit" value="Create Instructor"/>
		</form>
		<form action="../index.php">
			<input type="submit" value="Back" />
		</form>
	</div>
</main>
</body>
</html>