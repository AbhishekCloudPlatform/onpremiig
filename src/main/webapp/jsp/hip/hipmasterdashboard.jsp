<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../cdg_header1.jsp" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$("#feedIdFilter").change(function() {
			var feed_id = $(this).val();
			$.post('/hip/hipmasterdashboard1', {
				feed_id : feed_id
			}, function(data) {
				$('#datdyn').html(data)
			});
		});
	});
</script>
<div class="main-panel" style="width:100%">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
							<div class="row">
							<div class="col-md-4">
							
							</div>
											<div class="col-md-4">
												<div class="form-group">
													<h4 class="card-title" align="left">Select Feed</h4> <select class="form-control"
														id="feedIdFilter" name="feedIdFilter">
														<option value="" selected disabled>Select Feed
															...</option>
														<c:forEach items="${feed_id}" var="feed_id">
															<option value="${feed_id}">${feed_id}</option>
														</c:forEach>
													</select>
												</div>
											</div>
							<div class="col-md-4">
							<h5 style="text-align:right;"><a style="text-decoration:none;" href="/">Home</a></h5>
							</div>
										</div>
										<div id="datdyn">
										</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer1.jsp" />