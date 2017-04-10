<?php 
include __DIR__. DIRECTORY_SEPARATOR .'Controller'. DIRECTORY_SEPARATOR .'validateLogin.php';

if (isset($_SESSION['username'])) {
	header("location: ./View/home.php");
}
?>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8">
		<title> TAMAS </title>
		<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
	    <link href="style.css" rel="stylesheet">
	    <script
		  src="https://code.jquery.com/jquery-3.2.1.min.js"
		  integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
		  crossorigin="anonymous"></script>
		<script type="text/javascript" src="./main.js"></script>
	</head>
	<body 
	<?php 
	if(isset($_SESSION['dark'])) {
		if($_SESSION['dark']=="true") echo "class=\"dark\"";
	}
	?>
	>
		<header>
			<label class="switch">
			  <input type="checkbox" id="checkTheme">
			  <div class="slider round" id="theme"></div>
			</label>
		</header>
		<main class="index">
			<span class="intro">
				<h3>TAMAS</h3>
				<h4>Login</h4><br><br>
				<p>Instructor Platform</p><br><br>
				<p class="error">
				<?php
					if(isset($_SESSION['error']) && !empty($_SESSION['error'])){
						echo $_SESSION["error"];
					}
				?>
				</p>
			</span>
			<div class="actions">
				<form action="" method="post">
				    Username
				    <input type="text" name="username" placeholder="i.e. JamesMcGill" required autofocus/>
				    <br><br>
					Password
					<input type="password" name="password" placeholder="*********" required/>
					<br><br>
					<input class="btn 
					<?php 
						if(isset($_SESSION['dark'])) {
							if($_SESSION['dark']=="true") echo "dark";
						}
					?>" type="submit" name="login" value="Login" />
				</form>
			</div>
		</main>
	</body>
</html>