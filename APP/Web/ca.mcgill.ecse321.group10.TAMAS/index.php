<?php 
include __DIR__.'\View\login.php';

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
	</head>
	<body>
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
				    Username<input type="text" name="username" placeholder="i.e. JamesMcGill" required/><br><br>
					Password<input type="password" name="password" placeholder="*********" required/>
					<br><br>
					<input type="submit" name="login" value="Login" />
				</form>
			</div>
		</main>
	</body>
</html>