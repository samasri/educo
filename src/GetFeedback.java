

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;
import Interface.Request;

@WebServlet("/GetFeedback")
public class GetFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetFeedback() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		
		//parameters
		String email = request.getParameter("email");
		
		Request[] r = null;
		try {
			r = DumInterface.getRequests(sessionNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(Request current : r) {
			if(current.tutorEmail.equals(email)) response.getWriter().write(current.feedback);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
