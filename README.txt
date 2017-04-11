README
===========

TAMAS - Group 10

===========

INSTRUCTIONS FOR RUNNING APPLICATIONS
-------------------------------------

Desktop
-------
Navigate to the APP directory
Execute "ant init-desktop"
Run executable jar file: "java -jar Desktop/TAMAS/TAMAS.jar"
Alternatively:
	Navigate to the APP directory
	Execute "ant run-desktop"

Web
---
Start XAMPP server
Navigate to the APP directory
Execute "ant init-web"
Copy directory APP/Web/ca.mcgill.ecse321.group10.TAMAS to the htdocs directory
Launch your favorite web browser
Go to the url localhost/ca.mcgill.ecse321.group10.TAMAS/index.php
Alternatively:
	Navigate to the APP directory
	Execute "ant run-web" (This may need to be done with root priveleges)
		This step may require you to modify some paths designated in the build.xml, including:
			* The path to your PHPUnit executable
			* Your XAMPP directory
			* The path to the executable of your web browser of choice

Mobile
------
Launch Android Virtual Device, or connect Android phone to the computer and start the ADB server
Navigate to the APP directory
Execute android install-mobile


SUMMARY OF BUILD TARGETS
------------------------
The most crucial build targets are listed here. Of course, a full overview of the build targets may be seen in APP/build.xml.

Desktop
-------
init-desktop: Creates the necessary directories for the build and for storing persistence
build-desktop: Compiles the source
export-desktop: Creates an executable JAR of the Desktop Application (after going through JUnit tests)
run-desktop: Launches the Desktop Application (after going through JUnit tests)
clean-desktop: Removes directories for storing class files and JUnit logs
remove-pc-persistence: Removes the persistence directory (NOTE: init-desktop should be run after doing this)

Web
---
init-web: Creates directory for storing persistence
export-web: Creates zip file containing the APP/Web/ca.mcgill.ecse321.group10.TAMAS directory (containing all relevant web code)
run-web: Copies the APP/Web/ca.mcgill.ecse321.group10.TAMAS directory to the htdocs folder, starts XAMPP, and launches the web application in a browser (requires configuration of build script)

Mobile
------
clean-mobile: Removes directories and class files used to build the project
build-mobile: Compiles the source
export-mobile: Builds the APK file for the Android Application
install-mobile: Installs the APK file to a connected Android Phone or running Android Virtual Device


USING THE APPLICATION
---------------------
The first step to using TAMAS involves logging in or registering accounts.
The Web and Mobile applications programmatically provide profiles, since registering profiles is forbidden on those platforms, as only Admins may create profiles.
TODO: INSERT YOUR DEFAULT LOGIN INFO HERE
On mobile, the default configuration includes mainly the student with username "jshnai" and password "123". However, custom data may be imported into the mobile application as explained in the subsection below.

	Importing Custom XML to the Mobile Application
	----------------------------------------------
	It is possible, with the Android Application, to transfer persistence files from the Desktop Application for use in the mobile app. This can be done by generating data with the Desktop Application,
	then copying the output XML files into the appropriate directory within the Android device. The XML output by the Desktop Application is found in $HOME/.tamas/output/, where $HOME is the home directory and
	varies by the Operating System and username in use. The XML files in this directory must be moved into the /storage/emulated/0/Android/obb/ca.mcgill.ecse321.group10.tamas/ directory. This directory was chosen
	as it had proven to be easy to delete files from it and copy files into at, as opposed to the /data/data/app/... directory. With these XML files in place, the Android Application will no longer 
	generate default persistence data; rather, it will use the data contained in the XML files.

There are three types of profiles in TAMAS, each offering different capabilities. The Admin profile, however, can act on all functionalities and can disguise itself as any other profile. The different functions will be
listed below.
	
	Admin
	-----
	The Admin Profile may accomplish the following actions:
		* Register Profiles
		* Upload Course Data
		* View proposed Job Offers and accept/reject them
	When the Admin logs in, he may select the Instructor or Student tabs, which allow him to act as any instructor or student of his choice.

	Instructor
	----------
	The Instructor Profile may accomplish the following actions:
		* Publish Job Postings for courses they are instructing
		* Hire Students that applied to their job postings
		* Evaluate the Students that accepted their job offers

	Student
	-------
	The Student Profile may accomplish the following actions:
		* Apply to job postings
		* View job offers and choose to accept or reject them
		* View evaluations sent by the instructors

	Furthermore, all profiles may modify their profile information (except for their unique usernames).

The dynamic of accepting job offers by the administrators is rather interesting, and deserves some extra attention. In order to avoid scheduling conflicts when a student rejects a job offer, all students are forbidden from
viewing job offers until another approval of job offers has been made. Thus, once a student rejects a job offer, the instructors are free to hire students again, so the allocation of jobs cannot be approved. However,
students that have accepted job offers before another student rejects a job offer still get to keep their jobs.
Finally, it is important to note that the web application is to be used only by instructors, and the mobile app is to be used only by students. Profiles of different types will not be able to log in.
Finally, in the interest of accomadating the color-blind, the Desktop and Web applications provide the ability to switch color schemes (light/dark modes) by flipping a switch on the Web application pages, and by clicking
a button on the main menu of the Desktop application.
