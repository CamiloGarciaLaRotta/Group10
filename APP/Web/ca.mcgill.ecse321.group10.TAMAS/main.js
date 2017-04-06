function handleEvents() {
	// show time budgets of selected course
	$('#courseCDN').change(function() {
		updateBudget(this.value);
	});
	
	//update time budgets if hired
	$('#hire').on('click', function() {
		updateBudget($('#courseCDN').val());
	});
	
	//display application information
	$('#applicationID').on('select', function() {
		$.ajax({
			type: 'post',
			url: '/ca.mcgill.ecse321.group10.TAMAS/Controller/getAppContent.php',
			data: "id=" + this.value,
			success: function(response) {
				$('#ApplicationInfo').text(response);
			}
		});
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
	
	// if ever a handler is needed to retrieve the application
//	$('#postingCDN').on("change", function(){
//		var id = $(this).find('option:selected').attr("name");
//		console.log(id)
//		$.ajax({
//			type: 'post',
//			url: '/ca.mcgill.ecse321.group10.TAMAS/Controller/getJobData.php',
//			data: "id=" + $(this).val(),
//			success: function(response) {
//				console.log(response)
//			}
//		});
//	});
}

function updateBudget(cdn) {
	$.ajax({
		type: 'post',
		url: '../Controller/getBudget.php',
		data: { 'cdn':cdn },
		success: function(response) {
			var budget = response.split(',')
			$('#TAhours').text("TA Budget: "+budget[0]);
			$('#Graderhours').text("TA Budget: "+budget[1]);
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
	if($('main').hasClass("job") || $('main').hasClass("application") ) updateBudget($('#courseCDN').val())
	setSlider()
	handleEvents();
});