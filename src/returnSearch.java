

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;
import Interface.SearchResult;

@WebServlet("/returnSearch")
public class returnSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public returnSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		PrintWriter writer = response.getWriter();
		//Parameters
		String query = request.getParameter("query");
		String type= request.getParameter("type");

		response.setContentType("text");
		SearchResult[] result = null;
		try {
			result = DumInterface.search(type, query, sessionNumber);
			if("".equals(query)) return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(SearchResult current : result) {
			String courseCode = null;
			if(current.name != null) {
				courseCode = (new Scanner(current.name)).next();
			}
			writer.write("{\"name\": \"" + current.name + "\", ");
			writer.write("\"tutors\": \"" + current.tutors + "\", ");
			writer.write("\"courseCode\": \"" + courseCode + "\", ");
			writer.write("\"numberOfTutors\" : \" " + current.numberofTutors+ "\", ");
			writer.write("\"major\": \"" + current.major+ "\", ");
			if(type.toLowerCase().equals("course")) writer.write("\"imgURL\": \"" + "/Educo/images/AUB.png" + "\", ");
			else writer.write("\"imgURL\": \"" + "/Educo/images/profile.png" + "\", ");
			writer.write("\"email\": \"" + current.email+ "\", ");
			writer.write("\"courses\": \"" + current.courses + "\"}<br>\n");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	

}
