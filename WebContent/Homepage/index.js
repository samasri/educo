var isFirstTime = true;

$(function() {
    $("#signAsStudent").on("click",signAsStudent);
    $("#signAsTutor").on("click",showExtraInfo);
    $("#signUpTutor").on("click",signAsTutor);
    $("#username").on("keyup", login);
    
	$("#major").on("keyup", searchMajors);
});

var showExtraInfo = function() {
	$('#extraInfo').show(1000);
};

var signAsStudent = function() {
    var email = document.getElementById("email");
    var phoneNumber = document.getElementById("phoneNumber");
    var password = document.getElementById("password");
    var confirmPassword = document.getElementById("confirmPassword");
    if(password == "") {
    	alert("no password");
    	return;
    }
    if (password.value == confirmPassword.value) {
        $.ajax (
        {
            type : "POST",
            url : '/Educo/signup',
            data : { 'email': email.value, 'phoneNumber': phoneNumber.value, 'password': password.value, 'role':'student'},
            cache: false,
            success: function(data){
            	location.reload();
            },
            fail: function(){}
        });
    }
    else {
        alert("Passwords don't Match!");
    }
    disablePopup();
    
};
var signAsTutor = function() {
    var email = document.getElementById("email").value;
    var phoneNumber = document.getElementById("phoneNumber").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var name = document.getElementById("name").value;
    var major = document.getElementById("major").value;
    
    $("#extraInfo").slideDown(1000);
    if (password == confirmPassword) {

        $.ajax (
        {
            type : "POST",
            url : '/Educo/signup',
            data : { 'email': email, 'phoneNumber': phoneNumber, 'password': password, 'fname': name, 'major': major, 'role':'tutor'},
            cache: false,
            success: function(){location.reload();},
            fail: function(){}
        });
    } 
    else {
    	alert("Passwords don't Match!");
    }
};


var searchMajors = function() {
	var query = $("#major").val();

	$.ajax
    (
        {
            url:'/Educo/GetMajors',
            data:{"query":query},
            type:'get',
            cache:false,
            success:function(data){
            	var availableCourses = getArray(data);
            	$("#major").autocomplete({
            		source: availableCourses
            	});
            },
            error:function(){/*alert('error');*/}
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
		arrayOfResult[i] = arrayOfJSON[i].major;
	}
	return arrayOfResult;
};