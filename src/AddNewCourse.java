

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;

@WebServlet("/AddNewCourse")
public class AddNewCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddNewCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		
		//parameters
		String courseCodeAndTitle = request.getParameter("courseCodeAndTitle");
		int price = Integer.parseInt(request.getParameter("price"));
		
		courseCodeAndTitle = (new Scanner(courseCodeAndTitle)).next();
		
		try {
			DumInterface.addNewCourseOffering(sessionNumber, courseCodeAndTitle, price);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
