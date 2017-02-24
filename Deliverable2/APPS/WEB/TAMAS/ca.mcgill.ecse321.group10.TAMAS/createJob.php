<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title> Create Job Posting </title>
<style>
.error {color: #FF0000;}
</style>
</head>
<body>
<?php

$timezone = date_default_timezone_set('America/New_York');

require_once __DIR__.'\.\Persistence\PersistenceTAMAS.php';
require_once __DIR__.'\.\Model\ApplicationManager.php';
require_once __DIR__.'\.\Model\Application.php';
require_once __DIR__.'\.\Model\ProfileManager.php';
require_once __DIR__.'\.\Model\Profile.php';
require_once __DIR__.'\.\Model\Instructor.php';
require_once __DIR__.'\.\Model\CourseManager.php';
require_once __DIR__.'\.\Model\Course.php';

session_start();

//Retrieve the data from the model
$pt = new PersistenceTAMAS();
$am = $pt->loadApplicationManagerFromStore();
?>

<p><span class ="error">
	<?php
 		// TODO IMPLEMENT ERROR PROMPT
	?>
</span></p>

<form action='validateJob.php' method='post'>
	<p>Job Application Form:</p>
	<p>
		Course CDN: <input type ="text" name="job_courseCDN" required/>
		Instructor Username: <input type="text" name="job_instructorUsername" required/><br>
		Salary: <input type ="text" name="job_salary" required/>
		Requirements: <input type ="text" name="job_requirements" required/><br>				<!-- TODO MAKE REQUIREMENTS INPUT TEXT LARGER -->
		Start Time: <input type= "date" name="event_date" value=" <?php echo date('Y-m-d');?>" />
		Date: <input type= "date" name="event_date" value=" <?php echo date('Y-m-d');?>" />
	</p>
	<p><input type= "submit" value="Create Course"/></p>
</form>

</body>
</html>