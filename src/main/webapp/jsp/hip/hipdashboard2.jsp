
	<input type="hidden" id="x" value='${x}' name="x"/>
	<input type="hidden" id="y" value='${y}'name="y"/>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
				 <table class="table table-bordered"   >
                    <thead>
                      <tr style="color: green;;font: bolder;">
                      <th>
                          Feed Id
                        </th>
                        <th>
                          Batch Date
                        </th>
                        <th>
                         Run Id
                        </th>
                        <th>
                         Start Time
                        </th>
                    	<th>
                         End Time
                        </th>
                        <th>
                        Duration (in Minutes)
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                   <c:forEach var="row" items="${arrHipDashboard}">
	                    <tr>
	                    <td><c:out value="${row.batch_id}" /></td>
	                    <td><c:out value="${row.batch_date}" /></td>
						<td><c:out value="${row.run_id}" /></td>
						<td><c:out value="${row.start_time}" /></td>
						<td><c:out value="${row.end_time}" /></td>
						<td><c:out value="${row.duration}" /></td>
						</tr>
	                </c:forEach>
                      
                     </tbody>
                  </table>