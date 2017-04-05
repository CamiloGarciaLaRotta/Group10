<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8">
		<title> Dashboard </title>
		<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
		<link href="../style.css" rel="stylesheet">
    	<script
		  src="https://code.jquery.com/jquery-3.2.1.min.js"
		  integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
		  crossorigin="anonymous"></script>
		<script type="text/javascript" src="../main.js"></script>
	</head>
<?php

session_start();

// initialize all prompts
$_SESSION['errorCourse'] = "";
$_SESSION['successCourse'] = "";
$_SESSION['errorProfile'] = "";
$_SESSION['successProfile'] = "";
$_SESSION['errorJob'] = "";
$_SESSION['successJob'] = "";
?>			
	<body 	
	<?php if(isset($_SESSION['dark'])) {
		if($_SESSION['dark']=="true") echo "class=\"dark\"";
		}
	?>
	>
		<header>
			<label class="switch">
			  <input type="checkbox" id="chk">
			  <div class="slider round" id="theme"></div>
			</label>
		</header>
		<main>
			<span class="intro">
				<h3>TAMAS</h3>
				<h4>Dashboard</h4><br><br>
				<p>Welcome back <?php echo $_SESSION['username']?></p>
			</span>
			<div class="actions">
				<?php 
				if ($_SESSION['username'] == "admin") {
				?>
			 	<form action="./createCourse.php">
			    	<input type="submit" class="btn <?php 
	if(isset($_SESSION['dark'])) {
		if($_SESSION['dark']=="true") echo "dark";
	}
	?>" value="Create Course" />
				</form>
				<form action="./createInstructor.php">
				    <input type="submit" class="btn <?php 
	if(isset($_SESSION['dark'])) {
		if($_SESSION['dark']=="true") echo "dark";
	}
	?>" value="Create Instructor" />
				</form>
				<?php }?>
				<?php 
				if ($_SESSION['username'] != "admin") {
				?>
				<form action="./modifyProfile.php">
				    <input type="submit"  class="btn <?php 
	if(isset($_SESSION['dark'])) {
		if($_SESSION['dark']=="true") echo "dark";
	}
	?>"value="Modify Profile" />
				</form>
				<form action="./postJob.php">
				    <input type="submit" class="btn <?php 
	if(isset($_SESSION['dark'])) {
		if($_SESSION['dark']=="true") echo "dark";
	}
	?>" value="Post Job" />
				</form>
				<form action="./manageApplication.php">
				    <input type="submit"  class="btn <?php 
	if(isset($_SESSION['dark'])) {
		if($_SESSION['dark']=="true") echo "dark";
	}
	?>" value="Manage Applications" />
				</form>
				<?php }?>
				<form action="../Controller/validateLogout.php">
				    <input type="submit"  class="btn <?php 
	if(isset($_SESSION['dark'])) {
		if($_SESSION['dark']=="true") echo "dark";
	}
	?>" value="Logout" />
				</form>
			</div>
		</main>
	</body>
</html>