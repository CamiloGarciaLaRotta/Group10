<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Job
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Job Attributes
  private $startTime;
  private $endTime;
  private $day;
  private $salary;
  private $requirements;

  //Autounique Attributes
  private $id;

  //Job State Machines
  private static $PositionTA = 1;
  private static $PositionGRADER = 2;
  private $position;

  //Job Associations
  private $course;
  private $instructor;
  private $students;
  private $applications;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aStartTime, $aEndTime, $aDay, $aSalary, $aRequirements, $aCourse, $aInstructor)
  {
    $this->startTime = $aStartTime;
    $this->endTime = $aEndTime;
    $this->day = $aDay;
    $this->salary = $aSalary;
    $this->requirements = $aRequirements;
    $this->id = self::$nextId++;
    $didAddCourse = $this->setCourse($aCourse);
    if (!$didAddCourse)
    {
      throw new Exception("Unable to create job due to course");
    }
    $didAddInstructor = $this->setInstructor($aInstructor);
    if (!$didAddInstructor)
    {
      throw new Exception("Unable to create job due to instructor");
    }
    $this->students = array();
    $this->applications = array();
    $this->setPosition(self::$PositionTA);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setStartTime($aStartTime)
  {
    $wasSet = false;
    $this->startTime = $aStartTime;
    $wasSet = true;
    return $wasSet;
  }

  public function setEndTime($aEndTime)
  {
    $wasSet = false;
    $this->endTime = $aEndTime;
    $wasSet = true;
    return $wasSet;
  }

  public function setDay($aDay)
  {
    $wasSet = false;
    $this->day = $aDay;
    $wasSet = true;
    return $wasSet;
  }

  public function getStartTime()
  {
    return $this->startTime;
  }

  public function getEndTime()
  {
    return $this->endTime;
  }

  public function getDay()
  {
    return $this->day;
  }

  public function getSalary()
  {
    return $this->salary;
  }

  public function getRequirements()
  {
    return $this->requirements;
  }

  public function getId()
  {
    return $this->id;
  }

  public function getPositionFullName()
  {
    $answer = $this->getPosition();
    return $answer;
  }

  public function getPosition()
  {
    if ($this->position == self::$PositionTA) { return "PositionTA"; }
    elseif ($this->position == self::$PositionGRADER) { return "PositionGRADER"; }
    return null;
  }

  public function setPosition($aPosition)
  {
    if ($aPosition == "PositionTA" || $aPosition == self::$PositionTA)
    {
      $this->position = self::$PositionTA;
      return true;
    }
    elseif ($aPosition == "PositionGRADER" || $aPosition == self::$PositionGRADER)
    {
      $this->position = self::$PositionGRADER;
      return true;
    }
    else
    {
      return false;
    }
  }

  public function getCourse()
  {
    return $this->course;
  }

  public function getInstructor()
  {
    return $this->instructor;
  }

  public function getStudent_index($index)
  {
    $aStudent = $this->students[$index];
    return $aStudent;
  }

  public function getStudents()
  {
    $newStudents = $this->students;
    return $newStudents;
  }

  public function numberOfStudents()
  {
    $number = count($this->students);
    return $number;
  }

  public function hasStudents()
  {
    $has = $this->numberOfStudents() > 0;
    return $has;
  }

  public function indexOfStudent($aStudent)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->students as $student)
    {
      if ($student->equals($aStudent))
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

  public function setCourse($aCourse)
  {
    $wasSet = false;
    if ($aCourse == null)
    {
      return $wasSet;
    }
    
    $existingCourse = $this->course;
    $this->course = $aCourse;
    if ($existingCourse != null && $existingCourse != $aCourse)
    {
      $existingCourse->removeJob($this);
    }
    $this->course->addJob($this);
    $wasSet = true;
    return $wasSet;
  }

  public function setInstructor($aInstructor)
  {
    $wasSet = false;
    if ($aInstructor == null)
    {
      return $wasSet;
    }
    
    $existingInstructor = $this->instructor;
    $this->instructor = $aInstructor;
    if ($existingInstructor != null && $existingInstructor != $aInstructor)
    {
      $existingInstructor->removeJob($this);
    }
    $this->instructor->addJob($this);
    $wasSet = true;
    return $wasSet;
  }

  public static function minimumNumberOfStudents()
  {
    return 0;
  }

  public function addStudent($aStudent)
  {
    $wasAdded = false;
    if ($this->indexOfStudent($aStudent) !== -1) { return false; }
    if ($this->indexOfStudent($aStudent) !== -1) { return false; }
    if ($this->indexOfStudent($aStudent) !== -1) { return false; }
    $this->students[] = $aStudent;
    if ($aStudent->indexOfJob($this) != -1)
    {
      $wasAdded = true;
    }
    else
    {
      $wasAdded = $aStudent->addJob($this);
      if (!$wasAdded)
      {
        array_pop($this->students);
      }
    }
    return $wasAdded;
  }

  public function removeStudent($aStudent)
  {
    $wasRemoved = false;
    if ($this->indexOfStudent($aStudent) == -1)
    {
      return $wasRemoved;
    }

    $oldIndex = $this->indexOfStudent($aStudent);
    unset($this->students[$oldIndex]);
    if ($aStudent->indexOfJob($this) == -1)
    {
      $wasRemoved = true;
    }
    else
    {
      $wasRemoved = $aStudent->removeJob($this);
      if (!$wasRemoved)
      {
        $this->students[$oldIndex] = $aStudent;
        ksort($this->students);
      }
    }
    $this->students = array_values($this->students);
    return $wasRemoved;
  }

  public function addStudentAt($aStudent, $index)
  {  
    $wasAdded = false;
    if($this->addStudent($aStudent))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfStudents()) { $index = $this->numberOfStudents() - 1; }
      array_splice($this->students, $this->indexOfStudent($aStudent), 1);
      array_splice($this->students, $index, 0, array($aStudent));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveStudentAt($aStudent, $index)
  {
    $wasAdded = false;
    if($this->indexOfStudent($aStudent) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfStudents()) { $index = $this->numberOfStudents() - 1; }
      array_splice($this->students, $this->indexOfStudent($aStudent), 1);
      array_splice($this->students, $index, 0, array($aStudent));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addStudentAt($aStudent, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfApplications()
  {
    return 0;
  }

  public function addApplicationVia($aStudent)
  {
    return new Application($aStudent, $this);
  }

  public function addApplication($aApplication)
  {
    $wasAdded = false;
    if ($this->indexOfApplication($aApplication) !== -1) { return false; }
    if ($this->indexOfApplication($aApplication) !== -1) { return false; }
    if ($this->indexOfApplication($aApplication) !== -1) { return false; }
    $existingJobs = $aApplication->getJobs();
    $isNewJobs = $existingJobs != null && $this !== $existingJobs;
    if ($isNewJobs)
    {
      $aApplication->setJobs($this);
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
    //Unable to remove aApplication, as it must always have a jobs
    if ($this !== $aApplication->getJobs())
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
    $placeholderCourse = $this->course;
    $this->course = null;
    $placeholderCourse->removeJob($this);
    $placeholderInstructor = $this->instructor;
    $this->instructor = null;
    $placeholderInstructor->removeJob($this);
    $copyOfStudents = $this->students;
    $this->students = array();
    foreach ($copyOfStudents as $aStudent)
    {
      $aStudent->removeJob($this);
    }
    while (count($this->applications) > 0)
    {
      $aApplication = $this->applications[count($this->applications) - 1];
      $aApplication->delete();
      unset($this->applications[$this->indexOfApplication($aApplication)]);
      $this->applications = array_values($this->applications);
    }
    
  }

}
?>