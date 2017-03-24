<?php
require_once __DIR__.'\..\Model\Job.php';
require_once __DIR__.'\..\Model\ApplicationManager.php';
require_once __DIR__.'\..\Controller\ApplicationController.php';
require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';

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

	}
	
	
}
?>