
This is a basic startup guide for the project. Once it is working correctly, running the project on the
server should display "hello world!"

1) Install Eclipse Luna (other versions of Eclipse might work fine, but Luna is definitely sufficient).

2) Install the web tools development plugin suite for Luna. To do that, open up Luna, then
go to help--> install new software. Enter this link
http://download.eclipse.org/releases/luna
And from that download site, install everything web related. You might need to install one set of plugins,
restart Luna, and then install a second set.

3) Create a new Dynamic Web Project named EduTechOnline (file -> new-> project->other...-> search "dynamic web project")

4) After the project has been created, copy and paste the project directory from GitHub. It has to be done this way
because Eclipse has no option for importing a Dynamic web project.

5) In the project properties, search build path, and ensure that the JRE8 library is on the build path.

6) Unzip the apache-tomcat zip file included in the project to any desired location. Then, in the project properties,
add the Apache Tomcat library for Tomcat 8. Eclipse will ask for the Tomcat directory, so point it wherever you
unzipped tomcat.

7) Unzip the mysql-connector zip file and add the .jar file within to the /lib folder that is within the tomat directory.
This JAR file is the java database connector, so we will need it for our database.
