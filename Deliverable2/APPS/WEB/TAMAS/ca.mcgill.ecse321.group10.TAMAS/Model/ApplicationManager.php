<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class ApplicationManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ApplicationManager Associations
  private $applications;
  private $profileManager;
  private $jobs;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct()
  {
    $this->applications = array();
    $this->profileManager = array();
    $this->jobs = array();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function getApplication_index($index)
  {
    $aApplication = $this->applications[$index];
    return $aApplication;
  }

  public function getApplications()
  {
    $newApplications = $this->applications;
    return $newApplications;
  }

  public function numberOfApplications()
  {
    $number = count($this->applications);
    return $number;
  }

  public function hasApplications()
  {
    $has = $this->numberOfApplications() > 0;
    return $has;
  }

  public function indexOfApplication($aApplication)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->applications as $application)
    {
      if ($application->equals($aApplication))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getProfileManager_index($index)
  {
    $aProfileManager = $this->profileManager[$index];
    return $aProfileManager;
  }

  public function getProfileManager()
  {
    $newProfileManager = $this->profileManager;
    return $newProfileManager;
  }

  public function numberOfProfileManager()
  {
    $number = count($this->profileManager);
    return $number;
  }

  public function hasProfileManager()
  {
    $has = $this->numberOfProfileManager() > 0;
    return $has;
  }

  public function indexOfProfileManager($aProfileManager)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->profileManager as $profileManager)
    {
      if ($profileManager->equals($aProfileManager))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getJob_index($index)
  {
    $aJob = $this->jobs[$index];
    return $aJob;
  }

  public function getJobs()
  {
    $newJobs = $this->jobs;
    return $newJobs;
  }

  public function numberOfJobs()
  {
    $number = count($this->jobs);
    return $number;
  }

  public function hasJobs()
  {
    $has = $this->numberOfJobs() > 0;
    return $has;
  }

  public function indexOfJob($aJob)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->jobs as $job)
    {
      if ($job->equals($aJob))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public static function minimumNumberOfApplications()
  {
    return 0;
  }

  public function addApplication($aApplication)
  {
    $wasAdded = false;
    if ($this->indexOfApplication($aApplication) !== -1) { return false; }
    $this->applications[] = $aApplication;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeApplication($aApplication)
  {
    $wasRemoved = false;
    if ($this->indexOfApplication($aApplication) != -1)
    {
      unset($this->applications[$this->indexOfApplication($aApplication)]);
      $this->applications = array_values($this->applications);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addApplicationAt($aApplication, $index)
  {  
    $wasAdded = false;
    if($this->addApplication($aApplication))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfApplications()) { $index = $this->numberOfApplications() - 1; }
      array_splice($this->applications, $this->indexOfApplication($aApplication), 1);
      array_splice($this->applications, $index, 0, array($aApplication));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveApplicationAt($aApplication, $index)
  {
    $wasAdded = false;
    if($this->indexOfApplication($aApplication) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfApplications()) { $index = $this->numberOfApplications() - 1; }
      array_splice($this->applications, $this->indexOfApplication($aApplication), 1);
      array_splice($this->applications, $index, 0, array($aApplication));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addApplicationAt($aApplication, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfProfileManager()
  {
    return 0;
  }

  public function addProfileManager($aProfileManager)
  {
    $wasAdded = false;
    if ($this->indexOfProfileManager($aProfileManager) !== -1) { return false; }
    $this->profileManager[] = $aProfileManager;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeProfileManager($aProfileManager)
  {
    $wasRemoved = false;
    if ($this->indexOfProfileManager($aProfileManager) != -1)
    {
      unset($this->profileManager[$this->indexOfProfileManager($aProfileManager)]);
      $this->profileManager = array_values($this->profileManager);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addProfileManagerAt($aProfileManager, $index)
  {  
    $wasAdded = false;
    if($this->addProfileManager($aProfileManager))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfProfileManager()) { $index = $this->numberOfProfileManager() - 1; }
      array_splice($this->profileManager, $this->indexOfProfileManager($aProfileManager), 1);
      array_splice($this->profileManager, $index, 0, array($aProfileManager));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveProfileManagerAt($aProfileManager, $index)
  {
    $wasAdded = false;
    if($this->indexOfProfileManager($aProfileManager) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfProfileManager()) { $index = $this->numberOfProfileManager() - 1; }
      array_splice($this->profileManager, $this->indexOfProfileManager($aProfileManager), 1);
      array_splice($this->profileManager, $index, 0, array($aProfileManager));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addProfileManagerAt($aProfileManager, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfJobs()
  {
    return 0;
  }

  public function addJob($aJob)
  {
    $wasAdded = false;
    if ($this->indexOfJob($aJob) !== -1) { return false; }
    $this->jobs[] = $aJob;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeJob($aJob)
  {
    $wasRemoved = false;
    if ($this->indexOfJob($aJob) != -1)
    {
      unset($this->jobs[$this->indexOfJob($aJob)]);
      $this->jobs = array_values($this->jobs);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addJobAt($aJob, $index)
  {  
    $wasAdded = false;
    if($this->addJob($aJob))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfJobs()) { $index = $this->numberOfJobs() - 1; }
      array_splice($this->jobs, $this->indexOfJob($aJob), 1);
      array_splice($this->jobs, $index, 0, array($aJob));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveJobAt($aJob, $index)
  {
    $wasAdded = false;
    if($this->indexOfJob($aJob) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfJobs()) { $index = $this->numberOfJobs() - 1; }
      array_splice($this->jobs, $this->indexOfJob($aJob), 1);
      array_splice($this->jobs, $index, 0, array($aJob));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addJobAt($aJob, $index);
    }
    return $wasAdded;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->applications = array();
    $this->profileManager = array();
    $this->jobs = array();
  }

}
?>