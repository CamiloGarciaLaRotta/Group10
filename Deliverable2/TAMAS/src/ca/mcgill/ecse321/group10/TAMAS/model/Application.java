/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse321.group10.TAMAS.model;

// line 56 "../../../../../../alternatemodel.ump"
// line 133 "../../../../../../alternatemodel.ump"
public class Application
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Application Attributes
  private int postingID;

  //Autounique Attributes
  private int id;

  //Application Associations
  private Student student;
  private Job jobs;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Application(int aPostingID, Student aStudent, Job aJobs)
  {
    postingID = aPostingID;
    id = nextId++;
    boolean didAddStudent = setStudent(aStudent);
    if (!didAddStudent)
    {
      throw new RuntimeException("Unable to create application due to student");
    }
    boolean didAddJobs = setJobs(aJobs);
    if (!didAddJobs)
    {
      throw new RuntimeException("Unable to create application due to jobs");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPostingID(int aPostingID)
  {
    boolean wasSet = false;
    postingID = aPostingID;
    wasSet = true;
    return wasSet;
  }

  public int getPostingID()
  {
    return postingID;
  }

  public int getId()
  {
    return id;
  }

  public Student getStudent()
  {
    return student;
  }

  public Job getJobs()
  {
    return jobs;
  }

  public boolean setStudent(Student aStudent)
  {
    boolean wasSet = false;
    if (aStudent == null)
    {
      return wasSet;
    }

    Student existingStudent = student;
    student = aStudent;
    if (existingStudent != null && !existingStudent.equals(aStudent))
    {
      existingStudent.removeApplication(this);
    }
    student.addApplication(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setJobs(Job aJobs)
  {
    boolean wasSet = false;
    if (aJobs == null)
    {
      return wasSet;
    }

    Job existingJobs = jobs;
    jobs = aJobs;
    if (existingJobs != null && !existingJobs.equals(aJobs))
    {
      existingJobs.removeApplication(this);
    }
    jobs.addApplication(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Student placeholderStudent = student;
    this.student = null;
    placeholderStudent.removeApplication(this);
    Job placeholderJobs = jobs;
    this.jobs = null;
    placeholderJobs.removeApplication(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "postingID" + ":" + getPostingID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "jobs = "+(getJobs()!=null?Integer.toHexString(System.identityHashCode(getJobs())):"null")
     + outputString;
  }
}