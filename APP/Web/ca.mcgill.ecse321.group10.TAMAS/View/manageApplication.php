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
	
	// gather instance of current logged in profile
	$profiles = $pm->getInstructors();
	foreach ($profiles as $p){
		if($p->getUsername() == $_SESSION['username']) {
			$profile = $p;
			break;
		}
	}
	?>
	<body 
	<?php 
	if(isset($_SESSION['dark'])) {
		if($_SESSION['dark']=="true") echo "class=\"dark\"";
	}
	?>>
		<header>
			<label class="switch">
			  <input type="checkbox" id="chk">
			  <div class="slider round" id="theme"></div>
			</label>
		</header>
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
				<br>
				<p id="studentName"> </p>
				<br>
				<br>
				<p id="studentExp"> </p>
				<br>
				<p class="error">
					<?php
					if(isset($_SESSION['errorHire']) && !empty($_SESSION['errorHire'])){
						echo $_SESSION["errorHire"];
					}
					?>
				</p>
				<br><br>
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
							<option value="<?php echo $course->getCdn() ?>"><?php echo $course->getClassName() ?></option>
						<?php }?>
					</select><br><br>		
					Application<br>
					<select size="5" name="application" id="applicationID" required>
					</select><br><br>
					Student Evaluation
					<textarea class="text" cols="20" rows ="5" name="evaluation" id="evaluation" maxlength="100" >
					</textarea><br><br>
					<br><input id="evaluate" type="submit" class="btn 
					<?php 
						if(isset($_SESSION['dark'])) {
							if($_SESSION['dark']=="true") echo "dark";
						}
					?>" name="manageApp" value="Submit Evaluation" />
					<br><input id="hire" type="submit" class="btn 
					<?php 
						if(isset($_SESSION['dark'])) {
							if($_SESSION['dark']=="true") echo "dark";
						}
					?>" name=""manageApp"" value="Hire" />
				</form>
				<form action="home.php">
					<input type="submit" class="btn 
					<?php 
					if(isset($_SESSION['dark'])) {
						if($_SESSION['dark']=="true") echo "dark";
					}
					?>" value="Back" />
				</form>
			</div>
		</main>
	</body>
</html>
