<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page import="javax.servlet.http.*"%>
<%@ page import="com.cdg.gcp.constants.*"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Cloud Data Grid</title>
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
  <script>
  function tog(ids)
  {
	  if(ids=="max")
	{
	  document.getElementById("min").style.display="block";
	  document.getElementById("max").style.display="none";
	}
	  if(ids=="min")
	{
		document.getElementById("max").style.display="block";
		document.getElementById("min").style.display="none";
	}
  }
  function multisel(src_id,tgt_id)
  {
	var el = document.getElementById(src_id);
	var result = "";
	var options = el.options;
	var opt;
	for (var i = 0, iLen = options.length; i < iLen; i++) {
		opt = options[i];
		if (opt.selected) {
			result=result+","+opt.value;
		}
	}
	result=result.substring(1);
	document.getElementById(tgt_id).value=result;
  }
  </script>
   <style>
.cust {
  width:95%;
  margin:5px;
  padding:0.875rem 0.5rem;
}
  
.row1 {
  display: flex;
  flex-wrap: wrap;
  padding: 0 4px;
}

/* Create four equal columns that sits next to each other */
.column1,.column2 {
  flex: 16.66%;
  max-width: 16.66%;
  padding: 0 5px;
}

.column1 img {
  margin-top: 8px;
  vertical-align: middle;
  width: 150px;
  height: 175px;
  border: 1px solid black;
}
.column2 img {
  margin-top: 8px;
  vertical-align: middle;
  width: 150px;
  border: 1px solid black;
}
/* Responsive layout - makes a two column-layout instead of four columns */
@media screen and (max-width: 800px) {
  .column1,.column2 {
    flex: 50%;
    max-width: 50%;
  }
}
/* Responsive layout - makes the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
  .column1,.column2{
    flex: 100%;
    max-width: 100%;
  }
}
#bgDiv {
  position:fixed;
  top:0px;
  bottom:0px;
  left:260px;
  right:0px;
  overflow:hidden;
  padding:0;
  margin:0;
  background-color:white;
  filter:alpha(opacity=25);
  opacity:0.25;
  z-index:1000;
}

.fs {
  border:1px groove #DCDCDC;
  padding:10px;
  margin:10px;
  border-radius:10px;
}
.leg {
  font-size:0.8em;
  font-weight:bold;
}
.grid-container {
  display: grid;
  height: 100%;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  grid-template-rows: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  grid-gap: 0.5px 0.5px;
       }

@media all and (-ms-high-contrast: none) {
  .grid-container {
    display: -ms-grid;
    -ms-grid-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
    -ms-grid-rows: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  }
}
.grid-container-header {
  display: grid;
  height: 100%;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  grid-template-rows: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  grid-gap: 0.5px 0.5px;
       }

@media all and (-ms-high-contrast: none) {
  .grid-container-header {
    display: -ms-grid;
    -ms-grid-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
    -ms-grid-rows: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  }
}
</style>
<script>
	function allowDrop(ev) {
		ev.preventDefault();
	}
	function drag(ev) {
		ev.dataTransfer.setData("text", ev.target.id);
	}
	function drop(ev) {
		ev.preventDefault();
		var data = ev.dataTransfer.getData("text");
		ev.target.appendChild(document.getElementById(data));
	}
	function load()
	{ 
		var x=document.getElementsByClassName("grid-container")[0];
		var x1=document.getElementsByClassName("grid-container-header")[0];
		var y="";
		var z="";
		for(var i=0;i<10;i++)
		{
			for(var j=0;j<100;j=j+10)
			{
				y=y+"<div id='"+(i+j)+"' name='"+(i+j)+"' class='form-control' ondrop='drop(event)' ondragover='allowDrop(event)' style='width:100%;height:50px;padding:0px;'></div>";
			}
		}
		for (var k=0;k<10;k++)
		{
			z=z+"<div style='text-align:center;'>"+k+"</div>";
		}
		x.innerHTML=y;
		x1.innerHTML=z;
	}
	function get_seq()
	{
		var json="",divid,dagid,val;
		for(var i=0;i<10;i++)
		{
			for(var j=0;j<100;j=j+10)
			{
				divid=document.getElementsByClassName("form-control")[(i+j)];
				if(divid.innerHTML!="")
				{
					dagid=divid.innerHTML.split('id="')[1].split('"')[0];
					//alert(divid.id+" "+dagid);
					if(val==null)
					{
						json+='{"seq":[["'+dagid+'"';
					}
					else if(val==Math.floor(divid.id/10))
					{
						json+=',"'+dagid+'"';
					}
					else
					{
						json+='],["'+dagid+'"';
					}
					val=Math.floor(divid.id/10);
				}
			}
		}
		json+="]]}";
		//document.getElementById("output").innerHTML=json;
		var code=json.replace(new RegExp('"','g'),'');
		var codearr=code.split('],[');
		var finalcode=[];
		var csv="";
		finalcode[0]=codearr[0].split('[[')[1];
		finalcode[codearr.length-1]=codearr[codearr.length-1].split(']]')[0];
		for(var i=1;i<codearr.length-1;i++)
		{
			finalcode[i]=codearr[i];
		}
		for(var j=0;j<finalcode.length;j++)
		{
			if(j==0)
			{
				code=finalcode[j]+"|";
				csv=finalcode[j]+"|";
			}
			else
			{
				var curr=finalcode[j].split(",");
				var dep=prev.split(",");
				for(k=0;k<curr.length;k++)
				{
					for(l=0;l<dep.length;l++)
					{
						code+=curr[k]+".set_upstream("+dep[l]+")|";
						csv+=curr[k]+","+dep[l]+"|";
					}
				}
			}
			var prev=finalcode[j];
		}
		code=code.slice(0,-1);
		csv=csv.slice(0,-1);
		//document.getElementById("code").innerHTML=code;
		document.getElementById("csv").innerHTML=csv;
	}
	</script>
</head>
<body  onload="load();">
  <div class="container-scroller">
    <!-- partial:partials/_navbar.html -->
    <nav class="navbar default-layout-navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
      <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
        <a class="navbar-brand brand-logo" href="/GCPHomeServlet"><img src="/assets/img/gcp.png" alt="logo" style="height:145px;"/></a>
        <a class="navbar-brand brand-logo-mini" href="/GCPHomeServlet"><img src="/assets/img/gcp.png" alt="logo" style="height:145px;"/></a>
		<b>Cloud Data Grid</b>
      </div>
      <div class="navbar-menu-wrapper d-flex align-items-stretch">
        <!--<div class="search-field d-none d-md-block">
          <form class="d-flex align-items-center h-100" action="#">
            <div class="input-group">
              <div class="input-group-prepend bg-transparent">
                  <i class="input-group-text border-0 mdi mdi-magnify"></i>                
              </div>
              <input type="text" class="form-control bg-transparent border-0" placeholder="Search projects">
            </div>
          </form>
        </div>-->
        <ul class="navbar-nav navbar-nav-right">
          <li class="nav-item nav-profile dropdown">
            <a class="nav-link dropdown-toggle" id="profileDropdown" href="#" data-toggle="dropdown" aria-expanded="false">
              <div class="nav-profile-img">
              
           <c:choose>
			    <c:when test="${user.username=='admin'}"><img src="/assets/img/faces/jiten.jpg" alt="image"></c:when>
			    <c:when test="${user.username=='user1'}"><img src="img/faces/rylan.jpg" alt="image"></c:when>
			    <c:when test="${user.username=='user2'}"><img src="img/faces/biraj.jpg" alt="image"></c:when>
			    <c:when test="${user.username=='jiten'}"><img src="img/faces/jiten.jpg" alt="image"></c:when>
			    <c:when test="${user.username=='rylan'}"><img src="img/faces/rylan.jpg" alt="image"></c:when>
			    <c:when test="${user.username=='biraj'}"><img src="img/faces/biraj.jpg" alt="image"></c:when>
			    <c:when test="${user.username=='utpal'}"><img src="img/faces/utpal.jpg" alt="image"></c:when>
			    <c:when test="${user.username=='sid'}"><img src="img/faces/siddharth.jpg" alt="image"></c:when>
			    <c:otherwise><img src="/assets/img/faces/face.png" alt="image"></c:otherwise>
			</c:choose>
			 
                <span class="availability-status online"></span>             
              </div>
              <div class="nav-profile-text">
                <p class="mb-1 text-black">${user.username}</p>
              </div>
            </a>
            <div class="dropdown-menu navbar-dropdown" aria-labelledby="profileDropdown">
              <a class="dropdown-item" href="/UserProfile">
                <i class="mdi mdi-account-circle mr-2 text-success"></i>
                Profile
              </a>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item" href="https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=<%=MySqlConstants.PROJECTURL%>">
                <i class="mdi mdi-logout mr-2 text-primary"></i>
                Logout
              </a>
            </div>
          </li>
          <li class="nav-item d-none d-lg-block full-screen-link">
            <a class="nav-link">
              <i class="mdi mdi-fullscreen" id="fullscreen-button"></i>
            </a>
          </li>
          <!--<li class="nav-item dropdown">
            <a class="nav-link count-indicator dropdown-toggle" id="messageDropdown" href="#" data-toggle="dropdown" aria-expanded="false">
              <i class="mdi mdi-email-outline"></i>
              <span class="count-symbol bg-warning"></span>
            </a>
            <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list" aria-labelledby="messageDropdown">
              <h6 class="p-3 mb-0">Messages</h6>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item preview-item">
                <div class="preview-thumbnail">
                    <img src="img/faces/face4.jpg" alt="image" class="profile-pic">
                </div>
                <div class="preview-item-content d-flex align-items-start flex-column justify-content-center">
                  <h6 class="preview-subject ellipsis mb-1 font-weight-normal">Mark sent you a message</h6>
                  <p class="text-gray mb-0">
                    1 Minute ago
                  </p>
                </div>
              </a>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item preview-item">
                <div class="preview-thumbnail">
                    <img src="img/faces/face2.jpg" alt="image" class="profile-pic">
                </div>
                <div class="preview-item-content d-flex align-items-start flex-column justify-content-center">
                  <h6 class="preview-subject ellipsis mb-1 font-weight-normal">Craig sent you a message</h6>
                  <p class="text-gray mb-0">
                    15 Minutes ago
                  </p>
                </div>
              </a>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item preview-item">
                <div class="preview-thumbnail">
                    <img src="img/faces/face3.jpg" alt="image" class="profile-pic">
                </div>
                <div class="preview-item-content d-flex align-items-start flex-column justify-content-center">
                  <h6 class="preview-subject ellipsis mb-1 font-weight-normal">Profile picture updated</h6>
                  <p class="text-gray mb-0">
                    18 Minutes ago
                  </p>
                </div>
              </a>
              <div class="dropdown-divider"></div>
              <h6 class="p-3 mb-0 text-center">3 new messages</h6>
            </div>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link count-indicator dropdown-toggle" id="notificationDropdown" href="#" data-toggle="dropdown">
              <i class="mdi mdi-bell-outline"></i>
              <span class="count-symbol bg-danger"></span>
            </a>
            <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list" aria-labelledby="notificationDropdown">
              <h6 class="p-3 mb-0">Notifications</h6>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item preview-item">
                <div class="preview-thumbnail">
                  <div class="preview-icon bg-success">
                    <i class="mdi mdi-calendar"></i>
                  </div>
                </div>
                <div class="preview-item-content d-flex align-items-start flex-column justify-content-center">
                  <h6 class="preview-subject font-weight-normal mb-1">Event today</h6>
                  <p class="text-gray ellipsis mb-0">
                    Just a reminder that you have an event today
                  </p>
                </div>
              </a>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item preview-item">
                <div class="preview-thumbnail">
                  <div class="preview-icon bg-warning">
                    <i class="mdi mdi-settings"></i>
                  </div>
                </div>
                <div class="preview-item-content d-flex align-items-start flex-column justify-content-center">
                  <h6 class="preview-subject font-weight-normal mb-1">Settings</h6>
                  <p class="text-gray ellipsis mb-0">
                    Update dashboard
                  </p>
                </div>
              </a>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item preview-item">
                <div class="preview-thumbnail">
                  <div class="preview-icon bg-info">
                    <i class="mdi mdi-link-variant"></i>
                  </div>
                </div>
                <div class="preview-item-content d-flex align-items-start flex-column justify-content-center">
                  <h6 class="preview-subject font-weight-normal mb-1">Launch Admin</h6>
                  <p class="text-gray ellipsis mb-0">
                    New admin wow!
                  </p>
                </div>
              </a>
              <div class="dropdown-divider"></div>
              <h6 class="p-3 mb-0 text-center">See all notifications</h6>
            </div>
          </li>-->
          <li class="nav-item nav-logout d-none d-lg-block">
            <a class="nav-link" href="https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=<%=MySqlConstants.PROJECTURL%>">
              <i class="mdi mdi-power"></i>
            </a>
          </li>
          <!--<li class="nav-item nav-settings d-none d-lg-block">
            <a class="nav-link" href="#">
              <i class="mdi mdi-format-line-spacing"></i>
            </a>
          </li>-->
        </ul>
        <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
          <span class="mdi mdi-menu"></span>
        </button>
      </div>
    </nav>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <!-- partial:partials/_sidebar.html -->
      <nav class="sidebar sidebar-offcanvas" id="sidebar">
        <ul class="nav">
          <li class="nav-item nav-profile">
            <a href="#" class="nav-link">
              <div class="nav-profile-image">
                <c:choose>
			    <c:when test="${user.username=='admin'}"><img src="img/faces/jiten.jpg" alt="profile"></c:when>
			    <c:when test="${user.username=='user1'}"><img src="img/faces/rylan.jpg" alt="profile"></c:when>
			    <c:when test="${user.username=='user2'}"><img src="img/faces/biraj.jpg" alt="profile"></c:when>
			    <c:when test="${user.username=='jiten'}"><img src="img/faces/jiten.jpg" alt="profile"></c:when>
			    <c:when test="${user.username=='rylan'}"><img src="img/faces/rylan.jpg" alt="profile"></c:when>
			    <c:when test="${user.username=='biraj'}"><img src="img/faces/biraj.jpg" alt="profile"></c:when>
			    <c:when test="${user.username=='utpal'}"><img src="img/faces/utpal.jpg" alt="profile"></c:when>
			    <c:when test="${user.username=='sid'}"><img src="img/faces/siddharth.jpg" alt="profile"></c:when>
			    <c:otherwise><img src="/assets/img/faces/face.png" alt="profile"></c:otherwise>
			</c:choose>
                <span class="login-status online"></span> <!--change to offline or busy as needed-->              
              </div>
              <div class="nav-profile-text d-flex flex-column">
                <span class="font-weight-bold mb-2">${user.username}</span>
                <span class="text-secondary text-small">Application User</span>
              </div>
              <i class="mdi mdi-bookmark-check text-success nav-profile-badge"></i>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/feature">
              <span class="menu-title">Feature Dashboard</span>
              <i class="mdi mdi-home menu-icon"></i>
            </a>
          </li>
			${feature_code}
        </ul>
      </nav>
      <!-- partial -->

<div id="min" style="display:block;position:fixed;bottom:0px;right:0px;z-index:999;text-align:right;background-color:white;padding:10px;">
<div onclick="tog('min');" style="cursor:pointer;width:280px;padding:5px;">
<div style="float:left;">Chatbot - How may I help you?</div>
<div style="float:right;"><b>+</b></div>
</div>
</div>      
<div id="max" style="display:none;position:fixed;bottom:400px;right:0px;z-index:999;text-align:right;background-color:white;padding:10px;">
<div onclick="tog('max');" style="cursor:pointer;width:280px;padding:5px;">
<div style="float:left;">Chatbot - How may I help you?</div>
<div style="float:right;"><b>-</b></div>
</div>
<iframe
frameborder="0" allowtransparency="yes" scrolling="no" allow="microphone"
src="https://console.dialogflow.com/api-client/demo/embedded/a2b4f40f-b4a5-467a-8257-bb383322b071"
style="width:300px;height:400px;position:fixed;right:0px;bottom:0px;margin:0;padding:0;" >
</iframe>
</div> --%>