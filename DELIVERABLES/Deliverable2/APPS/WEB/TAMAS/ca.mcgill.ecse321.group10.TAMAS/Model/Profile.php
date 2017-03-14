<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

class Profile
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Profile Attributes
  private $username;
  private $password;
  private $firstName;
  private $lastName;

  //Autounique Attributes
  private $id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aUsername, $aPassword, $aFirstName, $aLastName)
  {
    $this->username = $aUsername;
    $this->password = $aPassword;
    $this->firstName = $aFirstName;
    $this->lastName = $aLastName;
    $this->id = self::$nextId++;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setUsername($aUsername)
  {
    $wasSet = false;
    $this->username = $aUsername;
    $wasSet = true;
    return $wasSet;
  }

  public function setPassword($aPassword)
  {
    $wasSet = false;
    $this->password = $aPassword;
    $wasSet = true;
    return $wasSet;
  }

  public function setFirstName($aFirstName)
  {
    $wasSet = false;
    $this->firstName = $aFirstName;
    $wasSet = true;
    return $wasSet;
  }

  public function setLastName($aLastName)
  {
    $wasSet = false;
    $this->lastName = $aLastName;
    $wasSet = true;
    return $wasSet;
  }

  public function getUsername()
  {
    return $this->username;
  }

  public function getPassword()
  {
    return $this->password;
  }

  public function getFirstName()
  {
    return $this->firstName;
  }

  public function getLastName()
  {
    return $this->lastName;
  }

  public function getId()
  {
    return $this->id;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {}

}
?>