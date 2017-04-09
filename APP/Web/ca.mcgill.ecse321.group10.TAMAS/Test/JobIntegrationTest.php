<?php
require_once __DIR__.'\..\Model\ApplicationManager.php';
require_once __DIR__.'\..\Controller\ApplicationController.php';
require_once __DIR__.'\..\Model\CourseManager.php';
require_once __DIR__.'\..\Controller\CourseController.php';
require_once __DIR__.'\..\Model\ProfileManager.php';
require_once __DIR__.'\..\Controller\ProfileController.php';
require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';


//tests the integration of the CourseController and
//ProfileController classes with the ApplicationController class and persistence
class JobIntegrationTest extends PHPUnit_Framework_TestCase {

	protected $ac;
	protected $pt;
	protected $am;
	protected $pc;
	protected $cc;
	protected $pm;
	protected $cm;
	
	
	protected function setUp(){

		$this->ac = new ApplicationController();
		$this->pc = new ProfileController();
		$this->cc = new CourseController();
		$this->pt = new PersistenceTAMAS();
		$this->am = $this->pt->loadApplicationManagerFromStore();
		$this->pm = $this->pt->loadProfileManagerFromStore();
		$this->cm = $this->pt->loadCourseManagerFromStore();

		//start with a clean persistent file
		$this->am->delete();
		$this->cm->delete();
		$this->pm->delete();
		$this->pt->writeApplicationDataToStore($this->am);
		$this->pt->writeCourseDataToStore($this->cm);
		$this->pt->writeProfileDataToStore($this->pm);
		
	}

	protected function tearDown(){
		//end with a clean persistent file
		$this->am->delete();
		$this->cm->delete();
		$this->pm->delete();
		$this->pt->writeApplicationDataToStore($this->am);
		$this->pt->writeCourseDataToStore($this->cm);
		$this->pt->writeProfileDataToStore($this->pm);
	}
	
	//atttempt to create a job posting with appplication controller
	public function testCreateJobWithController(){
		
		//make a valid course
		$courseName = "Math 363";
		$CDN = 5450;
		$graderTimeBudget = 46;
		$TATimeBudget = 60;
		$labTimeBudget = 75;
		
		$this->cc->createCourse($courseName,$CDN,$graderTimeBudget,$TATimeBudget,$labTimeBudget);
		
		//make a valid instructor
		$uName = "TRam";
		$password = "123";
		$firstName = "Tony";
		$lastName = "Ramundo";
		$cdns=array([5450]);
		
		
		$this->pc->createInstructor($uName,$password,$firstName,$lastName,$cdns);
		$this->pt->loadProfileManagerFromStore();
		
		//make a job with application controller
		$this->ac->createJob(10,"Wednesday","PositionGRADER", 100, "None", 5450, $this->pm->getInstructor_index(0));
		
		
		$this->pt->loadApplicationManagerFromStore();
		$job =  $this->am->getJob_index(0);
		// validate stored data
		$this->assertEquals(10,$job->getTimeBudget());
		$this->assertEquals("Wednesday", $job->getDay());
		$this->assertEquals("PositionGRADER", $job->getPosition());
		$this->assertEquals(100, $job->getSalary());
		$this->assertEquals("None", $job->getRequirements());
		$this->assertEquals($this->pm->getInstructor(0),$job->getInstructor());
		
	}

	//create a job without a course
	public function testCreateJobNoCourse(){
		$error="";
		//make a valid instructor
		$this->pc->createInstructor("TRam","123", "Tony", "Ramundo",null);
		
		//make the job, catch the error that should be thrown
		try{
			$this->ac->createJob(10,"Wednesday","PositionGRADER", 100, "None",null, $this->pm->getInstructor_index(0));
		} catch(Exception $e){
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "CDN must be a non null Integer!<br><br>");
		
	}
		
	//create a job with an invalid course
	public function testCreateJobInvalidCourse(){
		$error="";
		//make a valid instructor
		$this->pc->createInstructor("TRam","123", "Tony", "Ramundo",null);
		
		//make the job, catch the error that should be thrown
		try{
			$this->ac->createJob(10,"Wednesday","PositionGRADER", 100, "None",-1, $this->pm->getInstructor_index(0));
		} catch(Exception $e){
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "CDN must be a positive Integer!<br><br>");
		
	
		//make the job, catch the error that should be thrown
		try{
			$this->ac->createJob(10,"Wednesday","PositionGRADER", 100, "None",12316544654, $this->pm->getInstructor_index(0));
		} catch(Exception $e){
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "Course was not found!<br><br>");
}
	
	
	//create a job without an instructor
	public function testCreateJobNoInstructor(){
		$error="";
		
		//create valid course
		$this->cc->createCourse("ECSE 489",489,50,50,50);
		
		//make the job, catch the error that should be thrown
		try{
			$this->ac->createJob(10,"Wednesday","PositionGRADER", 100, "None",489, $this->pm->getInstructor_index(0));
		} catch(Exception $e){
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "Instructor was not found!<br><br>");
		}
		
	
	//create a job with invalid time
	public function testCreateJobInvalidStartTime(){
		$error="";
	
		//create valid course
		$this->cc->createCourse("ECSE 489",489,60,60,60);
		
		//make a valid instructor
		$this->pc->createInstructor("TRam","123", "Tony", "Ramundo", array([489]));
		
		
		//make the job, catch the error that should be thrown
		try{
			$this->ac->createJob(-1,"Wednesday","PositionGRADER", 100, "None",null, $this->pm->getInstructor_index(0));
		} catch(Exception $e){
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "Time budget must be a positive Number!<br><br>");
		
		
		try{
			$this->ac->createJob(null,"Wednesday","PositionGRADER", 100, "None",null, $this->pm->getInstructor_index(0));
		} catch(Exception $e){
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "Time Budget must be a non null Number!<br><br>");
		
		
	}
	
	//create a job with null, non integer or negative salary
	public function testCreateJobInvalidSalary(){
		
		//create valid course
		$this->cc->createCourse("ECSE 489",489, 80,80,80);
		
		//make a valid instructor
		$this->pc->createInstructor("TRam","123", "Tony", "Ramundo", array([489]));
		
		//null salary
		try{
			$this->ac->createJob(10,"Wednesday","PositionGRADER", null, "None",489, $this->pm->getInstructor_index(0));
		} catch(Exception $e){
			$error = $e->getMessage();
		}
		$this->assertEquals($error,"Salary must be a non null Number!<br><br>");

		//non integer
		try{
			$this->ac->createJob(10,"Wednesday","PositionGRADER", "YOOOOO", "None",489, $this->pm->getInstructor_index(0));
		} catch(Exception $e){
			$error = $e->getMessage();
		}
		$this->assertEquals($error,"Salary must be a non null Number!<br><br>");
		
		//negative
		try{
			$this->ac->createJob(10,"Wednesday","PositionGRADER", -5, "None",489, $this->pm->getInstructor_index(0));
		} catch(Exception $e){
			$error = $e->getMessage();
		}
		$this->assertEquals($error,"Salary must be a positive Number!<br><br>");
		
	}	
	

}