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
			url: '../Controller/getAppContent.php',
			data: "id=" + this.value,
			success: function(response) {
				$('#ApplicationInfo').text(response);
			}
		});
	});
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

$(function() {
	console.log("Client side active");
	updateBudget($('#courseCDN').val())
	handleEvents();
});