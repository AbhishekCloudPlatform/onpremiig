<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function jsonconstruct() {
		for (var y = 1; y <= document.getElementById("counter").value; y++) {
			multisel('col_name' + y, 'columns_name' + y);
			if (document.getElementById("where_clause" + y).value === "") {
				document.getElementById("where_clause" + y).value = "1=1";
			}
		}
		var data = {};
		$(".form-control").serializeArray().map(function(x) {
			data[x.name] = x.value;
		});
		var x = '{"header":{"user":"info@clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
				+ JSON.stringify(data) + '}}';
		document.getElementById('x').value = x;
		//alert(x);
		//console.log(x);
		document.getElementById('DataDetails').submit();
	}
	var i = 1;
	function dup_div() {
		var tbl = document.getElementById('table_name' + i);
		var col = document.getElementById('col_name' + i);
		var whr = document.getElementById('where_clause' + i);
		var fth = document.getElementById('fetch_type' + i);
		var inc = document.getElementById('incr_col' + i);
		var tbldiv = tbl.parentNode.cloneNode(true);
		var coldiv = col.parentNode.cloneNode(true);
		var whrdiv = whr.parentNode.cloneNode(true);
		var fthdiv = fth.parentNode.cloneNode(true);
		var incdiv = inc.parentNode.cloneNode(true);
		var tbl1 = tbldiv.childNodes[3];
		var col1 = coldiv.childNodes[3];
		var whr1 = whrdiv.childNodes[3];
		var fth1 = fthdiv.childNodes[3];
		var inc1 = incdiv.childNodes[3];

		var x = ++i;
		tbl1.id = "table_name" + x;
		col1.id = "col_name" + x;
		whr1.id = "where_clause" + x;
		fth1.id = "fetch_type" + x;
		inc1.id = "incr_col" + x;
		tbl1.name = "table_name" + x;
		col1.name = "col_name" + x;
		whr1.name = "where_clause" + x;
		fth1.name = "fetch_type" + x;
		inc1.name = "incr_col" + x;
		incdiv.id = "incc" + x;

		var c = document.createElement('div');
		c.className = "form-group row";
		var d = document.createElement('div');
		d.id = "fldd" + i;

		tbl.parentNode.parentNode.parentNode.appendChild(c);
		c.appendChild(tbldiv);
		c.appendChild(fthdiv);
		col.parentNode.parentNode.parentNode.appendChild(d);
		d.appendChild(incdiv);
		d.appendChild(coldiv);
		whr.parentNode.parentNode.appendChild(whrdiv);

		var counter = document.getElementById('counter').value;
		for (var j = 1; j <= counter; j++) {
			var vl = document.getElementById('table_name' + j).value;
			var curr = document.getElementById(tbl1.id);
			for (var k = 0; k < curr.length; k++) {
				if (curr.options[k].value == vl)
					curr.remove(k);
			}
		}

		document.getElementById('incc' + x).style.display = "none";
		document.getElementById('counter').value = i;
	}
	function incr(id, val) {
		var in1 = id.slice(-1);
		var in2 = id.slice(-2, -1);
		if (in2 === "e")
			;
		else {
			in1 = id.slice(-2);
		}
		var in3 = 'incc' + in1;
		if (val == "incr") {
			document.getElementById(in3).style.display = "block";
		} else if (val == "full") {
			document.getElementById(in3).style.display = "none";
		}
	}
	function getcols(id, val) {
		var in1 = id.slice(-1);
		var in2 = id.slice(-2, -1);
		if (in2 === "e")
			;
		else {
			in1 = id.slice(-2);
		}
		var id = in1;
		var table_name = val;
		var src_val = document.getElementById("src_val").value;
		var connection_id = document.getElementById("connection_id").value;
		$.post('/extraction/DataDetails2', {
			id : id,
			src_val : src_val,
			table_name : table_name,
			connection_id : connection_id
		}, function(data) {
			$("#fldd" + id).html(data);
		});
	}
	$(document).ready(function() {
		$("#src_sys_id").change(function() {
			var src_sys_id = $(this).val();
			var src_val = document.getElementById("src_val").value;
			$.post('/extraction/DataDetails1', {
				src_sys_id : src_sys_id,
				src_val : src_val
			}, function(data) {
				$('#datdyn').html(data)
			});
		});
	});
		
</script>

<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Data Extraction</h4>
						<p class="card-description">Data Details</p>
						<%
							if (request.getAttribute("successString") != null) {
						%>
						<p class="text-success h4">${successString}</p>
						<%
							}
						%>
						<%
							if (request.getAttribute("errorString") != null) {
						%>
						<p class="text-danger h4">${errorString}</p>
						<%
							}
						%>
						<form class="forms-sample" id="DataDetails" name="DataDetails"
							method="POST" action="/extraction/DataDetails3"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> 
							<input type="hidden" name="src_val" id="src_val" value="${src_val}">
							<div class="form-group">
									<label>Source System Name *</label> <select name="src_sys_id"
										id="src_sys_id" class="form-control">
										<option value="" selected disabled>Source System Name
											...</option>
										<c:forEach items="${src_sys_val}" var="src_sys_val">
											<option value="${src_sys_val.src_sys_id}">${src_sys_val.src_unique_name}</option>
										</c:forEach>
									</select>
								</div>
								<div id="datdyn"></div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />