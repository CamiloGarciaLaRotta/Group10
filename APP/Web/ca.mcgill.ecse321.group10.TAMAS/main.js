// event handlers across the application
function handleEvents() {
	// show time budgets of selected course
	$('#courseCDN').change(function() {
		var cdn = $("#courseCDN").val();
		updateBudget(cdn);
		if($('main').hasClass('application')) {
			updateApplications(cdn);
		}
	});
	
	// show information about selected application
	$('#applicationID').change(function() {
		var id = $("#applicationID").val();
		// enable evaluation textarea for valid applications
		$('#evaluation').prop('disabled', false);
		updateAppInfo(id);
	});
		
	// change colour theme
	$("#checkTheme").change(function(){
		var dark = $(this).is(':checked');
		if(dark){
			$('body').addClass("dark")
			$('.btn').addClass("dark")
		} else {
			$('body').removeClass("dark")
			$('.btn').removeClass("dark")
		}
		// save the theme choice in the users $_SESSION
		$.post("/ca.mcgill.ecse321.group10.TAMAS/Controller/validateTheme.php", {"set":dark})
	});
}

// retrieve and display the courses current time budget
function updateBudget(cdn) {
	$.ajax({
		type: 'post',
		url: '../Controller/getBudget.php',
		data: { 'cdn':cdn },
		success: function(response) {
			// response: "tut,lab,grader"
			var budget = response.split(',')
			var tut = parseInt(budget[0]);
			var lab = parseInt(budget[1]);
			var grader = parseInt(budget[2]);
			
			// sont show radio button if
			// no more time is available
			if (tut <= 0) {
				tut = 0;
				$('#tut').hide();
			} else {
				$('#tut').show();
			}
			if (lab <= 0) {
				lab = 0;
				$('#lab').hide();
			} else {
				$('#lab').show();
			}
			if (grader <= 0) {
				grader = 0;
				$('#grader').hide();
			} else {
				$('#grader').show();
			}
			if(tut == 0 && lab == 0 && grader == 0) {
				$('#info').hide();
			} else {
				$('#info').show();
			}
			
			// display budgets on browser
			$('#tutHours').text("Tut. Budget: "+tut);
			$('#labHours').text("Lab. Budget: "+lab);
			$('#Graderhours').text("Grader Budget: "+grader);
		}
	});
}

// retrieve and display applications for a given course
function updateApplications(cdn){
	// clear all fields
	$('#applicationID').empty();
	$('#studentName').html("");
	$('#studentExp').html("");
	$('#evaluation').val("");
	
	$.ajax({
		type: 'post',
		url: '../Controller/getApplicationInfo.php',
		data: { 'cdn':cdn },
		success: function(response) {
			var r = JSON.parse(response)
			// disable input of evaluation if no 
			// applications are found for the course
			if (jQuery.isEmptyObject(r)) {
				$('#evaluation').prop('disabled', true);
			}
			jQuery.each(r, function(id, app) {
				$('#applicationID').append($('<option>', {
				    value: id,
				    text: app
				}));
			});
		}
	});
}

// retrieve and display information about selected application
function updateAppInfo(id){
	$.ajax({
		type: 'post',
		url: '../Controller/getApplicationInfo.php',
		data: { 'id':id },
		success: function(response) {
			var r = JSON.parse(response)
			$('#studentName').html("Student Name:<br>   "+r.student);
			$('#studentExp').html("Experience:<br>    "+r.experience);
			$('#evaluation').val(r.evaluation)
		}
	});
}

// get $_SESSION theme and place slider acordingly
function setSlider() {
	$.ajax({
		type: 'post',
		data: {get: "get"},
		url: '/ca.mcgill.ecse321.group10.TAMAS/Controller/validateTheme.php',
		success: function(response) {
			if(response === "true") $('#checkTheme').prop('checked', true);
			else  $('#checkTheme').prop('checked', false);
		}
	});
}

// main entry point
$(function() {
	console.log("Client side active");
	setSlider()
	// only update budget if on job or application pages
	if($('main').hasClass("job") || $('main').hasClass("application") ) {
		updateBudget($("#courseCDN").val());
	}
	// only update application info if on application page
	if($('main').hasClass('application')) {
		updateApplications(cdn);
		$('#evaluation').prop('disabled', true);
	}
	handleEvents();
});