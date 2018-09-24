<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<input type="hidden" name="ext_type" id="ext_type" value="${ext_type}">
<c:if test="${ext_type=='Batch'}">
<jsp:include page="../cdg_scheduler.jsp" />
<button onclick="jsonconstruct();"
	class="btn btn-rounded btn-gradient-info mr-2">Schedule</button>
</c:if>
<c:if test="${ext_type=='Real'}">
<button onclick="jsonconstruct();"
	class="btn btn-rounded btn-gradient-info mr-2">Extract</button>
</c:if>
