//switch functionality -start
	function switchfun(){
		 var checkBox = document.getElementById("myCheck");
		    var text = document.getElementById("text");
		    if (checkBox.checked == true){
		    	alert('hello');
		      //  text.style.display = "block";
		    } else {
		    	alert('friends');
		       //text.style.display = "none";
		    }
	}
	//switch functionality -end

		//home button function-start
		function homebutton(){
			//alert("test");
			window.location.href="/";
		}
		//home button function-end



$(document).ready(function() {
	
	
		
	//fetch the job filer values using feedid-Start
						$("#feedIdFilter")
								.change(
										function() {
											var feed_id = $(this).val();
											//	alert(feed_id);
											$
													.post(
															'/hip/feedIdFilter',
															{
																feed_id : feed_id
															},
															function(data) {
																$('#table')
																		.html(
																				data);
																document
																		.getElementById('chartId').style.display = "block";
																document
																		.getElementById('table').style.display = "block";
																//alert(feed_id);
																var x = document
																		.getElementById("x").value;
																var y = document
																		.getElementById("y").value;
																var newx = x
																		.split(',');
																var newy = y
																		.split(',');
																//var a=[180801,180802,180805];
																//var b=[26.0000,13.0000,18];
																newx[0] = newx[0]
																		.replace(
																				"[",
																				"");
																newy[0] = newy[0]
																		.replace(
																				"[",
																				"")
																newx[newx.length - 1] = newx[newx.length - 1]
																		.replace(
																				"]",
																				"");
																newy[newy.length - 1] = newy[newy.length - 1]
																		.replace(
																				"]",
																				"");

																var areaData = {

																	labels : newx,
																	datasets : [ {
																		label : 'Duration of Feed in Minutes',
																		data : newy,
																		backgroundColor : [
																				'rgba(169,169,169,1)',
																				//'rgba(54, 162, 235, 0.2)',
																				'rgba(255, 206, 86, 0.2)',
																				'rgba(75, 192, 192, 0.2)',
																				'rgba(153, 102, 255, 0.2)',
																				'rgba(255, 159, 64, 0.2)',
																				'rgba(255, 99, 132, 0.2)',],
																		borderColor : [
																				'rgba(169,169,169, 1)',
																				//'rgba(255,99,132,1)',
																				'rgba(54, 162, 235, 1)',
																				'rgba(255, 206, 86, 1)',
																				'rgba(75, 192, 192, 1)',
																				'rgba(153, 102, 255, 1)',
																				'rgba(255, 159, 64, 1)' ],
																		borderWidth : 1,
																		fill : true, // 3: no fill
																	} ]
																};

																var areaOptions = {
																	plugins : {
																		filler : {
																			propagate : true
																		}
																	}
																}

																if ($("#areaChart").length) {
																	var areaChartCanvas = $(
																			"#areaChart")
																			.get(
																					0)
																			.getContext(
																					"2d");
																	var areaChart = new Chart(
																			areaChartCanvas,
																			{
																				type : 'line',
																				data : areaData,
																				options : areaOptions
																			});
																}

															});

										});

						//fetch the job filer values using feedid-End

						//search button-start

						$("#feedid_s")
								.keyup(
										function() {
											var feed_id = $(this).val();

											//feed id available check-start
											if (feed_id != '') {
													$.ajax({ 
												          type: "POST",
												          url: "/hip/filterSearch",
												          data: {feed_id: feed_id},
												          cache: false,
												          success: function(html) {
												        		$('#table')
																.html(
																		html);
														document
																.getElementById('chartId').style.display = "block";
														document
																.getElementById('table').style.display = "block";
														//alert(feed_id);
														var x = document
																.getElementById("x").value;
														var y = document
																.getElementById("y").value;
														var newx = x
																.split(',');
														var newy = y
																.split(',');
														//var a=[180801,180802,180805];
														//var b=[26.0000,13.0000,18];
														newx[0] = newx[0]
																.replace(
																		"[",
																		"");
														newy[0] = newy[0]
																.replace(
																		"[",
																		"")
														newx[newx.length - 1] = newx[newx.length - 1]
																.replace(
																		"]",
																		"");
														newy[newy.length - 1] = newy[newy.length - 1]
																.replace(
																		"]",
																		"");

														var areaData = {

															labels : newx,
															datasets : [ {
																label : 'Duration of Feed in Minutes',
																data : newy,
																backgroundColor : [
																		'rgba(169,169,169,1)',
																		//'rgba(54, 162, 235, 0.2)',
																		'rgba(255, 206, 86, 0.2)',
																		'rgba(75, 192, 192, 0.2)',
																		'rgba(153, 102, 255, 0.2)',
																		'rgba(255, 159, 64, 0.2)',
																		'rgba(255, 99, 132, 0.2)',],
																borderColor : [
																		'rgba(169,169,169, 1)',
																		//'rgba(255,99,132,1)',
																		'rgba(54, 162, 235, 1)',
																		'rgba(255, 206, 86, 1)',
																		'rgba(75, 192, 192, 1)',
																		'rgba(153, 102, 255, 1)',
																		'rgba(255, 159, 64, 1)' ],
																borderWidth : 1,
																fill : true, // 3: no fill
															} ]
														};

														var areaOptions = {
															plugins : {
																filler : {
																	propagate : true
																}
															}
														}

														if ($("#areaChart").length) {
															var areaChartCanvas = $(
																	"#areaChart")
																	.get(
																			0)
																	.getContext(
																			"2d");
															var areaChart = new Chart(
																	areaChartCanvas,
																	{
																		type : 'line',
																		data : areaData,
																		options : areaOptions
																	});
														}
												        	  
												            }
												          
												        });
												

											}

											//feed id available check-end
										});

						//search button-end
						
						
						$('#clear').click(function() {
						    location.reload();
						});

					});




function feedpage(){
	document.getElementById('feedpage').style.display = "block";
	document.getElementById('tablepage').style.display = "none";
}

function tablepage(){
	document.getElementById('feedpage').style.display = "none";
	document.getElementById('tablepage').style.display = "block";
}

