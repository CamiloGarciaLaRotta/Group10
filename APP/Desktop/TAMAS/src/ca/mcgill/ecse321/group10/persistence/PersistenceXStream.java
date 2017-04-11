package ca.mcgill.ecse321.group10.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

import ca.mcgill.ecse321.group10.TAMAS.model.Admin;
import ca.mcgill.ecse321.group10.TAMAS.model.Application;
import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Course;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Instructor;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.Profile;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;

public abstract class PersistenceXStream {

    private static XStream xstream = new XStream();
    private static String filename = "data.xml";
    
    /**
     * Initializes an arraylist of integers storing saved data from the application.
     * Data is formatted as follows:
     * 	First element: 1 when admin has approved of job modifications, 0 otherwise
     * 	Second element: 0 or 1, decides whether to show light or dark theme
     * @param fileName where to initialize arraylist
     * @return
     */
    public static ArrayList<Integer> initializeConstants(String fileName) {
    	ArrayList<Integer> constants;
    	setFilename(fileName);
    	File file = new File(fileName);
    	if(file.exists()) {
    		constants = (ArrayList<Integer>) loadFromXMLwithXStream();
    	} else  {
    		try {
    			file.createNewFile();
    		} catch (Exception e) {
    			e.printStackTrace();
    			System.exit(1);
    		}
    		constants = new ArrayList<Integer>();
    		constants.add(0); //Add int data for admin approval status
    		constants.add(0); //Add int data for color scheme
    		saveToXMLwithXStream(constants);
    	}
    	return constants;
    }

    /**
     * Initializes an ApplicationManager using ApplicationManager data stored in a given file
     * @param fileName contains XML data corresponding to an ApplicationManager. May be an empty file
     * @return
     */
    public static ApplicationManager initializeApplicationManager(String fileName) {
        // Initialization for persistence
        ApplicationManager am;
        setFilename(fileName);
        setAlias("job", Job.class);
        setAlias("student", Student.class);
        setAlias("application", Application.class);
        setAlias("course",Course.class);
        setAlias("amanager", ApplicationManager.class);

        // load model if exists, create otherwise
        File file = new File(fileName);
        if (file.exists()) {
            am = (ApplicationManager) loadFromXMLwithXStream();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            am = new ApplicationManager();
            saveToXMLwithXStream(am);
        }
        return am;

    }
    
    /**
     * Initializes a ProfileManager using ProfileManager data stored in a given file
     * @param fileName contains XML data corresponding to a ProfileManager. May be an empty file
     * @return
     */
    public static ProfileManager initializeProfileManager(String fileName) {
    	ProfileManager pm;
    	setFilename(fileName);
    	setAlias("admin",Admin.class);
    	setAlias("instructor",Instructor.class);
    	setAlias("student",Student.class);
    	setAlias("profile",Profile.class);
    	setAlias("pmanager",ProfileManager.class);
    	setAlias("course",Course.class);
    	setAlias("job", Job.class);
        setAlias("application", Application.class);
    	
    	File file = new File(fileName);
    	if(file.exists()) {
    		pm = (ProfileManager) loadFromXMLwithXStream();
    	} else {
    		try {
    			file.createNewFile();
    		}catch(IOException e) {
    			e.printStackTrace();
    			System.exit(1);
    		}
    		pm = new ProfileManager();
    		saveToXMLwithXStream(pm);
    	}
    	return pm;
    }
    
    /**
     * Initializes a CourseManager using CourseManager data stored in a given file
     * @param fileName contains XML data corresponding to a CourseManager. May be an empty file
     * @return
     */
    public static CourseManager initializeCourseManager(String fileName) {
    	CourseManager cm;
    	setFilename(fileName);
    	setAlias("course",Course.class);
    	setAlias("cmanager",CourseManager.class);
    	
    	File file = new File(fileName);
    	if(file.exists()) {
    		cm = (CourseManager) loadFromXMLwithXStream();
    	} else {
    		try {
    			file.createNewFile();
    		}catch(IOException e) {
    			e.printStackTrace();
    			System.exit(1);
    		}
    		cm = new CourseManager();
    		saveToXMLwithXStream(cm);
    	}
    	return cm;
    }

    /**
     * Saves a given object serialized in an XML file. File located at PersistenceXStream.filename
     * @param obj
     * @return
     */
    public static boolean saveToXMLwithXStream(Object obj) {
        xstream.setMode(XStream.ID_REFERENCES);
        String xml = xstream.toXML(obj); // save our xml file

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(xml);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * De-serializes and loads XML data from the file specified by PersistenceXStream.filename
     * @return
     */
    public static Object loadFromXMLwithXStream() {
        xstream.setMode(XStream.ID_REFERENCES);
        try {
            FileReader fileReader = new FileReader(filename); // load our xml file
            return xstream.fromXML(fileReader);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets XML aliases for arbitrary classes
     * @param xmlTagName
     * @param className
     */
    public static void setAlias(String xmlTagName, Class<?> className) {
        xstream.alias(xmlTagName, className);
    }

    
    /**
     * Modifies the value of PersistenceXStream.filename
     * @param fn
     */
    public static void setFilename(String fn) {
        filename = fn;
    }

}
