<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function() {
			
			$("#save").click(function(){
			var project_id = document.getElementById("project_id").value;
			var project_name = document.getElementById("project_name").value;
			var project_owner = document.getElementById("project_owner").value;
			var project_details = document.getElementById("project_details").value;
				$.post('/admin/addProjectDetails', {
					project_id : project_id,
					project_name : project_name,
					project_owner : project_owner,
					project_details : project_details
				}, function(data) {
					$('#allvalues').html(data)
					  $("button").show();
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
						<h4 class="card-title">Create Project</h4>
						<p class="card-description">Project Details</p>
						<form class="forms-sample" id="ProjectDetails" name="ProjectDetails">
							<div>
								<div id="h1" class="hx" ">
									<div class="form-group row">
										<div class="col-sm-6">
											<label>Project ID *</label> <input type="text"
												class="form-control" id="project_id"
												name="project_id" placeholder="Project ID">
										</div>
										<div class="col-sm-6">
											<label>Project Name *</label> <input type="text"
												class="form-control" id="project_name"
												name="project_name" placeholder="Project Name">
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label>Project Owner *</label> <input type="text"
												class="form-control" id="project_owner"
												name="project_owner" placeholder="Project Owner">
										</div>
										<div class="col-sm-6">
											<label>Project Details </label> <input type="text"
												class="form-control" id="project_details"
												name="project_details" placeholder="Project Details">
										</div>
									</div>
								</div>
								</div>
							<button id="save" name="save"
								class="btn btn-rounded btn-gradient-info mr-2">Save</button>
								<button id="createsystem" name="createsystem" hidden="true"
								class="btn btn-rounded btn-gradient-info mr-2">Create System</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />