<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title> Create Course </title>
	<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
    <link href="../style.css" rel="stylesheet">
</head>
<body>
<?php

$timezone = date_default_timezone_set('America/New_York');

require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';
require_once __DIR__.'\..\Model\CourseManager.php';
require_once __DIR__.'\..\Model\Course.php';

session_start();

//Retrieve the data from the model
$pt = new PersistenceTAMAS();
$cm = $pt->loadCourseManagerFromStore();
?>
<main class="course">
	<span class="intro">
		<h3>Course Form</h3>
		<br>
		<p class="error">
			<?php
			if(isset($_SESSION['errorCourse']) && !empty($_SESSION['errorCourse'])){
				echo $_SESSION["errorCourse"];
			}
			?>
		</p>
		<br>
		<p class="success">
			<?php
			if(isset($_SESSION['successCourse']) && !empty($_SESSION['successCourse'])){
				echo $_SESSION["successCourse"];
			}
			?>
		</p>
	</span>
	<div class="actions">
		<form action='validateCourse.php' method='post'>
			Course Name<input type ="text" name="course_name" required /><br><br>
			CDN<input type ="text" name="course_CDN" required/><br><br>
			Grader Time Budget<input type="text" name="course_graderBudget" required/><br><br>
			TA Time Budget<input type ="text" name="course_TABudget" required/><br>
			<br><input type= "submit" value="Create Course"/>
		</form>
		<form action="../index.php">
		    <input type="submit" value="Back" />
		</form>
	</div>
</main>
</body>
</html>