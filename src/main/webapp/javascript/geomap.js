
function prepareMap( ){
	
	$(document).ready(function() {
		var accesstoken = 'pk.eyJ1IjoiMTUzMGRvZ3Byb2plY3QiLCJhIjoiNzFmYjZiNWNiYTg0ODcxYzYwNzM3OTZiY2JlNzc0ODQifQ._SJtkTq_1yyADMyNnQdRQA';
		L.mapbox.accessToken = accesstoken;
		var map = L.mapbox.map('map', 'mapbox.comic').setView([ 41.079, -73.864 ], 10);
		var myLayer = L.mapbox.featureLayer().addTo(map);
		map.scrollWheelZoom.enable();
		return map;
	});
}
function populateMap(dogsArray) {
  var map = prepareMap( );
  var query = "XXX";
  var arrayOfCounts = parseDogs(dogsArray);

  var geojson = [];

  $.getJSON("javascript/geolocations.json", function(jsonresponse) {
    geojson = jsonresponse;
  });
  var filteredGeoJson = [];
  for( var i = 0; i < geojson.length; i++ ){
  		var location = geojson[i].properties.title;
  		if( arrayOfCounts[location] != undefined || arrayOfCounts[location] != 0 ){
  			geojson[i].properties.description = "Found " + arrayOfCounts[location] + " dogs matching the query \"" + query + "\"."; 
  			filteredGeoJson.push(geojson[i]);
  		}
  }
  
  L.mapbox.featureLayer().addTo(map)c.setGeoJSON(filteredGeoJson);
}

function parseDogs(dogsArray) {
	var locationCounts = [];

	for(var i = 0; i<dogsArray.length; i++) {
		var location = dogsArray[i].location;
		if( locationCounts[location] == undefined ){
			locationCounts[location] = 0;
		}
		locationCounts[location]++;
	}

	return locationCounts;
}


  
