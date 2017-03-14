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
  private $jobs;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct()
  {
    $this->applications = array();
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
    $this->jobs = array();
  }

}
?>