

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;
import Interface.Enums;

@WebServlet("/Schedule")
public class Schedule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Schedule() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		String[][] scheduleContent = null;
		Enums.Role role = null;
		try {
			scheduleContent = DumInterface.getSchedule(sessionNumber);
			role = DumInterface.getRole(sessionNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		PrintWriter writer = response.getWriter();
		writeHTML(writer, scheduleContent, role.equals(Enums.Role.Admin));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	void writeHTML(PrintWriter writer, String[][] scheduleContent, boolean isAdmin) {
		writer.write("<!doctype html>\n");
		writer.write("<html>\n");
		writeHead(writer);
		writeHeader(writer);
		writer.write("<div class='lefts'></div>");
		writeRightS(writer, scheduleContent);
		writeSearchDiv(writer);
		writePopup(writer, isAdmin);
		writer.write("</body>\n");
		writeScripts(writer);
		writer.write("</html>\n");
	}
	
	public void writeHead(PrintWriter writer) {
		writer.write("<head>\n");
		writer.write("\t<title> Dashboard </title>\n");
		writer.write("\t<link rel='stylesheet' type='text/css' href='/Educo/JSCSS/Dashboard1.css'>\n");
		writer.write("\t<link rel='stylesheet' type='text/css' href='/Educo/JSCSS/Dashboard.css'>\n");
		writer.write("\t<link rel='stylesheet' type='text/css' href='/Educo/JSCSS/SignInPopup/popup.css'>\n");
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
	
	void writeRightS(PrintWriter writer, String[][] schedule) {
		writer.write("\t<div class='rights'>\n");
		writer.write("\t\t<table width='88%' height='520px' align='left'>\n");
		writer.write("\t\t\t<div id='head_nav'>\n");
		writer.write("\t\t\t<tr>\n");
		writer.write("\t\t\t\t<th>Time</th>\n");
		writer.write("\t\t\t\t<th>Monday</th>\n");
		writer.write("\t\t\t\t<th>Tuesday</th>\n");
		writer.write("\t\t\t\t<th>Wednesday</th>\n");
		writer.write("\t\t\t\t<th>Thursday</th>\n");
		writer.write("\t\t\t\t<th>Friday</th>\n");
		writer.write("\t\t\t\t<th>Saturday</th>\n");
		writer.write("\t\t\t</tr>\n");
		writer.write("\t\t\t</div>\n");
		for(int i = 0; i < schedule.length; i++) {
			String[] current = schedule[i];
			writer.write("\t\t\t<tr id='" + i + "'>\n");
			
			String startTime = timeToString(i+8);
			String endTime = timeToString(i+9);
			
			writer.write("\t\t\t\t<th>" + startTime +" --> " + endTime + "</th>\n");
			for(int j = 0; j < 6; j++) {
				String toWrite = current[j];
				writer.write("\t\t\t\t<td id='" + j + "' class='");
				if("-1".equals(current[j])) {
					writer.write("unavailable");
					toWrite = "";
				}
				else if(current[j].isEmpty()) writer.write("available");
				else writer.write("booked");
				writer.write("'>" + toWrite + "</td>\n");
			}
			writer.write("\t\t\t\t</tr>\n");
		}
		writer.write("\t\t</table>\n");
		writer.write("\t\t</div>\n");
	}
	
	public String timeToString(int intTime) {
		String time = "";
		if(intTime < 12) time = intTime + ":00AM";
		else if(intTime == 12) time = intTime + ":00PM";
		else time = (intTime - 12) + ":00PM";
		return time;
	}
	
	void writeFirstRow(PrintWriter writer) {
		
		writer.write("\t<head>\n");
		writer.write("\t\t<title>Schedule</title>\n");
		writer.write("\t\t<link rel='stylesheet' type='text/css' href='/Educo/JSCSS/schedule.css'>\n");
		writer.write("\t</head>\n");
		writer.write("\t<body>\n");
		writer.write("\t\t<div id='content'>\n");
		writer.write("\t\t\t<div class='slot'> Time </div>\n");
		writer.write("\t\t\t<div class='slot'> Monday </div>\n");
		writer.write("\t\t\t<div class='slot'> Tuesday </div>\n");
		writer.write("\t\t\t<div class='slot'> Wednesday </div>\n");
		writer.write("\t\t\t<div class='slot'> Thursday </div>\n");
		writer.write("\t\t\t<div class='slot'> Friday </div>\n");
		writer.write("\t\t\t<div class='slot'> Saturday </div>\n");
		writer.write("\t\t\t<div class='slot'> Sunday </div>\n");
		
	}
	
	public void writeSearchDiv(PrintWriter writer) {
		writer.write("\t<!--Search div -->\n");
		writer.write("\t<div id='searchDiv'>\n");
		writer.write("\t\n");
		writer.write("\t</div>\n");
		writer.write("\t<!--End of search div -->\n");
	}
	
	public void writeScripts(PrintWriter writer) {
		//scripts
		writer.write("<script src='/Educo/JSCSS/jquery.js'></script>\n");
		writer.write("<script src='/Educo/JSCSS/search.js'></script>\n");
		writer.write("<script src='/Educo/JSCSS/schedule.js'></script>\n");
		writer.write("<script src='/Educo/JSCSS/SignInPopup/popup.js'></script>\n");
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
}
