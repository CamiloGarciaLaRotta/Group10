/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse321.group10.TAMAS.model;
import java.util.*;
import java.sql.Date;

// line 63 "../../../../../../alternatemodel.ump"
public class ApplicationManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ApplicationManager Associations
  private List<Application> applications;
  private List<Profile> profiles;
  private List<Job> jobs;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ApplicationManager()
  {
    applications = new ArrayList<Application>();
    profiles = new ArrayList<Profile>();
    jobs = new ArrayList<Job>();
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public Profile getProfile(int index)
  {
    Profile aProfile = profiles.get(index);
    return aProfile;
  }

  public List<Profile> getProfiles()
  {
    List<Profile> newProfiles = Collections.unmodifiableList(profiles);
    return newProfiles;
  }

  public int numberOfProfiles()
  {
    int number = profiles.size();
    return number;
  }

  public boolean hasProfiles()
  {
    boolean has = profiles.size() > 0;
    return has;
  }

  public int indexOfProfile(Profile aProfile)
  {
    int index = profiles.indexOf(aProfile);
    return index;
  }

  public Job getJob(int index)
  {
    Job aJob = jobs.get(index);
    return aJob;
  }

  public List<Job> getJobs()
  {
    List<Job> newJobs = Collections.unmodifiableList(jobs);
    return newJobs;
  }

  public int numberOfJobs()
  {
    int number = jobs.size();
    return number;
  }

  public boolean hasJobs()
  {
    boolean has = jobs.size() > 0;
    return has;
  }

  public int indexOfJob(Job aJob)
  {
    int index = jobs.indexOf(aJob);
    return index;
  }

  public static int minimumNumberOfApplications()
  {
    return 0;
  }

  public boolean addApplication(Application aApplication)
  {
    boolean wasAdded = false;
    if (applications.contains(aApplication)) { return false; }
    applications.add(aApplication);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeApplication(Application aApplication)
  {
    boolean wasRemoved = false;
    if (applications.contains(aApplication))
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

  public static int minimumNumberOfProfiles()
  {
    return 0;
  }

  public boolean addProfile(Profile aProfile)
  {
    boolean wasAdded = false;
    if (profiles.contains(aProfile)) { return false; }
    profiles.add(aProfile);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeProfile(Profile aProfile)
  {
    boolean wasRemoved = false;
    if (profiles.contains(aProfile))
    {
      profiles.remove(aProfile);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addProfileAt(Profile aProfile, int index)
  {  
    boolean wasAdded = false;
    if(addProfile(aProfile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProfiles()) { index = numberOfProfiles() - 1; }
      profiles.remove(aProfile);
      profiles.add(index, aProfile);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveProfileAt(Profile aProfile, int index)
  {
    boolean wasAdded = false;
    if(profiles.contains(aProfile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProfiles()) { index = numberOfProfiles() - 1; }
      profiles.remove(aProfile);
      profiles.add(index, aProfile);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addProfileAt(aProfile, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfJobs()
  {
    return 0;
  }

  public boolean addJob(Job aJob)
  {
    boolean wasAdded = false;
    if (jobs.contains(aJob)) { return false; }
    jobs.add(aJob);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeJob(Job aJob)
  {
    boolean wasRemoved = false;
    if (jobs.contains(aJob))
    {
      jobs.remove(aJob);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addJobAt(Job aJob, int index)
  {  
    boolean wasAdded = false;
    if(addJob(aJob))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfJobs()) { index = numberOfJobs() - 1; }
      jobs.remove(aJob);
      jobs.add(index, aJob);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveJobAt(Job aJob, int index)
  {
    boolean wasAdded = false;
    if(jobs.contains(aJob))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfJobs()) { index = numberOfJobs() - 1; }
      jobs.remove(aJob);
      jobs.add(index, aJob);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addJobAt(aJob, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    applications.clear();
    profiles.clear();
    jobs.clear();
  }

}