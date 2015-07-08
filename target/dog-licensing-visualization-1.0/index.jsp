
<html>  
<jsp:include page="header.jsp" />
  <body>	
	<div class="form-group" id="search-bar">
		<div class="row">
  			<div class="col-lg-6">
    			<div class="input-group">
      				<span class="input-group-addon">
        				<input type="checkbox" aria-label="..." name="search-property" value="Name">
        				<label for="name">Name</label>
      				</span>
      				<span class="input-group-addon">
        				<input type="checkbox" aria-label="..." name="search-property" value="Breed">
        				<label for="breed">Breed</label>
      				</span>
      				<span class="input-group-addon">
        				<input type="checkbox" aria-label="..." name="search-property" value="City">
        				<label for="location">Location</label>
      				</span>
      				<input type="text" class="form-control" placeholder="Search for..." id="search-text">
 			        <span class="input-group-btn">
       					 <button data-dismiss="alert" class="btn btn-default" type="button" id="submit-search">Go!</button>
      				</span>      				
			    </div>
  			</div>
	</div>
	<br><br>
	<div id="search-results">
		
	</div>
	
	<script>
		$("#submit-search").click(function(){
			var arr = [];
			$('input[name="search-property"]:checked:enabled').each(function () {
                arr.push($(this).val());
            });
            if( arr.length == 0 ){
            	return;
            }
			var queryText = $('#search-text').val();
			var json = JSON.stringify(arr);
		    $.ajax({
		    	url: "/search",
		    	type: 'GET',
		    	data: {
		    		'search-text' : queryText,
		    		'search-properties' : json
		    	},			
		    	dataType: 'JSON',
		    	success: function( data ){
					var tableDiv = $("#search-results");
					tableDiv.empty();
					
					var arrayOfDogs = data.dogs;
					if( arrayOfDogs.length == 0 ){
						return;
					}
					var tableString = buildSearchResultsTableString(arrayOfDogs);
					tableDiv.append(tableString);
					$(document).ready(function() {
						$("#search-results-table").dataTable();
					});
		    	}
		        
		    });
		});
		
		function buildSearchResultsTableString( arrayOfDogs ){
			var table = '';
			table += '<table id=\"search-results-table\" data-toggle=\"table\" class=\"table table-bordered\">';
			table += '<thead><tr>';
	        table += '<th data-field=\"name\">Name</th>';
	        table += '<th data-field=\"breed\">Breed</th>';
	        table += '<th data-field=\"sex\">Sex</th>';
	        table += '<th data-field=\"color\">Color</th>';  
	        table += '<th data-field=\"condition\">Condition</th>';
	        table += '<th data-field=\"location\">Location</th>';
			table += '</tr></thead>';
			
	        for (var i = 0; i < arrayOfDogs.length; i++){
	        	var dog = arrayOfDogs[i];
	        	table += '<tr>';
	        	table += '<td>'+dog.name+'</td>';
	        	table += '<td>'+dog.breed+'</td>';
	        	table += '<td>'+dog.sex+'</td>';
	        	table += '<td>'+dog.color+'</td>';			        	
	        	table += '<td>'+dog.condition+'</td>';
	        	table += '<td>'+dog.location+'</td>';	
	        	table += '</tr>';
	   		}
			return table;
		
		}
    </script>
	
  </body>
</html>
