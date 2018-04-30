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

The complete list of features and other relevant information are found in the [documentation directory](https://github.com/samasri/educo/tree/master/Documentation)

# Screenshots
1. Homepage: The homepage presented to the user when first visiting the website.
![Homepage](https://github.com/samasri/educo/blob/master/Screenshots/1.%20Homepage.PNG)
2. Schedule: The user's schedule of appointments presented on a visual calendar.
![Schedule](https://github.com/samasri/educo/blob/master/Screenshots/2.%20Schedule.PNG)
3. Tutor profile: The profile of a tutor, screenshotted when the same tutor was logged in.
![Tutor profile](https://github.com/samasri/educo/blob/master/Screenshots/3.%20Tutor%20Profile.PNG)
4. Student profile: The profile of a student, screenshotted when the same student was logged in.
![Student profile](https://github.com/samasri/educo/blob/master/Screenshots/4.%20Student%20Profile.PNG)
5. Dashboard: After a user is logged in, this page shows them information about what is happening in their community; such as if a tutor has been recently rated. The page also shows the user their coming appointments.
![Dashboard](https://github.com/samasri/educo/blob/master/Screenshots/5.%20Dashboard.PNG)
6. Search feature: A screenshot of the page when a student is searching for a tutor whome name starts with 's'.
![Search feature](https://github.com/samasri/educo/blob/master/Screenshots/6.%20Search.PNG)
