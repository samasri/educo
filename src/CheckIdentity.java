

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;

@WebServlet("/CheckIdentity")
public class CheckIdentity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckIdentity() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		
		//parameters
		String email = request.getParameter("personEmail");
		
		PrintWriter writer  = response.getWriter();
		try {
			if(DumInterface.checkIdentity(sessionNumber, email))
				writer.write("{ \"answer\":\"true\" }");
			else writer.write("{ \"answer\":\"false\" }");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
