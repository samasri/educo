

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;

@WebServlet("/InterviewSchedule")
public class InterviewSchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InterviewSchedule() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		String[][] scheduleContent = null;
		try {
			scheduleContent = DumInterface.getAvailableInterviewTimes(sessionNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter writer = response.getWriter();
		
		writeHTML(writer, scheduleContent);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	void writeHTML(PrintWriter writer, String[][] scheduleContent) {
		writer.write("<!doctype html>\n");
		writer.write("<html>\n");
		writeHead(writer);
		writer.write("<body>\n");
		writer.write("\t<div id='content'>\n");
		writeHeader(writer);
		writer.write("<div class='lefts'></div>");
		writeRightS(writer, scheduleContent);
		writeSearchDiv(writer);
		writer.write("\t</div>\n");
		writer.write("</body>\n");
		writeScripts(writer);
		writer.write("</html>\n");
	}
	
	public void writeSearchDiv(PrintWriter writer) {
		writer.write("\t<!--Search div -->\n");
		writer.write("\t<div id='searchDiv'>\n");
		writer.write("\t\n");
		writer.write("\t</div>\n");
		writer.write("\t<!--End of search div -->\n");
	}
	
	public void writeHead(PrintWriter writer) {
		writer.write("<head>\n");
		writer.write("\t<title> Dashboard </title>\n");
		writer.write("\t<link rel='stylesheet' type='text/css' href='/Educo/JSCSS/Dashboard1.css'>\n");
		writer.write("\t<link rel='stylesheet' type='text/css' href='/Educo/JSCSS/Dashboard.css'>\n");
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
			
			writer.write("\t\t\t\t<th>" + startTime +" &#x2192 " + endTime + "</th>\n");
			for(int j = 0; j < 6; j++) {
				if("".equals(current[j])) writer.write("\t\t\t\t<td id='" + j + "' class='available'></td>\n");
				else if("-1".equals(current[j])) writer.write("\t\t\t\t<td id='" + j + "' class='unavailable'></td>\n");
				else writer.write("\t\t\t\t<td id='" + j + "'class='booked'> "+ current[j] + " </td>\n");
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
	
	public void writeScripts(PrintWriter writer) {
		//scripts
		writer.write("<script src='/Educo/JSCSS/jquery.js'></script>\n");
		writer.write("<script src='/Educo/JSCSS/search.js'></script>\n");
		writer.write("<script src='/Educo/JSCSS/interviewSchedule.js'></script>\n");
		//writer.write("<script src='/Educo/JSCSS/SignInPopup/popup.js'></script>\n");
	}
}
