<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title> Create Instructor </title>
<style>
.error {color: #FF0000;}
</style>
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

<p><span class ="error">
	<?php
	if(isset($_SESSION['errorInstructor']) && !empty($_SESSION['errorInstructor'])){
		echo $_SESSION["errorInstructor"];
	}
	?>
</span></p>

<form action='validateInstructor.php' method='post'>
	<p>Instructor Form:</p>
	<p>
		Username: <input type ="text" name="instructor_username" required/>
		First Name? <input type ="text" name="instructor_fName" required/><br>
		Password? <input type="password" name="instructor_password" required/>
		Last Name? <input type ="text" name="instructor_lName" required/><br>
	</p>
	<p><input type= "submit" value="Create Instructor"/></p>
</form>

<br>

<form action="../index.php">
		    <input type="submit" value="Back" />
</form><br>
</body>
</html>