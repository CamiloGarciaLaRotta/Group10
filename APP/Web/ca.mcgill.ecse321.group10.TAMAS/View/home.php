<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title> Dashboard </title>
<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
<link href="../style.css" rel="stylesheet">
</head>
<body>
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
			    	<input type="submit" value="Create Course" />
				</form>
				<form action="./createInstructor.php">
				    <input type="submit" value="Create Instructor" />
				</form>
				<?php }?>
				<?php 
				if ($_SESSION['username'] != "admin") {
				?>
				<form action="./modifyProfile.php">
				    <input type="submit" value="Modify Profile" />
				</form>
				<form action="./postJob.php">
				    <input type="submit" value="Post Job" />
				</form>
				<form action="./manageApplication.php">
				    <input type="submit" value="Manage Applications" />
				</form>
				<?php }?>
				<form action="../Controller/validateLogout.php">
				    <input type="submit" value="Logout" />
				</form>
			</div>
		</main>
	</body>
</html>