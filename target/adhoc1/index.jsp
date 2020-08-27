<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Query Generator</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script src="http://code.jquery.com/jquery-2.0.0.min.js" type="text/javascript"></script>
	
	<script>
	
	$(document).ready(function()
			{   
				var api;
				
				api = "http://localhost:8055/adhoc1_war_exploded/webapi/DatabaseController/alldatabases";
				
				$.get(api, function(data, status)
				{
					
					var myString="<option>--Default--</option>";
					
					for(var i = 0; i < data.length; i++)
					{
				 		myString=myString+"<option value = '"+ data[i].name +"'>"+ data[i].name +"</option>";
					}
					console.log(myString)

					$('#db').html(myString);
				});	
			});
	
	function fetchTables()
	{
	   // $('#addColumn').prop('disabled', true);
		var api;
		var dbname = $('#db').find(":selected").text();
		api = "http://localhost:8055/adhoc1_war_exploded/webapi/TableController/tables/" + dbname;

		$.get(api, function(data, status)
		{
			var myString="<option>--Default--</option>";
			for(var i = 0; i < data.length; i++)
			{
		 		myString=myString+"<option value = '"+ data[i].name +"'>"+ data[i].name +"</option>";
			}

			$('#tb').html(myString);

		});	
	}


	function fetchColumns()
	{

		//$('#addColumn').prop('disabled', true);
		var api;
		var dbname = $('#db').find(":selected").text();
		var tbname = $('#tb').find(":selected").text();
		
		api = "http://localhost:8055/adhoc1_war_exploded/webapi/ColumnController/columns/" + dbname + "/" + tbname;
		
		$.get(api, function(data, status)
		{
	
			var myString="<option>--Default--</option>";
			for(var i = 0; i < data.length; i++)
			{
		 		myString=myString+"<option value = '"+ data[i].name +"'>"+ data[i].name +"</option>";
			}
            
			$('#cl').html(myString);
		});	
	}
	
	var columns = [];
	function addColumn()
	{
		var dbname = $('#db').find(":selected").text();
		var tbname = $('#tb').find(":selected").text();
		var clname = $('#cl').find(":selected").text();
		
		$('#db').prop("disabled", true);
		var columnName = {dbname:dbname, tbname:tbname, clname:clname};
		columns.push(columnName)
		var index = columns.indexOf(columnName);

		
		var dropDown = 	"<select class='form-control'><option value = '0'>--None--</option>" +
						"<option value = '1'>Sensitive</option>" +
						"<option value = '2'>Insensitive</option>" +
						"<option value = '3'>Identifying</option>" +
						"<option value = '4'>Quasi-Identifying</option> </select>";
		var dropDown2="<select class='form-control' id='where_cond"+index+"'><option value = '0'>--None--</option>" +
				"<option value = '<'><</option>" +
				"<option value = '>'>></option>" +
				"<option value = '='>=</option>" +
				"<option value = '<='><=</option>"+
				"<option value = '>='><=</option>"+
				"<option value = 'like'>like</option> </select>";
		var clause="<input type='text' id='clause"+index+"'/ >"
		var or_and="<select class='form-control' id='or_and"+index+"'><option value = '0'>--None--</option>" +
		"<option value = 'or'>or</option>" +
		"<option value = 'and'>and</option> </select>"
		var selColm="<input type='checkbox' id='sel"+clname+"' align='centre'/>"

		var ord="<input type='checkbox' id='ord"+index+"'/>"

		  				
		var myString = "<tr id='selRow" + index + "'><td>" + tbname + "</td><td>" + clname + "</td><td>"+ dropDown +"</td><td>"+ selColm +"</td><td>"+
				dropDown2+"</td><td> "+clause +"</td><td> "+or_and +"</td><td> "+ord +"</td><tr>";
		$('#columnValues > tbody:last').append(myString);

	}
	var whStrArr=[]
	function getWhere(){
		var whereStrG=""
		var whereStrL=""
		var lastwhr=0;
		var lastwhrcond="";
		var lastwhrclause="";
		for(var i=0;i<columns.length;i++){
			whereStrL=""
			console.log('clause'+i)
			var clse = document.getElementById('clause' + i);
			var clause=clse.value;
			var whereConde = document.getElementById('where_cond' + i);
			var whereCond=whereConde.options[whereConde.selectedIndex].value;
			if(clause!="" && whereCond!=0) {

				var or_ande = document.getElementById('or_and' + i);
				var or_and = or_ande.options[or_ande.selectedIndex].value;

				whereStrL = columns[i].tbname + "." + columns[i].clname + " " + whereCond + " " + clause + " " + or_and + " ";
				//whereStrG = whereStrG + columns[i].tbname + "." + columns[i].clname + " " + whereCond + " " + clause + " " + or_and + " ";
				columns[i]["whr"] = whereStrL;
				whStrArr.push(whereStrL)
				console.log(whereStrL);
				lastwhr=i;
				lastwhrcond=whereCond;
				lastwhrclause=clause;
			}
			else{
				columns[i]["whr"]="";
			}

		}
		if(lastwhrclause!=""&&lastwhrcond!="")
			columns[lastwhr]["whr"]=columns[lastwhr].tbname + "." + columns[lastwhr].clname + " " +lastwhrcond + " " + lastwhrclause+ " ";

		//console.log(whereStr)
	}
	var orderBy=[]
	function getOrderBy(){
		for(var i=0;i<columns.length;i++){
			var order_chk=document.getElementById('ord'+i);
			columns[i]["ord"]=order_chk.checked;
		}
	}
	var colm_to_show=[];
	function printTable(result)
	{
		var myString = "<table class = 'table table-striped table-bordered'><thead><tr>";
		for(var i = 0; i < columns.length; i++)
		{
			if(colm_to_show[i]) {
				console.log(columns[i].clname);
				myString = myString + "<th>" + columns[i].clname + "</th>";
			}
		}
		myString = myString + "</tr></thead><tbody>";
		for(var i = 0; i < result.length; i++)
		{

			myString = myString + "<tr>";
			for(var j = 0; j < result[i].length; j++)
			{
				myString = myString + "<td>" + result[i][j] + "</td>";
			}
			myString = myString + "</tr>";
		}
		myString = myString + "</tbody></table>";
		//alert(myString);
		console.log(myString);
		$('#resultTable').html(myString);
	}

	var queryString="";
	function previewQuery()
		{
			getWhere();
			console.log( columns)
			columns.forEach(function(clm){
				//console.log(clm.clname)
				var chkd= document.getElementById("sel"+clm.clname);
				//console.log(chkd.checked)
				clm["show_me"]=chkd.checked
				colm_to_show.push(chkd.checked)
			});
			getOrderBy();
			//console.log(colm_to_show)

			console.log(columns)
			var columnNames = JSON.stringify(columns);

			console.log(columnNames);
			$.ajax({
				type: 'POST',
				url: 'http://localhost:8055/adhoc1_war_exploded/webapi/ColumnController/setcolumns/',
				data: columnNames,
				contentType: 'application/json',
				dataType: 'json',
				processData: false,
				async: true,

				success:function(result) {
					console.log(columnNames);
					//console.log(result.queryStr)
					//alert('success');
					queryString=result.queryStr;
					printTable(result.resultSet);

				},

				error: function (err) {
					alert(err.status);
				}
			});
		}
	function saveQuery(){
		var dbname = $('#db').find(":selected").text();
		var qname = document.getElementById("qname").value;
		var queryObj={dbname: dbname, qname:qname,qstr:queryString};

		var query = JSON.stringify(queryObj);
		console.log(queryObj);
		$.ajax({
			type: 'POST',
			url: 'http://localhost:8055/adhoc1_war_exploded/webapi/QueryController/saveQuery/',
			data: query,
			contentType: 'application/json',
			dataType: 'json',
			processData: false,
			async: true,

			success:function(result) {

				alert('success');

			},

			error: function (err) {
				alert(err.status);
			}
		});
	}
	

</script>





</head>


<body>



<label for="qname">Query Name: </label> <input type="text" id="qname"></input>
<div>
<label for="db">Databases:</label>
<select id="db" onchange="fetchTables();">
	<option>--None--</option>
	<!-- Databases Drop Down from JavaScript -->
</select>
</div>
<div>
	<label for="tb">Tables:</label>
	<select id="tb" onchange="fetchColumns();">
		<option>--None--</option>
		<!-- Tables Drop Down from JavaScript -->
	</select>
	</div>
	
	<div>
	<label for="cl">Columns:</label>
	<select id="cl">
		<option>--None--</option>
		<!-- Columns Drop Down from JavaScript -->
	</select>
	</div>

   
<div>
		<button onclick="addColumn()" id="addColumn">Add Column</button>
	</div>
	
	<div class="container">
		<!-- Column Values from JavaScript -->
		<table id="columnValues" class = 'table table-striped table-bordered'>
			<thead>
 				<tr>
    				<th>Table Name</th>
				    <th>Column Name</th>
				    <th>Privacy Filter</th>
					<th>Select Column</th>
					<th>Where Condition</th>
					<th>Where Clause</th>
					<th>Where Connector</th>
					<th>Order By</th>

 				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
<div>
		<button onclick="previewQuery()" id="previewQuery">Preview Query</button>
		<button onclick="saveQuery()" id="saveQuery">Save Query</button>
</div>
<div class="container" id="resultTable"></div>
</body>
</html>