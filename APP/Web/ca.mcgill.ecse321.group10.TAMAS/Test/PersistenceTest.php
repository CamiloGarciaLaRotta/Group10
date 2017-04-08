// <?php
require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';
require_once __DIR__.'\..\Model\Course.php';
require_once __DIR__.'\..\Model\CourseManager.php';
require_once __DIR__.'\..\Model\Application.php';
require_once __DIR__.'\..\Model\ApplicationManager.php';
require_once __DIR__.'\..\Model\Profile.php';
require_once __DIR__.'\..\Model\ProfileManager.php';


// Note that based on RegistrationManager's Persistence test, 
// we will modify the model directly (i.e. not through the controllers)

class CourseTest extends PHPUnit_Framework_TestCase {

	// controllers
	protected $ac;
	protected $cc;
	protected $pc;

	// persistence
	protected $pt;

	// managers
	protected $am;
	protected $cm;
	protected $pm;

	protected function setUp(){

		$this->pt = new PersistenceTAMAS();

		$this->am = $this->pt->loadApplicationManagerFromStore();
		$this->cm = $this->pt->loadCourseManagerFromStore();
		$this->pm = $this->pt->loadProfileManagerFromStore();

		//start with a clean persistent file
		$this->am->delete();
		$this->cm->delete();
		$this->pm->delete();

		$this->pt->writeApplicationDataToStore($this->am);
		$this->pt->writeCourseDataToStore($this->cm);
		$this->pt->writeProfileDataToStore($this->pm);
	}

	protected function tearDown(){

	}
	
	public function testApplicationPersistence(){
		//TODO after JobTest.php is done
	}
	
	public function testCoursePersistence(){
		
		$this->assertCount(0, $this->cm->getCourses());
		
		// create dummy course
		$className = "MATH 363";
		$CDN = 1200;
		$graderTime = 50;
		$TATime = 60;
		
		$course = new Course($className, $CDN, $graderTime, $TATime,0);
		$this->cm->addCourse($course);
		
		// store data
		$this->pt->writeCourseDataToStore($this->cm);
		
		$this->cm->delete();
		$this->assertEquals(0, $this->cm->numberOfCourses());
		
		// reload data from persistence and compare
		$this->cm = $this->pt->loadCourseManagerFromStore();
		
		$this->assertEquals(1, $this->cm->numberOfCourses());
		$this->assertEquals($className, $this->cm->getCourse_index(0)->getClassName());
		$this->assertEquals($CDN, $this->cm->getCourse_index(0)->getCdn());
		$this->assertEquals($graderTime, $this->cm->getCourse_index(0)->getGraderBudget());
		$this->assertEquals($TATime, $this->cm->getCourse_index(0)->getTaBudget());
	}
	
	public function testProfilePersistence(){
		
		$this->assertEquals(0, $this->pm->numberOfInstructors());
		
		// create dummy Instructor
		$firstName = "Diego";
		$lastName = "Costa";
		$username = "DCosta";
		$password = "passw0rd";
		
		$instructor = new Profile($username, $password, $firstName, $lastName);
		$this->pm->addInstructor($instructor);
				
		// store data
		$this->pt->writeProfileDataToStore($this->pm);
		
		// reload data from persistence and compare
		$this->pm = $this->pt->loadProfileManagerFromStore();
		$this->assertCount(1, $this->pm->getInstructors());
		$this->assertEquals("Diego", $this->pm->getInstructor_index(0)->getFirstName());
		$this->assertEquals("Costa", $this->pm->getInstructor_index(0)->getLastName());
		$this->assertEquals("DCosta", $this->pm->getInstructor_index(0)->getUsername());
		$this->assertEquals("passw0rd", $this->pm->getInstructor_index(0)->getPassword());
	}
}
?>