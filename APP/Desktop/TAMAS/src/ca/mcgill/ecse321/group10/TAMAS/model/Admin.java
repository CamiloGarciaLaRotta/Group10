/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.group10.TAMAS.model;
import java.util.*;

/**
 * unsure if class should create instances of Intrsuctor/Student
 * or rather have all the methods of Instructor/Student
 */
// line 83 "../../../../../../alternatemodel.ump"
// line 142 "../../../../../../alternatemodel.ump"
public class Admin extends Profile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Admin Associations
  private List<Instructor> intructors;
  private List<Student> students;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Admin(String aUsername, String aPassword, String aFirstName, String aLastName)
  {
    super(aUsername, aPassword, aFirstName, aLastName);
    intructors = new ArrayList<Instructor>();
    students = new ArrayList<Student>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Instructor getIntructor(int index)
  {
    Instructor aIntructor = intructors.get(index);
    return aIntructor;
  }

  public List<Instructor> getIntructors()
  {
    List<Instructor> newIntructors = Collections.unmodifiableList(intructors);
    return newIntructors;
  }

  public int numberOfIntructors()
  {
    int number = intructors.size();
    return number;
  }

  public boolean hasIntructors()
  {
    boolean has = intructors.size() > 0;
    return has;
  }

  public int indexOfIntructor(Instructor aIntructor)
  {
    int index = intructors.indexOf(aIntructor);
    return index;
  }

  public Student getStudent(int index)
  {
    Student aStudent = students.get(index);
    return aStudent;
  }

  public List<Student> getStudents()
  {
    List<Student> newStudents = Collections.unmodifiableList(students);
    return newStudents;
  }

  public int numberOfStudents()
  {
    int number = students.size();
    return number;
  }

  public boolean hasStudents()
  {
    boolean has = students.size() > 0;
    return has;
  }

  public int indexOfStudent(Student aStudent)
  {
    int index = students.indexOf(aStudent);
    return index;
  }

  public static int minimumNumberOfIntructors()
  {
    return 0;
  }

  public boolean addIntructor(Instructor aIntructor)
  {
    boolean wasAdded = false;
    if (intructors.contains(aIntructor)) { return false; }
    intructors.add(aIntructor);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeIntructor(Instructor aIntructor)
  {
    boolean wasRemoved = false;
    if (intructors.contains(aIntructor))
    {
      intructors.remove(aIntructor);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addIntructorAt(Instructor aIntructor, int index)
  {  
    boolean wasAdded = false;
    if(addIntructor(aIntructor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfIntructors()) { index = numberOfIntructors() - 1; }
      intructors.remove(aIntructor);
      intructors.add(index, aIntructor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveIntructorAt(Instructor aIntructor, int index)
  {
    boolean wasAdded = false;
    if(intructors.contains(aIntructor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfIntructors()) { index = numberOfIntructors() - 1; }
      intructors.remove(aIntructor);
      intructors.add(index, aIntructor);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addIntructorAt(aIntructor, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfStudents()
  {
    return 0;
  }

  public boolean addStudent(Student aStudent)
  {
    boolean wasAdded = false;
    if (students.contains(aStudent)) { return false; }
    students.add(aStudent);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeStudent(Student aStudent)
  {
    boolean wasRemoved = false;
    if (students.contains(aStudent))
    {
      students.remove(aStudent);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addStudentAt(Student aStudent, int index)
  {  
    boolean wasAdded = false;
    if(addStudent(aStudent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudents()) { index = numberOfStudents() - 1; }
      students.remove(aStudent);
      students.add(index, aStudent);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStudentAt(Student aStudent, int index)
  {
    boolean wasAdded = false;
    if(students.contains(aStudent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudents()) { index = numberOfStudents() - 1; }
      students.remove(aStudent);
      students.add(index, aStudent);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStudentAt(aStudent, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    intructors.clear();
    students.clear();
    super.delete();
  }

}