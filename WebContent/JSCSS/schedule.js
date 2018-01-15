var searchMethod = "tutor";
var isAdmin = false;

window.onload = function() {
	$("#searchInput").on("focus",search_click);
	$("#searchInput").on("focusout",exitSearch);
	$("#searchInput").on("keyup", searchRequest);
	
	$("#searchByTutor").on("click", changeSearchBy);
	$("#searchByCourse").on("click", changeSearchBy);
	$("#searchByStudent").on("click", changeSearchBy);
	
	$(".available").on("click", send);
	$(".unavailable").on("click", send);
};

var send = function() {
	var day = "" + this.id;
	var column = "" + this.parentNode.id;
	var availabilityBool = "true";
	var url = '';
	if(this.className == 'available') {
		url = "/Educo/ChangeAvailability";
		availabilityBool = "false";
		sendAjax(url, availabilityBool, day, column, this);
	}
	if(this.className == 'unavailable') {
		url = "/Educo/ChangeAvailability";
		sendAjax(url, availabilityBool, day, column, this);
	}
	if(this.className == 'booked') {
		url = "/Educo/CancelReservation";
	}
};

var sendAjax = function(url, boool, day, time, caller) {
	$.ajax
    (
        {
            url:'/Educo/ChangeAvailability',
            data:{"dayNumber":day,"timeNumber":time,"availability":boool},
            type:'post',
            cache:false,
            success:function(){
            	if(caller.className == 'available') caller.className = 'unavailable';
            	else if(caller.className == 'unavailable') caller.className = 'available';
            },
            error:function(){alert('Thank you');}
        }
    );
};

