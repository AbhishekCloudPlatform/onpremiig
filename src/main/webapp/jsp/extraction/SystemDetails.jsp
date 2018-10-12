<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function jsonconstruct(val) {
		multisel('targetx', 'target');
		var data = {};
		document.getElementById('button_type').value = val;
		$(".form-control").serializeArray().map(function(x) {
			data[x.name] = x.value;
		});
		var x = '{"header":{"user":"info@clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
				+ JSON.stringify(data) + '}}';
		document.getElementById('x').value = x;
		//alert(x);
		//console.log(x);
		document.getElementById('ExtractData').submit();
	}
	function sch(val) {
		if (val == "Batch") {
			document.getElementById('scheduling_div').style.display = "block";
		} else if (val == "Real") {
			document.getElementById('scheduling_div').style.display = "none";
		}
	}
	$(document).ready(function() {
		$("#src_unique_name").keyup(function() {
			var sun = $(this).val();
			if (sun != '') {
				$.ajax({
					type : "POST",
					url : "/extraction/SystemDetails1",
					data : {
						sun : sun
					},
					cache : false,
					success : function(html) {
						$("#res").html(html).show();
					}
				});
			}
			return false;
		});
		$("#src_sys").change(function() {
			if (document.getElementById("src_sys").value == "") {
				window.location.reload();
			} else {
				var src_sys = $(this).val();
				var src_val = document.getElementById("src_val").value;
				$.post('/extraction/SystemDetailsEdit', {
					src_sys : src_sys,
					src_val : src_val
				}, function(data) {
					$('#cud').html(data)
				});
			}
		});
	});

	function funccheck(val) {
		if (val == 'create') {
			window.location.reload();
		} else {
			document.getElementById('sysfunc').style.display = "block";
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
						<p class="card-description">System Details</p>
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
						<form class="forms-sample" id="ExtractData" name="ExtractData"
							method="POST" action="/extraction/SystemDetails2"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> <input
								type="hidden" name="target" id="target" class="form-control"
								value=""> <input type="hidden" name="src_val"
								id="src_val" value="${src_val}"> 
								<input type="hidden" name="button_type" id="button_type" value="">
								
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Source System</label>
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
							<div class="form-group" id="sysfunc" style="display: none;">
								<label>Select Source System</label> <select name="src_sys" id="src_sys"
									class="form-control">
									<option value="" selected disabled>Select Source System ...</option>
									<c:forEach items="${src_sys_val}" var="src_sys_val">
										<option value="${src_sys_val.src_sys_id}">${src_sys_val.src_unique_name}</option>
									</c:forEach>
								</select>
							</div>
							<div id="cud">								
							<fieldset class="fs">
								<div class="form-group row">
									<div class="col-sm-6">
										<label>Unique Extract Name *</label> <input type="text"
											class="form-control" id="src_unique_name"
											name="src_unique_name" placeholder="Unique Extract Name">
										<div id="res" style="font-size: 0.7em; text-align: center;"></div>
									</div>
									<div class="col-sm-6">
										<label>Extract Description *</label> <input type="text"
											class="form-control" id="src_sys_desc" name="src_sys_desc"
											placeholder="Extract Description">
									</div>
								</div>
								<div class="form-group">
									<label>Select Source *</label> <select class="form-control"
										id="connection_id" name="connection_id">
										<option value="" selected disabled>${src_val}
											Sources...</option>
										<c:forEach items="${conn_val}" var="conn_val">
											<option value="${conn_val.connection_id}">${conn_val.connection_name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<label>Select Target *</label> <select name="targetx"
										id="targetx" class="form-control" multiple="multiple">
										<c:forEach items="${tgt}" var="tgt">
											<option value="${tgt}">${tgt}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group row">
									<div class="col-sm-6">
										<label>Extraction Type *</label> <select
											name="src_extract_type" id="src_extract_type"
											class="form-control" onchange="sch(this.value);">
											<option value="" selected disabled>Extraction Type
												...</option>
											<option value="Real">One Time Full Extract</option>
											<option value="Batch">Scheduled Batch Run</option>
										</select>
									</div>
									<div class="col-sm-6">
										<label>Country</label> <select class="form-control"
											id="country_code" name="country_code">
											<option value="" selected disabled>Country ...</option>
											<c:forEach items="${countries}" var="countries">
												<option value="${countries.country_code}">${countries.country_name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div id="scheduling_div" style="display: none;">
									<!-- <div class="form-group">
										<label>Reservoir Id</label> <select name="reservoir_name"
											id="reservoir_name" class="form-control">
											<option value="" selected disabled>Reservoir Id ...</option>
											<c:forEach items="${reservoir}" var="reservoir">
												<option value="${reservoir.reservoir_name}">${reservoir.reservoir_name} - ${reservoir.reservoir_desc}</option>
											</c:forEach>
										</select>
									</div>-->
								</div>
							</fieldset>
							<button onclick="jsonconstruct('add');"
								class="btn btn-rounded btn-gradient-info mr-2">Save</button>
								</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<script>
			var select = document.getElementById('targetx');
			multi(select, {});
		</script>
		<jsp:include page="../cdg_footer.jsp" />