<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function() {
			
			$("#run ").click(function(){
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
			
			$("#delete ").click(function(){
				var $row = $(this).closest("tr");
				var $feedId = $row.find('td:eq( 0 )').html();
				var $jobId = $row.find('td:eq( 1 )').html();
					   $.post('/scheduler/deleteMasterJob', {
						   feedId : $feedId,
						   jobId : $jobId
						}, function(data) {
							
						});
						window.location.reload();
					});
					
					$("#suspend ").click(function(){
				var $row = $(this).closest("tr");
				var $feedId = $row.find('td:eq( 0 )').html();
				var $jobId = $row.find('td:eq( 1 )').html();
				var $val = $row.find('td:eq( 6 )').html();
				if($val.includes("SUS-Y")){
					   $.post('/scheduler/unSuspendMasterJob', {
						   feedId : $feedId,
						   jobId : $jobId
						}, function(data) {
						window.location.reload();
						});
				} else {
					   $.post('/scheduler/suspendMasterJob', {
						   feedId : $feedId,
						   jobId : $jobId
						}, function(data) {
						window.location.reload();
						});
				}
						
					});			
});
</script>

			<div id="allvalues" style="display: block;">
				 <table class="table table-bordered"   >
                    <thead>
                      <tr style="color: green;;font: bolder;">
                      <th>
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
						<a href="#" ><img name="run" id="run" src="../../assets/img/run.png"  alt="Image" height="160" width="160"class="rounded"></a>
						
						<!-- <button type="button" class="btn btn-success btn-fw">Run</button> -->
						</td>
						<td>
						<a href="#" ><img name="delete" id="delete" src="../../assets/img/delete.png"  alt="Image" height="160" width="160"class="rounded">
						</a>
						<!-- <button type="button" class="btn btn-danger btn-fw">Delete</button> -->
						</td>
						<td>
							<a href="#">
								<img class="img-fluid img-thumbnail" id="suspend" name="suspend" src="../../assets/img/${row.is_suspended}.png" 
					      				alt="Image" height="160" width="160"class="rounded"  >
							</a>
						</td>
						</tr>
	                </c:forEach>
                      
                     </tbody>
                  </table>
                 </div>