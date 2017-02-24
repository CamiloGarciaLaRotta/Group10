<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class ProfileManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ProfileManager Associations
  private $students;
  private $instructors;
  private $admins;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct()
  {
    $this->students = array();
    $this->instructors = array();
    $this->admins = array();
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public function getInstructor_index($index)
  {
    $aInstructor = $this->instructors[$index];
    return $aInstructor;
  }

  public function getInstructors()
  {
    $newInstructors = $this->instructors;
    return $newInstructors;
  }

  public function numberOfInstructors()
  {
    $number = count($this->instructors);
    return $number;
  }

  public function hasInstructors()
  {
    $has = $this->numberOfInstructors() > 0;
    return $has;
  }

  public function indexOfInstructor($aInstructor)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->instructors as $instructor)
    {
      if ($instructor->equals($aInstructor))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getAdmin_index($index)
  {
    $aAdmin = $this->admins[$index];
    return $aAdmin;
  }

  public function getAdmins()
  {
    $newAdmins = $this->admins;
    return $newAdmins;
  }

  public function numberOfAdmins()
  {
    $number = count($this->admins);
    return $number;
  }

  public function hasAdmins()
  {
    $has = $this->numberOfAdmins() > 0;
    return $has;
  }

  public function indexOfAdmin($aAdmin)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->admins as $admin)
    {
      if ($admin->equals($aAdmin))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
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

  public static function minimumNumberOfInstructors()
  {
    return 0;
  }

  public function addInstructor($aInstructor)
  {
    $wasAdded = false;
    if ($this->indexOfInstructor($aInstructor) !== -1) { return false; }
    $this->instructors[] = $aInstructor;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeInstructor($aInstructor)
  {
    $wasRemoved = false;
    if ($this->indexOfInstructor($aInstructor) != -1)
    {
      unset($this->instructors[$this->indexOfInstructor($aInstructor)]);
      $this->instructors = array_values($this->instructors);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addInstructorAt($aInstructor, $index)
  {  
    $wasAdded = false;
    if($this->addInstructor($aInstructor))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfInstructors()) { $index = $this->numberOfInstructors() - 1; }
      array_splice($this->instructors, $this->indexOfInstructor($aInstructor), 1);
      array_splice($this->instructors, $index, 0, array($aInstructor));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveInstructorAt($aInstructor, $index)
  {
    $wasAdded = false;
    if($this->indexOfInstructor($aInstructor) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfInstructors()) { $index = $this->numberOfInstructors() - 1; }
      array_splice($this->instructors, $this->indexOfInstructor($aInstructor), 1);
      array_splice($this->instructors, $index, 0, array($aInstructor));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addInstructorAt($aInstructor, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfAdmins()
  {
    return 0;
  }

  public function addAdmin($aAdmin)
  {
    $wasAdded = false;
    if ($this->indexOfAdmin($aAdmin) !== -1) { return false; }
    $this->admins[] = $aAdmin;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeAdmin($aAdmin)
  {
    $wasRemoved = false;
    if ($this->indexOfAdmin($aAdmin) != -1)
    {
      unset($this->admins[$this->indexOfAdmin($aAdmin)]);
      $this->admins = array_values($this->admins);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addAdminAt($aAdmin, $index)
  {  
    $wasAdded = false;
    if($this->addAdmin($aAdmin))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfAdmins()) { $index = $this->numberOfAdmins() - 1; }
      array_splice($this->admins, $this->indexOfAdmin($aAdmin), 1);
      array_splice($this->admins, $index, 0, array($aAdmin));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveAdminAt($aAdmin, $index)
  {
    $wasAdded = false;
    if($this->indexOfAdmin($aAdmin) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfAdmins()) { $index = $this->numberOfAdmins() - 1; }
      array_splice($this->admins, $this->indexOfAdmin($aAdmin), 1);
      array_splice($this->admins, $index, 0, array($aAdmin));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addAdminAt($aAdmin, $index);
    }
    return $wasAdded;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->students = array();
    $this->instructors = array();
    $this->admins = array();
  }

}
?>