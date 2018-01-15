package Interface;

import java.sql.SQLException;


public class Driver {

	public static void main(String[] args) throws SQLException {
		System.out.println("Driver Working...");
				
		DatabaseInterface I = new DatabaseAccessLayer () ;
		I.connect() ;
		//addRany(I);
		//addRany(I);
		//Status Report = I.login("mmu00@mail.aub.edu", "password") ;
		//I.book(Report.sessionNumber, 5, Enums.Day.Monday, Enums.TimeSlot.PM2, courseCode)
		I.disconnect();
	}
	
	public static void addRany (DatabaseInterface I) throws SQLException
	{
		
		I.addNewTutor("Rany", "Kahil", "Computer Science", "rk00@aub.edu.lb", "70123456", "password") ;
		I.acceptTutor("rk00@aub.edu.lb") ;
		
		Status st = I.login("rk00@aub.edu.lb", "password") ;
		I.makeAvailable(st.sessionNumber, Enums.Day.Monday , Enums.TimeSlot.PM12) ;
		I.makeAvailable(st.sessionNumber, Enums.Day.Tuesday , Enums.TimeSlot.PM2) ;
		I.makeAvailable(st.sessionNumber, Enums.Day.Wednesday , Enums.TimeSlot.PM3) ;
		I.makeAvailable(st.sessionNumber, Enums.Day.Thursday , Enums.TimeSlot.PM4) ;
		I.makeAvailable(st.sessionNumber, Enums.Day.Friday , Enums.TimeSlot.AM9) ;
		I.makeAvailable(st.sessionNumber, Enums.Day.Saturday , Enums.TimeSlot.PM12) ;
		I.logout(st.sessionNumber) ;
		
		
	}

}
