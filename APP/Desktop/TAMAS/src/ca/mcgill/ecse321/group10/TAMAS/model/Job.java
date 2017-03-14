/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse321.group10.TAMAS.model;
import java.sql.Time;
import java.util.*;

// line 11 "../../../../../../alternatemodel.ump"
// line 92 "../../../../../../alternatemodel.ump"
// line 126 "../../../../../../alternatemodel.ump"
public class Job
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Job Attributes
  private Time startTime;
  private Time endTime;
  private String day;
  private double salary;
  private String requirements;

  //Autounique Attributes
  private int id;

  //Job State Machines
  public enum Position { TA, GRADER }
  private Position position;

  //Job Associations
  private Course course;
  private Instructor instructor;
  private List<Student> students;
  private List<Application> applications;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Job(Time aStartTime, Time aEndTime, String aDay, double aSalary, String aRequirements, Course aCourse, Instructor aInstructor)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    day = aDay;
    salary = aSalary;
    requirements = aRequirements;
    id = nextId++;
    boolean didAddCourse = setCourse(aCourse);
    if (!didAddCourse)
    {
      throw new RuntimeException("Unable to create job due to course");
    }
    boolean didAddInstructor = setInstructor(aInstructor);
    if (!didAddInstructor)
    {
      throw new RuntimeException("Unable to create job due to instructor");
    }
    students = new ArrayList<Student>();
    applications = new ArrayList<Application>();
    setPosition(Position.TA);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setDay(String aDay)
  {
    boolean wasSet = false;
    day = aDay;
    wasSet = true;
    return wasSet;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public String getDay()
  {
    return day;
  }

  public double getSalary()
  {
    return salary;
  }

  public String getRequirements()
  {
    return requirements;
  }

  public int getId()
  {
    return id;
  }

  public String getPositionFullName()
  {
    String answer = position.toString();
    return answer;
  }

  public Position getPosition()
  {
    return position;
  }

  public boolean setPosition(Position aPosition)
  {
    position = aPosition;
    return true;
  }

  public Course getCourse()
  {
    return course;
  }

  public Instructor getInstructor()
  {
    return instructor;
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

  public Application getApplication(int index)
  {
    Application aApplication = applications.get(index);
    return aApplication;
  }

  public List<Application> getApplications()
  {
    List<Application> newApplications = Collections.unmodifiableList(applications);
    return newApplications;
  }

  public int numberOfApplications()
  {
    int number = applications.size();
    return number;
  }

  public boolean hasApplications()
  {
    boolean has = applications.size() > 0;
    return has;
  }

  public int indexOfApplication(Application aApplication)
  {
    int index = applications.indexOf(aApplication);
    return index;
  }

  public boolean setCourse(Course aCourse)
  {
    boolean wasSet = false;
    if (aCourse == null)
    {
      return wasSet;
    }

    Course existingCourse = course;
    course = aCourse;
    if (existingCourse != null && !existingCourse.equals(aCourse))
    {
      existingCourse.removeJob(this);
    }
    course.addJob(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setInstructor(Instructor aInstructor)
  {
    boolean wasSet = false;
    if (aInstructor == null)
    {
      return wasSet;
    }

    Instructor existingInstructor = instructor;
    instructor = aInstructor;
    if (existingInstructor != null && !existingInstructor.equals(aInstructor))
    {
      existingInstructor.removeJob(this);
    }
    instructor.addJob(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfStudents()
  {
    return 0;
  }

  public boolean addStudent(Student aStudent)
  {
    boolean wasAdded = false;
    if (students.contains(aStudent)) { return false; }
    if (students.contains(aStudent)) { return false; }
    if (students.contains(aStudent)) { return false; }
    students.add(aStudent);
    if (aStudent.indexOfJob(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aStudent.addJob(this);
      if (!wasAdded)
      {
        students.remove(aStudent);
      }
    }
    return wasAdded;
  }

  public boolean removeStudent(Student aStudent)
  {
    boolean wasRemoved = false;
    if (!students.contains(aStudent))
    {
      return wasRemoved;
    }

    int oldIndex = students.indexOf(aStudent);
    students.remove(oldIndex);
    if (aStudent.indexOfJob(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aStudent.removeJob(this);
      if (!wasRemoved)
      {
        students.add(oldIndex,aStudent);
      }
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

  public static int minimumNumberOfApplications()
  {
    return 0;
  }

  public Application addApplication(Student aStudent)
  {
    return new Application(aStudent, this);
  }

  public boolean addApplication(Application aApplication)
  {
    boolean wasAdded = false;
    if (applications.contains(aApplication)) { return false; }
    if (applications.contains(aApplication)) { return false; }
    if (applications.contains(aApplication)) { return false; }
    Job existingJobs = aApplication.getJobs();
    boolean isNewJobs = existingJobs != null && !this.equals(existingJobs);
    if (isNewJobs)
    {
      aApplication.setJobs(this);
    }
    else
    {
      applications.add(aApplication);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeApplication(Application aApplication)
  {
    boolean wasRemoved = false;
    //Unable to remove aApplication, as it must always have a jobs
    if (!this.equals(aApplication.getJobs()))
    {
      applications.remove(aApplication);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addApplicationAt(Application aApplication, int index)
  {  
    boolean wasAdded = false;
    if(addApplication(aApplication))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfApplications()) { index = numberOfApplications() - 1; }
      applications.remove(aApplication);
      applications.add(index, aApplication);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveApplicationAt(Application aApplication, int index)
  {
    boolean wasAdded = false;
    if(applications.contains(aApplication))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfApplications()) { index = numberOfApplications() - 1; }
      applications.remove(aApplication);
      applications.add(index, aApplication);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addApplicationAt(aApplication, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Course placeholderCourse = course;
    this.course = null;
    placeholderCourse.removeJob(this);
    Instructor placeholderInstructor = instructor;
    this.instructor = null;
    placeholderInstructor.removeJob(this);
    ArrayList<Student> copyOfStudents = new ArrayList<Student>(students);
    students.clear();
    for(Student aStudent : copyOfStudents)
    {
      aStudent.removeJob(this);
    }
    while (applications.size() > 0)
    {
      Application aApplication = applications.get(applications.size() - 1);
      aApplication.delete();
      applications.remove(aApplication);
    }
    
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "day" + ":" + getDay()+ "," +
            "salary" + ":" + getSalary()+ "," +
            "requirements" + ":" + getRequirements()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "course = "+(getCourse()!=null?Integer.toHexString(System.identityHashCode(getCourse())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "instructor = "+(getInstructor()!=null?Integer.toHexString(System.identityHashCode(getInstructor())):"null")
     + outputString;
  }
}