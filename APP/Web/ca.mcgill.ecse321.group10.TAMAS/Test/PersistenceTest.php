// <?php
require_once __DIR__.'\..\Persis\PersistenceTAMAS.php';

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

		$ac = new ApplicationController();
		$cc = new CourseController();
		$pc = new ProfileController();

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
}
?>