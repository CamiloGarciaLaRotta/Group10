<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8">
		<title> TAMAS </title>
		<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
	    <link href="style.css" rel="stylesheet">
	</head>
	<body>
		<main>
			<span class="intro">
				<h3>TAMAS</h3>
				<p>Welcome back,</p>
				<p>please select an action professor</p>
			</span>
			<div class="actions">
				<form action="./View/createCourse.php">
				    <input type="submit" value="Create Course" />
				</form>
				<form action="./View/createInstructor.php">
				    <input type="submit" value="Create Instructor" />
				</form>
				<form action="./View/job.php">
				    <input type="submit" value="Manage Job Postings" />
				</form>
			</div>
		</main>
	</body>
</html>