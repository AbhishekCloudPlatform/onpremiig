<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../cdg_header.jsp" />
<script>

function jsonconstruct() {
	var data = {};
	$(".form-control").serializeArray().map(function(x) {
		data[x.name] = x.value;
	});
	var x = '{"header":{"user":"http://clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
			+ JSON.stringify(data) + '}}';
	document.getElementById('x').value = x;
	document.getElementById('SystemOnboard').submit();
}

history.pushState(null, null, document.URL);
window.addEventListener('popstate', function () {
    history.pushState(null, null, document.URL);
});

function sys_typ(id,val) {
	var in1 = id.slice(-1);
	var in2 = id.slice(-2, -1);
	if (in2 === "e");
	else {
		in1 = id.slice(-2);
	}
	var in3 = 'g' + in1;
	var in4 = 'h' + in1;
	var in5 = 'f' + in1;
	if (val == "GCS") {
		document.getElementById(in3).style.display = "block";
		document.getElementById(in4).style.display = "none";
		document.getElementById(in5).style.display = "none";
	} else if (val == "HDFS") {
		document.getElementById(in4).style.display = "block";
		document.getElementById(in3).style.display = "none";
		document.getElementById(in5).style.display = "none";
	}else if (val == "FILE" || val == "ORACLE") {
		document.getElementById(in3).style.display = "none";
		document.getElementById(in4).style.display = "none";
		document.getElementById(in5).style.display = "block";
	}
}


$(document).ready(function() {
	$("#system_region").change(function() {
	      var region = $(this).val();
	      if(region!='') {
	        $.ajax({ 
	          type: "POST",
	          url: "/system/FetchCountriesForRegion",
	          data: {region: region},
	          cache: false,
	          success: function(html) {
		            $("#co").html(html).show();
		          }
	        });
	      }
	      return false;
	});


	$("#system_eim").keyup(function() {
	      var eim = $(this).val();
	      if(eim!='') {
	        $.ajax({ 
	          type: "POST",
	          url: "/system/EIMValidation",
	          data: {eim: eim},
	          cache: false,
	          success: function(html) {
	        	  $("#validate").html(html);
	            if(document.getElementById("eimvalidation").value=='1'){
		            $("#systemregister").hide();
		            }else{
	            	 $("#systemregister").show();
		            }
	            }
	        });
	      }
	      return false;
	});
});
</script>

<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">System Onboarding</h4>
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
						<form class="forms-sample" id="SystemOnboard" name="SystemOnboard"
							method="POST" action="/system/register"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> <input
								type="hidden" name="src_val" id="src_val" value="${src_val}">
							<fieldset class="fs">
								<div class="form-group row">
									<div class="col-sm-6">
										<label>System EIM*</label> <input type="text"
											class="form-control" id="system_eim" name="system_eim"
											placeholder="System EIM" required="required" autofocus>
										<div id="validate"
											style="font-size: 0.7em; text-align: center;"></div>

									</div>

									<div class="col-sm-6">
										<label>System Name*</label> <input type="text"
											class="form-control" id="system_name" name="system_name"
											placeholder="Source Name" required="required">
									</div>
								</div>

								<div class="form-group row">
									<div class="col-sm-6">
										<label>System Region*</label> <select class="form-control"
											id="system_region" name="system_region"
											placeholder="Source Region" required="required">
											<option value="">Region ...</option>
											<option value="asp">ASP</option>
											<option value="emea">EMEA</option>
											<option value="mena">MENA</option>
											<option value="americas">AMERICAS</option>
										</select>
									</div>
									<div class="col-sm-6" id="co">
										<label>Country*</label> <select class="form-control"
											id="system_country" name="system_country" required="required">
											<option value="" selected disabled>Country ...</option>
										</select>
									</div>
								</div>

								<div class="form-group row">
									<div class="col-sm-6">
										<label>Owner*</label> <input type="text" class="form-control"
											id="owner" name="owner" placeholder="Owner"
											required="required">
									</div>

									<div class="col-sm-6">
										<label>Environment Type*</label> <select name="environment_type"
											id="environment_type" class="form-control">
											<option value="" selected disabled>Environment Type ...</option>
											<option value="DEV" selected="selected">Development</option>
											<option value="UAT">UAT</option>
											<option value="prod">Production</option>
										</select>
									</div>
								</div>

								<div class="form-group row">
									<div class="col-sm-6">
										<label>Platform Type *</label> <select name="platform_type1"
											id="platform_type1" class="form-control"
											onfocus="sys_typ(this.id,this.value)"
											onchange="sys_typ(this.id,this.value)">
											<option value="" selected disabled>System Type ...</option>
											<option value="GCS" selected="selected">Google Cloud
												Storage</option>
											<option value="HDFS">Hadoop File System</option>
											<option value="FILE">File</option>
											<option value="DB2">DB2</option>
											<option value="ORACLE">Oracle</option>
										</select>
									</div>
								</div>

								<div id="g1" class="gx">
									<div class="form-group row">
										<div class="col-sm-4">
											<label>Target Project*</label> <input type="text"
												class="form-control" id="target_project1"
												name="target_project1" placeholder="Target Project"
												value="juniperonprem">
										</div>
										<div class="col-sm-4">
											<label>Service Account*</label> <input type="text"
												class="form-control" id="service_account1"
												name="service_account1" placeholder="Service Account"
												value="Extraction_CDG_UK">
										</div>
										<div class="col-sm-4">
											<label>Target Bucket*</label> <input type="text"
												class="form-control" id="target_bucket1"
												name="target_bucket1" placeholder="Target Bucket"
												value="extraction-bucket1">
										</div>
									</div>
								</div>

								<div id="h1" class="hx" style="display: none;">
									<div class="form-group row">
										<div class="col-sm-6">
											<label>KNOX URL*</label> <input type="text"
												class="form-control" id="knox_url1" name="knox_url1"
												placeholder="KNOX URL" required="required">
										</div>
										<div class="col-sm-6">
											<label>Port Number*</label> <input type="text"
												class="form-control" id="hadoop_port" name="hadoop_port"
												placeholder="Port Number" required="required">
										</div>
									</div>
								</div>


								<div id="f1" class="fx" style="display: none;">
									<div class="form-group row">
										<div class="col-sm-6">
											<label>Host*</label> <input type="text" class="form-control"
												id="host_name" name="host_name" placeholder="Host Name"
												required="required">
										</div>
										<div class="col-sm-6">
											<label>Port Number*</label> <input type="text"
												class="form-control" id="file_port" name="file_port"
												placeholder="Port Number" required="required">
										</div>
									</div>
								</div>




							</fieldset>
							<!--  <input id="systemregister"
								class="btn btn-rounded btn-gradient-info mr-2" type="submit">-->
							<button id="systemregister" onclick="jsonconstruct();"
								class="btn btn-rounded btn-gradient-info mr-2">Submit</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />