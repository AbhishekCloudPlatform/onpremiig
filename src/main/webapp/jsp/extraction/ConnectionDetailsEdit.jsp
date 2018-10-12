<fieldset class="fs">
	<div class="form-group row">
		<div class="col-sm-6">
			<label>Connection Name *</label> <input type="text"
				class="form-control" id="connection_name" name="connection_name"
				placeholder="Connection Name" value="${conn_val.connection_name}">
		</div>
		<div class="col-sm-6">
			<label>Connection Type *</label> <input type="text"
				class="form-control" id="connection_type" name="connection_type"
				value="${src_val}" readonly="readonly">
		</div>
	</div>
	<div class="form-group row">
		<div class="col-sm-3">
			<label>Host Name *</label> <input type="text" class="form-control"
				id="host_name" name="host_name" placeholder="Host Name" value="${conn_val.host_name}">
		</div>
		<div class="col-sm-3">
			<label>Port Number *</label> <input type="text" class="form-control"
				id="port" name="port" placeholder="Port Number" value="${conn_val.port_no}">
		</div>
		<div class="col-sm-3">
			<label>Username *</label> <input type="text" class="form-control"
				id="user_name" name="user_name" placeholder="Username" value="${conn_val.username}">
		</div>
		<div class="col-sm-3">
			<label>Password *</label> <input type="password" class="form-control"
				id="password" name="password" placeholder="Password" value="${conn_val.password}">
		</div>
	</div>
	<!-- 
	<div class="form-group" id="db" style="display: none;">
		<label>Database Name *</label> <input type="text" class="form-control"
			id="db_name" name="db_name" placeholder="Database Name" value="${conn_val.database_name}">
	</div>
	-->
	<div class="form-group" id="service">
		<label>Service Name/ID *</label> <input type="text"
			class="form-control" id="service_name" name="service_name"
			placeholder="Service Name/ID" value="${conn_val.service_name}">
	</div>
</fieldset>
<button onclick="jsonconstruct('upd');"
	class="btn btn-rounded btn-gradient-info mr-2">Update</button>
<button onclick="jsonconstruct('del');"
	class="btn btn-rounded btn-gradient-info mr-2">Delete</button>