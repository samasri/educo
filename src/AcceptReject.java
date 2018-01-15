

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;
import Interface.Enums;

@WebServlet("/AcceptReject")
public class AcceptReject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AcceptReject() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		
		//parameters
		Enums.RequestType type = Enums.getRequestTypeEnum(request.getParameter("type"));
		String email = request.getParameter("email");
		String courseTitle = request.getParameter("courseTitle");
		String dayy = request.getParameter("day");
		String timee = request.getParameter("time");
		
		Enums.Day day = null;
		Enums.TimeSlot time = null;
		if(dayy != null) day = Enums.getDayEnum(dayy);
		if(timee != null) time = Enums.getTimeEnum(timee);
		
		String actionPar = request.getParameter("action");
		
		boolean action = false;
		if(actionPar.toLowerCase().equals("accept")) action = true;
		System.out.println(action + " " + email + " " + courseTitle);
		switch(type) {
			case CourseAcceptance:
				try {
					System.out.println(action + " " + email + " " + courseTitle);
					if(action) DumInterface.acceptNewCourseOffering(email, courseTitle);
					else DumInterface.rejectNewCourseOffering(email, courseTitle);
				} catch(Exception e) {
					e.printStackTrace();
				}
				break;
			case TutorAcceptance:
				try {
					if(action) DumInterface.acceptTutor(email);
					else DumInterface.rejectTutor(email);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case Reservation:
				
				try {
					if(action) DumInterface.acceptReservation(sessionNumber, email, day, time);
					else DumInterface.rejectReservation(sessionNumber, email, day, time);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

}
