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
		$graderTimeBudget = 40;
		$TATimeBudget = 60;
		
		$this->cc->createCourse($courseName,$CDN,$graderTimeBudget,$TATimeBudget);
		
		//make a valid instructor
		$uName = "TRam";
		$password = "123";
		$firstName = "Tony";
		$lastName = "Ramundo";
		$cdns=array([5450]);
		
		
		$this->pc->createInstructor($uName,$password,$firstName,$lastName,$cdns);
		$this->pt->loadProfileManagerFromStore();
		
		//make a job with application controller
		$this->ac->createJob("8:00", "18:00","Wednesday","PositionGRADER", 100, "None", 5450, $this->pm->getInstructor_index(0));
		
		
		
		// validate stored data
		$this->assertCount(1, $this->am->getJobs());
		$this->assertEquals("8:00", $this->am->getJob_index(0)->getStartTime());
		$this->assertEquals("18:00", $this->am->getJob_index(0)->getEndTime());
		$this->assertEquals("PositionGRADER", $this->am->getJob_index(0)->getPosition());
		$this->assertEquals(100, $this->am->getJob_index(0)->getSalary());
		$this->assertEquals("None", $this->am->getJob_index(0)->getRequirements());
		//$this->assertEquals($this->validCourse, $this->am->getJob_index(0)->getCourse());
		//$this->assertEquals($this->validInstructor, $this->am->getJob_index(0)->getInstructor());
	}

	//create a job without a course
	public function testCreateJobNoCourse(){
		$error="";
		//make a valid instructor
		$this->pc->createInstructor("TRam","123", "Tony", "Ramundo",null);
		
		//make the job, catch the error that should be thrown
		try{
			$this->ac->createJob("8:00", "18:00","Wednesday","PositionGRADER", 100, "None",null, $this->pm->getInstructor_index(0));
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
			$this->ac->createJob("8:00", "18:00","Wednesday","PositionGRADER", 100, "None",-1, $this->pm->getInstructor_index(0));
		} catch(Exception $e){
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "CDN must be a positive Integer!<br><br>");
		
	}
	
	
	
	//create a job without an instructor
	public function testCreateJobNoInstructor(){
		$error="";
		//make a valid instructor
		$this->pc->createInstructor("TRam","123", "Tony", "Ramundo",null);
		
		//make the job, catch the error that should be thrown
		try{
			$this->ac->createJob("8:00", "18:00","Wednesday","PositionGRADER", 100, "None",-1, $this->pm->getInstructor_index(0));
		} catch(Exception $e){
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "CDN must be a positive Integer!<br><br>");
		
		
	}
		
	
	//create a job with invalid start time
	public function testCreateJobInvalidStartTime(){
		$error="";
		//make a valid instructor
		$this->pc->createInstructor("TRam","123", "Tony", "Ramundo",null);
		
		
		//make the job, catch the error that should be thrown
		try{
			$this->ac->createJob("8:00", "18:00","Wednesday","PositionGRADER", 100, "None",, $this->pm->getInstructor_index(0));
		} catch(Exception $e){
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "The time budget must be a positive integer!<br><br>");
		
		
	}
/*	//create a job with invalid end time
	public function testCreateJobInvalidEndTime(){
		
		//make a valid instructor using the controller
		
		//make a valid course using the controller
		
		//make the job, catch the error that should be thrown
		
		
	}
	
	//create a job with start time after end time
	public function testCreateJobStartAfterEnd(){
		
		//make a valid instructor using the controller
		
		//make a valid course using the controller
		
		//make the job, catch the error that should be thrown
		
		
	}
	
	//create a job with non integer or negative salary
	public function testCreateJobInvalidSalary(){
		
		//make a valid instructor using the controller
		
		//make a valid course using the controller
		
		//make the job, catch the error that should be thrown
		
		
	}
	
	//create a job with no salary
	public function testCreateJobNoSalary(){
		
		//make a valid instructor using the controller
		
		//make a valid course using the controller
		
		//make the job, catch the error that should be thrown
		
		
	}
	
	//create a job with no position listed
	public function testCreateJobNoPosition(){

		//make a valid instructor using the controller
		
		//make a valid course using the controller
		
		//make the job, catch the error that should be thrown
		
	}
	
	//create a job with an invalid position
	public function testCreateJobInvalidPosition(){
		
		//make a valid instructor using the controller
		
		//make a valid course using the controller
		
		//make the job, catch the error that should be thrown
		
	}
	
**/
}