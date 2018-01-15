var searchMethod = "tutor";
var isAdmin = false;
var params = {email:[], courseTitle:[]};
var tutor = "";
var ratingG;
$(function() {
	getRequests();	
	//Continue onload in the next method
});

var continueExection = function() {
	$("#feedBack").hide();
	//setHeight();
	$("#searchInput").on("focus",search_click);
	//$("#searchInput").on("focusout",exitSearch);
	
	$("#searchInput").on("keyup", searchRequest);
	$(".requests a").on("click", remove);
	$(".checkFeedback").on("click", feedback);
	$("#searchByTutor").on("click", changeSearchBy);
	$("#searchByCourse").on("click", changeSearchBy);
	$("#searchByStudent").on("click", changeSearchBy);
	$("#checkFeedback").on("click", feedback);
	getRatings();
};

//Compute the heights of all elements to set the height of the content div
var setHeight = function() {
	//check each child's height, and compute the sum
	var totalHeight = 0;
	var headerHeight = $("#header").outerHeight();
	var bodyHeight = max("left", "left1", "right1", "rightS", "rightI", "leftA", "rightA");
	totalHeight = headerHeight + bodyHeight;
	$("#content").height(totalHeight);
};

var max = function(s1, s2, s3, s4, s5, s6, s7) {
	s1 = $("."+s1).outerHeight();
	s2 = $("."+s2).outerHeight();
	s3 = $("."+s3).outerHeight();
	s4 = $("."+s4).outerHeight();
	s5 = $("."+s5).outerHeight();
	s6 = $("."+s6).outerHeight();
	s7 = $("."+s7).outerHeight();
	var temp1 = Math.max(s1, s2);
	var temp2 = Math.max(s3, s4);
	var temp3 = Math.max(s5, s6);
	temp1 = Math.max(temp1, temp2);
	temp3 = Math.max(temp3, s7);
	return Math.max(temp1, temp3);
};



var remove = function() {
	if(this.className.indexOf("checkFeedback") == -1) {
		var status = this.innerHTML;
		var parent = this.parentNode;
		parent.innerHTML = "Thank you for your feedback";
		var grandParent = parent.parentNode;
		$(parent).delay(1000).fadeOut(1000, function(){
		   $(this).remove();
		   if (grandParent.childNodes.length == 5) {
				document.getElementById("requests").innerHTML = "<p class='tasks'>You have no more tasks :)</p>";
			}
			setHeight();
		});
	}
};

var feedback = function() {
	$("#searchByStudent").hide();
	$("#searchByTutor").hide();
	$("#searchByCourse").hide();
	$("#feedBack").show();
	isAdmin = true; //In order to activate the searchByStudent when closing the popup
};

var getRequests = function() {
	$.ajax
    (
        {
            url:'/Educo/GetRequests',
            data:{},
            type:'get',
            cache:false,
            success:function(data){
            	var array = parseJSON(data);
            	if(typeof(array[0]) != 'undefined') {
	            	if(array[0].type.toLowerCase().trim() == 'courseacceptance') writeCourseAcceptance(array);
	            	if(array[0].type.toLowerCase().trim() == 'tutoracceptance') writeTutorAcceptance(array);
	            	if(array[0].type.toLowerCase().trim() == 'reservation') writeReservation(array);
	            	if(array[0].type.toLowerCase().trim() == 'interviewfeedback') writeInterviewFeedback(array);
            	}
            	continueExection();
            },
            error:function(){alert('Thank you');}
        }
    );
};

var  parseJSON = function(data) {
	var arrayTemp = data.split('\n');
	var array = []; 
	for(var i = 0; i < arrayTemp.length-1; i++) {
		array[i] = JSON.parse(arrayTemp[i]);
	}
	return array;
};

var writeCourseAcceptance = function(array) {
	for(var i = 0; i < array.length; i++) {
		var current = array[i];
		var p = document.createElement('p');
		p.className = 'requests';
		p.innerHTML = '<a href="/Educo/PersonProfile?email=' + current.tutorEmail + '">' +  
			current.tutorName + '</a>' + ' requested teaching ' + current.courseName + '<br>';
		document.getElementById('requests').appendChild(p);
		createAcceptRejectButtons(p, sendCourseAcceptanceRequest, current.tutorEmail, current.courseName);
	}
};

var createAcceptRejectButtons = function(p, handler, email, courseName, day, time) {
	var a = document.createElement('a');
	a.innerHTML = 'Accept';
	a.className = 'button twitter';
	p.appendChild(a);
	a.onclick = handler;
	a.id=email+"---"+courseName+"---"+day+"---"+time;
	a = document.createElement('a');
	a.innerHTML = 'Reject';
	a.className = 'button twitter';
	a.onclick = handler;
	a.id=email+"---"+courseName+"---"+day+"---"+time;
	p.appendChild(a);
};

var writeTutorAcceptance = function(array) {
	for (var i = 0; i < array.length; i++) {
		var current = array[i];
		var p = document.createElement('p');
		p.className = 'requests';
		p.innerHTML = '<a href="/Educo/PersonProfile?email=' + current.tutorEmail + '"> ' +
				current.tutorName + "</a> was interviewed by <a href='/Educo/PersonProfile?email=" + 
				current.interviewerEmail + "'> " + current.interviewerName + '</a> and was rated ' + 
				current.rating + '/5';
		document.getElementById('requests').appendChild(p);
		createAcceptRejectButtons(p, writeTutorAcceptanceRequests, email);
	}
};

var writeReservation = function(array) {
	for(var i = 0; i < array.length; i++) {
		var current = array[i];
		var p = document.createElement('p');
		p.className = 'requests';
		p.innerHTML = '<a href="/Educo/PersonProfile?email=' + current.studentEmail + '">' +  
		current.studentName + '</a> requested a session on ' + current.day + ' ' + 
		current.time + 'for <a href="/Educo/CourseProfile?courseCode=' + current.courseCode + '">' +  
		current.courseName + '</a>';
		document.getElementById('requests').appendChild(p);
		createAcceptRejectButtons(p, writeReservationRequests, current.studentEmail, current.courseName, current.day, current.time);
	}
};

var writeInterviewFeedback = function(array) {
	for(var i = 0; i < array.length; i++) {
		var current = array[i];
		var p = document.createElement('p');
		p.className = 'requests';
		p.innerHTML = 'Please rate <a href="/Educo/PersonProfile?email=' + current.tutorEmail + '">' +  
		current.tutorName + '</a>';
		a = document.createElement('a');
		a.className = 'checkFeedback topopup button twitter';
		
		
		a.innerHTML = "Feedback";	 
	
		
		p.appendChild(a);
		p.id = current.tutorEmail;
		document.getElementById('requests').appendChild(p);
		createAcceptRejectButtons(p, sendInterviewFeedback, current.tutorEmail, tutor.courseName, tutor.day, tutor.time);
	}
};

var sendCourseAcceptanceRequest = function() {
	var attr = this.id.split('---');
	var email = attr[0];
	var type = 'courseAcceptance';
	var courseTitle = attr[1];
	var action;
	if(this.innerHTML == 'Accept') {
		action = 'accept';
		$.ajax
	    (
	        {
	            url:'/Educo/AcceptReject',
	            data:{"email": email, "courseTitle":courseTitle, "action": action, "type": type},
	            type:'post',
	            cache:false,
	            success:function(){},
	            error:function(){alert('Thank you');}
	        }
	    );
	}
	else if(this.innerHTML == 'Reject') {
		action = 'reject';
		$.ajax
	    (
	        {
	            url:'/Educo/AcceptReject',
	            data:{"email": email, "courseTitle":courseTitle, "action": action, "type": type},
	            type:'post',
	            cache:false,
	            success:function(){},
	            error:function(){alert('Thank you');}
	        }
	    );
	}
};

var writeTutorAcceptanceRequests = function() {
	var type = 'tutorAcceptance';
	var email = this.id.split('---')[0];
};

var writeReservationRequests = function() {
	var attr = this.id.split('---');
	var email = attr[0];
	var type = 'reservation';
	var courseTitle = attr[1];
	var day = attr[2];
	var time = attr[3];
	var action;
	if(this.innerHTML == 'Accept') {
		action = 'accept';
		$.ajax
	    (
	        {
	            url:'/Educo/AcceptReject',
	            data:{"email": email, "courseTitle":courseTitle, "action": action, "type": type, "day": day, "time": time},
	            type:'post',
	            cache:false,
	            success:function(){},
	            error:function(){alert('Thank you');}
	        }
	    );
	}
	else if(this.innerHTML == 'Reject') {
		action = 'reject';
		$.ajax
	    (
	        {
	            url:'/Educo/AcceptReject',
	            data:{"email": email, "courseTitle":courseTitle, "action": action, "type": type, "day": day, "time": time},
	            type:'post',
	            cache:false,
	            success:function(){},
	            error:function(){alert('Thank you');}
	        }
	    );
	}
};

var feedback = function() {
	if(document.getElementById('getFeedback') != null) {
		var email = this.parentNode.id;
		$.ajax
	    (
	        {
	            url:'/Educo/GetFeedback',
	            data:{"email": email},
	            type:'get',
	            cache:false,
	            success:function(data){
	            	$("#getFeedback").val(data);
	            	location.reload();},
	            error:function(){alert('Thank you');}
	        }
	    );
	}
	$("#dialog").dialog();
	tutor = this.parentNode.id;
	$("#sendFeedback").on("click", sendFeedback);
};

var sendFeedback = function() {
	var feedback = $("#feedbackContent").val();
	var rating = $("#rating").val();
	$.ajax
    (
        {
            url:'/Educo/submitFeedback',
            data:{"email": tutor, "feedback":feedback, "rating": rating},
            type:'post',
            cache:false,
            success:function(){location.reload();},
            error:function(){alert('Thank you');}
        }
    );
};

var getRatings = function() {
	$.ajax
    (
        {
            url:'/Educo/GetRatings',
            data:{},
            type:'post',
            cache:false,
            success:function(data){
            	var array = parseJSON(data);
            	var newsfeed = document.getElementById('newsfeed');
            	for(var i = 0; i < array.length; i++) {
            		var current = array[i];
            		var p = document.createElement('p');
            		p.innerHTML = 'Rate your session with <a href="/Educo/PersonProfile?email=' + current.email + '"' + current.name + '</a>: ';
            		newsfeed.appendChild(p);
            		p.innerHTML += '<br>Input: ';
            		var input = document.createElement('input');
            		input.type = 'number';
            		input.onkeyup = function() {
            			ratingG = input.value;
            		};
            		p.appendChild(input);
            		var button = document.createElement('button');
            		button.innerHTML = 'Save';
            		p.appendChild(button);
            		p.id = current.courseCode + '---' + current.email;
            		button.onclick = sendRating;
            	}
            },
            error:function(){alert('Thank you');}
        }
    );
};

var sendRating = function() {
	var rating = ratingG;
	var attr = this.parentNode.id.split('---');
	var courseCode = attr[0];
	var email = attr[1];
	$.ajax
    (
        {
            url:'/Educo/submitRating',
            data:{"email": email, "rating":rating, "courseCode": courseCode},
            type:'post',
            cache:false,
            success:function(){location.reload();},
            error:function(){alert('Thank you');}
        }
    );
};

var sendInterviewFeedback = function() {
	var attr = this.parentNode.id.split();
	var email = attr[0];
	var action = "";
	if(this.innerHTML.toLowerCase()=='accept') action = 'accept';
	else action='reject';
	$.ajax
    (
        {
            url:'/Educo/AcceptReject',
            data:{"email": email, "type":"TutorAcceptance", "action": action},
            type:'post',
            cache:false,
            success:function(){location.reload();},
            error:function(){alert('Thank you');}
        }
    );
};