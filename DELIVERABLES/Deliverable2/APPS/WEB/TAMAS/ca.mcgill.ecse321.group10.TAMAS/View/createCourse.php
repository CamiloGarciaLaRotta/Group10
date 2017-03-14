<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title> Create Course </title>
<style>
.error {color: #FF0000;}
</style>
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

<p><span class ="error">
	<?php
	if(isset($_SESSION['errorCourse']) && !empty($_SESSION['errorCourse'])){
		echo $_SESSION["errorCourse"];
	}
	?>
</span></p>

<form action='validateCourse.php' method='post'>
	<p>Course Form:</p>
	<p>
		Course Name: <input type ="text" name="course_name" required />
		Grader Time Budget <input type="text" name="course_graderBudget" required/><br>
		CDN <input type ="text" name="course_CDN" required/>
		TA Time Budget <input type ="text" name="course_TABudget" required/><br>
	</p>
	<p><input type= "submit" value="Create Course"/></p>
</form>

<br>

<form action="../index.php">
		    <input type="submit" value="Back" />
</form><br>
</body>
</html>