// show time budgets of selected course
$('#courseCDN').on('change', function() {
	console.log(this.value);
	updateBudget(this.value);
});

//update time budgets if hired
$('#hire').on('click', function() {
	var cdn = $('#courseCDN').val();
	console.log(cdn);
	updateBudget(cdn);
});

//display application information
$('#applicationID').on('change', function() {
	console.log(this.value);
	$.ajax({
		type: 'post',
		url: 'getAppContent.php',
		data: "id=" + this.value,
		success: function(response) {
			$('#ApplicationInfo').text(response);
		}
	});
});

function updateBudget(cdn) {
	$.ajax({
		type: 'post',
		url: 'getBudget.php',
		data: "cdn=" + cdn,
		success: function(response) {
			var budget = response.split(',')
			$('#TAhours').text("<strong>TA Budget: </strong>"+budget[0]);
			$('#Graderhours').text("<strong>TA Budget: </strong>"+budget[1]);
		}
	});
}

$(function() {
	console.log("Client side active")
});