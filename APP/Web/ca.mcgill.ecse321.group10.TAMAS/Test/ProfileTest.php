<?php
require_once __DIR__.'\..\Model\Profile.php';
require_once __DIR__.'\..\Model\ProfileManager.php';
require_once __DIR__.'\..\Controller\ProfileController.php';
require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';

class ProfileTest extends PHPUnit_Framework_TestCase {

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

	}
}
