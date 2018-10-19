<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="classif" scope="page" value="" />
<%-- <c:forEach items="${feed}" var="feed">
	<div class="row">
    <div class="col-md-4"><b>${feed.classification}</b></div>
	<div class="col-md-4">${feed.subclassification}</div>
	<div class="col-md-4">${feed.value}</div>
	</div>
</c:forEach> --%>
<c:forEach items="${feed}" var="feed">
	<c:choose>
		<c:when test="${classif eq ''}">
			<c:set var="classif" scope="page" value="${feed.classification}" />
			<div class="row">
				<div class="col-md-4 stretch-card" style="padding:10px;">
					<div class="card bg-gradient-info card-img-holder text-white">
						<div class="card-body">
							<img src="/assets/img/circle.svg" class="card-img-absolute"
								alt="circle-image" />
							<h2 class="font-weight-normal mb-3">${feed.classification}</h2>
							<table border="0" style="font-size:1.3em;">
							<tr><td style="text-align:left;">${feed.subclassification}</td><td>:</td><td style="text-align:left;">${feed.value}</td></tr>
		</c:when>
		<c:when test="${classif eq feed.classification}">
			<c:set var="classif" scope="page" value="${feed.classification}" />
			<tr><td style="text-align:left;">${feed.subclassification}</td><td>:</td><td style="text-align:left;">${feed.value}</td></tr>
		</c:when>
		<c:otherwise>
			<c:set var="classif" scope="page" value="${feed.classification}" />
			</table>
			</div>
			</div>
			</div>
				<div class="col-md-4 stretch-card" style="padding:10px;">
				<div class="card bg-gradient-info card-img-holder text-white">
						<div class="card-body">
							<img src="/assets/img/circle.svg" class="card-img-absolute"
								alt="circle-image" />
							<h2 class="font-weight-normal mb-3">${feed.classification}</h2>
							<table border="0" style="font-size:1.3em;">
							<tr><td style="text-align:left;">${feed.subclassification}</td><td>:</td><td style="text-align:left;">${feed.value}</td></tr>
		</c:otherwise>
	</c:choose>
</c:forEach>
</table>
</div>
</div>
</div>
</div>