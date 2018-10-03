<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
function jsonconstruct() {
	multisel('targetx', 'target');
	var data = {};
	$(".form-control").serializeArray().map(function(x) {
		data[x.name] = x.value;
	});
	var x = JSON.stringify(data);
	document.getElementById('x').value = x;
	//alert(x);
	//console.log(x);
	document.getElementById('onboarduser').submit();
}

$(document).ready(function() {
	$("#username").keyup(function() {
	      var user = $(this).val();
	      if(user!='') {
	        $.ajax({ 
	          type: "POST",
	          url: "/admin/selectuser",
	          data: {user: user},
	          cache: false,
	          success: function(data) {
	            $("#userid").html(data);
				document.getElementById('features').style.display= "block";

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
						<h4 class="card-title">On-Board User</h4>
					
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
						<form  class="forms-sample" id="onboarduser" name="onboarduser"
							method="POST" action="/admin/onboarduser"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value="">
							<input type="hidden" name="target" id="target" class="form-control" value="">
							<input type="hidden" name="counter" id="counter" class="form-control" value="1"> 
							
							<fieldset class="fs">
								<div class="form-group row">
									  	<div class="col-sm-6">
											<label>Select User</label>
											<input type="text"
											class="form-control" id="username"
											name="username" placeholder="USER ID">
											
										</div>
										<div id="userid" class="col-sm-6">
											  	
										
										</div>
								</div>
								<div id="features" class="form-group row" style="display: none;">
									<div class="col-sm-12">
										<label>Select Features</label> <select name="targetx"
											id="targetx" class="form-control" multiple="multiple">											
											<c:forEach items="${arrFeature}" var="arrFeature">
												<option value="${arrFeature.feature_order}">${arrFeature.feature_name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</fieldset>
							<button onclick="jsonconstruct();"
								class="btn btn-rounded btn-gradient-info mr-2">Submit</button>
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