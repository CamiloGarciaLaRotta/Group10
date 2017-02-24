<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

// unsure if class should create instances of Intrsuctor/Student
// or rather have all the methods of Instructor/Student
class Admin extends Profile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Admin Associations
  private $intructors;
  private $students;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aId, $aUsername, $aPassword, $aFirstName, $aLastName)
  {
    parent::__construct($aId, $aUsername, $aPassword, $aFirstName, $aLastName);
    $this->intructors = array();
    $this->students = array();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function getIntructor_index($index)
  {
    $aIntructor = $this->intructors[$index];
    return $aIntructor;
  }

  public function getIntructors()
  {
    $newIntructors = $this->intructors;
    return $newIntructors;
  }

  public function numberOfIntructors()
  {
    $number = count($this->intructors);
    return $number;
  }

  public function hasIntructors()
  {
    $has = $this->numberOfIntructors() > 0;
    return $has;
  }

  public function indexOfIntructor($aIntructor)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->intructors as $intructor)
    {
      if ($intructor->equals($aIntructor))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
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

  public static function minimumNumberOfIntructors()
  {
    return 0;
  }

  public function addIntructor($aIntructor)
  {
    $wasAdded = false;
    if ($this->indexOfIntructor($aIntructor) !== -1) { return false; }
    $this->intructors[] = $aIntructor;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeIntructor($aIntructor)
  {
    $wasRemoved = false;
    if ($this->indexOfIntructor($aIntructor) != -1)
    {
      unset($this->intructors[$this->indexOfIntructor($aIntructor)]);
      $this->intructors = array_values($this->intructors);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addIntructorAt($aIntructor, $index)
  {  
    $wasAdded = false;
    if($this->addIntructor($aIntructor))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfIntructors()) { $index = $this->numberOfIntructors() - 1; }
      array_splice($this->intructors, $this->indexOfIntructor($aIntructor), 1);
      array_splice($this->intructors, $index, 0, array($aIntructor));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveIntructorAt($aIntructor, $index)
  {
    $wasAdded = false;
    if($this->indexOfIntructor($aIntructor) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfIntructors()) { $index = $this->numberOfIntructors() - 1; }
      array_splice($this->intructors, $this->indexOfIntructor($aIntructor), 1);
      array_splice($this->intructors, $index, 0, array($aIntructor));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addIntructorAt($aIntructor, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfStudents()
  {
    return 0;
  }

  public function addStudent($aStudent)
  {
    $wasAdded = false;
    if ($this->indexOfStudent($aStudent) !== -1) { return false; }
    $this->students[] = $aStudent;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeStudent($aStudent)
  {
    $wasRemoved = false;
    if ($this->indexOfStudent($aStudent) != -1)
    {
      unset($this->students[$this->indexOfStudent($aStudent)]);
      $this->students = array_values($this->students);
      $wasRemoved = true;
    }
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

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->intructors = array();
    $this->students = array();
    parent::delete();
  }

}
?>