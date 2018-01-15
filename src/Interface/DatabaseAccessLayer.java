package Interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import Interface.Enums.CourseStatus;
import Interface.Enums.RequestType;
import Interface.Enums.Role;

import com.mysql.jdbc.Statement;

public class DatabaseAccessLayer implements DatabaseInterface {
		
		Statement stmt=null ;
		ResultSet rset=null;
		private String query=null;
		private Connection conn=null;
		private final String comma = "," ;
		private final String database="local";
		
		
		@Override public  boolean connect () throws SQLException{
			
			String server, dbname, username, password;
			if ("local".equals(database))
			{
				server="localhost";
				dbname="EducoDB" ;
				username="root" ;
				password="123456";
				
			}
			else
			{
				server="sql5.freemysqlhosting.net";
				dbname="sql575662";
				username="sql575662";
				password="lP6%xC1%";	
			}
			
			DriverManager.registerDriver (new com.mysql.jdbc.Driver()) ;
			conn = DriverManager.getConnection("jdbc:mysql://" + server +"/"+ dbname,username, password); ;
			return true;
			
		}
		
		@Override public boolean disconnect() throws SQLException
		{
			conn.close();
			return true ;
		}
		
		
		
		private int getIDbySession (int session) throws SQLException 
		{
			stmt = (Statement) conn.createStatement();
			query = "Select PersonID FROM Session Where SessionNumber =" + Integer.toString(session) + ";" ;
			rset = stmt.executeQuery(query) ;
			rset.next() ;
			int ID = rset.getInt("PersonID") ;
			return ID;
		}
		
		private int getSlotNumber (Enums.Day day, Enums.TimeSlot time) throws SQLException
		{
			//Get day
			query = "SELECT Code FROM Days Where Day =" + qoute(day.toString()) + ";" ;
			stmt =  (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			rset.next() ;
			int daycode = rset.getInt(1);
			//Get time
			query = "SELECT Code FROM Start_Times WHERE Value =" + qoute(time.toString()) + ";" ;
			stmt =  (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			rset.next() ;
			int timecode = rset.getInt(1);
			
			
			//Get Slot number
			query = "Select Slot_Number from TIME_SLOTS Where Day="+  Integer.toString(daycode) + " AND Time="  + Integer.toString(timecode) +";"   ;
			stmt =  (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			rset.next() ;
			return  rset.getInt(1);
			
		}
		
		private int getnewSessionNumber() throws SQLException
		{
			int session = (int)(Math.random()*100000) ;
			boolean exists = true ;
			while (exists)
			{
				query = "Select * from Session where SessionNumber= " + Integer.toString(session)+ ";";
				Statement checksession = (Statement) conn.createStatement() ;
				ResultSet rset = checksession.executeQuery(query) ;
				if (!rset.next())
					exists=false ;
			}
			//Session is available 
			return session ;
		}
		
		private int getMajorID (String MajorName) throws SQLException
		{
			
			query = "SELECT Major_Number From MAJOR Where Major_Title =" + qoute(MajorName) +";" ;
			Statement getmajor = (Statement) conn.createStatement() ;
			ResultSet rset = getmajor.executeQuery(query) ;
			rset.next();
			int code = rset.getInt("Major_Number") ;
			
			return code;
			
			
		}
		
		public String makeInsertQuery (String TableName, String [] columns, String [] values)
		{
			String s = "INSERT INTO "+ TableName + " (" ;
			for (int i=0 ; i<columns.length ; i++ )
			{
				s += columns[i];
				if (i!= columns.length -1) 
					s += comma ; 
			}
			
			s += ") VALUES (" ;
			
			for (int i=0 ; i<values.length ; i++)
			{
				s += values[i] ;
				if (i!= values.length -1) 
					s += comma ; 
				
			}
			
			s += ");" ;
			
			
			
			return s;
		}
		
				
		private String qoute (String s)
		{
			return "\"" + s + "\"" ;
		}
		
		
		private String getCourseNameByID (int ID) throws SQLException
		{
			query = "SELECT Title FROM COURSE WHERE ID=" + ID + ";" ;
			stmt = (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			rset.next() ;
			return rset.getString(1);
			
			
		}
		
		
		
		
		@Override public Status addNewTutor (String fname, String lname, String major, String email, String phoneNumber, String password) throws SQLException{
		
			//check if account already exists:
			query = "Select * from PERSON where Email =" + qoute(email) +";"  ;
			Statement checkemail = (Statement) conn.createStatement() ;
			ResultSet rset = checkemail.executeQuery(query) ;
			Status report= new Status ();
			if (rset.next()) //Email already exists!!!
			{
				
				report.errorCode = 1;
				report.errorMessage="email address already exists!";
				report.operationSucceed = false ;
				
			}
			else
			{
				password = OneWayHashing.hash (password) ;
				// Query Major Number:
				int majorNumber =getMajorID(major);
				
				String [] col = {"First_Name","Last_Name", "phone_Number", "Email", "Password" , "Major", "Role"} ; 
				String [] val = {qoute(fname) , qoute(lname) , qoute(phoneNumber), qoute(email), qoute(password), Integer.toString(majorNumber),  "1"   } ;
				query = makeInsertQuery("Person", col , val) ;
				Statement insert = (Statement) conn.createStatement() ;
				insert.executeUpdate(query) ;
				report.operationSucceed = true ;
			}
			return report;			
		}
		
		
		
		
		
		@Override public Status addNewStudent(String fname, String lname, String major, String email, String phoneNumber, String password) throws SQLException
		{
			//check if account already exists:
			query = "Select * from PERSON where Email =" + qoute(email) +";"  ;
			Statement checkemail = (Statement) conn.createStatement() ;
			ResultSet rset = checkemail.executeQuery(query) ;
			Status report= new Status ();
			if (rset.next()) //Email already exists!!!
			{
				
				report.errorCode = 1;
				report.errorMessage="email address already exists!";
				report.operationSucceed = false ;
				
			}
			else
			{
				
				password = OneWayHashing.hash (password) ;
				// Query Major Number:
				int majorNumber =getMajorID(major);
				
				String [] col = {"First_Name","Last_Name", "phone_Number", "Email", "Password" , "Major", "Role"} ; 
				String [] val = {qoute(fname) , qoute(lname) , qoute(phoneNumber), qoute(email), qoute(password), Integer.toString(majorNumber),  "0"   } ;
				query = makeInsertQuery("Person", col , val) ;
				Statement insert = (Statement) conn.createStatement() ;
				insert.executeUpdate(query) ;
				report.operationSucceed = true ;
				
			}
			return report;
		}
		
		
		
		@Override public Status login (String email, String password) throws SQLException
		{
			password = OneWayHashing.hash(password) ;
			query = "Select ID From PERSON Where Email =" + qoute(email) + ";" ;
			Statement checkemail = (Statement) conn.createStatement() ;
			ResultSet rset = checkemail.executeQuery(query) ;
			Status Report = new Status() ;
			
			
			
			if (!rset.next()) //email doesn't exist
			{
				Report.operationSucceed=false ;
				Report.errorCode = 4 ;
				Report.errorMessage = "Email does not exist!" ;
			}
			else
			{
				int ID = rset.getInt("ID");
				//check password:
				query = "SELECT ID FRom PERSON Where ID=" +Integer.toString(ID)+" AND Password =" + qoute(password) + ";" ;
				Statement checkPassword = (Statement) conn.createStatement() ;
				rset = checkPassword.executeQuery(query) ;
				if (!rset.next()) //password is incorrect
				{
					Report.errorCode=5 ;
					Report.errorMessage = "Invalid passowrd" ;
					Report.operationSucceed = false ;
					
				}
				else //log in succeeded
				{
					//Create New Session number
					int session = getnewSessionNumber() ;
					query = "INSERT INTO Session VALUES (" + Integer.toString(ID) + "," + Integer.toString(session) + ");" ;
					Report.operationSucceed=true ;
					Report.sessionNumber=session ;
					Statement InsertSession = (Statement) conn.createStatement() ;
					InsertSession.executeUpdate(query) ;
					
					
				}	
			}
			return Report ;
		}
		
		@Override public Status logout (int sessionnumber) throws SQLException
		{
			query = "Delete from Session Where SessionNumber =" + Integer.toString(sessionnumber)+ ";" ;
			stmt = (Statement) conn.createStatement() ;
			stmt.executeUpdate(query) ;
			Status report = new Status ();
			report.operationSucceed=true ;
			return report;
		}
				
		
		
		@Override public String[][] getSchedule(int sessionNumber) throws SQLException
		{
			//Initialize
			int maxDays=7 ;
			int maxTimes=14 ;
			String [][] Schedule = new String [maxTimes][maxDays];
			for (int i=0 ; i<maxTimes; i++)
				for (int j=0; j<maxDays; j++)
					Schedule[i][j] = "-1" ;
			
			
			
			//Two steps
			//1- Get from Availablity:
			int ID = getIDbySession(sessionNumber) ;
			query = "SELECT Slot_Number from AVAILABLE_TIME Where Person_ID =" + Integer.toString(ID) + ";" ;
			stmt= (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			
			int [] slotNumbers = new int [200];
			int i=0 ;
			while (rset.next())
				slotNumbers[i++]=rset.getInt(1) ; 
						
			
			
			int slotnumber ;

			for (int j=0; j<i; j++)
			{
				slotnumber=slotNumbers[j]; 
				//query day and time from time_slots
				query = "Select * From TIME_SLOTS WHERE Slot_Number=" + slotnumber +";" ;
				ResultSet rset2 = stmt.executeQuery(query) ;
				rset2.next() ;
				int dayCode = rset2.getInt(2) ;
				int timeCode = rset2.getInt(3) ;
				Schedule[timeCode][dayCode] = "" ;
			}
			
			//2-Add Reservations
			//Two cases: If I am a student , or I am a tutor
			query = "SELECT * FROM RESERVATION WHERE Student_ID = " + ID + " OR Tutor_ID="+ ID + " ;" ;
			rset = stmt.executeQuery(query);
			i=0 ;
			int [] Courses = new int [200] ;
			while (rset.next())
			{
				Courses[i] = rset.getInt(3) ;
				slotNumbers[i]=rset.getInt(4) ;
				i++;
			}
			
			
			
			for (int j=0; j<i ; j++)
			{
				slotnumber=slotNumbers[j] ;
				query = "Select * From TIME_SLOTS WHERE Slot_Number=" + slotnumber +";" ;
				ResultSet rset2 = stmt.executeQuery(query) ;
				rset2.next() ;
				int dayCode = rset2.getInt(2) ;
				int timeCode = rset2.getInt(3) ;
				//getCourseNamebyID
				int courseID = Courses[j] ; 
				String course = getCourseNameByID (courseID) ;
				Schedule[timeCode][dayCode] = course;
			}
			
			return Schedule;
		}
		
		
		@Override public boolean makeAvailable(int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException
        {            int ID = getIDbySession(sessionNumber);
            int slotNumber = getSlotNumber(day, time) ;
            query = "INSERT INTO AVAILABLE_TIME VALUES (" + Integer.toString(ID) + comma + Integer.toString(slotNumber) +");" ;
            stmt =  (Statement) conn.createStatement() ;
            stmt.executeUpdate(query) ;
            
            return true ;            
        } 
		
		@Override public boolean makeUnavailable(int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException
		{

			int slotNumber = getSlotNumber(day, time) ;
			
			//delete
			int ID = getIDbySession(sessionNumber) ;
			query = "DELETE FROM AVAILABLE_TIME WHERE Person_ID =" + Integer.toString(ID) + " AND Slot_Number=" + Integer.toString(slotNumber) + ";" ;  
			stmt =  (Statement) conn.createStatement() ;
			stmt.executeUpdate(query) ;
			
			return true ;
		}
		
		@Override public boolean addNewCourseOffering(int sessionNumber, String codeAndTitle, int price) throws SQLException //to course_offering
		{
			int ID=getIDbySession(sessionNumber);
			int courseID = getCourseIDbyName(codeAndTitle) ;
			
			
			
			String [] col = {"Course_ID", "Tutor_ID","Hours_Taught", "Rating", "Status", "Charge_Per_Hour" } ;
			String [] val = {Integer.toString(courseID), Integer.toString(ID), "0", "0", "1", Integer.toString(price) } ;
			query = makeInsertQuery("COURSE_OFFERING", col, val);
			stmt.executeUpdate(query) ;
			
			return true ;
		}
		
		private int getCourseIDbyName (String courseName) throws SQLException
		{
			//exception if course name or code is not in database
			//It may be caused if the code+title 
			query = "Select ID From COURSE WHERE Title =" + qoute(courseName) + " OR Code="+ qoute(courseName)+ " OR concat(Code,\" \",Title) =" + qoute(courseName) + ";" ; 
			System.out.println(query);
			stmt = (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			rset.next() ;
			return rset.getInt(1) ;
			
		}
		
		
		
		private int getIDbyEmail (String email) throws SQLException
		{
			query = "Select ID from PERSON WHERE Email =" + qoute(email) + ";" ;
			stmt= (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			rset.next() ;
			return rset.getInt(1) ;
		}
		
		@Override public boolean book(int sessionNumber, String tutor_email, Enums.Day day, Enums.TimeSlot time, String courseCode) throws SQLException
		{
			
			int tutorID = getIDbyEmail(tutor_email);
			int ID = getIDbySession(sessionNumber);
			int slotNumber = getSlotNumber(day, time) ;
			int courseID = getCourseIDbyName(courseCode) ;
			
			//Make insertion
			query = "INSERT INTO RESERVATION VALUES ("+ ID + comma + tutorID + comma + courseID + comma + slotNumber + comma + "1" + comma + "0" + ");" ;
			stmt = (Statement) conn.createStatement() ;
			stmt.executeUpdate(query) ;
			
			return true ;
		}
		
		@Override public String getEmail (int SessionNumber) throws SQLException
		{
			query  = "select p.Email from PERSON p, Session s"+
					 " where p.ID = s.PersonID "+
					 " AND s.SessionNumber="+ SessionNumber + ";" ;
			stmt = (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			rset.next();
			return rset.getString(1) ;
					 
			
			
		}
		
		
		@Override public String[][] getSchedule(int sessionNumber, String email) throws SQLException  //For someone else's profile
		{
			//Initialize
			int maxDays=7 ;
			int maxTimes=14 ;
			String [][] Schedule = new String [maxTimes][maxDays];
			for (int i=0 ; i<maxTimes; i++)
				for (int j=0; j<maxDays; j++)
					Schedule[i][j] = "-1" ;
			
			
			
			//Two steps
			//1- Get from Availablity:
			int ID = getIDbyEmail(email);
			query = "SELECT Slot_Number from AVAILABLE_TIME Where Person_ID =" + Integer.toString(ID) + ";" ;
			stmt= (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			
			int [] slotNumbers = new int [200];
			int i=0 ;
			while (rset.next())
				slotNumbers[i++]=rset.getInt(1) ; 
						
			
			
			int slotnumber ;

			for (int j=0; j<i; j++)
			{
				slotnumber=slotNumbers[j]; 
				//query day and time from time_slots
				query = "Select * From TIME_SLOTS WHERE Slot_Number=" + slotnumber +";" ;
				ResultSet rset2 = stmt.executeQuery(query) ;
				rset2.next() ;
				int dayCode = rset2.getInt(2) ;
				int timeCode = rset2.getInt(3) ;
				Schedule[timeCode][dayCode] = "" ;
			}
			
			//2-Add Reservations
			//Two cases: If I am a student , or I am a tutor
			query = "SELECT * FROM RESERVATION WHERE Student_ID = " + ID + " OR Tutor_ID="+ ID + " ;" ;
			rset = stmt.executeQuery(query);
			i=0 ;
			int [] Courses = new int [200] ;
			while (rset.next())
			{
				Courses[i] = rset.getInt(3) ;
				slotNumbers[i]=rset.getInt(4) ;
				i++;
			}
			
			
			
			for (int j=0; j<i ; j++)
			{
				slotnumber=slotNumbers[j] ;
				query = "Select * From TIME_SLOTS WHERE Slot_Number=" + slotnumber +";" ;
				ResultSet rset2 = stmt.executeQuery(query) ;
				rset2.next() ;
				int dayCode = rset2.getInt(2) ;
				int timeCode = rset2.getInt(3) ;
				//getCourseNamebyID
				int courseID = Courses[j] ; 
				String course = getCourseNameByID (courseID) ;
				Schedule[timeCode][dayCode] = course;
			}
			
			return Schedule;
		}
		
		@Override public boolean checkIdentity(int sessionNumber, String email) throws SQLException{
			int ID1 = getIDbySession(sessionNumber);
			int ID2 = getIDbyEmail(email);
			return ID1 == ID2;
		}
		
		@Override public boolean deleteCourse(int sessionNumber, String courseCode) throws SQLException
		{
			
			int courseID = getCourseIDbyName(courseCode) ;
			query = "DELETE FROM COURSE_OFFERING Where Course_ID=" + courseID + ";" ;
			stmt = (Statement) conn.createStatement() ;
			stmt.executeUpdate(query) ;
			return true; 
		}
		
		@Override public boolean activateCourse(int sessionNumber, String courseCode) throws SQLException
		{
			int courseID = getCourseIDbyName(courseCode) ;
			query = "UPDATE COURSE_OFFERING SET Status = (SELECT Code FROM STATUS WHere Value = \"Accepted\") Where Course_ID=" + courseID + ";" ;
			stmt = (Statement) conn.createStatement() ;
			stmt.executeUpdate(query) ;
			return true ;
		}
		
		@Override public boolean deactivateCourse(int sessionNumber, String courseCode) throws SQLException
		{
			
			int courseID = getCourseIDbyName(courseCode) ;
			query = "UPDATE COURSE_OFFERING SET Status = (SELECT Code FROM STATUS Where Value = \"Hold\") Where Course_ID=" + courseID + ";" ;
			stmt = (Statement) conn.createStatement() ;
			stmt.executeUpdate(query) ;
			return true;
		}
		
		@Override public String[] getMajors() throws SQLException{ //We can do the majors as enum to make it simpler //Adnan: No!!!
		
			query = "select Major_Title from MAJOR;" ;
			stmt = (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			
			String [] Majors = new String [100] ;
			int i=0;
			while (rset.next())
				Majors[i++]=rset.getString(1) ;
			
			return Majors;
		}
		
		@Override public String [] getCourses(String Major) throws SQLException{
			int MajorID = getMajorID(Major) ;
			query="SELECT Code,Title FROM COURSE WHERE Major=" + MajorID + ";" ;
			stmt= (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			String [] Courses = new String [100] ;
			int i=0;
			while (rset.next())
			{
				Courses[i]=rset.getString(1) ;
				Courses[i]=rset.getString(2) ;
				i++ ;
			}
			
			return Courses;
		}
		
		
		@Override public int getRating (String email, String courseCode) throws SQLException
		{
			int ID= getIDbyEmail(email) ;
			int CourseID = getCourseIDbyName(courseCode) ;
			query = "select Rating From COURSE_OFFERING WHERE Course_ID=" + CourseID + " AND Tutor_ID =" + ID+";";
			stmt = (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			rset.next() ;
			
			
			return rset.getInt(1);
		}
		
		@Override public Enums.Role getRole(int sessionNumber) throws SQLException
		{
			query = "Select Role FROM PERSON p, session s Where s.personID = p.ID AND s.SessionNumber=" + sessionNumber +";" ;
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query) ;
			rset.next() ;
			int role = rset.getInt(1) ;
			Enums.Role R=null;
			
			//stupid java, no enum mapping
			if (role == 0)
				R=Role.Student;
			else if (role==1)
				R=Role.Tutor ;
			else if (role==2)
				R=Role.Interviewer;
			else if (role==3)
				R=Role.Admin;
			
			
			return R;
		}
		
		
		@Override public Enums.Role getRole (String Email) throws SQLException
		{
			int ID = getIDbyEmail(Email) ;
			query = "Select Role FROM PERSON p Where p.ID ="+ ID +  ";" ;
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query) ;
			rset.next() ;
			int role = rset.getInt(1) ;
			Enums.Role R=null;
			
			//stupid java, no enum mapping
			if (role == 0)
				R=Role.Student;
			else if (role==1)
				R=Role.Tutor ;
			else if (role==2)
				R=Role.Interviewer;
			else if (role==3)
				R=Role.Admin;
			
			
			return R;
		}
		
		@Override public boolean updateCoursePrice(int sessionNumber, String courseCode, int newPrice) throws SQLException
		{
			int TutorID = getIDbySession(sessionNumber) ;
			int CourseID = getCourseIDbyName(courseCode) ;
			query = "UPDATE COURSE_OFFERING  SET Charge_Per_Hour =" + newPrice + " Where Tutor_ID=" + TutorID + " AND Course_ID=" + CourseID + ";" ;
			stmt= (Statement) conn.createStatement() ;
			stmt.executeUpdate(query) ;
			
			
			return true ;
		}
		
		@Override public boolean cancelReservation(int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException
		{
		
			int ID = getIDbySession(sessionNumber);
			int slotNumber = getSlotNumber(day, time) ;
						
			//DELETE
			query = "DELETE FROM RESERVATION WHERE (Student_ID=" + ID + " OR Tutor_ID=" + ID + " )AND Slot_Number="+ slotNumber + ";" ;  
			stmt = (Statement) conn.createStatement() ;
			stmt.executeUpdate(query) ;
			return true ;
		}
		
		@Override public String[][] getAvailableInterviewTimes(int Session) throws SQLException
        {
            int maxdays = 7 ;
            int maxtimes=14 ;
            String [][] schedule = new String [maxtimes][maxdays] ;
            for (int d=0; d<maxdays; d++)
                for (int t=0; t<maxtimes; t++)
                    schedule [t][d]= "-1";
            
            query = "Select t.Time, t.Day FROM AVAILABLE_TIME a, PERSON p, TIME_SLOTS t WHERE a.Person_ID = p.ID AND t.Slot_Number = a.Slot_Number AND P.Role = (SELECT Role_Number From ROLE WHERE Role_Title = \"Interviewer\");" ;
            stmt= (Statement) conn.createStatement();
            rset = stmt.executeQuery(query) ;
            while (rset.next())
            {
                int time = rset.getInt(1) ;
                int day = rset.getInt(2) ;
                schedule[time][day]= "" ;
            }
            rset.close();
            
            int ID = getIDbySession(Session);
            //If the tutor has a session:
            query = " Select ts.Day, ts.Time"+
                    " FROM INTERVIEW inter, TIME_SLOTS ts"+
                    " WHERE inter.Slot_Number = ts.Slot_Number"+
                    " AND inter.Tutor_ID = " + ID + ";" ;
            stmt = (Statement) conn.createStatement();
            rset = stmt.executeQuery(query);
            
            if (rset.next())
            {
                int day = rset.getInt(1);
                int time =rset.getInt(2) ;
                schedule[time][day] = "Your Interview";
                
            }
            
            return schedule;
            
        } 
		
		@Override public boolean setInterviewTime(int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException
		{
			int slotID = getSlotNumber(day, time);
			int TutorID = getIDbySession(sessionNumber);
			
			//Get Interviewer ID:
			query = "SELECT (Person_ID) FROM AVAILABLE_TIME a, PERSON p, ROLE r WHERE p.ID=a.Person_ID AND r.Role_Number = p.Role AND r.Role_Title = \"Interviewer\" AND a.Slot_Number=" + slotID + ";" ; 
			stmt = (Statement) conn.createStatement();
			rset=stmt.executeQuery(query) ;
			rset.next();
			int InterviewerID = rset.getInt(1) ;
			query = "INSERT INTO INTERVIEW VALUES ("+ InterviewerID + comma+ TutorID + comma+ slotID + comma + "1);"  ;
			stmt = (Statement) conn.createStatement();
			stmt.executeUpdate(query) ;
			
			return true ;
		}
			
		
		@Override public Profile getProfile (int sessionNumber, String email) throws SQLException
		{
			//step 1: get basic info
			int ID= getIDbyEmail(email);
			Profile prof = new Profile();
			query = "select * From PERSON p, MAJOR m WHERE p.ID =" + ID + " AND m.Major_Number = p.Major;" ;
			stmt = (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			rset.next();
			prof.name = rset.getString(2) + " " + rset.getString(4);
			prof.major = rset.getString(13) ;
			prof.email = rset.getString(6) ;
			prof.phoneNumber = rset.getString(5);
			prof.ID = ID ;
			prof.courses= null;
			

			String extraConstraint = "" ;
			if (getIDbyEmail(email)!=getIDbySession(sessionNumber))
				extraConstraint = " AND co.status = 0  " ;
			
			
			
			//step 2: count number of courses if tutor
			query = "select count(*) AS num From PERSON p, COURSE_OFFERING co WHERE co.Tutor_ID = p.ID AND co.Status<>2  AND p.ID=" + ID + extraConstraint +";" ;
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query) ;
			rset.next() ;
			int size = rset.getInt(1) ;
			prof.courses = new Course [size] ;
			
			
			//step 3: get all courses
			query = "select crs.Code, crs.Title, co.Hours_Taught, co.Charge_Per_Hour, co.Rating, co.Status"+
					" From PERSON p, COURSE_OFFERING co, COURSE crs"+
					" WHERE co.Tutor_ID = p.ID"+
					" AND co.Course_ID = crs.ID"+
					" AND co.Status <> 2" +
					 extraConstraint + 
					" AND p.ID =" + ID + ";" ;
			stmt = (Statement) conn.createStatement();
			rset=stmt.executeQuery(query) ;
			
			for (int i=0; i<size; i++)
			{
				rset.next();
				prof.courses[i] = new Course();
				prof.courses[i].code = rset.getString(1) ;
				prof.courses[i].name = rset.getString(2) ;
				prof.courses[i].hoursTaught = rset.getInt(3) ;
				prof.courses[i].pricePerHour = rset.getInt(4) ;
				prof.courses[i].rating = rset.getInt(5) ;
				
				//Status:
				int statusCode = rset.getInt(6);
				if (statusCode == 0)
					prof.courses[i].status = CourseStatus.Accepted;
				else if (statusCode ==1)
					prof.courses[i].status = CourseStatus.Pending;
				else if (statusCode ==2)
					prof.courses[i].status = CourseStatus.Rejected;
				else if (statusCode ==3)
					prof.courses[i].status = CourseStatus.OnHold;
			}
			
			return prof;
		}
		
		
		
		
		public SearchResult[] search(String type, String searchQuery, int sessionNumber) throws SQLException
		{
			type = type.toLowerCase();
			if ("tutor".equals(type))
				return searchForTutors(searchQuery, sessionNumber) ;
			else if ("course".equals(type))  //Search is for Courses
				return searchForCourses(searchQuery, sessionNumber) ;
			else if ("student".equals(type)) 
				return searchForStudents (searchQuery, sessionNumber) ;
			return null;
		}
		
		
		private SearchResult [] searchForTutors(String searchQuery, int session) throws SQLException
		{
			//Query count
			query = " Select Count(*) as size "+
					" FROM PERSON p, Major m, ROLE r" + 
					" WHERE (lower(concat(First_Name,\" \",Last_Name)) Like lower("+ qoute("%"+searchQuery+"%") +") "+ 
					" OR lower(concat(First_Name,Last_Name)) Like lower(" + qoute("%"+searchQuery+"%") +")) "+
					" AND p.Major = m.Major_Number"+
					" AND r.Role_Number = p.Role" +
					" AND r.Role_Title = \"Tutor\";" ;
			
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query) ;
			rset.next();
			int size = rset.getInt(1) ;
			
			
			//Query persons			
			query = "select p.First_Name, p.Last_Name, m.Major_Title, p.ID, p.Email "+
					" FROM PERSON p, Major m, ROLE r" + 
					" WHERE (lower(concat(First_Name,\" \",Last_Name)) Like lower("+ qoute("%"+searchQuery+"%") +") "+ 
					" OR lower(concat(First_Name,Last_Name)) Like lower(" + qoute("%"+searchQuery+"%") +")) "+
					" AND p.Major = m.Major_Number"+
					" AND r.Role_Number = p.Role" +
					" AND r.Role_Title = \"Tutor\";" ;
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query) ;
			SearchResult [] search = new SearchResult [size] ;
			int [] IDs = new int [size] ;
			
			int i=0 ;
			while (rset.next())
			{
				search[i]=new SearchResult();
				search[i].name = rset.getString(1) + " " + rset.getString(2);
				search[i].major = rset.getString(3);
				IDs[i] = rset.getInt(4);
				search[i].email = rset.getString(5);
				i++;
			}
			
			//get courses of each tutor
			for (int j=0; j<size; j++)
			{
				query = "select crs.Code"+
						" From PERSON p, COURSE_OFFERING co, COURSE crs"+
						" Where p.ID = co.Tutor_ID"+
						" AND co.Course_ID = crs.ID"+
						" AND p.ID =" +IDs[j] + ";" ;
				stmt = (Statement) conn.createStatement();
				rset = stmt.executeQuery(query);
				search[j].courses="";
				while (rset.next())
					search[j].courses += rset.getString(1)+" ";
			}
			return search;
		}
		
		private SearchResult [] searchForStudents(String searchQuery, int session) throws SQLException
		{
			//Query count
			query = " Select Count(*) as size "+
					" FROM PERSON p, Major m, ROLE r" + 
					" WHERE (lower(concat(First_Name,\" \",Last_Name)) Like lower("+ qoute("%"+searchQuery+"%") +") "+ 
					" OR lower(concat(First_Name,Last_Name)) Like lower(" + qoute("%"+searchQuery+"%") +")) "+
					" AND p.Major = m.Major_Number"+
					" AND r.Role_Number = p.Role" +
					" AND r.Role_Title = \"Student\";" ;  
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query) ;
			rset.next();
			int size = rset.getInt(1) ;
			
			
			//Query persons			
			query = "select p.First_Name, p.Last_Name, m.Major_Title, p.ID "+
					" FROM PERSON p, Major m, ROLE r" + 
					" WHERE (lower(concat(First_Name,\" \",Last_Name)) Like lower("+ qoute("%"+searchQuery+"%") +") "+ 
					" OR lower(concat(First_Name,Last_Name)) Like lower(" + qoute("%"+searchQuery+"%") +")) "+
					" AND p.Major = m.Major_Number"+
					" AND r.Role_Number = p.Role" +
					" AND r.Role_Title = \"Student\";" ;
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query) ;
			SearchResult [] search = new SearchResult [size] ;
			int [] IDs = new int [size] ;
			
			
			int i=0 ;
			while (rset.next())
			{
				search[i]=new SearchResult();
				search[i].name = rset.getString(1) + " " + rset.getString(2);
				search[i].major = rset.getString(3);
				IDs[i] = rset.getInt(4);
				i++;
			}
			
			return search;
		}
		
		
		private SearchResult[] searchForCourses(String searchQuery, int session) throws SQLException
		{
			
			int limit = 15;
			//Step2: Get Courses info:
			query = "select crs.ID, crs.Code, crs.Title FROM COURSE crs WHERE lower(concat(crs.Code,\" \",crs.Title)) Like lower(" + qoute("%"+searchQuery+"%")+");" ;
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query);
			int size = getResultSetRowCount(rset) ;
			if (size > limit)
				size = limit;
			int [] IDs = new int [size] ;
			
			SearchResult [] search = new SearchResult [size] ;
			for (int i=0; i<size; i++)
			{
				search[i] = new SearchResult();
				rset.next();
				IDs[i]= rset.getInt(1) ;
				search[i].name = rset.getString(2) + " " + rset.getString(3);
			}
			
			//Step3: Get tutor of each course:
			for (int i=0 ; i<size; i++) //for each course:
			{
				query="select p.First_Name, p.Last_Name"+
					  " From Person p, COURSE_OFFERING co"+
					  " WHERE p.ID = co.Tutor_ID" +
					  " AND co.Course_ID =" + IDs[i] + ";";
				stmt = (Statement) conn.createStatement();
				rset = stmt.executeQuery(query) ;
				search[i].tutors="";
				int numOfTutors=0;
				while (rset.next())
				{
					//search[i]=new SearchResult();
					search[i].tutors += rset.getString(1) + "" + rset.getString(2) ;
					numOfTutors++;
				}
				search[i].numberofTutors = numOfTutors ;
			}
			
			return search;
		}
		
		
		
		public Course courseInfo (String courseCode) throws SQLException
		{
			Course crs=new Course();
			query="select * FROM COURSE WHERE Code=" +qoute(courseCode) +" OR Title=" + qoute(courseCode) + ";" ;
			stmt = (Statement) conn.createStatement();
			rset=stmt.executeQuery(query) ;
			rset.next();
			crs.code=rset.getString(2);
			crs.name=rset.getString(3);
			int ID = rset.getInt(1);
			
			//get list of tutors with a second query
			query = "select p.First_Name, p.Last_Name"+
					" FROM COURSE crs, Person p, COURSE_OFFERING co" +
					" WHERE crs.ID=" + ID +
					" AND co.Course_ID = crs.ID"+
					" AND co.Tutor_ID = p.ID;" ;
			stmt = (Statement) conn.createStatement();
			rset=stmt.executeQuery(query) ;
			int size = getResultSetRowCount(rset);
			crs.tutors = new String [size] ;
			for (int i=0; i<size; i++)
			{
				rset.next();
				crs.tutors[i] = rset.getString(1) + " " + rset.getString(2) ;
			}
			return crs;
		}
		
		@Override public boolean setPhoneNumber (int sessionNumber, String newNumber) throws SQLException
		{
			int ID = getIDbySession(sessionNumber);
			query = "Update PERSON SET Phone_Number ="+ qoute(newNumber) + "WHERE ID="+ ID + ";" ;
			stmt = (Statement) conn.createStatement() ;
			stmt.executeUpdate(query) ;
			return true;
		}
		
		@Override public Request [] getRequests (int sessionNumber) throws SQLException
		{
			
			Enums.Role role = getRole(sessionNumber) ;
			if (role.equals(Enums.Role.Admin))	
				return getAdminRequests();
			else if (role.equals(Enums.Role.Tutor))
				return getTutorRequests(sessionNumber);
			else if (role.equals(Enums.Role.Interviewer))
				return getInterviewerRequests(sessionNumber);
			return null;
		}
		
		private Request[] getAdminRequests () throws SQLException
		{
			String query1, query2;
			//get count of query 1
			query1 =" select p.First_Name, p.Last_Name, p.Email, inter.First_Name, inter.Last_Name, inter.Email, I.Feedback, I.Rating"+
					" from PERSON p, ROLE r, STATUS s, INTERVIEW I, STATUS Istatus, PERSON inter"+
					" where p.Role = r.Role_Number"+
					" AND r.Role_Title = \"Tutor\" "+
					" AND s.Code = p.Tutor_Status"+
					" AND s.Value = \"Pending\""+
					" AND I.Tutor_ID = p.ID"+
					" AND Istatus.Code = I.Status"+
					" AND I.Interviewer_ID = inter.ID"+
					" AND Istatus.Value = \"Rated\";" ;
			
			stmt = (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query1);
			int size = getResultSetRowCount(rset) ;
			
			
			
			//get count of query2 
			query2 = "select p.First_Name, p.Last_Name, p.Email, crs.Code, crs.Title"+
					" from COURSE_OFFERING co, STATUS s, PERSON p, COURSE crs"+
					" WHERE s.Code = co.status"+
					" AND co.Tutor_ID = p.ID"+
					" AND crs.ID = co.Course_ID"+
					" AND s.Value = \"Pending\";";
			
			stmt = (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query2);
			size += getResultSetRowCount(rset) ;
			Request [] req = new Request [size] ;
			
			
			//fill query2 data			
			int i=0;
			while (rset.next())
			{
				req[i]= new Request();
				req[i].type = Enums.RequestType.CourseAcceptance;
				req[i].tutorName = rset.getString(1)+ " " + rset.getString(2);
				req[i].tutorEmail= rset.getString(3);
				req[i].courseName = rset.getString(4)+ " " + rset.getString(5);
				i++;
			}
			
					
			//re-execute query1
			stmt = (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query1);
			
			//fill query1 Data
			while (rset.next())
			{
				req[i]= new Request();
				req[i].tutorName = rset.getString(1) + " " +rset.getString (2);
				req[i].tutorEmail= rset.getString(3) ;
				req[i].InterviewerName = rset.getString(4) + " " + rset.getString(5);
				req[i].InterviewerEmail = rset.getString (6);
				req[i].feedback = rset.getString(7);
				req[i].rating = rset.getInt(8);
				req[i].type = Enums.RequestType.InterviewFeedback;
				i++;
			}
			
			return req;
		}
		
		private Request [] getTutorRequests (int sessionNumber) throws SQLException
		{
			
			query= "Select stud.First_Name, stud.Last_Name, crs.Code, crs.Title, d.Day, t.Value, stud.Email"+
					" FROM RESERVATION r, PERSON p, Session s, Status sts, COURSE crs, TIME_SLOTS ts, Days d, Start_Times t, PERSON stud"+
					" WHERE r.Tutor_ID = p.ID"+
					" AND s.PersonID = p.ID"+
					" AND r.Status = sts.Code"+
					" AND sts.Value = \"Pending\""+
					" AND crs.ID = r.Course_ID"+
					" AND ts.Slot_Number = r.SLot_Number"+
					" AND t.Code = ts.Time"+
					" AND d.Code = ts.Day"+
					" AND stud.ID = r.Student_ID" +
					" AND s.SessionNumber = " + sessionNumber +";" ;
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query);
			int count = getResultSetRowCount(rset);
			Request req [] = new Request [count];
			
			for (int i=0; i<count; i++)
			{
				rset.next();
				req[i] = new Request();
				req[i].studentName = rset.getString(1) + " " + rset.getString(2) ;
				req[i].courseName = rset.getString(3) + " " + rset.getString(4);
				req[i].day = Enums.getDayEnum(rset.getString(5));
				req[i].time = Enums.getTimeEnum(rset.getString(6));
				req[i].type = Enums.RequestType.Reservation;
				req[i].studentEmail = rset.getString(7);

			}
			return req;
			
			
		}
		
		private Request [] getInterviewerRequests (int sessionNumber) throws SQLException
		{
			query = "select tutor.First_Name, tutor.Last_Name, tutor.Email"+
					" FROM INTERVIEW inter, PERSON p, Session sess, STATUS sts, Person tutor"+
					" WHERE  p.ID = inter.Interviewer_ID"+
					" AND sess.PersonID = p.ID"+
					" AND sts.Code = inter.Status"+
					" AND sts.Value <> \"Rated\" "+
					" AND tutor.ID = inter.Tutor_ID"+
					" AND sess.SessionNumber =" + sessionNumber + ";" ;
			stmt = (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query);
			int size = getResultSetRowCount(rset) ;
			
			Request [] req = new Request [size] ;
			
			for (int i=0; i<size; i++)
			{
				rset.next();
				req[i] = new Request();
				req[i].type = RequestType.InterviewFeedback;
				req[i].tutorName = rset.getString(1) + " "+ rset.getString(2);
				req[i].tutorEmail = rset.getString(3) ;
				
			}
			return req;
		}
		
		private int getResultSetRowCount(ResultSet resultSet) {
		    int size = 0;
		    try {
		        resultSet.last();
		        size = resultSet.getRow();
		        resultSet.beforeFirst();
		    }
		    catch(Exception ex) {
		        return 0;
		    }
		    return size;
		}
		
		@Override public String [] serachMajors (String searchQuery) throws SQLException
		{
			query = "select m.Major_Title"+ 
					" FROM MAJOR m"+
					" WHERE lower(m.Major_Title) Like concat(\"%\",lower(" + qoute(searchQuery) +"),\"%\");" ;
			stmt = (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query);
			int size = getResultSetRowCount(rset) ;
			String [] majors = new String [size] ;
			
			int i=0;
			while (rset.next())
				majors[i++] = rset.getString(1) ;
	
			return majors;
			
		}
		
		public String [] getAllCourse () throws SQLException
		{
			query = "select Code, Title from COURSE;" ;
			stmt = (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query) ;
			int size = getResultSetRowCount(rset) ;
			String courses [] = new String [size] ;
			int i=0 ;
			while (rset.next())
				courses [i++] = rset.getString(1) + " " + rset.getString(2) ;
			return courses;
		}
		
		public String getEmailByName(String name) throws SQLException {
			query  =  "select p.Email FROM PERSON p WHERE concat(p.First_Name,\" \", p.Last_Name) =" + qoute(name) + ";" ;
			System.out.println(query);
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query);
			rset.next();
			return rset.getString(1);
		}
		
		@Override public boolean acceptTutor(String email) throws SQLException //Tutor's email
        {
            query = "Update PERSON Set Tutor_Status= (SELECT Code FROM Status WHERE STATUS.Value = \"Accepted\") WHERE Email=" + qoute(email) +";" ;
            stmt = (Statement) conn.createStatement() ;
            stmt.executeUpdate(query) ;
            
            int ID = getIDbyEmail(email) ;
            
            String [] col = {"PersonID","EventTime",  "Type"};
            String [] val = {Integer.toString(ID),"NOW()", "0"};
            query = makeInsertQuery("EVENTS", col, val);
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
            
            return true ;
        }
        
        @Override public boolean rejectTutor (String email) throws SQLException
        {
            query = "Update PERSON Set Tutor_Status= (SELECT Code FROM Status WHERE STATUS.Value = \"Rejected\") WHERE Email=" + qoute(email) +";" ;
            stmt = (Statement) conn.createStatement() ;
            stmt.executeUpdate(query) ;
            
            int ID = getIDbyEmail(email) ;
            
            String [] col = {"PersonID","EventTime",  "Type"};
            String [] val = {Integer.toString(ID),"NOW()", "3"};
            query = makeInsertQuery("EVENTS", col, val);
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
            
            return true ;
        }
        @Override public boolean acceptNewCourseOffering (String email, String courseTitle) throws SQLException
        {
            int TutorID = getIDbyEmail (email) ;
            int CourseID = getCourseIDbyName(courseTitle) ;
            query = "Update COURSE_OFFERING SET Status = 0 Where Course_ID ="+ CourseID+" AND Tutor_ID="+ TutorID + ";" ;
            stmt = (Statement) conn.createStatement() ;
            stmt.executeUpdate(query) ;
            
            
            String [] col = {"PersonID","CourseID","EventTime",  "Type"};
            String [] val = {Integer.toString(TutorID), Integer.toString(CourseID) ,"NOW()", "2"};
            query = makeInsertQuery("EVENTS", col, val);
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
            
            
            
            return true;
        }
        
        @Override public boolean rejectNewCourseOffering (String email, String courseTitle) throws SQLException
        {
            int TutorID = getIDbyEmail (email) ;
            int CourseID = getCourseIDbyName(courseTitle) ;
            query = "Update COURSE_OFFERING SET Status = 2 Where Course_ID ="+ CourseID+" AND Tutor_ID="+ TutorID + ";" ;
            stmt = (Statement) conn.createStatement() ;
            stmt.executeUpdate(query) ;
            
            
            String [] col = {"PersonID","CourseID","EventTime",  "Type"};
            String [] val = {Integer.toString(TutorID), Integer.toString(CourseID) ,"NOW()", "4"};
            query = makeInsertQuery("EVENTS", col, val);
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
            
            return true;
        }
        
        @Override public boolean bookInterview (int sessionNumber, Enums.Day day, Enums.TimeSlot time) throws SQLException
        {
            
            int TutorID = getIDbySession(sessionNumber);
            int timeslot = getSlotNumber(day, time);
            
            //Check if tutor is already has a slot
            query = "select *"+
                    " From INTERVIEW"+
                    " WHERE Tutor_ID ="+ TutorID +";" ;
            stmt = (Statement) conn.createStatement();
            rset = stmt.executeQuery(query);
            
            if (rset.next()) //has a slot
                return false;
            
            //Else reserve one
            query = " select p.ID"+
                    " from AVAILABLE_TIME av, PERSON p, ROLE r "+
                    " WHERE p.Role = r.Role_Number"+
                    " AND r.Role_Title = \"Interviewer\"" +
                    " AND p.ID = av.Person_ID" + 
                    " AND av.Slot_Number =" + timeslot + ";" ;
            System.out.println(query);
            
            stmt = (Statement) conn.createStatement() ;
            rset = stmt.executeQuery(query);
            
            rset.next();
            int interviewerID = rset.getInt(1);
            
           
            query = "INSERT INTO INTERVIEW Values ("+ interviewerID + comma + TutorID + comma + timeslot + comma + "1" + comma + qoute("") 
            + comma + "0);" ;
            System.out.println(query);
            stmt.executeUpdate(query);
            
            return true;
        }
        
        @Override public boolean unbookInterview (int sessionNumber) throws SQLException
        {
            int ID = getIDbySession(sessionNumber);
            query ="delete From INTERVIEW Where Tutor_ID =" + ID + ";" ;
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
            return true;
        } 
        
        @Override public Enums.CourseStatus checkTutorStatus (int SessionNumber) throws SQLException
        {
            query = "select sts.Value"+
                    " From PERSON p, STATUS sts, Session sess" +
                    " WHERE sess.PersonID = p.ID" +
                    " AND p.Tutor_Status = sts.Code" +
                    " AND sess.SessionNumber =" + SessionNumber+ ";" ;
            stmt = (Statement) conn.createStatement();
            rset = stmt.executeQuery(query);
            rset.next();
            String status = rset.getString(1);
            if ("Accepted".equals(status))
                return CourseStatus.Accepted;
            if ("Rejected".equals(status))
                return CourseStatus.Rejected;
            if ("Pending".equals(status))
                return CourseStatus.Pending;
            return CourseStatus.Pending;
        }
        
        @Override public boolean acceptReservation (int tutorSession, String studentEmail, Enums.Day day, Enums.TimeSlot time) throws SQLException
        {
            int tutor_ID = getIDbySession(tutorSession);
            int Student_ID = getIDbyEmail(studentEmail);
            int slotNumber = getSlotNumber(day, time);
            
            //Update Reservation
            query=  " UPDATE RESERVATION"+
                    " SET Status = (SELECT Code"+
                    " FROM STATUS "+
                    " WHERE Value = \"Accepted\")"+
                    " Where Student_ID =" + Student_ID+
                    " AND Tutor_ID =" + tutor_ID +
                    " AND Slot_Number =" + slotNumber + ";" ;
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
            
            //Get course ID
            query = "select Course_ID from RESERVATION"+
                    " Where Student_ID =" + Student_ID+
                    " AND Tutor_ID =" + tutor_ID+
                    " AND Slot_Number =" + slotNumber + ";" ;
            stmt = (Statement) conn.createStatement() ;
            rset = stmt.executeQuery(query);
            rset.next();
            int Course_ID = rset.getInt(1) ;
            
            
            //Add to Events pool
            String [] col = {"CourseID", "EventTime", "PersonID", "Person2ID", "Type", "SessionDay", "SessionTime"};
            String [] val = {Integer.toString(Course_ID), "NOW()", Integer.toString(tutor_ID), Integer.toString(Student_ID), "5", qoute(day.toString()), qoute(time.toString())};
            query = makeInsertQuery("EVENT", col, val);
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
            
            return true;
        }
        
        @Override public boolean rejectReservation (int tutorSession, String studentEmail, Enums.Day day, Enums.TimeSlot time) throws SQLException
        {
            int tutor_ID = getIDbySession(tutorSession);
            int Student_ID = getIDbyEmail(studentEmail);
            int slotNumber = getSlotNumber(day, time);
            
            //Get course ID
            query = "select Course_ID from RESERVATION"+
                    " Where Student_ID =" + Student_ID+
                    " AND Tutor_ID =" + tutor_ID+
                    " AND Slot_Number =" + slotNumber + ";" ;
            stmt = (Statement) conn.createStatement() ;
            rset = stmt.executeQuery(query);
            rset.next();
            int Course_ID = rset.getInt(1) ;
            
            
            
            //Delete Reservation
            query=  " Delete From RESERVATION"+
                    " Where Student_ID =" + Student_ID+
                    " AND Tutor_ID =" + tutor_ID +
                    " AND Slot_Number =" + slotNumber + ";" ;
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
            
            
            //Add to Events pool
            String [] col = {"CourseID", "EventTime", "PersonID", "Person2ID", "Type", "SessionDay", "SessionTime"};
            String [] val = {Integer.toString(Course_ID), "NOW()", Integer.toString(tutor_ID), Integer.toString(Student_ID), "6", qoute(day.toString()), qoute(time.toString()) };
            query = makeInsertQuery("EVENT", col, val);
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
            
            return true;
        }
        
        @Override public boolean rate (int StudentSession, String Tutoremail, String courseCode,  int rateValue) throws SQLException
        {    
            //get old rating and old hours
            int TutorID = getIDbyEmail(Tutoremail);
            int CourseID = getCourseIDbyName(courseCode);
            query= "select Rating, Hourse_Taught"+
                    " FROM COURSE_OFFERING"+
                    " WHERE Tutor_ID =" + TutorID+
                    " AND Course_ID =" + CourseID + ";" ;
            stmt = (Statement) conn.createStatement();
            rset = stmt.executeQuery(query) ;
            int oldRating = rset.getInt(1);
            int oldHours = rset.getInt(2);
            
            //Update formula
            int newHours = oldHours + 1 ;
            int newRating = (( oldRating * oldHours ) + rateValue ) / newHours;
                    
            
            
            //Update Rating
            query = " Update COURSE_OFFERING" +
                    " set Rating =" + newRating + comma +
                    " Hours_Taught=" + newHours +  
                    " Where Course_ID =" + CourseID +
                    " AND Tutor_ID ="+ TutorID + ";" ;
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
            
            
            //Flag the reservation session as rated
            int StudentID = getIDbySession(StudentSession) ;
            query = "Update RESERVATION"+
                    " Set Rated = 1"+
                    " WHere Student_ID =" + StudentID +
                    " Tutor_ID =" + TutorID +
                    " Course_ID =" + CourseID + ";" ;
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
            
            return true;
        }
        
        @Override public boolean rateInterview (int InterviewerSession, String TutorEmail, int Rating, String Feedback) throws SQLException
        {
            //Update rating
            int interviewerID = getIDbySession(InterviewerSession);
            int tutorID = getIDbyEmail(TutorEmail);
            query = "UPDATE INTERVIEW"+
                    " Set Rating=" + Rating + comma +
                    " Feedback =" +  qoute(Feedback) + comma +
                    " Status = 5"+
                    " WHERE Interviewer_ID=" + interviewerID+
                    " And Tutor_ID =" + tutorID + ";" ;
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
            
            //Insert Event:
            
            
            
            return true;
        }
        
        @ Override public Events[] getEvents (int sessionNumber) throws SQLException
		{
			Enums.Role role = getRole(sessionNumber);
			if (role.equals(Enums.Role.Admin))
				return getMainEvents (sessionNumber);
			if (role.equals(Enums.Role.Student))
				return getStudentEvents (sessionNumber);
			if (role.equals(Enums.Role.Tutor))
				return getMainEvents (sessionNumber);
			return null;
		}
		
		private Events [] getMainEvents (int sessionNumber) throws SQLException
		{
			//get size of Tutor joined
			String query1 ="  select p.First_Name, p.Last_Name, p.Email"+
							" FROM EVENTS e, EVENT_TYPES et, PERSON p"+
							" WHERE et.Code = e.Type"+
							" AND p.ID = e.PersonID " +
							" AND et.Type =\"Tutor Joined\"; " ;
			stmt  = (Statement) conn.createStatement() ;
			rset = stmt.executeQuery(query1) ;
			
			int size1 = getResultSetRowCount(rset) ;
			
			
			//get size of Course Offering
			String query2 = "select p.First_Name, p.Last_Name, crs.Code, crs.Title, p.Email"+
							" FROM EVENTS e, EVENT_TYPES et,PERSON p, COURSE crs"+
							" WHERE et.Code = e.Type"+
							" AND et.Type = \"Accepted Course Offering\""+
							" AND p.ID = e.PersonID" +
							" AND crs.ID = e.CourseID ;" ;
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query2);
			int size2 = getResultSetRowCount(rset);
			
			Events [] events = new Events [size1+size2];
			//file query2 data
			int i=0;
			for (i=0; i<size2; i++)
			{
				rset.next();
				events[i]=new Events();
				events[i].PersonName = rset.getString(1) + " " + rset.getString(2) ;
				events[i].CourseCode = rset.getString(3);
				events[i].CourseTitle= rset.getString(4) ;
				events[i].PersonEmail = rset.getString(5);
				events[i].type = Enums.EventType.CourseAccepted;
				
			}
			
			//re-execute query1
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query1);
			while (rset.next())
			{
				events[i] = new Events();
				events[i].type = Enums.EventType.TutorJoined;
				events[i].PersonEmail= rset.getString(3) ;
				events[i].PersonName = rset.getString(1) + " " + rset.getString(2) ;
				i++;
			}
			
			
			return events;
			
		}
		
		private Events [] getStudentEvents (int sessionNumber) throws SQLException
		{
			Events[] events1 = getMainEvents(sessionNumber);
			int ID = getIDbySession(sessionNumber);
			String query1 = "select e.Type, crs.Code, crs.Title, p.First_Name, p.Last_Name, p.Email"+
							" From EVENTS e, PERSON P, COURSE crs"+
							" WHERE (e.Type = 2 OR e.Type=4)"+
							" AND p.ID =" + ID +
							" AND crs.ID = e.CourseID"+
							" AND p.ID = e.PersonID;";
			System.out.println(query1);
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query1);
			
			int size_query_1 = getResultSetRowCount(rset) ;
			Events[] events2 = new Events [size_query_1];
			
			for (int i=0 ; i<size_query_1; i++)
			{
				rset.next();
				events2[i] = new Events() ;
				events2[i].CourseCode = rset.getString(2) ;
				events2[i].CourseTitle = rset.getString(3) ;
				events2[i].PersonName = rset.getString(4) + " " + rset.getString(5) ;
				events2[i].PersonEmail = rset.getString(6);
				int eventType = rset.getInt(1) ;
				if (eventType==2)
					events2[i].type = Enums.EventType.CourseAccepted;
				else
					events2[i].type = Enums.EventType.CourseRejected;
				
			}
			
			//Events: Rating
			String query2 = "select p.First_Name, p.Last_Name, p.Email, crs.Code, crs.Title"+
							" FROM RESERVATION r, PERSON p, COURSE crs"+
							" WHERE Student_ID=" + ID + 
							" AND p.ID = r.Tutor_ID"+
							" AND crs.ID =  r.Course_ID"+
							" AND Rated=0;" ;
			stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery(query2);
			int size_query2 = getResultSetRowCount(rset) ;
			Events [] events3 = new Events [size_query2] ;
			
			for (int i=0; i<size_query2; i++)
			{
				rset.next();
				events3[i] = new Events();
				events3[i].type = Enums.EventType.Rating;
				events3[i].PersonName = rset.getString(1)+ " " + rset.getString(2);
				events3[i].PersonEmail = rset.getString(3);
				events3[i].CourseCode = rset.getString(4);
				events3[i].CourseTitle = rset.getString(5);
				
			}
			
			
		
			//Merge All
			int i=0;
			int len1=events1.length;
			int len2=events2.length;
			int len3=events3.length;
			Events [] Allevents = new Events [len1+len2+len3];
			for (i=0; i<len1; i++)
				Allevents [i] = events1[i];
			int j=0;
			for (i=len1 ; i<len1+len2; i++)
				Allevents [i] = events2[j++];
			j=0;
			for (i=len1+len2 ; i<len1+len2+len3; i++)
				Allevents [i] = events3[j++];
			
		
			return Allevents;
		}

}
