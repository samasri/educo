

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;
import Interface.Enums;
import Interface.Events;

@WebServlet("/GetRatings")
public class GetRatings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetRatings() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		PrintWriter writer = response.getWriter();
		
		Events[] events = null;
		try {
			events = DumInterface.getEvents(sessionNumber);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Events current : events) {
			if(current.type.equals(Enums.EventType.Rating)) writer.write("{\"name\":"+current.PersonName+"\", \"email\":"+current.PersonEmail+"\", \"courseCode\":\"" + current.CourseCode + "\"}");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
