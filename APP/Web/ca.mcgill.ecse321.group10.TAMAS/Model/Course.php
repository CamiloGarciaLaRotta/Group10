<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

class Course
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Course Attributes
  private $className;
  private $cdn;
  private $graderBudget;
  private $tutorialBudget;
  private $labBudget;

  //Course Associations
  private $jobs;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aClassName, $aCdn, $aGraderBudget, $aTutorialBudget, $aLabBudget)
  {
    $this->className = $aClassName;
    $this->cdn = $aCdn;
    $this->graderBudget = $aGraderBudget;
    $this->tutorialBudget = $aTutorialBudget;
    $this->labBudget = $aLabBudget;
    $this->jobs = array();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setClassName($aClassName)
  {
    $wasSet = false;
    $this->className = $aClassName;
    $wasSet = true;
    return $wasSet;
  }

  public function setGraderBudget($aGraderBudget)
  {
    $wasSet = false;
    $this->graderBudget = $aGraderBudget;
    $wasSet = true;
    return $wasSet;
  }

  public function setTutorialBudget($aTutorialBudget)
  {
    $wasSet = false;
    $this->tutorialBudget = $aTutorialBudget;
    $wasSet = true;
    return $wasSet;
  }

  public function setLabBudget($aLabBudget)
  {
    $wasSet = false;
    $this->labBudget = $aLabBudget;
    $wasSet = true;
    return $wasSet;
  }

  public function getClassName()
  {
    return $this->className;
  }

  public function getCdn()
  {
    return $this->cdn;
  }

  public function getGraderBudget()
  {
    return $this->graderBudget;
  }

  public function getTutorialBudget()
  {
    return $this->tutorialBudget;
  }

  public function getLabBudget()
  {
    return $this->labBudget;
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

  public static function minimumNumberOfJobs()
  {
    return 0;
  }

  public function addJobVia($aHours, $aDay, $aSalary, $aRequirements, $aInstructor)
  {
    return new Job($aHours, $aDay, $aSalary, $aRequirements, $this, $aInstructor);
  }

  public function addJob($aJob)
  {
    $wasAdded = false;
    if ($this->indexOfJob($aJob) !== -1) { return false; }
    $existingCourse = $aJob->getCourse();
    $isNewCourse = $existingCourse != null && $this !== $existingCourse;
    if ($isNewCourse)
    {
      $aJob->setCourse($this);
    }
    else
    {
      $this->jobs[] = $aJob;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeJob($aJob)
  {
    $wasRemoved = false;
    //Unable to remove aJob, as it must always have a course
    if ($this !== $aJob->getCourse())
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
    while (count($this->jobs) > 0)
    {
      $aJob = $this->jobs[count($this->jobs) - 1];
      $aJob->delete();
      unset($this->jobs[$this->indexOfJob($aJob)]);
      $this->jobs = array_values($this->jobs);
    }
    
  }

}
?>