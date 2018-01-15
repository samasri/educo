package Interface;

import java.sql.SQLException;

public interface DatabaseInterface {

	
	
	
	//Home page
	public  boolean connect() throws SQLException;
	public boolean disconnect() throws SQLException;
	public Status addNewTutor(String fname, String lname, String major, String email, String phoneNumber, String password) throws SQLException;	
	public Status addNewStudent(String fname, String lname, String major, String email, String phoneNumber, String password) throws SQLException;
	public Status login(String email, String password) throws SQLException ;
	public Status logout (int sessionNumber) throws SQLException;
	
	
	//Student
	public boolean makeAvailable(int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException;
	public boolean makeUnavailable(int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException;
	public String[][] getSchedule(int sessionNumber) throws SQLException ; //schedule for your profile
	public String[][] getSchedule(int sessionNumber,String email) throws SQLException;  //For someone else's profile
	public boolean addNewCourseOffering(int sessionNumber, String codeAndTitle, int price) throws SQLException ;
		
	public boolean book(int sessionNumber, String tutor_email, Enums.Day day, Enums.TimeSlot time, String courseCode) throws SQLException;
	public boolean checkIdentity(int sessionNumber, String personID) throws SQLException;
	public boolean deleteCourse(int sessionNumber, String courseCode) throws SQLException ;
	
	public boolean activateCourse(int sessionNumber, String courseCode) throws SQLException ;
	public boolean deactivateCourse(int sessionNumber, String courseCode) throws SQLException ;
	
	public String[] getMajors() throws SQLException; //We can do the majors as enum to make it simpler // Adnan: NO!!
	public String[] getCourses(String Major) throws SQLException;
	public int getRating (String email, String courseCode) throws SQLException;
	public Enums.Role getRole(int sessionNumber) throws SQLException;
	public Enums.Role getRole (String Email) throws SQLException;
	public boolean updateCoursePrice(int sessionNumber, String courseCode, int newPrice) throws SQLException;
	public boolean cancelReservation(int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException;
	public boolean setInterviewTime(int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException;
	public String[][] getAvailableInterviewTimes(int Session) throws SQLException;
	public Profile getProfile (int sessionNumber, String email) throws SQLException ;
	public SearchResult[] search(String type, String searchQuery, int sessionNumber) throws SQLException;
	public Course courseInfo (String courseCode) throws SQLException;
	public String getEmail (int SessionNumber) throws SQLException;
	
	
	//Fri May 1
	public boolean setPhoneNumber (int sessionNumber, String newNumber) throws SQLException;
	public Request [] getRequests (int sessionNumber) throws SQLException;
	
	
	//Sat May 2
	//public boolean addPhoto (String email, ) throws SQLException;
	//public InputStream retreivePhoto (String email) throws SQLException;
	public String [] serachMajors (String searchQuery) throws SQLException;
	public String [] getAllCourse () throws SQLException;
	public String getEmailByName(String name) throws SQLException;
	
	//Accept-Reject handlers
	public boolean acceptTutor(String email) throws SQLException;
	public boolean rejectTutor (String email) throws SQLException;
	public boolean acceptNewCourseOffering (String email, String courseTitle) throws SQLException ;
	public boolean rejectNewCourseOffering (String email, String courseTitle) throws SQLException;
	public boolean acceptReservation (int tutorSession, String studentEmail, Enums.Day day, Enums.TimeSlot time) throws SQLException;
	public boolean rejectReservation (int tutorSession, String studentEmail, Enums.Day day, Enums.TimeSlot time) throws SQLException;
	
	
	//May 3
	public boolean bookInterview (int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException;
	public boolean unbookInterview (int sessionNumber) throws SQLException;
	public Enums.CourseStatus checkTutorStatus (int SessionNumber) throws SQLException;
	public boolean rate (int StudentSession, String Tutoremail, String courseCode,  int rateValue) throws SQLException;
	public boolean rateInterview (int InterviewerSession, String TutorEmail, int Rating, String Feedback) throws SQLException;
	public Events[] getEvents (int sessionNumber) throws SQLException;
	
}
