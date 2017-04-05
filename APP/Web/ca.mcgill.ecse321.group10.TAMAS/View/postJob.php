<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title> Post Job Offer </title>
	<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
    <link href="../style.css" rel="stylesheet">
    <script
	  src="https://code.jquery.com/jquery-3.2.1.min.js"
	  integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
	  crossorigin="anonymous"></script>
	 <script type="text/javascript" src="../main.js"></script>
</head>
<body>
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

$profile = null;
$profiles = $pm->getInstructors();
foreach ($profiles as $p){
	if($p->getUsername() == $_SESSION['username']) {
		$profile = $p;
		break;
	}
}
?>
<main class="job">
	<span class="intro">
		<h3>TAMAS</h3>
		<h4>Job Form</h4>
				<br><br>
		<p id="TAhours"> </p>
		<br><br>
		<p id="Graderhours"> </p>
		<br><br>
		<hr>
		<p id="ApplicationInfo"> </p><br><br>
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
		<form action='../Controller/validateJob.php' id="form" method='post'>
			Course CDN<br><select name='job_courseCDN' id="courseCDN">
				<?php foreach ($profile->getCourses() as $course){?>
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
			<br><input type= "submit" name="submit" value="Publish"/>
		</form>
		<form action="home.php">
			<input type="submit" value="Back" />
		</form>
	</div>
</main>
</body>
</html>