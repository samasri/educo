import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;

@WebServlet("/signup")
public class signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		writer.print("test");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//PrintWriter writer = response.getWriter();
		
		//parameter
		String email =  request.getParameter("email");
		int phoneNumber =  Integer.parseInt(request.getParameter("phoneNumber").trim());
		String password =  request.getParameter("password");
		String major =  request.getParameter("major");
		String fname =  request.getParameter("fname");
		String lname =  request.getParameter("lname");
		String role = request.getParameter("role");//student or tutor

		if("student".equals(role)) {
			int ATindex = email.indexOf('@');
			String emailTrimmed = email.substring(0,ATindex);
			String source = getUrlSource("http://www.aub.edu.lb/sites/services/search/Pages/people.aspx?k="+emailTrimmed);
			Scanner s = new Scanner(source);
			int count = 0;

			while(s.hasNextLine()) {
				String current = s.nextLine().trim();
				System.out.println(count + ":    " + current);
				if(count == 1290) {
					int HIPHENindex = current.indexOf('>');
					int HIPHEN2index = current.substring(1).indexOf('<');
					String fullName = current.substring(HIPHENindex+1,HIPHEN2index+1);
					try { 
						lname = fullName.substring(0,fullName.indexOf(','));
					}
					catch (Exception e) {
						response.getWriter().write("Email is not valid");
						return;
					}
					fname = fullName.substring(fullName.indexOf(',')+1);
				}
				if(count == 1306) {
					int HIPHENindex = current.indexOf('>');
					int HIPHEN2index = current.substring(1).indexOf('<');
					major = current.substring(HIPHENindex+1,HIPHEN2index+1);
					major = major.substring(0, major.length());
				}
				count++;
			}
		}
		else {
			lname = " ";
		}

		try {
			if(role.equals("tutor")) System.out.println(DumInterface.addNewTutor(fname, lname, 
					major, email, phoneNumber, password));
			else System.out.println(DumInterface.addNewStudent(fname, lname, 
					major, email,phoneNumber, password));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String getUrlSource(String actualUrl) throws IOException {
        URL url = new URL(actualUrl);
        URLConnection yc =  url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream(), "UTF-8"));
        String inputLine;
        String a = "";
        while ((inputLine = in.readLine()) != null) a += inputLine + "\n";
        in.close();
        
        return a;
    }

}
