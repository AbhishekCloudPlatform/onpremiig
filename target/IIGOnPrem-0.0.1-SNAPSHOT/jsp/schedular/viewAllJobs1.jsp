<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
						<a href="#" ><img src="../../assets/img/run.png"  alt="Image" height="160" width="160"class="rounded"></a>
						
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