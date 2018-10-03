<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
function saveProject(){
	document.getElementById('saveProjectDetailsForm').submit();
	document.getElementById('saveSystemDetails').style.display= "block";
	document.getElementById('saveProjectDetails').style.display= "none";


}
function saveSystem(){
	document.getElementById('saveSystemDetailsForm').submit();
	document.getElementById('saveSystemDetails').style.display= "none";
	document.getElementById('saveProjectDetails').style.display= "block";


}

</script>
<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">On-Board Project</h4>
					
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
						<div class="row" id="saveProjectDetails" style="display: block;">
						<form  class="forms-sample" id="saveProjectDetailsForm" name="saveProjectDetailsForm"
							method="POST" action="/admin/saveProjectDetailsForm"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value="">
							<input type="hidden" name="target" id="target" class="form-control" value="">
							<input type="hidden" name="counter" id="counter" class="form-control" value="1"> 
							
							<fieldset class="fs">
							
							</fieldset>
							<button onclick="saveProject();"
								class="btn btn-rounded btn-gradient-info mr-2">Save</button>
						</form>
						</div>
						<div class="row" id="saveSystemDetails" style="display: none;">
						<form  class="forms-sample" id="saveSystemDetailsForm" name="saveSystemDetailsForm"
							method="POST" action="/admin/saveSystemDetailsForm"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value="">
							<input type="hidden" name="target" id="target" class="form-control" value="">
							<input type="hidden" name="counter" id="counter" class="form-control" value="1"> 
							
							<fieldset class="fs">
							
							</fieldset>
							<button onclick="saveSystem();"
								class="btn btn-rounded btn-gradient-info mr-2">Save</button>
						</form>
						</div>
						
						<div class="row" id="onboardProject" style="display: none;">
						<form  class="forms-sample" id="submitProject" name="submitProject"
							method="POST" action="/admin/submitProject"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value="">
							<input type="hidden" name="target" id="target" class="form-control" value="">
							<input type="hidden" name="counter" id="counter" class="form-control" value="1"> 
							
							<fieldset class="fs">
							
							</fieldset>
							<button onclick="submitProject();"
								class="btn btn-rounded btn-gradient-info mr-2">Submit</button>
						</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		

<jsp:include page="../cdg_footer.jsp" />