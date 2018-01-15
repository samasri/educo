

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Interface.DumInterface;
import Interface.Enums;
import Interface.Enums.TimeSlot;

@WebServlet("/ChangeAvailability")
public class ChangeAvailability extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ChangeAvailability() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sessionNumber = (Integer) request.getSession().getAttribute("sessionNumber");
		
		//parameters
		String dayNumber = request.getParameter("dayNumber");
		String timeNumber = request.getParameter("timeNumber");
		String available = request.getParameter("availability");
		
		boolean availability = readBoolean(available);
		Enums.Day day = dayNumberToEnum(dayNumber);
		Enums.TimeSlot time = timeNumberToEnum(timeNumber);
		
		try {
			if(availability) DumInterface.makeAvailable(sessionNumber,day, time);
			else DumInterface.makeUnavailable(sessionNumber, day, time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Enums.Day dayNumberToEnum(String dayNumber) {
		int dayNum = Integer.parseInt(dayNumber);
		switch(dayNum) {
			case 0: return Enums.Day.Monday;
			case 1: return Enums.Day.Tuesday;
			case 2: return Enums.Day.Wednesday;
			case 3: return Enums.Day.Thursday;
			case 4: return Enums.Day.Friday;
			case 5: return Enums.Day.Saturday;
		}
		return null;
	}
	
	public Enums.TimeSlot timeNumberToEnum(String timeNumber) {
		int timeNum = Integer.parseInt(timeNumber);
		switch(timeNum) {
			case 0: return TimeSlot.AM8;
			case 1: return TimeSlot.AM9;
			case 2: return TimeSlot.AM10;
			case 3: return TimeSlot.AM11;
			case 4: return TimeSlot.PM12;
			case 5: return TimeSlot.PM1;
			case 6: return TimeSlot.PM2;
			case 7: return TimeSlot.PM3;
			case 8: return TimeSlot.PM4;
			case 9: return TimeSlot.PM5;
			case 10: return TimeSlot.PM6;
			case 11: return TimeSlot.PM7;
			case 12: return TimeSlot.PM8;
			case 13: return TimeSlot.PM9;
		}
		return null;
	}
	
	boolean readBoolean(String input) {
		boolean result = false;
		input = input.toLowerCase();
		if(input.equals("true")) result = true;
		return result;
	}

}
