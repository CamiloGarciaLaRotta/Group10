<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title> Manage Job Postings </title>
	<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
    <link href="../style.css" rel="stylesheet">
</head>
<?php

$timezone = date_default_timezone_set('America/New_York');

require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';
require_once __DIR__.'\..\Model\ApplicationManager.php';
require_once __DIR__.'\..\Model\Application.php';
require_once __DIR__.'\..\Model\ProfileManager.php';
require_once __DIR__.'\..\Model\Profile.php';
require_once __DIR__.'\..\Model\Instructor.php';
require_once __DIR__.'\..\Model\CourseManager.php';
require_once __DIR__.'\..\Model\Course.php';


session_start();

//Retrieve the data from the model
$pt = new PersistenceTAMAS();
$am = $pt->loadApplicationManagerFromStore();
$pm = $pt->loadProfileManagerFromStore();
$cm = $pt->loadCourseManagerFromStore();
?>
<body 
<?php if(isset($_SESSION['dark'])) {
	if($_SESSION['dark']=="true") echo "class=\"dark\"";
}
?>
>
<main class="job">
	<span class="intro">
		<h3>Job Form</h3>
		<br>
		<p class="error">
			<?php
			if(isset($_SESSION['errorJob']) && !empty($_SESSION['errorJob'])){
				echo $_SESSION["errorJob"];
			}
			?>
		</p>
		<br>
		<p class="success">
			<?php
			if(isset($_SESSION['successJob']) && !empty($_SESSION['successJob'])){
				echo $_SESSION["successJob"];
			}
			?>
		</p>
	</span>
	<div class="actions">
		<form action='validateJob.php' method='post'>
			
			Instructor Username<select name='job_instructorUsername'>
				<?php foreach ($pm->getInstructors() as $instructor){?>
					<option><?php echo $instructor->getUsername() ?></option>
				<?php }?>
			</select><br><br>
			Course CDN<br><select name='job_courseCDN'>
				<?php foreach ($cm->getCourses() as $course){?>
					<option><?php echo $course->getCdn() ?></option>
				<?php }?>
			</select><br><br>		
			Position<br>
			<input type="radio" name="job_position" value="PositionTA" required> TA
			<input type="radio" name="job_position" value="PositionGRADER" required> Grader<br><br>
			Requirements<textarea class="text" cols="20" rows ="5" name="job_requirements" maxlength="100" reqired></textarea><br><br>	
			Salary<input type ="text" name="job_salary" required/><br><br>
			Day<select name="job_day">
				<option value="monday">Monday</option>
				<option value="tuesday">Tuesday</option>
				<option value="wednesday">Wednesday</option>
				<option value="thursday">Thursday</option>
				<option value="friday">Friday</option>
			</select><br><br>
			Start Time<input type= "time" name="job_start" value="03:20" /><br><br>
			End Time<input type= "time" name="job_end" value="04:20" /><br><br>
			<br><input type= "submit" class="btn <?php 
	if(isset($_SESSION['dark'])) {
		if($_SESSION['dark']=="true") echo "dark";
	}
	?>" name="submit" value="Publish"/>
		</form>
		<form action="../index.php">
			<input type="submit" class="btn <?php 
	if(isset($_SESSION['dark'])) {
		if($_SESSION['dark']=="true") echo "dark";
	}
	?>" value="Back" />
		</form>
	</div>
</main>
</body>
</html>