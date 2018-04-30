# Educo

# Motivation and Objective
The website aims to connects students with private tutors in a convenient Facebook-like interface. A rating system is also implemented in order to highlight tutors with better tutoring skills. The project is tailored for the American University of Beirut (AUB) students and hence it has features cutomized for these students; such as checking that the user's email is part of the active students at AUB, or checking that the courses offered by the tutor are valid courses in the university.

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
Below are some of the significant functionality provided by our project:
* Facilitate making appointments between students and tutors
* Arranging the user's appointments in a visual calendar
* Having a profile for each student and tutor
* Rating system for tutors (being rated by students)
* Offering a news feed for users to observe how their tutors are being rated, or how their 
* Checks if user is in the American University of Beirut (AUB) before allowing them to register.
* When adding a course, a tutor has to pick from a list of courses. The list is always synched with courses offerred at AUB.
For the complete list of features, the [final report]() presented as part of the requirements to complete a university course.

# Screenshots
