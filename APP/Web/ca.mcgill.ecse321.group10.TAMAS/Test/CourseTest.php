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
		$graderTime = 10;
		$TATime = 20;
		
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime);
		} catch(Exception $e) {
			echo $e->getMessage();
			$this->fail();
		}
		
		// validate stored data
		$this->cm = $this->pt->loadCourseManagerFromStore();
		$this->assertEquals(1, $this->cm->numberOfCourses());
		$this->assertEquals($className, $this->cm->getCourse_index(0)->getClassName());
		$this->assertEquals($CDN, $this->cm->getCourse_index(0)->getCdn());
		$this->assertEquals($graderTime, $this->cm->getCourse_index(0)->getGraderTimeBudget());
		$this->assertEquals($TATime, $this->cm->getCourse_index(0)->getTaTimeBudget());
		
		$className = "ECSE 321";
		$CDN = 800;
		$graderTime = 5;
		$TATime = 10;
		
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime);
		} catch(Exception $e) {
			echo $e->getMessage();
			$this->fail();
		}
		
		// validate stored data
		$this->cm = $this->pt->loadCourseManagerFromStore();
		$this->assertEquals(2, $this->cm->numberOfCourses());
		$this->assertEquals($className, $this->cm->getCourse_index(1)->getClassName());
		$this->assertEquals($CDN, $this->cm->getCourse_index(1)->getCdn());
		$this->assertEquals($graderTime, $this->cm->getCourse_index(1)->getGraderTimeBudget());
		$this->assertEquals($TATime, $this->cm->getCourse_index(1)->getTaTimeBudget());
		
	}
	
	// attempt to create a course with various forms of invalid attributes
	public function testCreateInvalidCourse() {

		$this->assertEquals(0, $this->cm->numberOfCourses());
	
		$className = "   ";
		$CDN = 999;
		$graderTime = 99;
		$TATime = 9;
	
		$error = "";
	
		// validate empty course name
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime);
		} catch(Exception $e) {
			$error =  $e->getMessage();
		}
		$this->assertEquals("Course name cannot be empty!<br> ", $error);
	
	
		// validate non numerical CDN
		$className = "Test 101";
		$CDN = "abc";
	
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime);
		} catch(Exception $e) {
			$error =  $e->getMessage();
		}
		$this->assertEquals("CDN must be a non null Integer!<br> ", $error);
	
		// validate negativie time
		$CDN = 1234;
		$graderTime = -99;
	
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime);
		} catch(Exception $e) {
			$error =  $e->getMessage();
		}
		$this->assertEquals("Time budget must be a positive Integer!<br> ", $error);
	
		// validate negativie time
		$graderTime = 99;
		$TATime = -9;
	
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime);
		} catch(Exception $e) {
			$error =  $e->getMessage();
		}
		$this->assertEquals("Time budget must be a positive Integer!<br> ", $error);
	}
	
	// attempt to delete an existent course
	public function testDeleteValidCourse() {
		
		$this->assertCount(0, $this->cm->getCourses());
	
		$className = "MATH 363";
		$CDN = 1200;
		$graderTime = 10;
		$TATime = 20;
	
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime);
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
		$this->assertEquals($error, "No course found with CDN 123");
	}
	
	public function testRepeatedCDN() {
		
		$this->assertCount(0, $this->cm->getCourses());
		
		$className = "MATH 363";
		$CDN = 999;
		$graderTime = 99;
		$TATime = 9;
		
		$this->cc->createCourse($className, $CDN, $graderTime, $TATime);
		
		$className = "COMP 251";
		$CDN = 999;
		$graderTime = 10;
		$TATime = 100;
		
		$error = "";
		
		// validate empty course name
		try {
			$this->cc->createCourse($className, $CDN, $graderTime, $TATime);
		} catch(Exception $e) {
			$error =  $e->getMessage();
		}
		$this->assertEquals("CDN must be unique!<br> ", $error);
	}
}
?>