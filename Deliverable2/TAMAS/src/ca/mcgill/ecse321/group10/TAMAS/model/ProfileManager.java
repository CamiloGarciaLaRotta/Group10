/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse321.group10.TAMAS.model;
import java.util.*;

// line 69 "../../../../../../alternatemodel.ump"
public class ProfileManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ProfileManager Associations
  private List<Profile> profiles;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ProfileManager()
  {
    profiles = new ArrayList<Profile>();
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public void delete()
  {
    profiles.clear();
  }

}