<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Application
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Autounique Attributes
  private $id;

  //Application Associations
  private $student;
  private $jobs;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aStudent, $aJobs)
  {
    $this->id = self::$nextId++;
    $didAddStudent = $this->setStudent($aStudent);
    if (!$didAddStudent)
    {
      throw new Exception("Unable to create application due to student");
    }
    $didAddJobs = $this->setJobs($aJobs);
    if (!$didAddJobs)
    {
      throw new Exception("Unable to create application due to jobs");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function getId()
  {
    return $this->id;
  }

  public function getStudent()
  {
    return $this->student;
  }

  public function getJobs()
  {
    return $this->jobs;
  }

  public function setStudent($aStudent)
  {
    $wasSet = false;
    if ($aStudent == null)
    {
      return $wasSet;
    }
    
    $existingStudent = $this->student;
    $this->student = $aStudent;
    if ($existingStudent != null && $existingStudent != $aStudent)
    {
      $existingStudent->removeApplication($this);
    }
    $this->student->addApplication($this);
    $wasSet = true;
    return $wasSet;
  }

  public function setJobs($aJobs)
  {
    $wasSet = false;
    if ($aJobs == null)
    {
      return $wasSet;
    }
    
    $existingJobs = $this->jobs;
    $this->jobs = $aJobs;
    if ($existingJobs != null && $existingJobs != $aJobs)
    {
      $existingJobs->removeApplication($this);
    }
    $this->jobs->addApplication($this);
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $placeholderStudent = $this->student;
    $this->student = null;
    $placeholderStudent->removeApplication($this);
    $placeholderJobs = $this->jobs;
    $this->jobs = null;
    $placeholderJobs->removeApplication($this);
  }

}
?>