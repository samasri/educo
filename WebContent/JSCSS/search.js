var changeSearchBy = function() {
	if(this.id == "searchByTutor") {
		searchMethod = "tutor";
	}
	if(this.id == "searchByCourse") {
		searchMethod = "course";
	}
	if(this.id == "searchByStudent") {
		searchMethod = "student";
	}
	disablePopup();
};

var search_click = function() {
		if($("#searchInput").is(":focus")) {
			$("#searchInput").val("");
			hideForSearch();
			//$("#searchDiv").css("height", "500px");
			//$("#searchDiv").css("width", "500px");
			$("#searchDiv").css("visibility", "visible");
		}
};

var hideForSearch = function() {
	$(".left").hide(1000);
	$(".right").hide(1000);
	$(".left1").hide(1000);
	$(".lefts").hide(1000);
	$(".right1").hide(1000);
	$(".rightI").hide(1000);
	$(".rightS").hide(1000);
	$(".rights").hide(1000);
	$(".leftA").hide(1000);
	$(".rightA").hide(1000);
};

var showFromSearch = function() {
	$(".left").show(1000);
	$(".right").show(1000);
	$(".left1").show(1000);
	$(".lefts").show(1000);
	$(".right1").show(1000);
	$(".rightI").show(1000);
	$(".rightS").show(1000);
	$(".rights").show(1000);
	$(".leftA").show(1000);
	$(".rightA").show(1000);
};

var exitSearch = function() {
	deleteAllResults();
	$("#searchDiv").css("visibility","hidden");
	$("#searchInput").val("Search Here");
	showFromSearch();
};
var deleteAllResults = function() {
	for(var i = 0; i <= 100; i++) {
		$("#res"+i).remove();
	}
};

var searchRequest = function() {
	var query = this.value;
	var query = this.value;
	$.ajax
    (
        {
            url:'/Educo/returnSearch',
            data:{"query":query,"type":searchMethod},
            type:'get',
            cache:false,
            success:function(data){search(data);},
            error:function(){alert('Thank you');;}
        }
    );
};

var search = function(data) {
	if(searchMethod == "tutor") searchTutors(data);
	else if(searchMethod == "course") searchCourses(data);
	else searchStudents(data);
};

var searchTutors = function(data) {
	deleteAllResults();
	var arrayOfData = data.split("<br>\n");
	var arrayOfJSON = [];
	for(var i = 0; i < arrayOfData.length-1; i++) {
		arrayOfJSON[i] = JSON.parse(arrayOfData[i]);
		arrayOfJSON[i].url = "/Educo/PersonProfile?email="+arrayOfJSON[i].email;
	}
	for(var i = 0; i < arrayOfJSON.length;i++) {
		var result = document.createElement("div");
		result.id = 'res'+i;
		document.getElementById("searchDiv").appendChild(result);
		$("#res"+i).hide();
		$("#res"+i).attr("class", "results");
		$("#res"+i).append("<a href='" + arrayOfJSON[i].url + 
				"'><img class='resPics' src='" + arrayOfJSON[i].imgURL + 
				"'></a> <b>Tutor:</b> " + arrayOfJSON[i].name + 
				"<br><b>Courses:</b> " + arrayOfJSON[i].courses);
		$("#res"+i).show(1000);
	}
};

var searchStudents = function(data) {
	deleteAllResults();
	var arrayOfData = data.split("<br>\n").slice(2,data.length);
	var arrayOfJSON = [];
	for(var i = 0; i < arrayOfData.length-1; i++) {
		arrayOfJSON[i] = JSON.parse(arrayOfData[i]);
		arrayOfJSON[i].href="/Educo/PersonProfile?email="+arrayOfJSON[i].email;
	}
	for(var i = 0; i < arrayOfJSON.length-1;i++) {
		var result = document.createElement("div");
		result.id = 'res'+i;
		document.getElementById("searchDiv").appendChild(result);
		$("#res"+i).hide();
		$("#res"+i).attr("class", "results");
		$("#res"+i).append("<a href=" + arrayOfJSON[i].href + 
				"<img class='resPics' src='" + arrayOfJSON[i].imgURL + 
				"'></a> <b>Name:</b> " + arrayOfJSON[i].name);
		$("#res"+i).show(1000);
	}
};

var searchCourses = function(data) {
	deleteAllResults();
	var arrayOfData = data.split("<br>\n");
	var arrayOfJSON = [];
	for(var i = 0; i < arrayOfData.length-1; i++) {
		arrayOfJSON[i] = JSON.parse(arrayOfData[i]);
	}

	for(var i = 0; i < arrayOfJSON.length;i++) {
		var result = document.createElement("div");
		result.id = 'res'+i;
		document.getElementById("searchDiv").appendChild(result);
		$("#res"+i).hide();
		$("#res"+i).attr("class", "results");
		$("#res"+i).append("<a href='/Educo/CourseProfile?courseCode=" + arrayOfJSON[i].courseCode + "'> <img class='resPics' src='" + arrayOfJSON[i].imgURL + 
				"'></a> <b>Course:</b> " + arrayOfJSON[i].name);
		$("#res"+i).show(1000);
	}
};