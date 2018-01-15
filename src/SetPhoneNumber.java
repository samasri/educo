

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;

@WebServlet("/SetPhoneNumber")
public class SetPhoneNumber extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SetPhoneNumber() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		//parameters
		int phoneNumber = (Integer) Integer.parseInt(request.getParameter("phoneNumber").trim());
		
		try {
			DumInterface.setPhoneNumber(sessionNumber, "" + phoneNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

}
