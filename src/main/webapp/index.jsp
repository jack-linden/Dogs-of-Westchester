<jsp:include page="header.jsp" />



<div class="form-group" id="search-bar">
	<div class="row">
		<div class="col-lg-6">
			<div class="input-group">
				<span class="input-group-addon"> <input type="checkbox" aria-label="..." name="search-property" value="Name">
					<label for="name">Name</label>
				</span> <span class="input-group-addon"> <input type="checkbox" aria-label="..." name="search-property"
					value="Breed"> <label for="breed">Breed</label>
				</span> <span class="input-group-addon"> <input type="checkbox" aria-label="..." name="search-property" value="City">
					<label for="location">Location</label>
				</span> <input type="text" class="form-control" placeholder="Search for..." id="search-text"> <span
					class="input-group-btn">
					<button data-dismiss="alert" class="btn btn-default" type="button" id="submit-search">Go!</button>
				</span>
			</div>
		</div>
	</div>
</div>
<br>
<br>

<div>

	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active"><a href="#search-table" aria-controls="home" role="tab" data-toggle="tab">Search
				Table</a></li>
		<li role="presentation"><a href="#map" aria-controls="profile" role="tab" data-toggle="tab">Maps</a></li>
		<li role="presentation"><a href="#trends" aria-controls="messages" role="tab" data-toggle="tab">Trends</a></li>
	</ul>
	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="search-table">
			<div id="search-results"></div>
		</div>
		<div role="tabpanel" class="tab-pane" id="map">
			<div id="mapping-results">
				<br>
				<!-- <iframe width='100%' height='500px' frameBorder='0' src='https://a.tiles.mapbox.com/v4/1530dogproject.mn63dk7k/attribution,zoompan,zoomwheel,geocoder,share.html?access_token=pk.eyJ1IjoiMTUzMGRvZ3Byb2plY3QiLCJhIjoiNzFmYjZiNWNiYTg0ODcxYzYwNzM3OTZiY2JlNzc0ODQifQ._SJtkTq_1yyADMyNnQdRQA'></iframe> -->
				<iframe src='toggle.html' width='100%' height='500px' frameBorder='0'></iframe>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane" id="trends">
			<div id="trend-results"><br></div>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		$.ajax({
			url : '/trends',
			type : 'GET',
			dataType : 'json',
			success : function(data) {
				var trendDiv = $('#trend-results');
				for (var i = 0; i < data.trends.length; i++) {
					var list = buildTopTenTables(data.trends[i]);
					trendDiv.append(list);
				}
			}
		});
	});
	$('#myTabs a').click(function(e) {
		e.preventDefault()
		$(this).tab('show')
	});
	$("#submit-search").click(function() {
		var arr = [];
		$('input[name="search-property"]:checked:enabled').each(function() {
			arr.push($(this).val());
		});
		if (arr.length == 0) {
			return;
		}
		var queryText = $('#search-text').val();
		var json = JSON.stringify(arr);
		$.ajax({
			url : "/search",
			type : 'GET',
			data : {
				'search-text' : queryText,
				'search-properties' : json
			},
			dataType : 'JSON',
			success : function(data) {
				var tableDiv = $("#search-results");
				tableDiv.empty();

				var arrayOfDogs = data.dogs;
				if (arrayOfDogs.length == 0) {
					return;
				}
				var tableString = buildSearchResultsTableString(arrayOfDogs);
				tableDiv.append(tableString);
				$(document).ready(function() {
					$("#search-results-table").dataTable();
				});
				//TODO Pass data.dogs to map populating function
			}

		});
	});

	function buildSearchResultsTableString(arrayOfDogs) {
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

		for (var i = 0; i < arrayOfDogs.length; i++) {
			var dog = arrayOfDogs[i];
			table += '<tr>';
			table += '<td>' + dog.name + '</td>';
			table += '<td>' + dog.breed + '</td>';
			table += '<td>' + dog.sex + '</td>';
			table += '<td>' + dog.color + '</td>';
			table += '<td>' + dog.condition + '</td>';
			table += '<td>' + dog.location + '</td>';
			table += '</tr>';
		}
		return table;

	}
	function buildTopTenTables(trend) {
		var list = "";
		var arrOfData = trend.trendData;
		list += "<li class=\"side-by-side\">";
		list += "<div class=\"small-list-div\">";
		list += "<ul class=\"list-group small-list\">";
		list += "<li class=\"list-group-item\"><center>" + trend.trendType + "</center></li>";
		for (var i = 0; i < arrOfData.length; i++) {
			var trendData = arrOfData[i];
			list += "<li class=\"list-group-item\"><span class=\"badge\">" + trendData.count + "</span>" + trendData.value + "</li>";
		}
		list += "</ul>";
		list += "</div>";
		list += "</li>";
		return list;

	}
</script>
<jsp:include page="footer.jsp" />