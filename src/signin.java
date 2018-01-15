

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Interface.DumInterface;
import Interface.Status;

@WebServlet("/signin")
public class signin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public signin() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//parameters
    	String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		PrintWriter writer = response.getWriter();
		Status login = null;
		try {
			login = DumInterface.login(username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setContentType("text/html");
		if(login.operationSucceed) {
			HttpSession session = request.getSession();
			session.setAttribute("sessionNumber", login.sessionNumber);
			response.sendRedirect("DashBoard");
		}
		else errorMessage(writer);
	}

	public void errorMessage(PrintWriter writer) {
		writer.write("<html>\n<head>\n");
		writer.write("<meta http-equiv='refresh' content='5;URL=Homepage/index.html'>");
		writer.write("\n<//head>\n<body>");
		writer.write("The username/password combination you entered is not correct<br>" +
				"please try again with the correct ones");
		writer.write("\n<//body>\n<//html>");
	}

}
