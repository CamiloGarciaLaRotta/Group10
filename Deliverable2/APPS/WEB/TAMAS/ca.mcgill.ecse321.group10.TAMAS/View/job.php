<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title> Manage Job Postings </title>
<style>
.error {color: #FF0000;}
</style>
</head>
<body>
<?php

$timezone = date_default_timezone_set('America/New_York');

require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';
require_once __DIR__.'\..\Model\ApplicationManager.php';
require_once __DIR__.'\..\Model\Application.php';


session_start();

//Retrieve the data from the model
$pt = new PersistenceTAMAS();
$am = $pt->loadApplicationManagerFromStore();
?>

<p><span class ="error">
	<?php
	if(isset($_SESSION['errorJob']) && !empty($_SESSION['errorJob'])){
		echo $_SESSION["errorJob"];
	}
	?>
</span></p>

<form action='validateJob.php' method='post'>
	<p>Job Application Form:</p>
	<p>
		Course CDN: <input type ="text" name="job_courseCDN" required/>
		Requirements: <input type ="text" name="job_requirements" required/>				<!-- TODO MAKE REQUIREMENTS INPUT TEXT LARGER -->
		Start Time: <input type= "date" name="job_start" value=" <?php echo date('Y-m-d');?>" /><br>
				Instructor Username: <input type="text" name="job_instructorUsername" required/>
Salary: <input type ="text" name="job_salary" required/>
		
		End Time: <input type= "date" name="job_end" value=" <?php echo date('Y-m-d');?>" /><br>
	</p>
	<p><input type= "submit" value="Create"/></p>
</form>

<br>

<form action='validateJob.php' method='post'>
	<p>
		Select the Job ID to delete:  
			<select name='jobspinner'>
			<?php
			foreach ($am->getJobs() as $job){
				echo "<option>" . $job->getId() . "</option>";
			}
			?>
			</select>
		<input type ='submit' value='Delete' /><input type ='submit' value='Publish' />
	</p>
</form>

</body>
</html>