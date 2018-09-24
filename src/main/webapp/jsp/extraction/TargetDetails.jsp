<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function jsonconstruct() {
		var data = {};
		$(".form-control").serializeArray().map(function(x) {
			data[x.name] = x.value;
		});
		var x = '{"header":{"user":"info@clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
				+ JSON.stringify(data) + '}}';
		document.getElementById('x').value = x;
		alert(x);
		console.log(x);
		document.getElementById('TargetDetails').submit();
	}
	var i = 1;
	function dup_div() {
		var dyn = document.getElementById('dyn1');
		var dyndiv = dyn.cloneNode(true);
		var x = ++i;
		dyndiv.id="dyn"+i;
		dyndiv.getElementsByTagName('input')[0].id = "target_unique_name" + i;
		dyndiv.getElementsByTagName('input')[0].name = "target_unique_name" + i;
		dyndiv.getElementsByTagName('select')[0].id = "target_type" + i;
		dyndiv.getElementsByTagName('select')[0].name = "target_type" + i;	
		dyndiv.getElementsByTagName('input')[1].id = "target_project" + i;
		dyndiv.getElementsByTagName('input')[1].name = "target_project" + i;	
		dyndiv.getElementsByTagName('input')[2].id = "service_account" + i;
		dyndiv.getElementsByTagName('input')[2].name = "service_account" + i;
		dyndiv.getElementsByTagName('input')[3].id = "target_bucket" + i;
		dyndiv.getElementsByTagName('input')[3].name = "target_bucket" + i;
		dyndiv.getElementsByTagName('input')[4].id = "target_cluster" + i;
		dyndiv.getElementsByTagName('input')[4].name = "target_cluster" + i;	
		dyndiv.getElementsByTagName('input')[5].id = "username" + i;
		dyndiv.getElementsByTagName('input')[5].name = "username" + i;
		dyndiv.getElementsByTagName('input')[6].id = "target_dir" + i;
		dyndiv.getElementsByTagName('input')[6].name = "target_dir" + i;
		dyndiv.getElementsByClassName('gx')[0].id="g"+i;
		dyndiv.getElementsByClassName('hx')[0].id="h"+i;
		dyn.parentNode.appendChild(dyndiv);
		document.getElementById('counter').value=i;
	}
	function sys_typ(id,val) {
		var in1 = id.slice(-1);
		var in2 = id.slice(-2, -1);
		if (in2 === "e");
		else {
			in1 = id.slice(-2);
		}
		var in3 = 'g' + in1;
		var in4 = 'h' + in1;
		if (val == "GCS") {
			document.getElementById(in3).style.display = "block";
			document.getElementById(in4).style.display = "none";
		} else if (val == "HDFS") {
			document.getElementById(in4).style.display = "block";
			document.getElementById(in3).style.display = "none";
		}
	}
</script>

<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Data Extraction</h4>
						<p class="card-description">Target Details</p>
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
						<form class="forms-sample" id="TargetDetails" name="TargetDetails"
							method="POST" action="/extraction/TargetDetails1"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> <input
								type="hidden" name="counter" id="counter" class="form-control"
								value="1">
							<fieldset class="fs">
							<div>
							<div id="dyn1">
								<div class="form-group row">
									<div class="col-sm-6">
										<label>Target Name</label> <input type="text"
											class="form-control" id="target_unique_name1"
											name="target_unique_name1" placeholder="Target Name">
									</div>
									<div class="col-sm-6">
										<label>Target Type</label> <select name="target_type1"
											id="target_type1" class="form-control"
											onchange="sys_typ(this.id,this.value)">
											<option value="" selected disabled>Target Type ...</option>
											<option value="GCS" selected="selected">Google Cloud
												Storage</option>
											<option value="HDFS">Hadoop File System</option>
										</select>
									</div>
								</div>
								<div id="g1" class="gx">
									<div class="form-group row">
										<div class="col-sm-4">
											<label>Target Project</label> <input type="text"
												class="form-control" id="target_project1"
												name="target_project1" placeholder="Target Project"
												value="juniperonprem">
										</div>
										<div class="col-sm-4">
											<label>Service Account</label> <input type="text"
												class="form-control" id="service_account1"
												name="service_account1" placeholder="Service Account"
												value="Extraction_CDG_UK">
										</div>
										<div class="col-sm-4">
											<label>Target Bucket</label> <input type="text"
												class="form-control" id="target_bucket1"
												name="target_bucket1" placeholder="Target Bucket"
												value="extraction-bucket1">
										</div>
									</div>
								</div>
								<div id="h1" class="hx" style="display: none;">
									<div class="form-group row">
										<div class="col-sm-4">
											<label>Target Cluster</label> <input type="text"
												class="form-control" id="target_cluster1"
												name="target_cluster1" placeholder="Target Cluster">
										</div>
										<div class="col-sm-4">
											<label>Username</label> <input type="text"
												class="form-control" id="username1"
												name="username1" placeholder="Username">
										</div>
										<div class="col-sm-4">
											<label>Target Directory</label> <input type="text"
												class="form-control" id="target_dir1"
												name="target_dir1" placeholder="Target Directory">
										</div>
									</div>
								</div>
								</div>
								</div>
								<div class="form-group" style="float: right; position:relative; margin: 5px;">
									<button id="add" type="button"
										class="btn btn-rounded btn-gradient-info mr-2"
										onclick="return dup_div();">+</button>
								</div>
							</fieldset>
							<button onclick="jsonconstruct();"
								class="btn btn-rounded btn-gradient-info mr-2">Save</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />