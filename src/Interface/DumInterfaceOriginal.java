package Interface;


public class DumInterfaceOriginal{

	public static Status addNewTutor(String fname, String lname,
			String major, String email, int phoneNumber, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Status addNewStudent(String fname, String lname,
			String major, String email, int phoneNumber, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Status login(String email, String password) {
		Status t = new Status();
		t.operationSucceed = true;
		t.sessionNumber = (int) (Math.random()*1000);
		t.role = Enums.Role.Tutor;
		return t;
	}

	
	public static String[][] getSchedule(int sessionNumber) {
		String[][] schedule = new String[14][8];
		for(int i = 0; i < 14; i++) {
			for(int j = 0; j < 8; j++) {
				if(i == j) schedule[i][j] = "-1";
				else schedule[i][j] = "This is the slot " + i + "  - " + j;
			}
		}
		return schedule;
	}

	public static String[][] getSchedule(int sessionNumber, String TutorEmail) {
		String[][] schedule = new String[14][8];
		for(int i = 0; i < 14; i++) {
			for(int j = 0; j < 8; j++) {
				if(i == j) schedule[i][j] = "-1";
				else schedule[i][j] = "This is the slot " + i + "  - " + j;
			}
		}
		return schedule;
	}

	public static boolean makeAvailable(int sessionNumber, Enums.Day day, Enums.TimeSlot time) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean makeUnavailable(int sessionNumber, Enums.Day day, Enums.TimeSlot time) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean[][] getAvailableInterviewTimes() {
		boolean[][] result = new boolean[14][7];
		for(int i = 0; i < 14; i++) {
			for(int j = 0; j < 7; j++) {
				if( i == j) result[i][j] = true;
				else result[i][j] = false;
			}
		}
		return result;
	}

	public static boolean setInterviewTime(int sessionNumber, Enums.Day day, Enums.TimeSlot time) {
		// TODO Auto-generated method stub
		return false;
	}

	public static SearchResult[] search(Enums.Role type, String query, int sessionNumber) {
		SearchResult[] a = new SearchResult[10];
		for(int i = 0; i < 10; i++) {
			a[i] = new SearchResult();
			a[i].courses = "course"+i;
			a[i].imgURL = "/Educo/imags/image1.jpg";
			a[i].major = "cmps"+i;
			a[i].name = "samer"+i;
			a[i].numberofTutors=5;
			a[i].tutors="abop samra, adnan, abo wadi3";
			a[i].email="sha57";
		}
		return a;
	}

	public static Profile getProfile(String email) {
		Profile result = new Profile();
		result.ID = 123;
		result.imageURL = "images/image1.jpg";
		result.major = "CMPS";
		result.name = "Samer";
		result.courses = new Course[10];
		for(int i = 0; i < 10; i++) {
			result.courses[i] = new Course();
			result.courses[i].code = "CMPS00" + i;
			result.courses[i].hoursTaught = 10;
			result.courses[i].name = "batata " + i;
			result.courses[i].pricePerHour = i;
			result.courses[i].ratedHours = 50;
			result.courses[i].rating = i;
		}
		return result;
	}

	public static String[] getMajors() {
		String[] major = new String[10];
		
		for(int i = 0; i < major.length; i++) {
			major[i] = "major " + i;
		}
		
		return major;
	}

	 
	public static String[] getCourses(String Major) {
		String[] course = new String[10];
		
		for(int i = 0; i < course.length; i++) {
			course[i] = "course " + i;
		}
		
		return course;
	}

	 
	public static boolean addNewCourse(int sessionNumber, String codeAndTitle,
			int price) {
		// TODO Auto-generated method stub
		return false;
	}

	 
	public static boolean activateCourse(int sessionNumber, String courseCode) {
		// TODO Auto-generated method stub
		return false;
	}

	 
	public static boolean deactivateCourse(int sessionNumber, String courseCode) {
		// TODO Auto-generated method stub
		return false;
	}

	 
	public static boolean deleteCourse(int sessionNumber, String courseCode) {
		// TODO Auto-generated method stub
		return false;
	}

	 
	public static boolean updateCoursePrice(int sessionNumber, String courseCode,
			int newPrice) {
		// TODO Auto-generated method stub
		return false;
	}

	 
	public static boolean checkIdentity(int sessionNumber, String personEmail) {
		// TODO Auto-generated method stub
		return false;
	}

	 
	public static boolean book(int sessionNumber, String tutorEmail, Enums.Day day, Enums.TimeSlot time,
			String courseCode) {
		return true;
	}

	 
	public static boolean cancelReservation(int sessionNumber, Enums.Day day, Enums.TimeSlot time) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static Enums.Role getRole(String email) {
		return Enums.Role.Tutor;
	}
	
	public static Enums.Role getRole(int sessionNumber) {
		return Enums.Role.Student;
	}
	
	public static Course courseInfo(String courseCode) {
		Course result = new Course();
		result.code = "cmps253";
		result.name = "Software Engineering";
		result.hoursTaught = 50;
		String[] samer = {"sha57", "kam07", "bat50", "tet58", "tet58", "tet58", "tet58", "tet58", "tet58" };
		result.tutors = samer;
		return result;
	}
	
	
	public static int getRating(String email, String courseCode) {
		return 3;
	}
}
