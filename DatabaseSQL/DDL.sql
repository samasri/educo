/*==============================================================*/
/* Database name:  EducoDB                                      */
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     4/21/2015 00:32:52                           */
/*==============================================================*/

drop database if exists EducoDB;

/*==============================================================*/
/*==============================================================*/
create database EducoDB;

use EducoDB;

/*==============================================================*/
/* Table: AVAILABLE_TIME                                        */
/*==============================================================*/
create table AVAILABLE_TIME
(
   Person_ID            int not null,
   Slot_Number          int not null,
   primary key (Person_ID, Slot_Number)
);

/*==============================================================*/
/* Table: COURSE                                                */
/*==============================================================*/
create table COURSE
(
   ID                   int not null,
   Code                 varchar(20) not null,
   Title                varchar(50) not null,
   Major				int,
   Description          varchar(1000),
   primary key (ID)
);

/*==============================================================*/
/* Table: COURSE_OFFERING                                       */
/*==============================================================*/
create table COURSE_OFFERING
(
   Course_ID            int not null,
   Tutor_ID             int not null,
   Hours_Taught         int,
   Rating               tinyint,
   Status               tinyint not null,
   Charge_Per_Hour      tinyint,
   Rated_Hours			int default 0,
   primary key (Course_ID, Tutor_ID)
);

/*==============================================================*/
/* Table: Days                                                  */
/*==============================================================*/
create table Days
(
   Code                 int not null,
   Day                  varchar(10) not null,
   primary key (Code)
);


/*==============================================================*/
/* Table: INTERVIEW                                             */
/*==============================================================*/
create table INTERVIEW
(
   Interviewer_ID       int not null,
   Tutor_ID             int not null,
   Slot_Number          int not null,
   Status               tinyint not null,
   Feedback             longtext,
   primary key (Interviewer_ID, Tutor_ID)
);

/*==============================================================*/
/* Table: MAJOR                                                 */
/*==============================================================*/
create table MAJOR
(
   Major_Number         int not null,
   Major_Title          varchar(50) not null,
   primary key (Major_Number)
);


/*==============================================================*/
/* Table: PERSON                                                */
/*==============================================================*/
create table PERSON
(
   ID                   int AUTO_INCREMENT not null ,
   First_Name         varchar(30) not null,
   Middle_Name        varchar(30),
   Last_Name         varchar(30),
   Phone_Number       varchar(15) not null,
   Email                varchar(30) not null unique,
   Password             varchar(30) not null,
   Major                int not null,
   Role                 int not null,
   Image                blob,
   Tutor_Status         tinyint Default 1,
   primary key (ID)
);

/*==============================================================*/
/* Table: RESERVATION                                           */
/*==============================================================*/
create table RESERVATION
(
   Student_ID           int not null,
   Tutor_ID             int not null,
   Course_ID            int not null,
   Slot_Number          int not null,
   Status               tinyint not null,
   Rated                bit not null,
   primary key (Student_ID, Tutor_ID, Course_ID, Slot_Number)
);

/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
create table ROLE
(
   Role_Number          int not null,
   Role_Title           varchar(15) not null,
   primary key (Role_Number)
);



/*==============================================================*/
/* Table: STATUS                                                */
/*==============================================================*/
create table STATUS
(
   Code                 tinyint not null,
   Value                varchar(10) not null,
   primary key (Code)
);


/*==============================================================*/
/* Table: Start_Times                                           */
/*==============================================================*/
create table Start_Times
(
   Code                 int not null,
   Num  	          int not null,
   Value                Varchar(4) not null,
   primary key (Code)
);


/*==============================================================*/
/* Table: TIME_SLOTS                                            */
/*==============================================================*/
create table TIME_SLOTS
(
   Slot_Number          int not null,
   Day                  int not null,
   Time                 int not null,
   primary key (Slot_Number)
);

/*==============================================================*/
/* Table: TIME_SLOTS                                            */
/*==============================================================*/
create table Session
(
   PersonID             int not null,
   SessionNumber        int not null,
   primary key (PersonID, SessionNumber)
);


/*==============================================================*/
/* Table: NEWSFEED                                            */
/*==============================================================*/

create table EVENTS
(
	
   EventID				int AUTO_INCREMENT not null,
   CourseID				int,
   EventTime			datetime,
   PersonID             int,
   Person2ID			int,
   SessionDay			varchar (10),
   SessionTime			varchar (10),
   Type					int not null,
   primary key (EventID)
);


create table EVENT_TYPES
(
	Code	int not null,
    Type	varchar (40) not null,
    primary key (Code)

);


create table PHOTO
(
	PersonID int not null,
    picture longblob not null,
    primary key (PersonID)
);



alter table EVENTS add constraint FK_Reference_Manual_1 foreign key (PersonID)
      references PERSON (ID) on delete restrict on update restrict;

alter table EVENTS add constraint FK_Reference_Manual_2 foreign key (CourseID)
      references COURSE (ID) on delete restrict on update restrict;

alter table EVENTS add constraint FK_Reference_Manual_3 foreign key (Type)
      references EVENT_TYPES (Code) on delete restrict on update restrict;
      
alter table PHOTO add constraint FK_Reference_Manual_4 foreign key (PersonID)
      references PERSON (ID) on delete restrict on update restrict;

alter table PERSON add constraint FK_Reference_1 foreign key (Major)
      references MAJOR (Major_Number) on delete restrict on update restrict;

alter table PERSON add constraint FK_Reference_2 foreign key (Role)
      references ROLE (Role_Number) on delete restrict on update restrict;

alter table COURSE_OFFERING add constraint FK_Reference_3 foreign key (Course_ID)
      references COURSE (ID) on delete restrict on update restrict;

alter table COURSE_OFFERING add constraint FK_Reference_4 foreign key (Tutor_ID)
      references PERSON (ID) on delete restrict on update restrict;

alter table AVAILABLE_TIME add constraint FK_Reference_5 foreign key (Person_ID)
      references PERSON (ID) on delete restrict on update restrict;

alter table AVAILABLE_TIME add constraint FK_Reference_6 foreign key (Slot_Number)
      references TIME_SLOTS (Slot_Number) on delete restrict on update restrict;

alter table INTERVIEW add constraint FK_Reference_10 foreign key (Interviewer_ID)
      references PERSON (ID) on delete restrict on update restrict;

alter table INTERVIEW add constraint FK_Reference_11 foreign key (Tutor_ID)
      references PERSON (ID) on delete restrict on update restrict;

alter table INTERVIEW add constraint FK_Reference_12 foreign key (Slot_Number)
      references TIME_SLOTS (Slot_Number) on delete restrict on update restrict;


alter table COURSE add constraint FK_Reference_18 foreign key (Major)
      references MAJOR (Major_Number) on delete restrict on update restrict;

alter table COURSE_OFFERING add constraint FK_Reference_20 foreign key (Status)
      references STATUS (Code) on delete restrict on update restrict;

alter table INTERVIEW add constraint FK_Reference_21 foreign key (Status)
      references STATUS (Code) on delete restrict on update restrict;

alter table PERSON add constraint FK_Reference_19 foreign key (Tutor_Status)
      references STATUS (Code) on delete restrict on update restrict;

alter table RESERVATION add constraint FK_Reference_13 foreign key (Course_ID, Tutor_ID)
      references COURSE_OFFERING (Course_ID, Tutor_ID) on delete restrict on update restrict;

alter table RESERVATION add constraint FK_Reference_14 foreign key (Slot_Number)
      references TIME_SLOTS (Slot_Number) on delete restrict on update restrict;

alter table RESERVATION add constraint FK_Reference_15 foreign key (Student_ID)
      references PERSON (ID) on delete restrict on update restrict;

alter table RESERVATION add constraint FK_Reference_22 foreign key (Status)
      references STATUS (Code) on delete restrict on update restrict;

alter table TIME_SLOTS add constraint FK_Reference_16 foreign key (Day)
      references Days (Code) on delete restrict on update restrict;

alter table TIME_SLOTS add constraint FK_Reference_17 foreign key (Time)
      references Start_Times (Code) on delete restrict on update restrict;

alter table Session add constraint FK_Reference_30 foreign key (PersonID)
      references PERSON (ID) on delete restrict on update restrict;
      
ALTER TABLE INTERVIEW ADD COLUMN Rating INT DEFAULT 0;