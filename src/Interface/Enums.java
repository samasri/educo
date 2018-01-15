package Interface;

public class Enums {
	public enum Day {
		Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
	}
	
	public enum TimeSlot {
		AM8, AM9, AM10, AM11, PM12, PM1, PM2, PM3, PM4, PM5, PM6, PM7, PM8, PM9
	}
	
	public enum Role {
		Student, Tutor, Interviewer, Admin
	}
	
	public enum RequestType {
		Reservation, TutorAcceptance, CourseAcceptance, InterviewFeedback
	}
	
	public enum CourseStatus {
		Accepted, Pending, OnHold, Rejected
	}
	
	public enum EventType {
        TutorJoined, TutorRejected, CourseAccepted, CourseRejected, ReservationAccepted, ReservationRejected, Rating 
	}
	
	public static Enums.Day getDayEnum(String input) {
		Enums.Day result = null;
		input = input.toLowerCase();
		if(input.equals("monday")) {
			result = Enums.Day.Monday;
		}
		else if(input.equals("tuesday")) {
			result = Enums.Day.Tuesday;
		}
		else if(input.equals("wednesday")) {
			result = Enums.Day.Wednesday;
		}
		else if(input.equals("thursday")) {
			result = Enums.Day.Thursday;
		}
		else if(input.equals("friday")) {
			result = Enums.Day.Friday;
		}
		else if(input.equals("saturday")) {
			result = Enums.Day.Saturday;
		}
		else if(input.equals("sunday")) {
			result = Enums.Day.Sunday;
		}
		return result;
	}
	
	public static Enums.TimeSlot getTimeEnum(String input) {
		Enums.TimeSlot result = null;
		input = input.toLowerCase();
		if(input.equals("am10")) {
			result = Enums.TimeSlot.AM10;
		}
		else if(input.equals("am8")) {
			result = Enums.TimeSlot.AM8;
		}
		else if(input.equals("am9")) {
			result = Enums.TimeSlot.AM9;
		}
		else if(input.equals("am11")) {
			result = Enums.TimeSlot.AM11;
		}
		else if(input.equals("pm12")) {
			result = Enums.TimeSlot.PM12;
		}
		else if(input.equals("pm1")) {
			result = Enums.TimeSlot.PM1;
		}
		else if(input.equals("pm2")) {
			result = Enums.TimeSlot.PM2;
		}
		else if(input.equals("pm3")) {
			result = Enums.TimeSlot.PM3;
		}
		else if(input.equals("pm4")) {
			result = Enums.TimeSlot.PM4;
		}
		else if(input.equals("pm5")) {
			result = Enums.TimeSlot.PM5;
		}
		else if(input.equals("pm6")) {
			result = Enums.TimeSlot.PM6;
		}
		else if(input.equals("pm7")) {
			result = Enums.TimeSlot.PM7;
		}
		else if(input.equals("pm8")) {
			result = Enums.TimeSlot.PM8;
		}
		else if(input.equals("pm9")) {
			result = Enums.TimeSlot.PM9;
		}
		return result;
	}
	
	public static Enums.Role getRoleEnum(String input) {
		Enums.Role result = null;
		input = input.toLowerCase();
		if(input.equals("admin")) {
			result = Enums.Role.Admin;
		}
		else if(input.equals("interviewer")) {
			result = Enums.Role.Interviewer;
		}
		else if(input.equals("student")) {
			result = Enums.Role.Student;
		}
		else if(input.equals("tutor")) {
			result = Enums.Role.Tutor;
		}
		return result;
	}
	
	public static Enums.RequestType getRequestTypeEnum(String input) {
		Enums.RequestType result = null;
		input = input.toLowerCase();
		if(input.equals("reservation")) {
			result = Enums.RequestType.Reservation;
		}
		else if(input.equals("tutoracceptance")) {
			result = Enums.RequestType.TutorAcceptance;
		}
		else if(input.equals("courseacceptance")) {
			result = Enums.RequestType.CourseAcceptance;
		}
		else if(input.equals("interviewfeedback")) {
			result = Enums.RequestType.InterviewFeedback;
		}
		return result;
	}
	
	
}