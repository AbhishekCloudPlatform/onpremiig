<!DOCTYPE html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
  <meta charset="utf-8" />
  <!--<link rel="apple-touch-icon" sizes="76x76" href="./material/img/apple-icon.png">
  <link rel="icon" type="image/png" href="./material/img/favicon.png"> -->
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <title>
    Juniper
  </title>
  <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
  <!--     Fonts and icons     -->
  <!--<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">-->
  <!-- CSS Files -->
  <link href="../../material/css/material-kit.css?v=2.0.4" rel="stylesheet" />
  <!-- CSS Just for demo purpose, don't include it in your project -->
  <link href="../../material/demo/demo.css" rel="stylesheet" />
  
  
  <link rel="stylesheet" href="../../assets/iconfonts/mdi/css/materialdesignicons.min.css">
  <link rel="stylesheet" href="../../assets/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- inject:css -->
  <link rel="stylesheet" href="../../assets/css/style2.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="../../assets/img/favicon.ico" />
  <!-- Include multi.js -->
  <link rel="stylesheet" type="text/css" href="../../assets/css/multi.min.css">
  <script src="../../assets/js/multi.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function() {
	//fetch the job filer values using feedid-Start
	$("#feedIdFilter").change(function() {
		var feed_id = $(this).val();
	//	alert(feed_id);
		$.post('/hip/feedIdFilter', {		
			feed_id : feed_id	
		}, function(data) {
				$('#table').html(data);	
				  document.getElementById('chartId').style.display= "block";
				  document.getElementById('table').style.display= "block";
			//alert(feed_id);
		var x=document.getElementById("x").value;
		var y=document.getElementById("y").value;
		var newx = x.split(',');
		var newy = y.split(',');
		//var a=[180801,180802,180805];
		//var b=[26.0000,13.0000,18];
		newx[0]=newx[0].replace("[","");
		newy[0]=newy[0].replace("[","")
		newx[newx.length-1]=newx[newx.length-1].replace("]","");
		newy[newy.length-1]=newy[newy.length-1].replace("]","");
		
		 var areaData = {
				
				    labels: newx,
				    datasets: [{
				      label: 'Duration in Minutes',
				      data: newy,
				      backgroundColor: [
				       // 'rgba(255, 99, 132, 0.2)',
				        'rgba(54, 162, 235, 0.2)',
				        'rgba(255, 206, 86, 0.2)',
				        'rgba(75, 192, 192, 0.2)',
				        'rgba(153, 102, 255, 0.2)',
				        'rgba(255, 159, 64, 0.2)'
				      ],
				      borderColor: [
				        //'rgba(255,99,132,1)',
				        'rgba(54, 162, 235, 1)',
				        'rgba(255, 206, 86, 1)',
				        'rgba(75, 192, 192, 1)',
				        'rgba(153, 102, 255, 1)',
				        'rgba(255, 159, 64, 1)'
				      ],
				      borderWidth: 1,
				      fill: true, // 3: no fill
				    }]
				  };

				  var areaOptions = {
				    plugins: {
				      filler: {
				        propagate: true
				      }
				    }
				  }
				  
				  if ($("#areaChart").length) {
					    var areaChartCanvas = $("#areaChart").get(0).getContext("2d");
					    var areaChart = new Chart(areaChartCanvas, {
					      type: 'line',
					      data: areaData,
					      options: areaOptions
					    });
					  }
		
		
				
		});
	
		//fetch the job filer values using feedid-End
	
	});
		
	});
	

</script>
<body class="profile-page sidebar-collapse" data-parallax="true" style="background-image: url('./material/img/city-profile.jpg');">

  <div class="page-header header-filter" style="height: 100px; " >  </div>
  <div class="main main-raised">
    <div class="profile-content">
      <div class="container">
			<div class="tab-content tab-space">
          <div class="tab-pane active text-center gallery" id="studio">
          <div class="row">
          <div class="mt-3" align="left">
                	<a href="/">Home</a>
                </div>
          </div>
            <div class="row">

			
					<div class="card-body">
						<h2 class="card-title">Run Statics</h2>	
						<form class="forms-sample" id="runform" name="runform"
							method="post" action="/schedule/selectFeedId">
							<fieldset class="fs">
							<div class="row">
								<div class="form-group col-md-4">
								</div>
								<div class="form-group col-md-4 ">
										<h4 class="card-title">Select Feed</h4>
										 <select class="form-control"
											id="feedIdFilter" name="feedIdFilter">
											<option value="" selected disabled>Feed Data...</option>
											<c:forEach items="${feed_id}" var="feed_id">
												<option value="${feed_id}">${feed_id}</option>
											</c:forEach>
										</select>
								</div>
								<div class="form-group col-md-4" id="hello">
			
								</div>
								</div>
								<div class="row" style="display: none;" id="chartId" style="display: none;">
									<h4 class="card-title">Run chart</h4>
                  					<canvas id="areaChart" style="height:250px"></canvas>
								</div>
								<br>
								<div class="row" style="display: none;" id="table" style="display: none;">
								
                  					
								</div>
							</fieldset>							
						</form>		
					</div>
				</div>
			<%-- <div class="row" id="chartId" style="display: none;">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
                  		<h4 class="card-title">Run chart</h4>
                  		<canvas id="areaChart" style="height:250px"></canvas>
               		 </div>
				</div>
			</div>
		</div> --%>
              </div>
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

	

  <!--   Core JS Files   -->
  <!-- <script src="../../material/js/core/jquery.min.js" type="text/javascript"></script> -->
  <script src="../../material/js/core/popper.min.js" type="text/javascript"></script>
  <script src="../../material/js/core/bootstrap-material-design.min.js" type="text/javascript"></script>
  <script src="../../material/js/plugins/moment.min.js"></script>
   <!-- Control Center for Material Kit: parallax effects, scripts for the example pages etc -->
<!--   <script src="../../material/js/material-kit.js?v=2.0.4" type="text/javascript"></script> -->
  
  
   <script src="../../assets/js/vendor.bundle.base.js"></script>
  <script src="../../assets/js/vendor.bundle.addons.js"></script>
  <script src="../../assets/js/off-canvas.js"></script>
  <script src="../../assets/js/misc.js"></script>
  <script src="../../assets/js/dashboard.js"></script>
</body>

</html>