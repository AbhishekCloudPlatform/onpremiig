<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function() {
			$("#frequency").change(function() {
				var frequency = $(this).val();
				//alert(frequency);
				var batchId = document.getElementById('arrfeedId').value
				$.post('/scheduler/frequency', {
					frequency : frequency,
					batchId : batchId
				}, function(data) {
					$('#allvalues').html(data)
				});
		
	});
			
			
			$("#arrfeedId").change(function() {
				var frequency = document.getElementById('frequency').value
				var batchId = $(this).val();
				$.post('/scheduler/batchid', {
					batchId : batchId,
					frequency : frequency
				}, function(data) {
					$('#allvalues').html(data)
				});
			});
			
			
			$("#play ").click(function(){
			alert("in");
			var $row = $(this).closest("tr");
			var $feedId = $row.find('td:eq( 0 )').html();
			var $jobId = $row.find('td:eq( 1 )').html();
			alert("feedId"+$feedId);
			alert("jobId"+$jobId);
				   $.post('/scheduler/runMasterJob', {
					   feedId : $feedId,
					   jobId : $jobId
					}, function(data) {
						
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
					<h4 class="card-title">All Jobs</h4>
							<div class="row">
						   <div class="form-group col-md-6 ">
						   		<label>Feed Id</label> 
											<select class="form-control" name="arrfeedId" id="arrfeedId">
												<option value="ALL" selected="selected">All</option>
											    <c:forEach items="${arrfeedId}" var="arrfeedId">
													<option value="${arrfeedId}">${arrfeedId}</option>
												</c:forEach>									  	
											</select>
				          </div>

				          <div class="form-group col-md-6">
				          <label>Frequency</label> 
											<select class="form-control" name="frequency" id="frequency">
												<option value="ALL" selected="selected">All</option>
											    <option value="DAILY">Daily</option>
											    <option value="WEEKLY">Weekly</option>
											    <option value="MONTHLY">Monthly</option>
											    <option value="YEARLY">Yearly</option>
									  		</select>
				          </div>
				          </div>	
        
					</div>
				</div>
			</div>
		</div>
	
         
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
					
			
					
						<h4 class="card-title">Job Details</h4>
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
					<form class="forms-sample" id="extractionExtractData"
							name="extractionExtractData" method="POST"
							action="/extract/extractionExtractData1"
							enctype="application/json">

			<div id="allvalues" style="display: block;">
				 <table class="table table-bordered" id="feedId1"  >
                    <thead>
                      <tr style="color: green;;font: bolder;">
                      <th style="">
                          Feed Id
                        </th>
                        <th>
                          Job Id
                        </th>
                        <th>
                          Job Name
                        </th>
                        <th>
                         Schedule Info
                        </th>
                    	<th>
                          Run
                        </th>
                        <th>
                         Delete
                        </th>
                        <th>
                        Suspend
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                   <c:forEach var="row" items="${allLoadJobs}">
	                    <tr>
	                    <td><c:out value="${row.batch_id}" /></td>
	                    <td><c:out value="${row.job_id}" /></td>
						<td><c:out value="${row.job_name}" /></td>
						<td><c:out value="${row.consolidatedSchedule}" /></td>
						<td>
						<a href="#" ><img name="play" id="play" src="../../assets/img/run.png"  alt="Image" height="160" width="160"class="rounded"></a>
						
						<!-- <button type="button" class="btn btn-success btn-fw">Run</button> -->
						</td>
						<td>
						<a href="#" ><img src="../../assets/img/delete.png"  alt="Image" height="160" width="160"class="rounded">
						</a>
						<!-- <button type="button" class="btn btn-danger btn-fw">Delete</button> -->
						</td>
						<td>
						<a href="#" ><img src="../../assets/img/suspend.png"  alt="Image" height="160" width="160"class="rounded">
						</a>
						</td>	
						</tr>
	                </c:forEach>
                      
                     </tbody>
                  </table>
                 </div>
                 
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />