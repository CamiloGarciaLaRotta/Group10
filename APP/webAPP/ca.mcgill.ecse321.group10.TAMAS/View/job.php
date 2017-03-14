<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title> Manage Job Postings </title>
<style>
.error {color: #FF0000;}
</style>
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

<p><span class ="error">
	<?php
	if(isset($_SESSION['errorJob']) && !empty($_SESSION['errorJob'])){
		echo $_SESSION["errorJob"];
	}
	?>
</span></p>

<form action='validateJob.php' method='post'>
	<p>Job Application Form:</p>
	<p>
		Course CDN: <input type ="text" name="job_courseCDN" required/>
		Requirements: <input type ="text" name="job_requirements" required/>				<!-- TODO MAKE REQUIREMENTS INPUT TEXT LARGER -->
		Start Time: <input type= "time" name="job_start" value="03:20" />
		Day:<select name="job_day">
			<option value="monday">Monday</option>
			<option value="tuesday">Tuesday</option>
			<option value="wednesday">Wednesday</option>
			<option value="thursday">Thursday</option>
			<option value="friday">Friday</option>
		</select><br>
				Instructor Username: <input type="text" name="job_instructorUsername" required/>
Salary: <input type ="text" name="job_salary" required/>
		
		End Time: <input type= "time" name="job_end" value="04:20" />
		  <input type="radio" name="job_position" value="PositionTA" required> TA
		  <input type="radio" name="job_position" value="PositionGRADER" required> Grader<br>
	</p>
	<p><input type= "submit" name="submit" value="Publish"/></p>
</form>

<br>

<form action="../index.php">
		    <input type="submit" value="Back" />
</form><br>

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