<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8">
		<title> Modify Profile </title>
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
	require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'ProfileManager.php';
	require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'Profile.php';
	require_once __DIR__. DIRECTORY_SEPARATOR .'..'. DIRECTORY_SEPARATOR .'Model'. DIRECTORY_SEPARATOR .'Instructor.php';
	
	session_start();
	
	//Retrieve the data from the model
	$pt = new PersistenceTAMAS();
	$pm = $pt->loadProfileManagerFromStore();
	
	$profile = null;
	$profiles = $pm->getInstructors();
	foreach ($profiles as $p){
		if($p->getUsername() == $_SESSION['username']) {
			$profile = $p;
			break;
		}
	}
	
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
		<main class="profile">
			<span class="intro">
				<h3>TAMAS</h3>
				<h4>Modify Profile</h4>
				<br>
				<p>Update the desired parameters</p><br><br>
				<p>Firt Name: <?php echo $profile->getFirstName();?></p><br><br>
				<p>Last Name: <?php echo $profile->getLastName();?></p><br><br>
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
				<form action='../Controller/validateModifyProfile.php' method='post'>
					First Name<input type ="text" value="<?php echo $profile->getFirstName();?>" name="firstName" /><br><br>
					Last Name<input type ="text" value="<?php echo $profile->getLastName();?>" name="lastName" /><br><br>
					Old Password <input type="password" name="oldPassword" required/><br><br>
					New Password<input type="password" name="newPassword" required/><br><br>
					<input type= "submit" class="btn 
					<?php 
					if(isset($_SESSION['dark'])) {
						if($_SESSION['dark']=="true") echo "dark";
					}
					?>" value="Update Profile"/>
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