<jsp:include page="../cdg_header.jsp" />

<script>
	function pass(val) {
		document.getElementById('src_val').value = val;
		document.getElementById('ConnectionHome').submit();
	}
</script>
<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Data Extraction</h4>
						<p class="card-description">Connection Details</p>
						<form class="forms-sample" id="ConnectionHome" name="ConnectionHome"
							method="post" action="/extraction/ConnectionDetails">
							<input type="hidden" name="src_val" id="src_val" value="">
							<div class="row1">
								<div class="column2">
									<a href="#" onclick="pass('Oracle');"><img src="../assets/img/oracle.png"></a> 
									<a href="#"><img src="../assets/img/db2.png"></a> 
									<a href="#"><img src="../assets/img/sybase.png"></a>
								</div>
								<div class="column2">
									<a href="#" onclick="pass('Teradata');"><img src="../assets/img/teradata.png"></a> 
									<a href="#"><img src="../assets/img/hive.png"></a> 
									<a href="#"><img src="../assets/img/hbase.png"></a>
								</div>
								<div class="column2">
									<a href="#" onclick="pass('Mysql');"><img src="../assets/img/mysql.png"></a> 
									<a href="#"><img src="../assets/img/mongodb.png"></a> 
									<a href="#"><img src="../assets/img/kafka.png"></a>
								</div>
								<div class="column2">
									<a href="#" onclick="pass('Mssql');"><img src="../assets/img/sqlserver.png"></a> 
									<a href="#"><img src="../assets/img/avro.png"></a> 
									<a href="#"><img src="../assets/img/exadata.png"></a>
								</div>
								<div class="column2">
									<a href="#" onclick="pass('Unix');"><img src="../assets/img/linux.png"></a> 
									<a href="#"><img src="../assets/img/aws.png"></a> 
									<a href="#"><img src="../assets/img/azure.png"></a>
								</div>
								<div class="column2">
									<a href="#" onclick="pass('Hadoop');"><img src="../assets/img/hadoop.png"></a> 
									<a href="#"><img src="../assets/img/netezza.png"></a> 
									<a href="#"><img src="../assets/img/windows.png"></a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />