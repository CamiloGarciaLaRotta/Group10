<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title> Manage Job Postings </title>
	<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
    <link href="../style.css" rel="stylesheet">
</head>
<body>
<?php

$timezone = date_default_timezone_set('America/New_York');

require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';
require_once __DIR__.'\..\Model\ApplicationManager.php';
require_once __DIR__.'\..\Model\Application.php';


session_start();

//Retrieve the data from the model
$pt = new PersistenceTAMAS();
$am = $pt->loadApplicationManagerFromStore();
?>
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
			Instructor Username<input type="text" name="job_instructorUsername" required/><br><br>
			Course CDN<input type ="text" name="job_courseCDN" required/><br><br>
			Position<br>
			<input type="radio" name="job_position" value="PositionTA" required> TA
			<input type="radio" name="job_position" value="PositionGRADER" required> Grader<br><br>
			Requirements<input type ="text" name="job_requirements" required/><br><br>		<!-- TODO MAKE REQUIREMENTS INPUT TEXT LARGER -->
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
			<br><input type= "submit" name="submit" value="Publish"/>
		</form>
		<form action="../index.php">
			<input type="submit" value="Back" />
		</form>
	</div>
</main>


<!-- Leaving her ejust to facilitate its implementation in future deliverables -->
<!-- <form action='validateJob.php' method='post'> -->
<!-- 	<p> -->
<!-- 		Select the Job ID to delete:   -->
<!-- 			<select name='jobspinner'> -->
			<?php
// 			foreach ($am->getJobs() as $job){
// 				echo "<option>" . $job->getId() . "</option>";
// 			}
// 			?>
<!-- 			</select> -->
<!-- 		<input type ='submit' value='Delete' /><input type ='submit' value='Publish' /> -->
<!-- 	</p> -->
<!-- </form> -->

</body>
</html>