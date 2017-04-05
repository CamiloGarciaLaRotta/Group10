<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title> Manage Job Applications </title>
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
<main class="application">
	<span class="intro">
		<h3>TAMAS</h3>
		<h4>Application Manager</h4>
		<br><br>
		<p id="TAhours"> </p>
		<br><br>
		<p id="Graderhours"> </p>
		<br><br>
		<hr>
		<p id="ApplicationInfo"> </p>
		<p class="error">
			<?php
			if(isset($_SESSION['errorHire']) && !empty($_SESSION['errorHire'])){
				echo $_SESSION["errorHire"];
			}
			?>
		</p>
		<br>
		<p class="success">
			<?php
			if(isset($_SESSION['successHire']) && !empty($_SESSION['successHire'])){
				echo $_SESSION["successHire"];
			}
			?>
		</p>
	</span>
	<div class="actions">
		<form action='../Controller/validateHire.php' method='post'>
			Course CDN<br><select name='app_courseCDN' id="courseCDN">
				<?php foreach ($profile->getCourses() as $course){?>
					<option><?php echo $course->getCdn() ?></option>
				<?php }?>
			</select><br><br>		
			Application<br>
			<select size="5" name="application" id="applicationID">
				<?php 
				// TODO DISPLAY ALL APLICATIONS FOR CHOSEN CLASS
				?>
			</select><br><br>
			<br><input id="hire" type="submit" name="hire" value="Hire"/>
		</form>
		<form action="home.php">
			<input type="submit" value="Back" />
		</form>
	</div>
</main>
</body>
</html>