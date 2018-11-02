Name Game Test README

----------------------------
BUILT  10.29.2018
TESTER Rhonda Oglesby
----------------------------

PAGE OBJECTS
•	GamePage

@TESTS
•	Verify the "streak" counter is incrementing on "correct" selections. 
•	Verify the multiple "streak" counter resets after getting an incorrect answer. 
•	Verify that after 10 random selections the correct counters are being incremented for "tries" and "correct" counters. 
•	Verify name and displayed photos change after selecting the correct answer. 
•	Bonus - Write a test to verify that failing to select one person’s name correctly makes that person appear more frequently than other “correctly selected” people. 


ENVIRONMENT
•	Windows 8.1 Pro
•	Chrome 69.0.3497.100
•	Eclipse Oxygen.3a

TOOLS
•	Java 1.8.0_152
•	Maven 4.0
•	TestNG 6.13.1
•	Selenium 3.8.1
•	ChromeDriver 2.33.506120
----------------------------

TO EXECUTE
•	These instructions are for a Windows machine.
•	The following outlines the main process. Detailed steps follow.

   I. PRE-REQUISITES
      A. Java
      B. Build Automation
      C. Test environment
      E. Eclipse Oxygen
  
  II. To RUN THE AUTOMATION
      A. Clone the project from GitHub
      B. Open the project in GitHub
      C. Navigate to NameGame > src > test.java 
      D. Right click on "NGtests.java".
      E. Select Run As > TestNG Test
   --------------

      STEPS
   I. PRE-REQUISITES
      A. Java
         This project was developed in Java 1.8.0_152 - Java SE Development Kit 8u152 for Windows 64
          1. If you do not already have Java installed on your machine, go to Oracle's web site to download the executable
             Java SE Development Kit 8u152 for Windows 64
             http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
 	  2. Run the downloaded .exe to install Java. Take note of the path where it is installed on your machine.
             >> Ex. "C:\Program Files\Java\jdk1.8.0_152"
	  3. In Windows explorer right-click on "This PC" and select "Properties."
          4. To the right of "Computer name, domain, and workgroup settings" click "Change settings."
             >> The Systems Properties pop-up appears.
          5. Select the "Advanced" tab and select "Environment variables."
             >> The Environment Variables window opens.
          6. Editing the system Path variable will look different, depending on your version of Windows.
             You want to Edit the System variable "Path" by adding the location of the "bin" folding for the Java IDE you installed (step 2).
             >> Ex. "C:\Program Files\Java\jdk1.8.0_152\bin"
          7. Next you want to create a JAVA_HOME user variable (to help applications find your java). 
             In the Environment Variables window select "New" to create a new User variable.
          8. For Variable name enter "JAVA_HOME". For Variable value enter the location of java on your machine (step 2). 
             >> Ex. "C:\Program Files\Java\jdk1.8.0_152"
          9. Select OK on all the opened windows: New User Variable, Environment Variables, Systems Properties. 
  
      B. Build Automation
         This project uses Maven 4.0 to manage building the automation using Selenium and TestNG.
          1. If you do not already have Maven installed on your machine, go to Apache's web site to download the 
             Binary zip archive file.
             http://maven.apache.org/download.cgi
	  2. Extract the zip folder into a new folder called "maven".
             >> Ex. "C:\maven\apache-maven-4.0"
          3. Follow steps 3 - 6 under "A. IDE" above. This time you're adding the location of Maven to the System variable Path.
             >> Ex. " "C:\maven\apache-maven-4.0\bin"
                     
      C. Test environment
         These tests are designed to run in the Chrome browser.
         1. If you do not already have Chrome installed on your machine, go to Chrome's web site to download Chrome.
            https://www.google.com/chrome/browser/desktop/index.html?brand=CHBD&gclid=EAIaIQobChMIp72IoJqD2AIV4r3tCh3AEgzGEAAYASAAEgIhOPD_BwE

  II. RUN THE AUTOMATION TEST
      A. Download and extract
          1. From GitHub download the zip folder for the project. (link provided via email)
          2. Extract the zip folder.
      B. Execute
          1. From the command line navigate to the project folder. 
          2. Entering the 'dir' command should show the pom.xml file for the project.
          3. Enter "mvn test". 

 




