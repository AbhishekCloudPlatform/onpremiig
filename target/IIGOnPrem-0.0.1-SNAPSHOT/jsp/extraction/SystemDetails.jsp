<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function jsonconstruct() {
		multisel('targetx', 'target');
		var data = {};
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
		      if(sun!='') {
		        $.ajax({ 
		          type: "POST",
		          url: "/extraction/SystemDetails1",
		          data: {sun: sun},
		          cache: false,
		          success: function(html) {
		            $("#res").html(html).show();
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
						<form class="forms-sample" id="ExtractData"
							name="ExtractData" method="POST"
							action="/extraction/SystemDetails2"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value="">
							<input type="hidden" name="target" id="target" class="form-control" value="">
							 <input
								type="hidden" name="src_val" id="src_val" value="${src_val}">
								 <input type="hidden" name="counter"
								id="counter" class="form-control" value="1"> 
							<fieldset class="fs">
								<div class="form-group">
									<label>Select Source</label> <select class="form-control"
										id="connection_id" name="connection_id">
										<option value="" selected disabled>${src_val}
											Sources...</option>
										<c:forEach items="${conn_val}" var="conn_val">
											<option value="${conn_val.connection_id}">${conn_val.connection_name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<label>Select Target</label> <select name="targetx"
										id="targetx" class="form-control" multiple="multiple">
										<c:forEach items="${tgt}" var="tgt">
											<option value="${tgt}">${tgt}</option>
										</c:forEach>
									</select>
								</div>
								<!-- <div class="form-group">
									<label>Service Account</label> <select name="service_account"
										id="service_account" class="form-control">
										<option value="" selected disabled>Service Account
											...</option>
										<option value="Extraction_CDG_UK">Extraction_CDG_UK</option>
									</select>
								</div>-->
								<div class="form-group row">
									<div class="col-sm-6">
										<label>Unique Extract Name</label> <input type="text"
											class="form-control" id="src_unique_name"
											name="src_unique_name" placeholder="Unique Extract Name">
											<div id="res" style="font-size:0.7em;text-align:center;"></div>
									</div>
									<div class="col-sm-6">
										<label>Country</label> <select class="form-control" id="country_code" name="country_code">
											<option value="" selected disabled>Country ...</option>
											<c:forEach items="${countries}" var="countries">
											<option value="${countries.country_code}">${countries.country_name}</option>
										</c:forEach>
											</select>
									</div>
								</div>
								<div class="form-group">
									<label>Extract Description</label> <input type="text"
										class="form-control" id="src_sys_desc" name="src_sys_desc"
										placeholder="Extract Description">
								</div>
								<div class="form-group">
									<!-- <div class="col-sm-6">-->
										<label>Extraction Type</label> <select name="src_extract_type"
											id="src_extract_type" class="form-control"
											onchange="sch(this.value);">
											<option value="" selected disabled>Extraction Type
												...</option>
											<option value="Real">One Time Full Extract</option>
											<option value="Batch">Scheduled Batch Run</option>
										</select>
									<!-- </div>-->
									<!-- <div class="col-sm-6">
										<label>Target Bucket</label> <select name="target"
											id="target" class="form-control">
											 <option value="" selected disabled>Target Bucket ...</option>
											<c:forEach items="${buckets}" var="buckets">
												<option value="${buckets}">${buckets}</option>
											</c:forEach>
										</select>
									</div>-->
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
							<button onclick="jsonconstruct();"
								class="btn btn-rounded btn-gradient-info mr-2">Save</button>
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