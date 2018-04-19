# Educo

# Motivation and Objective
The website aims to connects students with private tutors in a convenient Facebook-like interface. A rating system is also implemented in order to highlight tutors with better tutoring skills.

# How to reproduce the project on a Windows machine
## Prerequisits
EclipseEE and MySQL
## Steps to setup the project
1. Use [coreservlets tutorial](http://www.coreservlets.com/Apache-Tomcat-Tutorial/tomcat-7-with-eclipse.html) integrate it with Tomcat 7
2. Copy the files in this repository to a project in EclipsEE named _Educo_
3. Use the SQL queries in _DatabaseSQL_ directory (in this repo) to create the database in the MySQL server
4. Download [MySQL Connector JAR](https://dev.mysql.com/downloads/connector/j/5.1.html):
    - Add it to the build directory of the project
    - Assuming tomcat was installed in a _C:\\tomcat\\_, then add the jar file in _C:\\tomcat\\lib\\_
5. Assuming that the tomcat server accessed from the browser on _localhost_, the main page of  the web app can be accessed by visiting: _http://localhost//Educo//Homepage//_

# Features
Checks if the student is in AUB
When a tutor wants to add a course, it has to be one of the courses of AUB

# Known bugs
