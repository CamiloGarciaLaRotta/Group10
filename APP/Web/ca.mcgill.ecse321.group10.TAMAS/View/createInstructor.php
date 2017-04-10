<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8">
		<title> Create Instructor </title>
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
	
	require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Persistence'. DIRECTORY_SEPARATOR .'PersistenceTAMAS.php';
	require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'CourseManager.php';
	require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'Course.php';
	
	session_start();
	
	//Retrieve the data from the model
	$pt = new PersistenceTAMAS();
	$cm = $pt->loadCourseManagerFromStore();
	?>
	<body 
	<?php if(isset($_SESSION['dark'])) {
		if($_SESSION['dark']=="true") echo "class=\"dark\"";
	}
	?>>
		<header>
			<label class="switch">
			  <input type="checkbox" id="checkTheme">
			  <div class="slider round" id="theme"></div>
			</label>
		</header>
		<main class="instructor">
			<span class="intro">
				<h3>TAMAS</h3>
				<h4>Instructor Form</h4>
				<br><br>
				<p>To select multiple courses at the same time hold Ctrl</p>
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
				<form action='../Controller/validateInstructor.php' method='post'>
					Username<input type ="text" name="instructor_username" required autofocus/><br><br>
					Password<input type="password" name="instructor_password" required/><br><br>
					First Name<input type ="text" name="instructor_fName" required/><br><br>
					Last Name<input type ="text" name="instructor_lName" required/><br><br>
					Courses<br><select name='cdn[]' size="5" id="courseCDN" multiple>
						<?php foreach ($cm->getCourses() as $course){?>
							<option value="<?php echo $course->getCdn() ?>"><?php echo $course->getClassName() ?></option>
						<?php }?>
					</select><br><br>	
					<input type= "submit" class="btn 
					<?php 
						if(isset($_SESSION['dark'])) {
							if($_SESSION['dark']=="true") echo "dark";
						}
					?>" value="Create Instructor"/>
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