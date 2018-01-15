package Interface;

import java.sql.SQLException;

public class DumInterface{
	
	static DatabaseAccessLayer db = null;
	
	public static DatabaseAccessLayer getInterface() throws SQLException {
		if(db == null) {
			db = new DatabaseAccessLayer();
			db.connect();
		}
		return db;
		
	}

	public static Status addNewTutor(String fname, String lname, String major, String email,
			int phoneNumber, String password) throws SQLException {
		return getInterface().addNewTutor(fname, lname, major, email, ""+phoneNumber, 
				password);
	}

	public static Status addNewStudent(String fname, String lname, String major, String email,
			int phoneNumber, String password) throws SQLException {
		return getInterface().addNewStudent(fname, lname, major, email, ""+phoneNumber, 
				password);
	}

	public static Status login(String email, String password) throws SQLException {
		
		return getInterface().login(email, password);
	}
	
	public static Status logout(int sessionNumber) throws SQLException {
		return getInterface().logout(sessionNumber);
	}

	
	public static String[][] getSchedule(int sessionNumber) throws SQLException {
		return getInterface().getSchedule(sessionNumber);
	}

	public static String[][] getSchedule(int sessionNumber, String personEmail) throws SQLException {
		return getInterface().getSchedule(sessionNumber, personEmail);
	}

	public static boolean makeAvailable(int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException {
		return getInterface().makeAvailable(sessionNumber, day, time);
	}

	public static boolean makeUnavailable(int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException {
		return getInterface().makeUnavailable(sessionNumber, day, time);
	}

	public static String[][] getAvailableInterviewTimes(int sessionNumber) throws SQLException {
		return getInterface().getAvailableInterviewTimes(sessionNumber);
	}

	/*public static boolean setInterviewTime(int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException {
		return getInterface().setInterviewTime(sessionNumber, day, time);
	}*/

	public static SearchResult[] search(String type, String query, int sessionNumber) throws SQLException {
		return getInterface().search(type, query, sessionNumber);
	}

	public static Profile getProfile(int sessionNumber, String email) throws SQLException {
		return getInterface().getProfile(sessionNumber, email);
	}

	public static String[] getMajors() throws SQLException {
		return getInterface().getMajors();
	}

	 
	public static String[] getCourses(String Major) throws SQLException {
		return getInterface().getCourses(Major);
	}

	 
	public static boolean addNewCourseOffering(int sessionNumber, String codeAndTitle,
			int price) throws SQLException {
		return getInterface().addNewCourseOffering(sessionNumber, codeAndTitle, price);
	}

	 
	public static boolean activateCourse(int sessionNumber, String courseCode) throws SQLException {
		return getInterface().activateCourse(sessionNumber, courseCode);
	}

	 
	public static boolean deactivateCourse(int sessionNumber, String courseCode) throws SQLException {
		return getInterface().deactivateCourse(sessionNumber, courseCode);
	}

	 
	public static boolean deleteCourse(int sessionNumber, String courseCode) throws SQLException {
		return getInterface().deleteCourse(sessionNumber, courseCode);
	}

	 
	public static boolean updateCoursePrice(int sessionNumber, String courseCode,
			int newPrice) throws SQLException {
		return getInterface().updateCoursePrice(sessionNumber, courseCode, newPrice);
	}

	 
	public static boolean checkIdentity(int sessionNumber, String email) throws SQLException {
		return getInterface().checkIdentity(sessionNumber, email);
	}

	 
	public static boolean book(int sessionNumber, String tutorEmail, Enums.Day day, Enums.TimeSlot time,
			String courseCode) throws SQLException {
		return getInterface().book(sessionNumber, tutorEmail, day, time, courseCode);
	}

	 
	public static boolean cancelReservation(int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException {
		return getInterface().cancelReservation(sessionNumber, day, time);
	}
	
	public static Enums.Role getRole(int sessionNumber) throws SQLException {
		return getInterface().getRole(sessionNumber);
	}
	
	public static Enums.Role getRole(String email) throws SQLException {
		return getInterface().getRole(email);
	}
	
	public static Course courseInfo (String courseCode) throws SQLException {
		return getInterface().courseInfo(courseCode);
	}

	public static int getRating(String email, String courseCode) throws SQLException {
		return getInterface().getRating(email, courseCode);
	}
	
	public static String getEmail(int sessionNumber) throws SQLException {
		return getInterface().getEmail(sessionNumber);
	}
	
	public static boolean setPhoneNumber (int sessionNumber, String newNumber) throws SQLException {
		return getInterface().setPhoneNumber(sessionNumber, newNumber);
	}
	
	public static Request[] getRequests(int sessionNumber) throws SQLException {
		return getInterface().getRequests(sessionNumber);
	}
	
	/*public static String [] serachMajors (String searchQuery) throws SQLException {
		return getInterface().searchMajors(searchQuery);
	}*/
	
	public static String [] getAllCourse () throws SQLException {
        return getInterface().getAllCourse();
    }
	
	public static String getEmailByName(String name) throws SQLException {
		return getInterface().getEmailByName(name);
	}
	
	public static boolean acceptTutor(String email) throws SQLException {
		return getInterface().acceptTutor(email);
	}
	public static boolean rejectTutor (String email) throws SQLException {
		return getInterface().rejectTutor(email);
	}
	public static boolean acceptNewCourseOffering (String email, String courseTitle) throws SQLException {
		return getInterface().acceptNewCourseOffering(email, courseTitle);
	}
	public static boolean rejectNewCourseOffering (String email, String courseTitle) throws SQLException {
		return getInterface().rejectNewCourseOffering(email, courseTitle);
	}
	
	public static boolean acceptReservation (int tutorSession, String studentEmail, Enums.Day day, Enums.TimeSlot time) throws SQLException {
		return getInterface().acceptReservation(tutorSession, studentEmail, day, time);
	}
	public static boolean rejectReservation (int tutorSession, String studentEmail, Enums.Day day, Enums.TimeSlot time) throws SQLException {
		return getInterface().rejectReservation(tutorSession, studentEmail, day, time);
	}
	
	public static boolean bookInterview (int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException {
		return getInterface().bookInterview(sessionNumber, day, time);
	}
	public static boolean unbookInterview (int sessionNumber) throws SQLException {
		return getInterface().unbookInterview(sessionNumber);
	}
	public static Enums.CourseStatus checkTutorStatus (int SessionNumber) throws SQLException {
		return getInterface().checkTutorStatus(SessionNumber);
	}
	
	public static boolean rate (int StudentSession, String Tutoremail, String courseCode,  int rateValue) throws SQLException {
		return getInterface().rate(StudentSession, Tutoremail, courseCode, rateValue);
	}
	public static boolean rateInterview (int InterviewerSession, String TutorEmail, int Rating, String Feedback) throws SQLException {
		return getInterface().rateInterview(InterviewerSession, TutorEmail, Rating, Feedback);
	}
	
	public static Events[] getEvents (int sessionNumber) throws SQLException {
		return getInterface().getEvents(sessionNumber);
	}
    
}
