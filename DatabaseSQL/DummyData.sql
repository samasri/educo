
select crs.ID, crs.Code, crs.Title FROM COURSE crs WHERE lower(concat(crs.Code," ",crs.Title)) Like lower("%cmps%");

select * from PHOTO;
select * from Session;
select * from STATUS;
SELECT * FROM Start_Times;
select * from AVAILABLE_TIME ;
select * from PERSON ;
select * from COURSE ;
select * from COURSE_OFFERING ;
select * from RESERVATION ;
select * from MAJOR ;
select * from Days;
select * from TIME_SLOTS;
select * from ROLE;
select * from EVENTS;
select * from EVENT_TYPES;
select * from INTERVIEW;


update PERSON p
SET p.password = "password"
where ID = 9;

Select PersonID FROM Session Where SessionNumber =80064;

update INTERVIEW set Status  = 2 Where Tutor_ID = 11;

truncate INTERVIEW;

update INTERVIEW set Status = 4
Where Interviewer_ID = 2
AND Tutor_ID = 5
AND Slot_Number = 5;


 
 select p.ID 
 from AVAILABLE_TIME av, PERSON p, ROLE r
 WHERE p.Role = r.Role_Number 
 AND r.Role_Title = "Interviewer" 
 AND av.Slot_Number =22;

select sts.Value From PERSON p, STATUS sts, Session sess WHERE sess.PersonID = p.ID AND p.Tutor_Status = sts.Code AND sess.SessionNumber =59110;



select from RESERVATION
Where Student_ID = 5
AND Tutor_ID = 6
AND Slot_Number = 0;


select tutor.First_Name, tutor.Last_Name, tutor.Email
FROM INTERVIEW inter, PERSON p, Session sess, STATUS sts, Person tutor
WHERE  p.ID = inter.Interviewer_ID
AND sess.PersonID = p.ID
AND sts.Code = inter.Status
AND sts.Value = "Done"
AND tutor.ID = inter.Tutor_ID
AND sess.SessionNumber = 500;

Update COURSE_OFFERING
set Rating = 5,
	Hours_Taught = 6
Where Course_ID = 7
AND Tutor_ID = 8;






select p.ID
from AVAILABLE_TIME av, PERSON p, ROLE r
WHERE p.Role = r.Role_Number
AND r.Role_Title = "Interviewer"
AND av.Slot_Number = 0;


select crs.Code, crs.Title, co.Hours_Taught, co.Charge_Per_Hour, co.Rating
From PERSON p, COURSE_OFFERING co, COURSE crs
WHERE co.Tutor_ID = p.ID
AND co.Course_ID = crs.ID
AND p.ID = 6;

select * from person p, Major m 
where p.Major = m.Major_Number
ANd p.ID = 6;


select crs.ID, crs.Code, crs.Title
FROM COURSE crs
WHERE lower(concat(crs.Code," ",crs.Title)) Like lower("%cmps%")


select p.First_Name, p.Last_Name
FRom Person p, COURSE_OFFERING co
WHERE p.ID = co.Tutor_ID
AND co.Course_ID = 1;


select crs.Code, crs.Title
From PERSON p, COURSE_OFFERING co, COURSE crs
Where p.ID = co.Tutor_ID
AND co.Course_ID = crs.ID
AND p.ID = 6;

select First_Name, Last_Name, count(*) AS num
FROM COURSE crs, Person p, COURSE_OFFERING co
WHERE crs.ID=1
AND co.Course_ID = crs.ID
AND co.Tutor_ID = p.ID;


select p.ID, p.First_Name, p.Last_Name, p.Phone_Number, p.Email, m.Major_Title, crs.Code, crs.Title, count(*) AS num, co.Hours_Taught, co.Rating, co.Charge_Per_Hour
from PERSON p, COURSE_OFFERING co, COURSE crs, MAJOR m
WHERE p.ID = 6
AND co.Tutor_ID = p.ID 
AND co.Status = "0"
AND crs.ID = co.Course_ID
AND m.Major_Number = p.Major;

Select t.Time, t.Day
FROM AVAILABLE_TIME a, PERSON p, TIME_SLOTS t
WHERE a.Person_ID = p.ID
AND t.Slot_Number = a.Slot_Number
AND P.Role = (SELECT Role_Number
From ROLE 
WHERE Role_Title = "Interviewer");

select p.First_Name, p.Last_Name, m.Major_Title 
FROM PERSON p, Major m
WHERE (lower(concat(First_Name," ",Last_Name)) Like lower("%samer E%")
OR lower(concat(First_Name,Last_Name)) Like lower(" + qoute("%"+samer E+"%") +"))
AND p.Major = m.Major_Number;


SELECT (Person_ID)
FROM AVAILABLE_TIME a, PERSON p, ROLE r
WHERE p.ID=a.Person_ID
AND r.Role_Number = p.Role
AND r.Role_Title = "Interviewer"


Select Role
FROM PERSON p, session s
Where s.personID = p.ID
AND s.SessionNumber=60907;


select Rating From COURSE_OFFERING 
WHERE Course_ID=1
AND Tutor_ID = 6;

UPDATE COURSE_OFFERING 
SET Status = (SELECT Code
FROM STATUS 
WHere Value = "Accepted")
Where Course_ID = 1;

SELECT Title FROM COURSE WHERE ID=1;


/*INSERT INTO COURSE (ID,Major, Code, Title) VALUES (2,3,"CMPS212", "Data Structure");*/

select ID 
From 

/*truncate Session ;*/

/*Update COURSE_OFFERING
SET Status = 0
Where Course_ID =  
AND Tutor_ID = ;*/

SELECT Title FROM COURSE 
WHERE ID=1;

#Accept a Tutor
Update PERSON
Set Tutor_Status= (SELECT Code
FROM Status
WHERE STATUS.Value = "Accepted")
WHERE Email="rk00@aub.edu.lb";


#Select Tutors with status
select p.First_Name, s.Value
From PERSON p, STATUS s, ROLE r
WHERE p.Tutor_Status = s.Code 
AND r.Role_Number = p.Role 
AND r.Role_Title="Tutor";


/*INSERT INTO RESERVATION VALUES (7,6,1,4,0,0);
truncate RESERVATION;*/



#get tutor and availability
Select p.First_Name, d.Day, t.Value 
From PERSON p, AVAILABLE_TIME a, TIME_SLOTS s, Days d, Start_Times t
WHERE p.ID = a.Person_ID
AND a.Slot_Number = s.Slot_Number
AND s.Day = D.day
AND s.Time = t.Code ; 

Select * From TIME_SLOTS ;


#get course ID by Title
Select ID
From COURSE
WHERE Title = "Software Engineering";

/*INSERT INTO RESERVATION VALUES (7,6,1,4,0,0);*/

/*Insert Into Course (ID, Major, Code, Title) Values (1,3,"CMPS", "Software Engineering");*/
