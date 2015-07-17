function populateMap(dogsArray) {

L.mapbox.accessToken = 'pk.eyJ1IjoiMTUzMGRvZ3Byb2plY3QiLCJhIjoiNzFmYjZiNWNiYTg0ODcxYzYwNzM3OTZiY2JlNzc0ODQifQ._SJtkTq_1yyADMyNnQdRQA';
  var mapTooltips = L.mapbox.map('map-tooltips', 'mapbox.comic')
  .setView([41.079, -73.864], 10); 
  var myLayer = L.mapbox.featureLayer().addTo(mapTooltips);
  
  var query = "XXX";
  var arrayOfCounts = parseDogs(dogsArray);
  arrayOfCounts["Rye"]

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
  
  myLayer.setGeoJSON(filteredGeoJson);
  mapTooltips.scrollWheelZoom.enable();
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


  
