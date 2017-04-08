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

		$stu1 = new Student("camilo", "camilo", "ca", "milo", "got A- on all classes");
		$stu1->setDegree("DegreeUNDERGRAD");
		$stu2 = new Student("eduardo", "eduardo", "ed", "uardo", "got A+ on all classes");
		$stu1->setDegree("DegreeGRADUATE");
		$stu3 = new Student("mike", "mike", "mi", "ke", "Honour student since 1993");
		$stu3->setDegree("DegreeGRADUATE");

		$pm->addStudent($stu1);
		$pm->addStudent($stu2);
		$pm->addStudent($stu3);

		$pt->writeProfileDataToStore($pm);

		$jobs = $am->getJobs();

		$app1 = new Application($stu1, $jobs[0]);
		$app2 = new Application($stu1, $jobs[1]);
		$app3 = new Application($stu1, $jobs[2]);
		$app4 = new Application($stu1, $jobs[3]);
		$app5 = new Application($stu2, $jobs[0]);
		$app6 = new Application($stu2, $jobs[2]);
		$app7 = new Application($stu3, $jobs[3]);

		$am->addApplication($app1);
		$am->addApplication($app2);
		$am->addApplication($app3);
		$am->addApplication($app4);
		$am->addApplication($app5);
		$am->addApplication($app6);
		$am->addApplication($app7);

		$pt->writeApplicationDataToStore($am);
	


?>