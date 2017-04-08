<?php
require_once __DIR__.'\..\Model\Job.php';
require_once __DIR__.'\..\Model\Application.php';
require_once __DIR__.'\..\Model\Student.php';
require_once __DIR__.'\..\Model\ProfileManager.php';
require_once __DIR__.'\..\Model\ApplicationManager.php';
require_once __DIR__.'\..\Controller\ApplicationController.php';
require_once __DIR__.'\..\Persistence\PersistenceTAMAS.php';
require_once __DIR__.'\..\Model\Profile.php';

class JobTest extends PHPUnit_Framework_TestCase {

	protected $ac;
	protected $pt;
	protected $am;
	protected $pm;

	
	// attempt to create 2 valid jobs
	public function testCreateFakeApplications() {

		$ac = new ApplicationController();
		$this->pt = new PersistenceTAMAS();
		$this->am = $this->pt->loadApplicationManagerFromStore();
		$this->pm = $this->pt->loadProfileManagerFromStore();
		
		$stu1 = new Student("camilo", "camilo", "ca", "milo", "got A- on all classes");
		$stu2 = new Student("eduardo", "eduardo", "ed", "uardo", "got A+ on all classes");
		$stu3 = new Student("mike", "mike", "mi", "ke", "Honour student since 1993");
		
		$this->pm->addStudent($stu1);
		$this->pm->addStudent($stu2);
		$this->pm->addStudent($stu3);
		
		$this->pt->writeProfileDataToStore($this->$pm);
		
		$jobs = $this->am->getJobs();
		
		$app1 = new Application($stu1, $jobs[0]);
		$app2 = new Application($stu1, $jobs[1]);
		$app3 = new Application($stu1, $jobs[2]);
		$app4 = new Application($stu1, $jobs[3]);
		$app5 = new Application($stu2, $jobs[0]);
		$app6 = new Application($stu2, $jobs[2]);
		$app7 = new Application($stu3, $jobs[3]);
		
		$this->am->addApplication($app1);
		$this->am->addApplication($app2);
		$this->am->addApplication($app3);
		$this->am->addApplication($app4);
		$this->am->addApplication($app5);
		$this->am->addApplication($app6);
		$this->am->addApplication($app7);
		
		$this->pt->writeApplicationDataToStore($this->$am);
	}

}
?>