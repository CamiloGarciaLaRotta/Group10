<?php
require_once __DIR__.'\..\Model\Job.php';
require_once __DIR__.'\..\Model\ApplicationManager.php';
require_once __DIR__.'\..\Controller\ApplicationController.php';
require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';

// Due to the dependance of the notion of adding a Job with the ProfileManager and CourseManager,
// the tests through the controller for these cases will be done at Integration test level.
// To ensure the basic unitary funcitonallities related to adding, modifying and deleting a job are valid
// we will perform this actions directly from the ApplicationManager

class JobTest extends PHPUnit_Framework_TestCase {

	protected $ac;
	protected $pt;
	protected $am;

	protected function setUp(){

		$ac = new ApplicationController();
		$this->pt = new PersistenceTAMAS();
		$this->am = $this->pt->loadApplicationManagerFromStore();

		//start with a clean persistent file
		$this->am->delete();
		$this->pt->writeApplicationDataToStore($this->am);
	}

	protected function tearDown(){
		//end with a clean persistent file
		$this->am->delete();
		$this->pt->writeApplicationDataToStore($this->am);
	}
	
	// attempt to create 2 valid jobs
	public function testCreateValidJobs() {
		
		$this->assertEquals(0, $this->am->numberOfJobs());
		
		$time=20;
		$day = "Monday";
		$position = "PositionTA";
		$salary = 80.30;
		$requirements = "None";
		$course = new Course("MATH 363", 3700, 10, 20);
		$instructor = new Instructor("PGuardiola", "123", "Pep", "Guardiola");
		
		$job = new Job($time, $day, $salary, $requirements, $course, $instructor);
		
		try {
			$this->am->addJob($job);
		} catch (Exception $e){
			echo $e->getMessage();
			$this->fail();
		}
		$this->am->getJob_index(0)->setPosition($position);
		
		// validate stored data
		$this->assertCount(1, $this->am->getJobs());
		$this->assertEquals($time, $this->am->getJob_index(0)->getTimeBudget());
		$this->assertEquals($position, $this->am->getJob_index(0)->getPosition());
		$this->assertEquals($salary, $this->am->getJob_index(0)->getSalary());
		$this->assertEquals($requirements, $this->am->getJob_index(0)->getRequirements());
		$this->assertEquals($course, $this->am->getJob_index(0)->getCourse());
		$this->assertEquals($instructor, $this->am->getJob_index(0)->getInstructor());
		
		$time = 10;
		$day = "Friday";
		$position = "PositionGRADER";
		$salary = 20.00;
		$requirements = "Much Requirements";
		$course = new Course("COMP 551", 2700, 10, 20);
		$instructor = new Instructor("JMourinho", "456", "Jose", "Mourinho");
		
		$job = new Job($time, $day, $salary, $requirements, $course, $instructor);
		
		try {
			$this->am->addJob($job);
		} catch (Exception $e){
			echo $e->getMessage();
			$this->fail();
		}
		$this->am->getJob_index(1)->setPosition($position);
		
		// validate stored data
		$this->assertCount(2, $this->am->getJobs());
		$this->assertEquals($time, $this->am->getJob_index(1)->getTimeBudget());
		$this->assertEquals($position, $this->am->getJob_index(1)->getPosition());
		$this->assertEquals($salary, $this->am->getJob_index(1)->getSalary());
		$this->assertEquals($requirements, $this->am->getJob_index(1)->getRequirements());
		$this->assertEquals($course, $this->am->getJob_index(1)->getCourse());
		$this->assertEquals($instructor, $this->am->getJob_index(1)->getInstructor());
	}
	
}
?>