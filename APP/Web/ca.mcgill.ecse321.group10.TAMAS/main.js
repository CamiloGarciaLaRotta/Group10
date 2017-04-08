function handleEvents() {
	// show time budgets of selected course
	$('#courseCDN').change(function() {
		var cdn = $("#courseCDN").val();
		updateBudget(cdn);
		if($('main').hasClass('application')) {
			updateApplications(cdn);
		}
	});
	
	$('#applicationID').change(function() {
		var id = $("#applicationID").val();
		$('#evaluation').prop('disabled', false);
		updateAppInfo(id);
	});
		
	$("#chk").change(function(){
		var dark = $(this).is(':checked');
		if(dark){
			$('body').addClass("dark")
			$('.btn').addClass("dark")
		} else {
			$('body').removeClass("dark")
			$('.btn').removeClass("dark")
		}
		$.post("/ca.mcgill.ecse321.group10.TAMAS/Controller/validateTheme.php", {"set":dark})
	});
}

function updateBudget(cdn) {
	//console.log(cdn)
	$.ajax({
		type: 'post',
		url: '../Controller/getBudget.php',
		data: { 'cdn':cdn },
		success: function(response) {
			//console.log(response)
			var budget = response.split(',')
			var ta = parseInt(budget[0]);
			var grader = parseInt(budget[1]);
			//console.log(ta + " " + grader)
			if (ta <= 0) {
				ta = 0;
				$('#ta').hide();
			} else {
				$('#ta').show();
			}
			if (grader <= 0) {
				grader = 0;
				$('#grader').hide();
			} else {
				$('#grader').show();
			}
			if(ta ==0 && grader == 0) {
				$('#info').hide();
			} else {
				$('#info').show();
			}
			$('#TAhours').text("TA Budget: "+ta);
			$('#Graderhours').text("Grader Budget: "+grader);
		}
	});
}

function updateApplications(cdn){
	$('#applicationID').empty();
	$('#studentName').html("");
	$('#studentExp').html("");
	$('#evaluation').val("");
	//console.log(cdn)
	$.ajax({
		type: 'post',
		url: '../Controller/getApplicationInfo.php',
		data: { 'cdn':cdn },
		success: function(response) {
			var r = JSON.parse(response)
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

function updateAppInfo(id){
	//console.log(id)
	$.ajax({
		type: 'post',
		url: '../Controller/getApplicationInfo.php',
		data: { 'id':id },
		success: function(response) {
			//console.log(response)
			var r = JSON.parse(response)
			$('#studentName').html("Student Name:<br>   "+r.student);
			$('#studentExp').html("Experience:<br>    "+r.experience);
			$('#evaluation').val(r.evaluation)
		}
	});
}

function setSlider() {
	$.ajax({
		type: 'post',
		data: {get: "get"},
		url: '/ca.mcgill.ecse321.group10.TAMAS/Controller/validateTheme.php',
		success: function(response) {
			//console.log(response)
			if(response === "true") $('#chk').prop('checked', true);
			else  $('#chk').prop('checked', false);
		}
	});
}

$(function() {
	console.log("Client side active");
	setSlider()
	if($('main').hasClass("job") || $('main').hasClass("application") ) {
		var cdn = $("#courseCDN").val();
		updateBudget(cdn);
	}
	if($('main').hasClass('application')) {
		updateApplications(cdn);
		$('#evaluation').prop('disabled', true);
	}
	handleEvents();
});