var searchMethod = "tutor";
var isAdmin = false;
var isFirstTime = true;
var inChange = false;

window.onload = function() {
	$("#uploadImg").hide();
	$("#searchInput").on("focus",search_click);
	$("#searchInput").on("focusout",exitSearch);
	$("#searchInput").on("keyup", searchRequest);
	
	$("#searchByTutor").on("click", changeSearchBy);
	$("#searchByCourse").on("click", changeSearchBy);
	$("#searchByStudent").on("click", changeSearchBy);
	
	$("#addCourse").on("click", getInput);
	$("#addCourse").on("keyup", addCourse);
	
	$("#editNumber").on("click", editNumber);
	
	$(".delete").on("click", editCourse);
	$(".hold").on("click", editCourse);
	$(".activate").on("click", editCourse);
	$("#uploadImgButton").on("click", showForm);
	
	$('td').on('click', book);
};

var getInput = function() {
	if(isFirstTime) {
		this.innerHTML = "<input id='addCourseInput'></input><br>" +
			"<input id='price' default='0' type='number'>" +
			"</input><br><button id='sendAddRequest'>Add Course</button>";
		$("#sendAddRequest").on("click", sendRequest);
	}
			
	isFirstTime = false;	
};

var addCourse = function() {
	var query = $("#addCourseInput").val();
	
	
	$.ajax
    (
        {
            url:'/Educo/GetCourses',
            data:{"query":query},
            type:'get',
            cache:false,
            success:function(data){
            	var availableCourse = getArray(data);
            	$("#addCourseInput").autocomplete({
            		source: availableCourse
            	});
            },
            error:function(){/*alert('Thank you');*/}
        }
    );
	
	
};

var getArray = function(data) {
	var arrayOfData = data.split("\n");
	var arrayOfJSON = [];
	for(var i = 0; i < arrayOfData.length-1; i++) {
		arrayOfJSON[i] = JSON.parse(arrayOfData[i]);
	}
	var arrayOfResult = [];
	for(var i = 0; i < arrayOfJSON.length; i++) {
		arrayOfResult[i] = arrayOfJSON[i].courseName;
	}
	return arrayOfResult;
};

var sendRequest = function() {
	var courseCodeAndTitle = $("#addCourseInput").val();
	var price = $("#price").val();
	
	$.ajax
    (
        {
            url:'/Educo/AddNewCourse',
            data:{"courseCodeAndTitle":courseCodeAndTitle, "price":price},
            type:'post',
            cache:false,
            success:function(data){
            	var availableCourse = getArray(data);
            	$("#addCourseInput").autocomplete({
            		source: availableCourse
            	});
            	location.reload();
            },
            error:function(){/*alert('Thank you');*/}
        }
    );
};

var editNumber = function() {
	if(inChange) {
		$("#phoneNumber").attr('readonly','true');
		$("#phoneNumber").css('background-color','#f4ebc3');
		inChange = false;
		var phoneNumber = $("#phoneNumber").val();
		this.value = 'Edit Number';
		$.ajax
	    (
	        {
	            url:'/Educo/SetPhoneNumber',
	            data:{"phoneNumber":phoneNumber},
	            type:'post',
	            cache:false,
	            success:function(){
	            	location.reload();
	            },
	            error:function(){}
	        }
	    );
	}
	else {
		this.value = 'Save';
		$("#phoneNumber").removeAttr('readonly');
		$("#phoneNumber").css('background-color','white');
		inChange = true;
	}
};

var editCourse = function() {
	var clas = this.className;
	var courseCode = this.parentNode.id;
	
	if(clas == 'delete') {
		$.ajax
	    (
	        {
	            url:'/Educo/DeleteCourse',
	            data:{"courseCode":courseCode},
	            type:'post',
	            cache:false,
	            success:function(){location.reload();},
	            error:function(){}
	        }
	    );
	}
	
	if(clas == 'hold') {
		$.ajax
	    (
	        {
	            url:'/Educo/DeactivateCourse',
	            data:{"courseCode":courseCode},
	            type:'post',
	            cache:false,
	            success:function(){location.reload();},
	            error:function(){}
	        }
	    );
	}
	
	if(clas == 'activate') {
		$.ajax
	    (
	        {
	            url:'/Educo/ActivateCourse',
	            data:{"courseCode":courseCode},
	            type:'post',
	            cache:false,
	            success:function(){location.reload();},
	            error:function(){}
	        }
	    );
	}
};

var showForm = function() {
	$("#uploadImgButton").hide(1000);
	$("#uploadImg").show(1000);
};

var book = function() {
	var day = "" + this.id;
	var time = "" + this.parentNode.id;
	if(this.className == 'available')  
		$.ajax
	    (
	        {
	            url:'/Educo/BookSession',
	            data:{"day":day,"time":time},
	            type:'post',
	            cache:false,
	            success:function(data){
	            	this.className = 'booked';
	            	location.reload();},
	            error:function(){}
	        }
	    );
	else if(this.className == 'booked') {
		$.ajax
	    (
	        {
	            url:'/Educo/CancelReservation',
	            data:{"day":day,"time":time},
	            type:'post',
	            cache:false,
	            success:function(data){
	            	this.className = 'available';
	            	location.reload();},
	            error:function(){}
	        }
	    );
	}
};