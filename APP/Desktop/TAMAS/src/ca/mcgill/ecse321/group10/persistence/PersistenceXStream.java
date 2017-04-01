package ca.mcgill.ecse321.group10.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

    public static ApplicationManager initializeApplicationManager(String fileName, String profileFileName) {
        // Initialization for persistence
        ApplicationManager am;
        ProfileManager pm = initializeProfileManager(profileFileName);
        setFilename(fileName);
        setAlias("job", Job.class);
        setAlias("student", Student.class);
        setAlias("application", Application.class);
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

    public static void setAlias(String xmlTagName, Class<?> className) {
        xstream.alias(xmlTagName, className);
    }

    public static void setFilename(String fn) {
        filename = fn;
    }

}