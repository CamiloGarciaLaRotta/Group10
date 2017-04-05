<?php
require_once __DIR__.'\..\Model\Profile.php';
require_once __DIR__.'\..\Model\ProfileManager.php';
require_once __DIR__.'\..\Controller\ProfileController.php';
require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';

class ProfileTest extends PHPUnit_Framework_TestCase {

	protected $pc;
	protected $pt;
	protected $pm;

	protected function setUp(){

		$this->pc = new ProfileController();
		$this->pt = new PersistenceTAMAS();
		$this->pm = $this->pt->loadProfileManagerFromStore();

		//start with a clean persistent file
		$this->pm->delete();
		$this->pt->writeProfileDataToStore($this->pm);

	}

	protected function tearDown(){
		//end with a clean persistent file
		$this->pm->delete();
		$this->pt->writeProfileDataToStore($this->pm);
	}
	
	// attempt to create two different instructors
	public function testCreateInstructors() {
		
		$this->assertEquals(0, $this->pm->numberOfInstructors());
		
		$firstName = "Diego";
		$lastName = "Costa";
		$username = "DCosta";
		$password = "passw0rd";
		$CDN = array ([123]);
		try {
			$this->pc->createInstructor($username, $password, $firstName, $lastName, $CDN);
		} catch (Exception $e) {
			echo $e->getMessage();
			$this->fail();
		}
		
		// validate stored data
		$this->pm = $this->pt->loadProfileManagerFromStore();
		//$this->assertEquals(1, $this->pm->numberOfInstructors());
		$this->assertCount(1, $this->pm->getInstructors());
		$this->assertEquals("Diego", $this->pm->getInstructor_index(0)->getFirstName());
		$this->assertEquals("Costa", $this->pm->getInstructor_index(0)->getLastName());
		$this->assertEquals("DCosta", $this->pm->getInstructor_index(0)->getUsername());
		$this->assertEquals("passw0rd", $this->pm->getInstructor_index(0)->getPassword());

		$firstName = "Didier";
		$lastName = "Drogba";
		$username = "DDrogba";
		$password = "1234";
		try {
			$this->pc->createInstructor($username, $password, $firstName, $lastName,$CDN);
		} catch (Exception $e) {
			echo $e->getMessage();
			$this->fail();
		}
		
		// validate stored data
		$this->pm = $this->pt->loadProfileManagerFromStore();
		$this->assertEquals(2, $this->pm->numberOfInstructors());
		$this->assertEquals("Didier", $this->pm->getInstructor_index(1)->getFirstName());
		$this->assertEquals("Drogba", $this->pm->getInstructor_index(1)->getLastName());
		$this->assertEquals("DDrogba", $this->pm->getInstructor_index(1)->getUsername());
		$this->assertEquals("1234", $this->pm->getInstructor_index(1)->getPassword());
		
		// validate that different IDs where given
		$this->assertTrue($this->pm->getInstructor_index(0)->getId() != $this->pm->getInstructor_index(1)->getId());
	}
	
	// attempt to create instructor with various invalid parameters
	public function testCreateInvalidInstructor() {
	
		$this->assertEquals(0, $this->pm->numberOfInstructors());
	
		$firstName = "  ";
		$lastName = "Costa";
		$username = "DCosta";
		$password = "passw0rd";
		$CDN_C = null;
		
		$error = "";
	
		try {
			$this->pc->createInstructor($username, $password, $firstName, $lastName,$CDN_C);
		} catch (Exception $e) {
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "First name name cannot be empty!<br><br>");
		
		$firstName = "Diego";
		$lastName = "  ";
		
		try {
			$this->pc->createInstructor($username, $password, $firstName, $lastName,$CDN_C);
		} catch (Exception $e) {
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "Last name name cannot be empty!<br><br>");
		
		$lastName = "Costa";
		$username = "   ";
		
		try {
			$this->pc->createInstructor($username, $password, $firstName, $lastName,$CDN_C);
		} catch (Exception $e) {
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "Username name cannot be empty!<br><br>");
		
		$username = "DCosta";
		$password = "   ";
		
		try {
			$this->pc->createInstructor($username, $password, $firstName, $lastName,$CDN_C);
		} catch (Exception $e) {
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "Password name cannot be empty!<br><br>");
	}
	
	public function testRepeatedUsername() {
		
		$this->assertCount(0, $this->pm->getInstructors());
		
		$firstName = "Diego";
		$lastName = "Costa";
		$username = "DCosta";
		$password = "passw0rd";
		$CDN_D = null;
		
		$this->pc->createInstructor($username, $password, $firstName, $lastName, $CDN_D);
		
		$firstName = "Didier";
		$lastName = "Costa";
		$username = "DCosta";
		$password = "1234";
		
		
		$error = "";
		
		try {
			$this->pc->createInstructor($username, $password, $firstName, $lastName,$CDN_D);
		} catch (Exception $e) {
			$error = $e->getMessage();
		}
		$this->assertEquals($error, "Username must be unique!<br><br>");
		
	}
}
