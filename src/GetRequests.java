

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;
import Interface.Enums;
import Interface.Enums.Day;
import Interface.Enums.RequestType;
import Interface.Enums.TimeSlot;
import Interface.Request;

@WebServlet("/GetRequests")
public class GetRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetRequests() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		PrintWriter writer = response.getWriter();
		
		Request[] requests = null;
		try {
			requests = DumInterface.getRequests(sessionNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(requests == null || requests.length == 0) return;

		for(int i = 0; i < requests.length; i++) {
			boolean isInterviewer = false;
			try {
				isInterviewer = Enums.Role.Interviewer.equals(DumInterface.getRole(sessionNumber));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			Request current = requests[i];
			writeJSON(writer, current.studentName, current.InterviewerName, current.tutorName, current.studentEmail, 
					current.InterviewerEmail, current.tutorEmail, current.courseName, current.day, 
					current.time, current.rating, current.type, current.feedback, isInterviewer);
			writer.write("\n");
		}
	}

	public static void writeJSON(PrintWriter writer, String studentName, String InterviewerName, String tutorName, 
			String studentEmail, String InterviewerEmail, String tutorEmail, String courseName,
			Day day, TimeSlot time, int rating, RequestType type, String feedback, boolean isInterviewer) {
		writer.write(("{\"studentName\": \"" + studentName + "\","));
		writer.write(("\"InterviewerName\": \"" + InterviewerName + "\","));
		writer.write(("\"tutorName\": \"" + tutorName + "\","));
		writer.write(("\"studentEmail\": \"" + studentEmail + "\","));
		writer.write(("\"InterviewerEmail\": \"" + InterviewerEmail + "\","));
		writer.write(("\"tutorEmail\": \"" + tutorEmail + "\","));
		writer.write(("\"courseName\": \"" + courseName + "\","));
		writer.write(("\"day\": \"" + day + "\","));
		writer.write(("\"time\": \"" + time + "\","));
		writer.write(("\"feedback\": \"" + feedback + "\","));
		writer.write(("\"rating\": \"" + rating + "\","));
		writer.write(("\"isInterviewer\": \"" + isInterviewer + "\","));
		writer.write(("\"type\": \"" + type + "\"}"));
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
