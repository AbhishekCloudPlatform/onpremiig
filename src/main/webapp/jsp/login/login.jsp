<!DOCTYPE html>
<html lang="en">

<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Juniper Login</title>
  <!-- plugins:css -->
  <link rel="stylesheet" href="../../assets/iconfonts/mdi/css/materialdesignicons.min.css">
  <link rel="stylesheet" href="../../assets/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- plugin css for this page -->
  <!-- End plugin css for this page -->
  <!-- inject:css -->
  <link rel="stylesheet" href="../../assets/css/style2.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="../../assets/img/favicon.png" />
</head>

<body>
  <div class="container-scroller" >
    <div class="container-fluid page-body-wrapper full-page-wrapper">
      <div class="content-wrapper d-flex align-items-center auth" style="background-image: url('../assets/img/bg7.jpg'); background-size: cover; background-position: top center;">
        <div class="row w-100">
          <div class="col-lg-4 mx-auto">
            <div class="auth-form-light text-left p-5">
              
               <h3>Juniper</h3> 
              
              <h5>Personalize your Data Movement Experience</h5>
              <h6 class="font-weight-light">Sign in to continue.</h6>
              <form class="pt-3" action="/login/submit" method="POST">
                <div class="form-group">
                  <input type="text" class="form-control form-control-lg" id="username" name="username" placeholder="Username">
                </div>
                <div class="form-group">
                  <input type="password" class="form-control form-control-lg" id="password" name="password" placeholder="Password">
                </div>
                <div class="mt-3">
                	<button type="submit" class="btn btn-block btn-facebook auth-form-btn">
                    SIGN IN
                  </button>
                </div>
                
                <div class="mt-3" align="center">
                	<a href="/#works"><img src="../../assets/img/home.png" alt="Image"  height="30" width="30" class="rounded">
 </a>
                </div>
                                <div class="mt-3" align="center">
                
                <%
							if (request.getAttribute("successString") != null) {
						%>
						<p class="text-success h4">${successString}</p>
						<%
							}
						%>
						<%
							if (request.getAttribute("errorString") != null) {
						%>
						<p class="text-danger h4">${errorString}</p>
						<%
							}
						%>
    </div>
              </form>
               
            </div>
          </div>
         
        </div>
        
          
      </div>
      <!-- content-wrapper ends -->
    </div>
    <!-- page-body-wrapper ends -->
  </div>
  <!-- container-scroller -->
  <!-- plugins:js -->
  <script src="../../assets/js/vendor.bundle.base.js"></script>
  <script src="../../assets/js/vendor.bundle.addons.js"></script>
  <!-- endinject -->
  <!-- inject:js -->
  <script src="../../js/off-canvas.js"></script>
  <script src="../../js/misc.js"></script>
  <!-- endinject -->
</body>

</html>
