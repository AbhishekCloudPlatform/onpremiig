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
  <link rel="stylesheet" href="../../assets/iconfonts/mdi/css/materialdesignicons.min.css">
  <link rel="stylesheet" href="../../assets/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- inject:css -->
  <link rel="stylesheet" href="../../assets/css/style2.css">
  <!-- Include multi.js -->
  <link rel="stylesheet" type="text/css" href="../../assets/css/multi.min.css">
  <script src="../../assets/js/multi.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
   <script type="text/javascript" src="../../assets/js/hip.js"></script>
</head>
<style>
* {
  box-sizing: border-box;
}
.menu {
  float:left;
  width:20%;
  text-align:center;
}
.menu a {
  background-color:#e5e5e5;
  padding:8px;
  margin-top:7px;
  display:block;
  width:100%;
  color:black;
}
.main {
  float:left;
  width:80%;
  padding:0 20px;
}
/* .right {
  background-color:#e5e5e5;
  float:left;
  width:20%;
  padding:15px;
  margin-top:7px;
  text-align:center;
} */

@media only screen and (max-width:620px) {
  /* For mobile phones: */
  .menu, .main/* , .right */ {
    width:100%;
  }
}
</style>
<body  style="background-image: url('../../assets/img/city-profile.jpg');">

<div class="row" style="margin: 1%;">
	<div class="col-12  stretch-card" >
		<div class="card">
			<div style="background-image:linear-gradient(to right, rgba(192,192,192,0), rgba(192,192,192,1));padding:15px;">
  				
  				<div class="row ">
  					
  					<div class=" col-md-12" align="center"><h1 style="font-family:fantasy;color: gray;">RUN STATISTICS</h1></div>
  					<!-- <div class=" col-md-2"  align="right">
	  					<button type="button" onclick="homebutton()"  class="btn btn-gradient-dark btn-rounded btn-icon" >
	  					  <i class="mdi mdi-home-outline"></i>
	                    </button>
  					</div> -->
  					
  				</div>
  				<br>
  				<div class="row " >
  				  
	  					<div class="menu" align="center">
	  	
	  					</div>
  					<div class="main" >
  						<button type="button" onclick="feedpage()" class="btn btn-gradient-secondary btn-fw">Feed Type</button>
  						<button type="button" onclick="tablepage()" class="btn btn-gradient-secondary btn-fw">Table Type</button>
  						
  					</div>

  				</div>
  			
			</div>

<div id="feedpage" style="overflow:auto;display: block;">
  <div class="menu" style="background-image:linear-gradient(to right, rgba(192,192,192,0), rgba(192,192,192,1));padding:15px;text-align:left;" >
  	<div class="form-group col-md-12 ">
	    <label>Search Feed</label>
		<input type="text" class="form-control" id="feedid_s" name="feedid_s" placeholder="Feed ID">
	</div>
    <div class="form-group col-md-12 ">
    	<label>Select Feed</label>
		<select class="form-control" id="feedIdFilter" name="feedIdFilter">
			<option value="" selected disabled>Feed Data...</option>
			<c:forEach items="${feed_id}" var="feed_id">
				<option value="${feed_id}">${feed_id}</option>
			</c:forEach>
		</select>
    </div>
    <div class="form-group col-md-12 ">
    	<label>Select Date</label>
		<div>
			<input class="form-control"  type="date"/> 
		</div>
    </div>
    <div class="form-group col-md-12 " align="center">
    	<button type="button" id="clear" class="btn btn-gradient-secondary btn-fw" >Clear</button>
    </div>
  </div>

  <div class="main">
  					<br>
  					<br>
  					<div class="row" >
									<div class=" col-md-2"></div>	
									 <div class="col-md-8" align="justify" id="chartId" style="display: none;border-style: ridge; " >
										<h4>Duration of Feed in Minutes</h4>
	                  					<canvas id="areaChart" style="height:250px"></canvas>
	                  				 </div>
	                  				 <div class=" col-md-2"></div>	
					</div>
								<br>
				    <div class="row" >
						<div class=" col-md-2"></div>			
						<div class=" col-md-8"  id="table" style="display: none;overflow-x:auto;border-style: ridge">
						</div>
                  		<div class=" col-md-2"></div>				
				    </div>
   </div>

  <!-- <div class="right">
    <h2>About</h2>
    <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit.</p>
  </div> -->
</div>
<div id="tablepage" style="overflow:auto;display: none;" >
</div>
<div style="background-image:linear-gradient(to right, rgba(192,192,192,0), rgba(192,192,192,1));text-align:center;padding:10px;margin-top:7px;">Copyright © 2018 <a href="https://www.infosys.com" target="_blank">Infosys Limited</a>. All rights reserved.</div>
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