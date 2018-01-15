

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
import Interface.Enums;
import Interface.Profile;

@WebServlet("/PersonProfile")
public class PersonProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PersonProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		
		//parameters
		String email = request.getParameter("email");
		
			try {
				if(email == null) email = DumInterface.getEmail(sessionNumber);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		Profile result = null;
		Enums.Role role = null;
		try {
			result = DumInterface.getProfile(sessionNumber, email);
			System.out.println(role);
			role = DumInterface.getRole(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter writer = response.getWriter();
		boolean isTutor = (role.equals(Enums.Role.Tutor));
		
		boolean isOwner = false;
		try {
			isOwner = DumInterface.checkIdentity(sessionNumber, email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		write(writer, result.imageURL, result.name, role.toString(), result.major, email, 
				result.phoneNumber, isTutor, result.courses, isOwner);
		//writeBody(writer, result.name, result.major, result.email, result.phoneNumber,
		//		result.courses, result.imageURL);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	public void write(PrintWriter writer, String src, String name, String status, 
			String major, String email, String phoneNumber, boolean isTutor, Course[] course,
			boolean isOwner) {
		writer.write("<!doctype html>\n");
		writer.write("<html>\n");
		writeHead(writer);
		writer.write("<body>\n");
		writer.write("<div id='content'>\n");
		writeHeader(writer);
		writeLeft(writer);
		writeRight(writer, src, name, status, major, email, phoneNumber, isTutor, course, isOwner);
		writer.write("</div>\n");
		writeSearchDiv(writer);
		writer.write("</body>\n");
		writeScripts(writer);
		writer.write("</html>");
		
	}
	public void writeHead(PrintWriter writer) {
		writer.write("<head>\n");
		writer.write("\t<title> Dashboard </title>\n");
		writer.write("\t<link rel='stylesheet' type='text/css' href='/Educo/JSCSS/jquery-ui/jquery-ui.css'>\n");
		writer.write("\t<link rel='stylesheet' type='text/css' href='/Educo/JSCSS/Dashboard1.css'>\n");
		writer.write("\t<link rel='stylesheet' type='text/css' href='/Educo/JSCSS/Dashboard.css'>\n");
		writer.write("</head>\n");
	}
	
	public void writeHeader(PrintWriter writer) {
		writer.write("\t<div id='header'>\n");
		writer.write("\t\t<a href='/Educo/DashBoard'> <img src='/Educo/images/logo.png' id='logo'></a>\n");
		writer.write("\t\t<div id='search'>\n");
		writer.write("\t\t\t<input type='text' id='searchInput' Value='Search Here'>\n");
		writer.write("\t\t</div>\n");
		writer.write("\t<div id='schedule'>\n");
		writer.write("\t\t\t<a href='/Educo/PersonProfile'><img id='profilePic' src='/Educo/images/profile.png' alt='Your profile'></a>\n");
		writer.write("\t\t<a href='/Educo/Schedule'><img id='logo' src='/Educo/images/schedule.png' alt='Tutor/Course\"s Name'></a>\n");
		writer.write("\t\t\t<a href='/Educo/Logout'><img id='logOut' src='/Educo/images/logout.png' alt='We hate to see you go!!'></a>\n");
		writer.write("\t</div>\n");
		writer.write("\t</div>\n");
	}
	
	public void writeLeft(PrintWriter writer) {
		writer.write("\t<div class='left'>\n");
		writer.write("\t</div>\n");
	}
	
	public void writeRight(PrintWriter writer, String src, String name, String status,
			String major, String email, String phoneNumber, 
			boolean isTutor, Interface.Course[] course,
			boolean isOwner) {
		src = "/Educo/" + src;
		writer.write("\t<div class='right'>\n");
		if(isTutor) writer.write("\t<div id='block1'>\n");
		writer.write("\t<p id='profile'><strong>Profile</strong></p>\n");
		writer.write("\t<img src=" + "/Educo/images/profile.png" + " id='profilePicture'> \n");
		//writer.write("<button id='uploadImgButton'> Upload image </button>");
		//writer.write("<div id='uploadImg'>\n");
		//writer.write("\t\t <form method='post' action='/Educo/AddImage'>\n");
		//writer.write("\t\t\t <input type='file' name='pic' accept='image/*'>\n");
		//writer.write("\t\t\t <input type='submit'>");
		//writer.write("\t\t</form>  \n");
		//writer.write("</div>");
		writer.write("\t<div id='profileInfo'>\n");
		writer.write("\t<p class='infoType'><em>Name </em><input class='info' " +
				"size='20'  name='name' type='text' value='" + name + "' readonly ></p>\n");

		writer.write("\t<p class='infoType'><em>Status </em><input class='info' " +
				"size='20'  name='status' type='text' value='" + status + "' readonly ></p>\n");

		writer.write("\t<p class='infoType' size='500'><em>Major </em><input class='info' " +
				"size='20'  name='major' type='text' value='" + major + "' readonly ></p>\n");

		writer.write("\t<p class='infoType'><em>Email </em><input class='info' " +
				"size='20'  name='email' type='text' value='" + email + "' readonly ></p>\n");

		writer.write("\t<p class='infoType'><em>Phone Number </em><input id='phoneNumber' class='info' " +
				"name='phoneNumber' type='text' value='" + phoneNumber + "' readonly='false' >");
		
		if(isOwner) writer.write("\t<input type='submit' id='editNumber' value='Edit number'>");
		writer.write("</p>\n");
		writer.write("\t</div>\n");
		if(isTutor) writer.write("\t</div>\n");
		
		if(isTutor) {
			writer.write("\t<div id='block2'>\n");
			if(!isOwner) writer.write("\t\t<p id='bookApt'>Book an appointment:</p>\n");
			writer.write("\t\t<div id='block3'>\n");
			for(int i = 0; i < course.length; i++) {
				writer.write("\t\t\t<div id='" + course[i].code + "' class='button' >\n");
				writer.write("\t\t\t<a href='" + "/Educo/TutorBookSchedule?courseCode="
						+course[i].code + "&email=" + email + "'>\n");
				writer.write("\t\t\t\t<font class='courseInfo'>");
				if(course[i].status.equals(Enums.CourseStatus.Pending)) writer.write("<strong>PENDING</strong><br>");
				writer.write("Course ID : <strong>" + course[i].code + "</strong>\n");
				writer.write("\t\t\t\t<br> Course Name : " + course[i].name + "\n");
				writer.write("\t\t\t\t<br> Hours taught : " + course[i].hoursTaught + " hours\n");
				writer.write("\t\t\t\t<br> Price Per Hour : $" + course[i].pricePerHour + "\n");
				writer.write("\t\t\t\t<br> Rating : " + course[i].rating + "\n");
				writer.write("\t\t\t</font>\n");
				writer.write("\t\t\t</a>\n");
				if(isOwner) {
					if(Enums.CourseStatus.Accepted.equals(course[i].status)) {
						writer.write("\t\t\t\t<br><button class='delete'> Delete Course </button>");
						writer.write("\t\t\t\t<br><button class='hold'> Put on hold</button>");
					}
					else if(Enums.CourseStatus.OnHold.equals(course[i].status)) {
						writer.write("\t\t\t\t<br><button class='delete'> Delete Course </button>");
						writer.write("\t\t\t\t<br><button class='activate'> Activate </button>");
					}
					else if(Enums.CourseStatus.Pending.equals(course[i].status)) {
						writer.write("\t\t\t\t<br><button class='delete'> Delete Course </button>");
					}
				}
				writer.write("\t\t\t</div>\n");
			}
			
			if(isOwner) {
				writer.write("\t\t\t<div id='addCourse' class='button' >\n");
				writer.write("\t\t\t\t Add new Course ");
				writer.write("\t\t\t</div>\n");
			}
			
			writer.write("\t\t</div>\n");
			writer.write("\t</div>\n");
		}
		
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
		writer.write("<script src='/Educo/JSCSS/jquery-ui/jquery-ui.js'></script>\n");
		writer.write("<script src='/Educo/JSCSS/search.js'></script>\n");
		writer.write("<script src='/Educo/JSCSS/PersonProfile.js'></script>\n");
	}
	
}
