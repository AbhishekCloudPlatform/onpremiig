<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../cdg_header.jsp" />
<script>
	function jsonconstruct(val) {
		var data = {};
		document.getElementById('button_type').value = val;
		$(".form-control").serializeArray().map(function(x) {
			data[x.name] = x.value;
		});
		var x = '{"header":{"user":"info@clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
				+ JSON.stringify(data) + '}}';
		document.getElementById('x').value = x;
		//console.log(x);
		//alert(x);
		document.getElementById('ConnectionDetails').submit();
	}
	$(document).ready(function() {
		$("#conn").change(function() {
			var conn = $(this).val();
			var src_val = document.getElementById("src_val").value;
			$.post('/extraction/ConnectionDetailsEdit', {
				conn : conn,
				src_val : src_val
			}, function(data) {
				$('#cud').html(data)
			});
		});
	});

	function funccheck(val) {
		if (val == 'create') {
			window.location.reload();
		} else {
			document.getElementById('connfunc').style.display = "block";
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
						<p class="card-description">Connection Details</p>
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
						<script type="text/javascript">
							window.onload = function() {
								if (document.getElementById("connection_type").value == "Oracle") {
									document.getElementById("host_name").value = "144.21.87.191";
									document.getElementById("port").value = "1521";
									document.getElementById("user_name").value = "arg_loans_db";
									document.getElementById("password").value = "cdc1";
									document.getElementById("service_name").value = "ORCL.607022672.oraclecloud.internal";
									document.getElementById("db").style.display = "none";
									document.getElementById("service").style.display = "block";
								} else if (document
										.getElementById("connection_type").value == "Mssql") {
									document.getElementById("db").style.display = "block";
									document.getElementById("service").style.display = "none";
								} else {
									document.getElementById("db").style.display = "none";
									document.getElementById("service").style.display = "none";
								}
							}
						</script>
						<form class="forms-sample" id="ConnectionDetails"
							name="ConnectionDetails" method="POST"
							action="/extraction/ConnectionDetails1"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> <input
								type="hidden" name="button_type" id="button_type" value="">
							<input type="hidden" name="src_val" id="src_val"
								value="${src_val}">

							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Connection</label>
								<div class="col-sm-4">
									<div class="form-check form-check-info">
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="radio" id="radio1"
											checked="checked" value="create"
											onclick="funccheck(this.value)"> Create
										</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-check form-check-info">
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="radio" id="radio2"
											value="edit" onclick="funccheck(this.value)"> Edit
										</label>
									</div>
								</div>
							</div>

							<div class="form-group" id="connfunc" style="display: none;">
								<label>Select Connection</label> <select name="conn" id="conn"
									class="form-control">
									<option value="" selected disabled>Select Connection
										...</option>
									<c:forEach items="${conn_val}" var="conn_val">
										<option value="${conn_val.connection_id}">${conn_val.connection_name}</option>
									</c:forEach>
								</select>
							</div>
							<div id="cud">
								<fieldset class="fs">
									<div class="form-group row">
										<div class="col-sm-6">
											<label>Connection Name *</label> <input type="text"
												class="form-control" id="connection_name"
												name="connection_name" placeholder="Connection Name">
										</div>
										<div class="col-sm-6">
											<label>Connection Type *</label> <input type="text"
												class="form-control" id="connection_type"
												name="connection_type" value="${src_val}"
												readonly="readonly">
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-3">
											<label>Host Name *</label> <input type="text"
												class="form-control" id="host_name" name="host_name"
												placeholder="Host Name">
										</div>
										<div class="col-sm-3">
											<label>Port Number *</label> <input type="text"
												class="form-control" id="port" name="port"
												placeholder="Port Number">
										</div>
										<div class="col-sm-3">
											<label>Username *</label> <input type="text"
												class="form-control" id="user_name" name="user_name"
												placeholder="Username">
										</div>
										<div class="col-sm-3">
											<label>Password *</label> <input type="password"
												class="form-control" id="password" name="password"
												placeholder="Password">
										</div>
									</div>
									<div class="form-group" id="db" style="display: none;">
										<label>Database Name *</label> <input type="text"
											class="form-control" id="db_name" name="db_name"
											placeholder="Database Name">
									</div>
									<div class="form-group" id="service" style="display: none;">
										<label>Service Name/ID *</label> <input type="text"
											class="form-control" id="service_name" name="service_name"
											placeholder="Service Name/ID">
									</div>
								</fieldset>
								<button onclick="jsonconstruct('add');"
									class="btn btn-rounded btn-gradient-info mr-2">Test &
									Save</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />