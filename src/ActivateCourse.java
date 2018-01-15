

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;

@WebServlet("/ActivateCourse")
public class ActivateCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ActivateCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		
		//parameters
		String courseCode = request.getParameter("courseCode");
		
		try {
			DumInterface.activateCourse(sessionNumber, courseCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
