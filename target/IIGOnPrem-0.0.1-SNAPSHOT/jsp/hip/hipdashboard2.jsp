<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Dashboard</title>
  <!-- plugins:css -->
  <link rel="stylesheet" href="/assets/iconfonts/mdi/css/materialdesignicons.min.css">
  <link rel="stylesheet" href="/assets/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- inject:css -->
  <link rel="stylesheet" href="/assets/css/style2.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="/assets/img/favicon.ico" />
  <!-- Include multi.js -->
  <link rel="stylesheet" type="text/css" href="../assets/css/multi.min.css">
  <script src="../assets/js/multi.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>

<script type="text/javascript">
$(document).ready(function() {
	//fetch the job filer values using feedid-Start
	$("#feedIdFilter").change(function() {
		var feed_id = $(this).val();
		alert(feed_id);
		$.post('/schedule/feedIdFilter', {		
			feed_id : feed_id	
		}, function(data) {
			//document.getElementById('chartId').style.display= "none";
			$('#jobIdFilter').html(data);
				
			//start job filter
				$("#lstJobId").change(function() {
					var job_id = $(this).val();
					alert("value of job id"+job_id);
					$.post('/schedule/jobIdFilter', {
						job_id : job_id	,
						feed_id :feed_id
					}, function(result) {
						
						$('#testValue').html(result);
						
						document.getElementById('testValue').style.display= "block";
						var a = eval('('+'${x}'+')'); 

						//var x = '${x}';
						alert(a);
						
						
						
						
					})
					
					
					
					
				});
			
				//end job filter
			
			
			});
		
		});
	
		//fetch the job filer values using feedid-End
	
	
		
	});
	

</script>
<body>


		<div class="row" >
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h2 class="card-title">Run Statics</h2>	
						<form class="forms-sample" id="runform" name="runform"
							method="post" action="/schedule/selectFeedId">
							<fieldset class="fs">
							<div class="row">
								<div class="form-group col-md-6 ">
										<label>Select Feed</label> <select class="form-control"
											id="feedIdFilter" name="feedIdFilter">
											<option value="" selected disabled>Feed Data...</option>
											<c:forEach items="${feed_id}" var="feed_id">
												<option value="${feed_id}">${feed_id}</option>
											</c:forEach>
										</select>
								</div>
								<div class="form-group col-md-6 " id="jobIdFilter">
								</div>
								</div>
								<div class="row" style="display: none;" id="testValue">
			
								</div>
							</fieldset>							
						</form>		
					</div>
				</div>
			</div>
			
			
		</div>
		
		<div class="row" id="chartId" style="display: none;">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
                  		<h4 class="card-title">Run chart</h4>
                  		<canvas id="areaChart" style="height:250px"></canvas>
               		 </div>
				</div>
			</div>
		</div>



<script src="../../assets/js/vendor.bundle.base.js"></script>
  <script src="../../assets/js/vendor.bundle.addons.js"></script>
  <script src="../../assets/js/off-canvas.js"></script>
  <script src="../../assets/js/misc.js"></script>
  <script src="../../assets/js/dashboard.js"></script>
</body>
</html>