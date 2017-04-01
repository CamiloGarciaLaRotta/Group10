/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.group10.TAMAS.model;

// line 50 "../../../../../../alternatemodel.ump"
// line 123 "../../../../../../alternatemodel.ump"
public class Profile
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Profile Attributes
  private String username;
  private String password;
  private String firstName;
  private String lastName;

  //Autounique Attributes

  /**
   * either student or employee ID, for the securities yo
   */
  private int id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Profile(String aUsername, String aPassword, String aFirstName, String aLastName)
  {
    username = aUsername;
    password = aPassword;
    firstName = aFirstName;
    lastName = aLastName;
    id = nextId++;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setFirstName(String aFirstName)
  {
    boolean wasSet = false;
    firstName = aFirstName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastName(String aLastName)
  {
    boolean wasSet = false;
    lastName = aLastName;
    wasSet = true;
    return wasSet;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  /**
   * either student or employee ID, for the securities yo
   */
  public int getId()
  {
    return id;
  }

  public void delete()
  {}


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "]"
     + outputString;
  }
}