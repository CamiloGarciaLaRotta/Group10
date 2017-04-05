<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

class Tutorial extends Job
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aStartTime, $aEndTime, $aDay, $aSalary, $aRequirements, $aCourse, $aInstructor)
  {
    parent::__construct($aStartTime, $aEndTime, $aDay, $aSalary, $aRequirements, $aCourse, $aInstructor);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    parent::delete();
  }

}
?>