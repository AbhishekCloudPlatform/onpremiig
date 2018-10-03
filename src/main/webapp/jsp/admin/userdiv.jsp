<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- 	<div id="userid" class="col-sm-6">
		<label>User ID</label>
		<input type="text" disabled="disabled" class="form-control" value="${userid}"  id="userId" name="userId" >
	</div> --%>

	<div class="col-sm-12">
		<label>Select Features</label>
			<select name="targetx" id="targetx" class="form-control" multiple="multiple">											
				<c:forEach items="${arrFeatureAlready}" var="arrFeatureAlready">
					<option value="${arrFeatureAlready.feature_sequence}"  >${arrFeatureAlready.feature_name}</option>
				</c:forEach>
				<c:forEach items="${arrFeature}" var="arrFeature">
					<option value="${arrFeature.feature_sequence}" selected>${arrFeature.feature_name}</option>
				</c:forEach> 
				
				
			</select>
	</div>
	
	
<script>
var select = document.getElementById('targetx');
multi(select, {});
</script>

