/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.group10.TAMAS.model;
import java.sql.Time;
import java.util.*;

// line 11 "../../../../../../alternatemodel.ump"
// line 94 "../../../../../../alternatemodel.ump"
// line 128 "../../../../../../alternatemodel.ump"
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
  private boolean offerSent;

  //Autounique Attributes
  private int id;

  //Job State Machines
  public enum Position { TA, GRADER }
  private Position position;

  //Job Associations
  private Course course;
  private Instructor instructor;
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
    offerSent = false;
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

  public boolean setOfferSent(boolean aOfferSent)
  {
    boolean wasSet = false;
    offerSent = aOfferSent;
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

  public boolean getOfferSent()
  {
    return offerSent;
  }

  public int getId()
  {
    return id;
  }

  public boolean isOfferSent()
  {
    return offerSent;
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
            "requirements" + ":" + getRequirements()+ "," +
            "offerSent" + ":" + getOfferSent()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "course = "+(getCourse()!=null?Integer.toHexString(System.identityHashCode(getCourse())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "instructor = "+(getInstructor()!=null?Integer.toHexString(System.identityHashCode(getInstructor())):"null")
     + outputString;
  }
}