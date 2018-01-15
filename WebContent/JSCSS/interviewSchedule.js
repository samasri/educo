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
	
	$('td').on('click', book);
};


var showForm = function() {
	$("#uploadImgButton").hide(1000);
	$("#uploadImg").show(1000);
};

var book = function() {
	var day = "" + this.id;
	var time = "" + this.parentNode.id;
	var action = "";
	if(this.className == 'available')  {
		action = "book";
		$.ajax
	    (
	        {
	            url:'/Educo/BookInterview',
	            data:{"action":action, "day":day,"time":time},
	            type:'post',
	            cache:false,
	            success:function(data){
	            	location.reload();},
	            error:function(){}
	        }
	    );
	}
	else if(this.className == 'booked') {
		action = "unbook";
		$.ajax
	    (
	        {
	            url:'/Educo/BookInterview',
	            data:{"action":action, "day":day, "time":time},
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