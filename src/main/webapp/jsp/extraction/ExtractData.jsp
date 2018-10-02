<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function jsonconstruct() {
		var ext_type=document.getElementById("ext_type").value;
		if(ext_type=="Batch")
		{
			document.getElementById("cron").value = cron_construct();
		}
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
	$(document).ready(function() {
		$("#src_unique_name").change(function() {
			var src_unique_name = $(this).val();
			var src_val = document.getElementById("src_val").value;
			$.post('/extraction/ExtractData1', {
				src_unique_name : src_unique_name,
				src_val : src_val
			}, function(data) {
				$('#datdyn1').html(data)
			});
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
						<p class="card-description">Extract Data</p>
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
							action="/extraction/ExtractData2"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> <input
								type="hidden" name="src_val" id="src_val" value="${src_val}">
							<input type="hidden" name="cron" id="cron" class="form-control"
								value=""> 
							<div class="form-group">
									<label>Source System Name *</label> <select name="src_unique_name"
										id="src_unique_name" class="form-control">
										<option value="" selected disabled>Source System Name
											...</option>
										<c:forEach items="${src_sys_val}" var="src_sys_val">
											<option value="${src_sys_val.src_unique_name}">${src_sys_val.src_unique_name}</option>
										</c:forEach>
									</select>
								</div>
								<div id="datdyn1"></div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />