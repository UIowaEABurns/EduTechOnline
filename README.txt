
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

8) Install MySQL community server from the following URL. Simply choose the most recent version for your platform.

http://dev.mysql.com/downloads/mysql/


9) Tomcat has a build in feature for managing logins. To use it, you need to add the following line of XML to your server.xml file.
To find server.xml, first go to your Eclipse workspace. Then, go into the "Servers" directory. Select the Tomcat directory
you are using for the project and open "server.xml". Inside server.xml, you will find the following realm.

<Realm className="org.apache.catalina.realm.LockOutRealm">
</Realm>

You must modify the realm to look like this, but with your own mysql username and password

<Realm className="org.apache.catalina.realm.LockOutRealm">
	<Realm className="org.apache.catalina.realm.JDBCRealm" connectionName="root" connectionPassword="ceufpxj1" connectionURL="jdbc:mysql://localhost/starexec" digest="SHA-512" driverName="com.mysql.jdbc.Driver" roleNameCol="role" userCredCol="password" userNameCol="email" userRoleTable="user_roles" userTable="users"/>
</Realm>
