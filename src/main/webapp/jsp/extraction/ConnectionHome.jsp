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
						<div class="container">
								  <div class="row text-center text-lg-left">
									 <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      	<a class="d-block mb-4 h-100" href="#" onclick="pass('Oracle');">
								      		<img class="img-fluid img-thumbnail" src="../assets/img/oracle.png" >
								      	</a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      	<a class="d-block mb-4 h-100" href="#" onclick="pass('Teradata');">
								      		<img class="img-fluid img-thumbnail" src="../assets/img/teradata.png">
								      	</a> 
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								     	<a class="d-block mb-4 h-100" href="#" onclick="pass('Mysql');">
								     		<img class="img-fluid img-thumbnail" src="../assets/img/mysql.png">
								     	</a> 
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100" onclick="pass('Mssql');">
								            <img class="img-fluid img-thumbnail" src="../assets/img/sqlserver.png">
								          </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100" onclick="pass('Unix');">
								            <img class="img-fluid img-thumbnail" src="../assets/img/aws.png">
								          </a>
								    </div>
								     <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100" onclick="pass('Hadoop');">
								            <img class="img-fluid img-thumbnail" src="../assets/img/hadoop.png">
								          </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								    	<a class="d-block mb-4 h-100" href="#">
								    		<img class="img-fluid img-thumbnail" src="../assets/img/db2.png">
								    	</a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      	<a class="thumbnail" href="#">
								      		<img class="img-fluid img-thumbnail" src="../assets/img/sybase.png">
								      	</a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a class="d-block mb-4 h-100" href="#">
								      	<img class="img-fluid img-thumbnail" src="../assets/img/hive.png">
								      </a> 
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								     <a class="d-block mb-4 h-100" href="#">
								     	<img class="img-fluid img-thumbnail" src="../assets/img/hbase.png">
								     </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100">
								            <img class="img-fluid img-thumbnail" src="../assets/img/mongodb.png">
								          </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100">
								            <img class="img-fluid img-thumbnail" src="../assets/img/kafka.png">
								          </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100">
								            <img class="img-fluid img-thumbnail" src="../assets/img/exadata.png">
								          </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100">
								            <img class="img-fluid img-thumbnail" src="../assets/img/linux.png">
								          </a>
								    </div>
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100">
								            <img class="img-fluid img-thumbnail" src="../assets/img/azure.png">
								          </a>
								    </div>
								   <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a href="#" class="d-block mb-4 h-100">
								            <img class="img-fluid img-thumbnail" src="../assets/img/windows.png">
								          </a>
								    </div>
								  </div>
							</div>	
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />