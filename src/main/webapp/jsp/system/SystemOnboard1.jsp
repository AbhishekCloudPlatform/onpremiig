<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
<label>Country*</label> 
<select class="form-control" id="system_country" name="system_country" required="required">
<c:forEach items="${countries}" var="countries">
<option value="" selected disabled>Country ...</option>
<option value="${countries.country_code}">${countries.country_name}</option>
</c:forEach>
</select>
</div>