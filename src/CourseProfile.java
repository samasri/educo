

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.Course;
import Interface.DumInterface;
import Interface.Profile;

@WebServlet("/CourseProfile")
public class CourseProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CourseProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		PrintWriter writer = response.getWriter();
		
		//parameters
		String courseCode = request.getParameter("courseCode");
		
		Course course = null;
		try {
			course = DumInterface.courseInfo(courseCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("text/html");
		try {
			writeHTML(writer, course, sessionNumber);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeHTML(PrintWriter writer, Course course, int sessionNumber) throws SQLException {
		String courseName = course.name;
		String courseCode = course.code;
		Profile[] profile = new Profile[course.tutors.length]; 
		for (int i = 0; i < course.tutors.length; i++) {
			String email = DumInterface.getEmailByName(course.tutors[i]);
			profile[i] = DumInterface.getProfile(sessionNumber, email);
		}
		
		writer.write("<html>\n");
		writeHead(writer);
		writer.write("<body>\n");
		writeHeader(writer);
		writer.write("\t\t<div class='left'></div>\n");
		writeRight(writer, courseName, courseCode, profile);
		writeSearchDiv(writer);
		writer.write("</body>\n");
		writeScripts(writer);
		writer.write("</html>");
		
	}
	
	public void writeHead(PrintWriter writer) {
		writer.write("<head>\n");
		writer.write("\t<title> Profile </title>\n");
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
		writer.write("\t\t\t<a href='/Educo/Schedule'><img id='logo' src='/Educo/images/schedule.png' alt='Tutor/Course's Name' ></a>\n");
		writer.write("\t\t\t<a href='/Educo/Logout'><img id='logOut' src='/Educo/images/logout.png' alt='We hate to see you go!!'></a>\n");
		writer.write("\t\t</div>\n");
		writer.write("\t</div>\n");
	}
	
	public void writeRight(PrintWriter writer, String courseName, String courseCode, Profile[] profile) throws SQLException {
		writer.write("\t\t<div class='right'>\n");
		writer.write("\t\t\t<div id='block1'>\n");
		writer.write("\t\t\t\t<p id='profile'><strong>Course Profile</strong></p>\n");
		writer.write("\t\t\t\t<img src='/Educo/images/AUB.png' id='profilePicture'>\n");
		writer.write("\t\t\t\t<div id='profileInfo'>\n");
		writer.write("\t\t\t\t\t<p class='infoType'><em> Course Name </em><input class='info' size='20'  name='Name' type='text' value='" + courseName + "' readonly ></p>\n");
		writer.write("\t\t\t\t\t<p class='infoType'><em> Course ID </em><input class='info' size='20'  name='Name' type='text' value='" + courseCode + "' readonly ></p>\n");
		writer.write("\t\t\t\t</div>\n");
		writer.write("\t\t\t</div>\n");
		writer.write("\t\t<div id='block2'>\n");
		writer.write("\t\t\t<p id='bookApt'>Course Offered By:</p>\n<br><br><br><br>\n");
		writer.write("\t\t\t<div id='block3'>\n");
		for(int i = 0; i < profile.length; i++) {
			writer.write("\t\t\t\t<a class=toCourse' href='/Educo/TutorBookSchedule?courseCode=" + courseCode + "&email=" + profile[i].email + "'>\n");
			writer.write("\t\t\t\t<button size='500' type='button' class='button'>\n");
			writer.write("\t\t\t\t<font id='courseInfo'> Tutor name : <strong> " + profile[i].name + " </strong>\n");
			int tutorRating = DumInterface.getRating(profile[i].email, courseCode);
			writer.write("\t\t\t\t<br> Tutor Rating: <strong>" + tutorRating + "/5</strong>\n");
			writer.write("\t\t\t\t<br><strong>BOOK NOW!</strong>\n");
			writer.write("\t\t\t\t</font>\n");
			writer.write("\t\t\t\t</button>\n");
			writer.write("\t\t\t\t</a>\n");
		}
		writer.write("\t\t\t</div>\n");
		writer.write("\t\t</div>\n");
		writer.write("\t</div>\n");
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
		writer.write("<script src='/Educo/JSCSS/PersonProfile.js'></script>\n");
	}
}
