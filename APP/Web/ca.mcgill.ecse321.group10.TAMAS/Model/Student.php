<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

class Student extends Profile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Student Attributes
  private $experience;

  //Student State Machines
  private static $DegreeUNDERGRAD = 1;
  private static $DegreeGRADUATE = 2;
  private $degree;

  //Student Associations
  private $jobs;
  private $applications;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aUsername, $aPassword, $aFirstName, $aLastName, $aExperience)
  {
    parent::__construct($aUsername, $aPassword, $aFirstName, $aLastName);
    $this->experience = $aExperience;
    $this->jobs = array();
    $this->applications = array();
    $this->setDegree(self::$DegreeUNDERGRAD);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setExperience($aExperience)
  {
    $wasSet = false;
    $this->experience = $aExperience;
    $wasSet = true;
    return $wasSet;
  }

  public function getExperience()
  {
    return $this->experience;
  }

  public function getDegreeFullName()
  {
    $answer = $this->getDegree();
    return $answer;
  }

  public function getDegree()
  {
    if ($this->degree == self::$DegreeUNDERGRAD) { return "DegreeUNDERGRAD"; }
    elseif ($this->degree == self::$DegreeGRADUATE) { return "DegreeGRADUATE"; }
    return null;
  }

  public function setDegree($aDegree)
  {
    if ($aDegree == "DegreeUNDERGRAD" || $aDegree == self::$DegreeUNDERGRAD)
    {
      $this->degree = self::$DegreeUNDERGRAD;
      return true;
    }
    elseif ($aDegree == "DegreeGRADUATE" || $aDegree == self::$DegreeGRADUATE)
    {
      $this->degree = self::$DegreeGRADUATE;
      return true;
    }
    else
    {
      return false;
    }
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

  public static function minimumNumberOfApplications()
  {
    return 0;
  }

  public function addApplicationVia($aJobs)
  {
    return new Application($this, $aJobs);
  }

  public function addApplication($aApplication)
  {
    $wasAdded = false;
    if ($this->indexOfApplication($aApplication) !== -1) { return false; }
    $existingStudent = $aApplication->getStudent();
    $isNewStudent = $existingStudent != null && $this !== $existingStudent;
    if ($isNewStudent)
    {
      $aApplication->setStudent($this);
    }
    else
    {
      $this->applications[] = $aApplication;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeApplication($aApplication)
  {
    $wasRemoved = false;
    //Unable to remove aApplication, as it must always have a student
    if ($this !== $aApplication->getStudent())
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

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->jobs = array();
    while (count($this->applications) > 0)
    {
      $aApplication = $this->applications[count($this->applications) - 1];
      $aApplication->delete();
      unset($this->applications[$this->indexOfApplication($aApplication)]);
      $this->applications = array_values($this->applications);
    }
    
    parent::delete();
  }

}
?>