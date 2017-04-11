<?php
require_once __DIR__.'\\Model\Job.php';
require_once __DIR__.'\Model\Application.php';
require_once __DIR__.'\Model\Profile.php';
require_once __DIR__.'\Model\Student.php';
require_once __DIR__.'\Model\ProfileManager.php';

require_once __DIR__.'\Model\ApplicationManager.php';
require_once __DIR__.'\Controller\ApplicationController.php';
require_once __DIR__.'\Persistence\PersistenceTAMAS.php';

	  $ac;
	  $pt;
	  $am;
	  $pm;



		$ac = new ApplicationController();
		$pt = new PersistenceTAMAS();
		$am = $pt->loadApplicationManagerFromStore();
		$pm = $pt->loadProfileManagerFromStore();

		$stu1 = new Student("TRAM", "student", "Tony", "Ramundo", "got A in all classes");
		$stu1->setDegree("DegreeUNDERGRAD");
		$stu2 = new Student("DRit", "student", "David", "Ritch", "got A+ in all classes");
		$stu1->setDegree("DegreeGRADUATE");
		$stu3 = new Student("MSalamanca", "student", "Mike", "Salamanca", "Dean's list");
		$stu3->setDegree("DegreeGRADUATE");
		$stu4 = new Student("ACapone", "student", "Al", "Capone", "got B on all classes");
		$stu4->setDegree("DegreeUNDERGRAD");

		$pm->addStudent($stu1);
		$pm->addStudent($stu2);
		$pm->addStudent($stu3);
		$pm->addStudent($stu4);

		$pt->writeProfileDataToStore($pm);

		$jobs = $am->getJobs();

		$app1 = new Application($stu1, $jobs[0]);
		$app2 = new Application($stu1, $jobs[1]);
		
		$app5 = new Application($stu2, $jobs[0]);
		$app3 = new Application($stu2, $jobs[2]);
		$app4 = new Application($stu2, $jobs[3]);
		
		$app6 = new Application($stu3, $jobs[2]);
		$app7 = new Application($stu3, $jobs[5]);
		
		$app8 = new Application($stu4, $jobs[3]);
		$app9 = new Application($stu4, $jobs[4]);
		

		$am->addApplication($app1);
		$am->addApplication($app2);
		$am->addApplication($app3);
		$am->addApplication($app4);
		$am->addApplication($app5);
		$am->addApplication($app6);
		$am->addApplication($app7);
		$am->addApplication($app8);
		$am->addApplication($app9);

		$pt->writeApplicationDataToStore($am);
	


?>