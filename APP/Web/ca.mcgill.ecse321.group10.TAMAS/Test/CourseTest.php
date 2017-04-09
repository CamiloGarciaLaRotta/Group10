<?php
require_once __DIR__.'\..\Model\Course.php';
require_once __DIR__.'\..\Model\CourseManager.php';
require_once __DIR__.'\..\Controller\CourseController.php';
require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';

class CourseTest extends PHPUnit_Framework_TestCase {
	
	protected $cc;
	protected $pt;
	protected $cm;
	
	protected function setUp(){
		
		$this->cc = new CourseController();
		$this->pt = new PersistenceTAMAS();
		$this->cm = $this->pt->loadCourseManagerFromStore();
		
		//start with a clean persistent file
		$this->cm->delete();
		$this->pt->writeCourseDataToStore($this->cm);
	}
	
	protected function tearDown(){
		//end with a clean persistent file
		$this->cm->delete();
		$this->pt->writeCourseDataToStore($this->cm);
	}
	
	// attempt to create 2 different courses
	public function testCreateCourses() {

		$this->assertCount(0, $this->cm->getCourses());
		
		$className = "MATH 363";
		$CDN = 1200;
		$graderTime = 50;
		$TATime = 50;
		$labTime = 64;
		
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime, $labTime);
		} catch(Exception $e) {
			echo $e->getMessage();
			$this->fail();
		}
		
		// validate stored data
		$this->cm = $this->pt->loadCourseManagerFromStore();
		$this->assertEquals(1, $this->cm->numberOfCourses());
		$this->assertEquals($className, $this->cm->getCourse_index(0)->getClassName());
		$this->assertEquals($CDN, $this->cm->getCourse_index(0)->getCdn());
		$this->assertEquals($graderTime, $this->cm->getCourse_index(0)->getGraderBudget());
		$this->assertEquals($TATime, $this->cm->getCourse_index(0)->getTutorialBudget());
		$this->assertEquals($labTime, $this->cm->getCourse_index(0)->getLabBudget());
		
		$className = "ECSE 321";
		$CDN = 800;
		$graderTime = 60;
		$TATime = 70;
		$labTime = 50;
		
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime, $labTime);
		} catch(Exception $e) {
			echo $e->getMessage();
			$this->fail();
		}
		
		// validate stored data
		$this->cm = $this->pt->loadCourseManagerFromStore();
		$this->assertEquals(2, $this->cm->numberOfCourses());
		$this->assertEquals($className, $this->cm->getCourse_index(1)->getClassName());
		$this->assertEquals($CDN, $this->cm->getCourse_index(1)->getCdn());
		$this->assertEquals($graderTime, $this->cm->getCourse_index(1)->getGraderBudget());
		$this->assertEquals($TATime, $this->cm->getCourse_index(1)->getTutorialBudget());
		$this->assertEquals($labTime, $this->cm->getCourse_index(1)->getLabBudget());
		
	}
	
	// attempt to create a course with various forms of invalid attributes
	public function testCreateInvalidCourse() {

		$this->assertEquals(0, $this->cm->numberOfCourses());
	
		$className = "   ";
		$CDN = 999;
		$graderTime = 99;
		$TATime = 49;
		$labTime = 50;
		$error = "";
	
		// validate empty course name
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime,$labTime);
		} catch(Exception $e) {
			$error =  $e->getMessage();
		}
		$this->assertEquals("Course name cannot be empty!<br><br>", $error);
	
	
		// validate non numerical CDN
		$className = "Test 101";
		$CDN = "abc";
	
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime,$labTime);
		} catch(Exception $e) {
			$error =  $e->getMessage();
		}
		$this->assertEquals("CDN must be a non null Integer!<br><br>", $error);
	
		// validate negative grader time
		$CDN = 1234;
		$graderTime = -99;
	
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime,$labTime);
		} catch(Exception $e) {
			$error =  $e->getMessage();
		}
		$this->assertEquals("Time budget must be a positive Integer!<br><br>Time budget must be at least 45 hours/semester!<br><br>", $error);
	
		// validate negative tutorial time
		$graderTime = 99;
		$TATime = -9;
	
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime,$labTime);
		} catch(Exception $e) {
			$error =  $e->getMessage();
		}
		$this->assertEquals("Time budget must be a positive Integer!<br><br>Time budget must be at least 45 hours/semester!<br><br>", $error);
		
		// validate negative lab time
		$TaTime = 99;
		$labTime = -9;
		
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime,$labTime);
		} catch(Exception $e) {
			$error =  $e->getMessage();
		}
		$this->assertEquals("Time budget must be a positive Integer!<br><br>Time budget must be at least 45 hours/semester!<br><br>", $error);
	}
	
	// attempt to delete an existent course
	public function testDeleteValidCourse() {
		
		$this->assertCount(0, $this->cm->getCourses());
	
		$className = "MATH 363";
		$CDN = 1200;
		$graderTime = 90;
		$TATime = 100;
		$labTime = 49;
	
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime,$labTime);
			$this->cc->deleteCourse($CDN);
		} catch(Exception $e) {
			echo $e->getMessage();
			$this->fail();
		}
	
		// validate stored data
		$this->assertEquals(0, $this->cm->numberOfCourses());
	}
	
	// attempt to delete a non existent course
	public function testDeleteInvalidCourse() {
	
		$this->assertCount(0, $this->cm->getCourses());
		
		$error = "";
	
		try {
			$this->cc->deleteCourse(123);
		} catch(Exception $e) {
			$error =  $e->getMessage();
		}
	
		// validate stored data
		$this->assertEquals($error, "No course found with CDN 123<br><br>");
	}
	
	public function testRepeatedCDN() {
		
		$this->assertCount(0, $this->cm->getCourses());
		
		$className = "MATH 363";
		$CDN = 999;
		$graderTime = 99;
		$TATime = 69;
		$labTime = 72;
		
		$this->cc->createCourse($className, $CDN, $graderTime, $TATime,$labTime);
		
		$className = "COMP 251";
		$CDN = 999;
		$graderTime = 80;
		$TATime = 100;
		
		$error = "";
		
		// validate empty course name
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime,$labTime);
		} catch(Exception $e) {
			$error =  $e->getMessage();
		}
		$this->assertEquals("CDN must be unique!<br><br>", $error);
	}
}
?>