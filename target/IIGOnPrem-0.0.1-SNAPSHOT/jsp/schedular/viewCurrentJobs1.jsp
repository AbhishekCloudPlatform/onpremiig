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
                          Status
                        </th>
                       <th >
                        Run/Re-Run
                        </th>
                       <th >
                        Kill/Abort
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                   <c:forEach var="row" items="${allLoadJobs}">
	                    <tr>
	                    <td><c:out value="${row.batch_id}" /></td>
	                    <td><c:out value="${row.job_id}" /></td>
						<td><c:out value="${row.job_name}" /></td>
						<td><c:out value="${row.job_schedule_time}" /></td>
						<td>
                        <c:out value="${row.status}" />
                        </td>
						<td>
						<a href="#" ><img src="../../assets/img/run.png"  alt="Image" height="160" width="160"class="rounded"></a>
						
						</td>
						<td>
						<a href="#" ><img src="../../assets/img/stop.png"  alt="Image" height="160" width="160"class="rounded">
						</a>
						</td>	
						</tr>
	                </c:forEach>
                      
                     </tbody>
                  </table>
                 </div>