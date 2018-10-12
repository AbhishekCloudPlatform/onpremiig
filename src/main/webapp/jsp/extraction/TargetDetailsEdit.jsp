<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<fieldset class="fs">
	<div>
		<div id="dyn1">
			<div class="form-group row">
				<div class="col-sm-6">
					<label>Target Name *</label> <input type="text"
						class="form-control" id="target_unique_name1"
						name="target_unique_name1" placeholder="Target Name" value="${tgtx[0]}">
				</div>
				<div class="col-sm-6">
					<label>Target Type *</label> <select name="target_type1"
						id="target_type1" class="form-control"
						onchange="sys_typ(this.id,this.value)">
						<c:if test="${tgtx[1]=='gcs' || tgtx[1]=='GCS'}">
						<option value="GCS" selected="selected">Google Cloud
							Storage</option>
						<option value="HDFS">Hadoop File System</option>
						</c:if>
						<c:if test="${tgtx[1]=='hdfs' || tgtx[1]=='HDFS'}">
						<option value="GCS">Google Cloud
							Storage</option>
						<option value="HDFS" selected="selected">Hadoop File System</option>
						</c:if>
					</select>
				</div>
			</div>
			<div id="g1" class="gx"
			<c:if test="${tgtx[1]=='gcs' || tgtx[1]=='GCS'}">
			style="display:block;"
			</c:if>
			<c:if test="${tgtx[1]=='hdfs' || tgtx[1]=='HDFS'}">
			style="display:none;"
			</c:if>
			>
				<div class="form-group row">
					<div class="col-sm-4">
						<label>Target Project *</label> <input type="text"
							class="form-control" id="target_project1" name="target_project1"
							placeholder="Target Project" value="${tgtx[2]}">
					</div>
					<div class="col-sm-4">
						<label>Service Account *</label> <input type="text"
							class="form-control" id="service_account1"
							name="service_account1" placeholder="Service Account"
							value="${tgtx[3]}">
					</div>
					<div class="col-sm-4">
						<label>Target Bucket *</label> <input type="text"
							class="form-control" id="target_bucket1" name="target_bucket1"
							placeholder="Target Bucket" value="${tgtx[4]}">
					</div>
				</div>
			</div>
			<div id="h1" class="hx"
			<c:if test="${tgtx[1]=='gcs' || tgtx[1]=='GCS'}">
			style="display:none;"
			</c:if>
			<c:if test="${tgtx[1]=='hdfs' || tgtx[1]=='HDFS'}">
			style="display:block;"
			</c:if>
			>
				<div class="form-group row">
					<div class="col-sm-6">
						<label>KNOX URL *</label> <input type="text" class="form-control"
							id="knox_url1" name="knox_url1" placeholder="KNOX URL"
							value="${tgtx[5]}">
					</div>
					<div class="col-sm-6">
						<label>Hadoop Path *</label> <input type="text"
							class="form-control" id="hadoop_path1" name="hadoop_path1"
							placeholder="Hadoop Path"
							value="${tgtx[6]}">
					</div>
				</div>
				<div class="form-group row">
					<div class="col-sm-6">
						<label>Username *</label> <input type="text" class="form-control"
							id="username1" name="username1" placeholder="Username"
							value="${tgtx[7]}">
					</div>
					<div class="col-sm-6">
						<label>Password *</label> <input type="password"
							class="form-control" id="password1" name="password1"
							placeholder="Password" value="${tgtx[8]}">
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- <div class="form-group" style="float: right; position:relative; margin: 5px;">
									<button id="add" type="button"
										class="btn btn-rounded btn-gradient-info mr-2"
										onclick="return dup_div();">+</button>
								</div>-->
</fieldset>
<button onclick="jsonconstruct('upd');"
	class="btn btn-rounded btn-gradient-info mr-2">Update</button>
<button onclick="jsonconstruct('del');"
	class="btn btn-rounded btn-gradient-info mr-2">Delete</button>