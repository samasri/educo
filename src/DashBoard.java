

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Interface.DumInterface;
import Interface.Enums;
import Interface.Enums.CourseStatus;
import Interface.Events;

@WebServlet("/DashBoard")
public class DashBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DashBoard() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		int sessionNumber = (Integer) session.getAttribute("sessionNumber");
		response.setContentType("text/html");
		Enums.Role role = null;
		try {
			role = DumInterface.getRole(sessionNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		switch(role) {
			case Student: 
			try {
				writeDashboardStudent(writer, sessionNumber);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				break;
			case Tutor: 
				try {
					if(CourseStatus.Pending.equals(DumInterface.checkTutorStatus(sessionNumber))) {
						response.sendRedirect("/Educo/InterviewSchedule");
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			try {
				writeDashboardTutor(writer, sessionNumber);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				break;
			case Interviewer: 
				writeDashboardInterviewer(writer);
				break;
			case Admin: 
			try {
				writeDashboardAdmin(writer, sessionNumber);
			} catch (Exception e) {
				e.printStackTrace();
			}
				break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	}
	
	public void writeDashboardStudent(PrintWriter writer, int sessionNumber) throws SQLException {
		writer.write("<!doctype html>\n");
		writer.write("<html>\n");
		writeHead(writer);
		writer.write("<!--End of writeHead-->\n");
		writer.write("<body>\n");
		writer.write("\t<div id='content'>\n");
		writeHeader(writer);
		writer.write("<!--End of writeHeader-->\n");
		writeLeftClass(writer);
		writeRightSClass(writer, sessionNumber);
		writeSearchDiv(writer);
		writer.write("<!--End of writeSearchDiv-->\n");
		writer.write("\t</div>\n");
		writeDialog(writer, false);
		writePopup(writer, false);
		writer.write("</body>\n");
		writeScripts(writer);
		writer.write("</html>");	
	}
	
	public void writeDashboardInterviewer(PrintWriter writer) {
		writer.write("<!doctype html>\n");
		writer.write("<html>\n");
		writeHead(writer);
		writer.write("<!--End of writeHead-->\n");
		writer.write("<body>\n");
		writer.write("\t<div id='content'>\n");
		writeHeader(writer);
		writer.write("<!--End of writeHeader-->\n");
		writeLeftClass(writer);
		writeRightIClass(writer);
		writeSearchDiv(writer);
		writer.write("<!--End of writeSearchDiv-->\n");
		writer.write("\t</div>\n");
		writeDialog(writer, true);
		writePopup(writer, true);
		writer.write("</body>\n");
		writeScripts(writer);
		writer.write("</html>");
	}
	
	public void writeDashboardAdmin(PrintWriter writer, int sessionNumber) throws SQLException {
		writer.write("<!doctype html>\n");
		writer.write("<html>\n");
		writeHead(writer);
		writer.write("<!--End of writeHead-->\n");
		writer.write("<body>\n");
		writer.write("\t<div id='content'>\n");
		writeHeader(writer);
		writer.write("<!--End of writeHeader-->\n");
		writeLeftAClass(writer);
		writeRightAClass(writer);
		writeRight1Class(writer, sessionNumber);
		writeSearchDiv(writer);
		writer.write("<!--End of writeSearchDiv-->\n");
		writer.write("\t</div>\n");
		writeDialog(writer, false);
		writePopup(writer, true);
		writer.write("</body>\n");
		writeScripts(writer);
		writer.write("</html>");	
	}
	
	public void writeDashboardTutor(PrintWriter writer, int sessionNumber) throws SQLException {
		writer.write("<!doctype html>\n");
		writer.write("<html>\n");
		writeHead(writer);
		writer.write("<!--End of writeHead-->\n");
		writer.write("<body>\n");
		writer.write("\t<div id='content'>\n");
		writeHeader(writer);
		writer.write("<!--End of writeHeader-->\n");
		writeLeftClass(writer);
		writer.write("<!--End of writeLeftClass-->\n");
		writeRightClass(writer, sessionNumber);
		writer.write("<!--End of writeRightClass-->\n");
		writeSearchDiv(writer);
		writer.write("<!--End of writeSearchDiv-->\n");
		writer.write("\t</div>\n");
		writeDialog(writer, false);
		writePopup(writer, false);
		writer.write("</body>\n");
		writeScripts(writer);
		writer.write("</html>");	
	}
	
	public void writeHead(PrintWriter writer) {
		writer.write("<head>\n");
		writer.write("\t<title> Dashboard </title>\n");
		writer.write("\t<link rel='stylesheet' type='text/css' href='/Educo/JSCSS/Dashboard1.css'>\n");
		writer.write("\t<link href='/Educo/JSCSS/login.css' rel='stylesheet' type='text/css' media='all' />\n");
		writer.write("\t<link href='/Educo/JSCSS/SignInPopup/popup.css' rel='stylesheet' type='text/css' media='all' />\n");
		writer.write("\t<link rel='stylesheet' href='/Educo/JSCSS/jquery-ui/jquery-ui2.css'\n>");
		writer.write("\t<link href='/Educo/JSCSS/acceptButtons-rejectButtons.css' rel='stylesheet' type='text/css' media='all' />\n");
		writer.write("</head>\n");
	}

	public void writeHeader(PrintWriter writer) {
		writer.write("\t\t<div id='header'>\n");
		writer.write("\t\t\t<a href='/Educo/DashBoard'> <img src='/Educo/images/logo.png' id='logo'></a>\n");
		writer.write("\t\t\t<div id='search'>\n");
		writer.write("\t\t\t\t<input id='searchInput' type='text' Value='Search Here'>\n");
		writer.write("\t\t\t\t<div id='searchBy'>\n");
		writer.write("\t\t\t\t	<a class='topopup'><img id='searchByImg' src='/Educo/images/searchBy.png'></a>\n");
		writer.write("\t\t\t</div>\n");
		writer.write("\t\t</div>\n");
		writer.write("\t\t<div id='schedule'>\n");
		writer.write("\t\t\t<a href='/Educo/PersonProfile'><img id='profilePic' src='/Educo/images/profile.png' alt='Your profile'></a>\n");
		writer.write("\t\t\t<a href='/Educo/Schedule'><img id='logo' src='/Educo/images/schedule.png' alt='Tutor/Course's Name'></a>\n");
		writer.write("\t\t\t<a href='/Educo/Logout'><img id='logOut' src='/Educo/images/logout.png' alt='We hate to see you go!!'></a>\n");
		writer.write("\t\t</div>\n");
		writer.write("\t</div>\n");
	}
	
	public void writeLeftClass(PrintWriter writer) {
		writer.write("\t<div class='left'>\n");
		writer.write("\t\t<p id='appointment'><strong>Appointments</strong></p>\n");
		writer.write("\t\t<div id='appointments'>\n");
		//appointments for loop here
		writer.write("\t\t\t<p class='appointments'> You need to meet Mohammad today at 3PM </p>\n");
		writer.write("\t\t\t<p class='appointments'> You need to meet Mohammad today at 3PM </p>\n");
		writer.write("\t\t\t<p class='appointments'> You need to meet Mohammad today at 3PM </p>\n");
		writer.write("\t\t\t<p class='appointments'> You need to meet Mohammad today at 3PM </p>\n");
		writer.write("\t\t</div>\n");
		writer.write("\t</div>\n");
	}
	
	public void writeLeftAClass(PrintWriter writer) {
		writer.write("\t<div class='leftA'></div>\n");
	}
	
	public void writeRightClass(PrintWriter writer, int sessionNumber) throws SQLException {
		writer.write("\t<div class='right'>\n");
		writer.write("\t\t<div class='left1'>\n");
		writer.write("\t\t\t<p id='request'><strong>Requests</strong></p>\n");
		writer.write("\t\t\t<div id='requests'>\n");
		//requests for loop here
		/*writer.write("\t\t\t\t<p class='requests'> Karim Fleifel needs your help with CMPS 256, he would like an appointment Monday at 10AM.<br>\n");
		writer.write("\t\t\t\t\t<a class='button twitter'>Accept</a>\n");
		writer.write("\t\t\t\t\t<a class='button twitter'>Reject</a>\n");
		writer.write("\t\t\t\t</p>\n");
		writer.write("\t\t\t\t<p class='requests'> Karim Fleifel needs your help with CMPS 256, he would like an appointment Monday at 10AM.<br>\n");
		writer.write("\t\t\t\t\t<a class='button twitter'>Accept</a>\n");
		writer.write("\t\t\t\t\t<a class='button twitter'>Reject</a>\n");
		writer.write("\t\t\t\t</p>\n");
		writer.write("\t\t\t\t<p class='requests'> Karim Fleifel needs your help with CMPS 256, he would like an appointment Monday at 10AM.<br>\n");
		writer.write("\t\t\t\t\t<a class='button twitter'>Accept</a>\n");
		writer.write("\t\t\t\t\t<a class='button twitter'>Reject</a>\n");
		writer.write("\t\t\t\t</p>\n");
		writer.write("\t\t\t\t<p class='requests'> Karim Fleifel needs your help with CMPS 256, he would like an appointment Monday at 10AM.<br>\n");
		writer.write("\t\t\t\t\t<a class='button twitter'>Accept</a>\n");
		writer.write("\t\t\t\t\t<a class='button twitter'>Reject</a>\n");
		writer.write("\t\t\t\t</p>\n");*/
		writer.write("\t\t\t</div></div>\n");
		
		
		writer.write("\t\t<div class='right1'>\n");
		writer.write("\t\t\t<p id='news'><strong>News Feed</strong></p>\n");
		writer.write("\t\t\t<div id='newsfeed'>\n");
		//news feed for loop here
		writeNewsFeed(writer, sessionNumber);
		writer.write("\t\t\t\t</div>\n");
		writer.write("\t\t\t</div>\n");
		writer.write("\t\t</div>\n");
	}
	
	public void writeRightSClass(PrintWriter writer, int sessionNumber) throws SQLException {
		writer.write("\t<div class='rightS'>\n");
		writer.write("\t\t<p id='news'><strong>News Feed</strong></p>\n");
		writer.write("\t\t<div id='newsfeed'>\n");
		writeNewsFeed(writer, sessionNumber);
		writer.write("\t\t</div>\n");
		writer.write("\t</div>\n");
	}
	
	public void writeRightIClass(PrintWriter writer) {
		writer.write("\t<div class='rightI'>\n");
		writer.write("\t\t<p id='request'><strong>Requests</strong></p>\n");
		writer.write("\t\t<div id='requests'>\n");/*
		writer.write("\t\t\t<p class='requests'> Karim Fleifel needs your help with CMPS 256, he would like an appointment Monday at 10AM.<br>\n");
		writer.write("\t\t\t<a class='button twitter'>Accept</a>\n");
		writer.write("\t\t\t<a class='button twitter'>Reject</a>\n");
		writer.write("\t\t\t</p>\n");
		writer.write("\t\t\t<p class='requests'> Karim Fleifel needs your help with CMPS 256, he would like an appointment Monday at 10AM.<br>\n");
		writer.write("\t\t\t<a class='button twitter'>Accept</a>\n");
		writer.write("\t\t\t<a class='button twitter'>Reject</a>\n");
		writer.write("\t\t\t</p>\n");
		writer.write("\t\t\t<p class='requests'> Karim Fleifel needs your help with CMPS 256, he would like an appointment Monday at 10AM.<br>\n");
		writer.write("\t\t\t<a class='button twitter'>Accept</a>\n");
		writer.write("\t\t\t<a class='button twitter'>Reject</a>\n");
		writer.write("\t\t\t</p>\n");*/
		writer.write("\t</div>\n");
		writer.write("\t</div>\n");
	}
	
	public void writeRightAClass(PrintWriter writer) {
		writer.write("\t<div class='rightA'>\n");
		writer.write("\t\t<div class='left1'>\n");
		writer.write("\t\t<p id='request'><strong>Requests</strong></p>\n");
		writer.write("\t\t<div id='requests'>\n");
		/*writer.write("\t\t\t<p class='requests'> \n");
		writer.write("\t\t\tKarim Fleifel was accepted by Adnan Utayim, the interviewer, would you like to accept him as a tutor?<br>\n");
		writer.write("\t\t\t<a class='checkFeedback topopup button twitter'>Check Feedback</a>\n");
		writer.write("\t\t\t<a class='button twitter'>Accept</a>\n");
		writer.write("\t\t\t<a class='button twitter'>Reject</a>\n");
		writer.write("\t\t\t</p>\n");
		writer.write("\t\t\t<p class='requests'> \n");
		writer.write("\t\t\tKarim Fleifel was accepted by Adnan Utayim, the interviewer, would you like to accept him as a tutor?<br>\n");
		writer.write("\t\t\t<a class='checkFeedback topopup button twitter'>Check Feedback</a>\n");
		writer.write("\t\t\t<a class='button twitter'>Accept</a>\n");
		writer.write("\t\t\t<a class='button twitter'>Reject</a>\n");
		writer.write("\t\t\t</p>\n");
		writer.write("\t\t\t<p class='requests'> \n");
		writer.write("\t\t\tKarim Fleifel was accepted by Adnan Utayim, the interviewer, would you like to accept him as a tutor?<br>\n");
		writer.write("\t\t\t<a class='checkFeedback topopup button twitter'>Check Feedback</a>\n");
		writer.write("\t\t\t<a class='button twitter'>Accept</a>\n");
		writer.write("\t\t\t<a class='button twitter'>Reject</a>\n");
		writer.write("\t\t\t</p>\n");
		writer.write("\t\t\t<p class='requests'> \n");
		writer.write("\t\t\tKarim Fleifel was accepted by Adnan Utayim, the interviewer, would you like to accept him as a tutor?<br>\n");
		writer.write("\t\t\t<a class='checkFeedback topopup button twitter'>Check Feedback</a>\n");
		writer.write("\t\t\t<a class='button twitter'>Accept</a>\n");
		writer.write("\t\t\t<a class='button twitter'>Reject</a>\n");
		writer.write("\t\t\t</p>\n");		*/
		writer.write("\t\t</div>\n");
		writer.write("\t</div>\n");
	}
	
	public void writeRight1Class(PrintWriter writer, int sessionNumber) throws SQLException {
		writer.write("\t<div class='right1'>\n");
		writer.write("\t\t<p id='news'><strong>News Feed</strong></p>\n");
		writer.write("\t\t<div id='newsfeed' style='width: 100%'>\n");
		writeNewsFeed(writer, sessionNumber);
		writer.write("\t\t</div>\n");
		writer.write("\t</div>\n");
		writer.write("\t</div>\n");
	}
	
	public void writeSearchDiv(PrintWriter writer) {
		writer.write("\t<!--Search div -->\n");
		writer.write("\t<div id='searchDiv'>\n");
		writer.write("\t\n");
		writer.write("\t</div>\n");
		writer.write("\t<!--End of search div -->\n");
	}
	
	public void writePopup(PrintWriter writer, boolean isAdmin) {
		//popup
		writer.write("\t<!--Popup div-->\n");
		writer.write("\t<div id='toPopup'>\n");
		writer.write("\t\t<div class='close'></div>\n");
		writer.write("\t\t\t<!--<span class='ecs_tooltip'><span class='arrow'>Cancel</span></span> This span appears on the left top of the dialog-->\n");
		writer.write("\t\t<div id='popup_content'> <!--your content start-->\n");
		writer.write("\t\t\t<div id='searchByTutor' class='searchBy'><strong>Tutor</strong></div>\n");
		writer.write("\t\t\t<div id='searchByCourse' class='searchBy'><strong>Course</strong></div>\n");
		if(isAdmin) writer.write("\t\t\t<div id='searchByStudent' class='searchBy'><strong>Student</strong></div>\n");
		writer.write("\t\t</div> <!--your content end-->\n");
		writer.write("\t</div>\n");
		writer.write("\t<div class='loader'></div>\n");
		writer.write("\t<div id='backgroundPopup'></div>\n");
		writer.write("\t<!--end of popup div-->\n");
	}
	
	public void writeDialog(PrintWriter writer, boolean isInterviewer) {
		writer.write("<div id='dialog' title='Basic dialog'>\n");
		if(isInterviewer) writer.write("<p id='feedbackDyn'>Please write your feedback here:</p>\n");
		if(isInterviewer) writer.write("<input id='feedbackContent'></input><br>\n");
		if(isInterviewer) writer.write("Rating/5: <input id='rating' type='number' min=0 max=5/><br>");
		if(isInterviewer) writer.write("<button id='sendFeedback'>Save</button>");
		else {
			writer.write("<p>Feedback: </p>");
			writer.write("<p id='getFeedback'></p>");
		}
		writer.write("</div>");
	}
	public void writeScripts(PrintWriter writer) {
		//scripts
		writer.write("<script src='/Educo/JSCSS/jquery.js'></script>\n");
		writer.write("<script src='/Educo/JSCSS/search.js'></script>\n");
		writer.write("<script src='/Educo/JSCSS/SignInPopup/popup.js'></script>\n");
		writer.write("<script src='/Educo/JSCSS/Dashboard.js'></script>\n");
		writer.write("<script src='/Educo/JSCSS/jquery-ui/jquery-ui2.js'></script>\n");
	}
	
	public void writeNewsFeed(PrintWriter writer, int sessionNumber) throws SQLException {
		Events[] events = DumInterface.getEvents(sessionNumber);
		for(Events current: events) {
			Enums.EventType type = current.type;
			writer.write("<p class='newsfeed'>\n");
			switch(type) {
				case CourseAccepted:
					writer.write(current.PersonName + " is now offering " + current.CourseCode + ".");
					break;
				/*case CourseRejected:
					writer.write("Unfortuantely, your course " + current.CourseCode + "was not accepted for tutoring, please review our terms of conditions and don't hesitate to contact us for more information.");
					break;*/
				case ReservationAccepted:
					writer.write("Your reservation with " + "<a href='/Educo/PersonProfile?email=" + current.PersonEmail + "'>" + current.PersonName + "</a> on " + current.day + " at " + current.time + " was accepted! Contact your tutor to know where to meet.");
					break;
				case ReservationRejected:
					writer.write("Unfortunately, " + "<a href='/Educo/PersonProfile?email=" + current.PersonEmail + "'>" + current.PersonName + "</a> is not able to tutor you on " + current.day + " at " + current.time + ".");
					break;
				case TutorJoined:
					writer.write("<a href='/Educo/PersonProfile?email=" + current.PersonEmail + "'>" +  current.PersonName + "</a> joined Educo!");
					break;
			}
			writer.write("\n</p>\n");
		}
	}
}
