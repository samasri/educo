

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;

@WebServlet("/submitFeedback")
public class submitFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public submitFeedback() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		
		//parameters
		String feedback = request.getParameter("feedback");
		String email = request.getParameter("email");
		int rating = Integer.parseInt(request.getParameter("rating"));
		
		try {
			DumInterface.rateInterview(sessionNumber, email, rating, feedback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
