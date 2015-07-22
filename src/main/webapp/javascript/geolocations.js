/*
 * This method populates the markers on the map.
 * Currently, the dog licensing data visualization service are available
 * in the following three locations: White Plains, Rye, and Bedford.
 */
function getGeoLocations() {
 	var geoLocations = [ {
 		"type" : "Feature",
 		"geometry" : {
 			"type" : "Point",
 			"coordinates" : [ -73.7636, 41.0342 ]
 		},
 		"properties" : {
 			"title" : "White Plains",
 			"description" : "",
 			"marker-color" : "#3ca0d3",
 			"marker-size" : "large",
 			"marker-symbol" : "dog-park"
 		}
 	},

 	{
 		"type" : "Feature",
 		"geometry" : {
 			"type" : "Point",
 			"coordinates" : [ -73.6837, 40.9807 ]
 		},
 		"properties" : {
 			"title" : "Rye",
 			"description" : "",
 			"marker-color" : "#63b6e5",
 			"marker-size" : "large",
 			"marker-symbol" : "dog-park"
 		}
 	},

 	{
 		"type" : "Feature",
 		"geometry" : {
 			"type" : "Point",
 			"coordinates" : [ -73.6437, 41.2046 ]
 		},
 		"properties" : {
 			"title" : "Bedford",
 			"description" : "",
 			"marker-color" : "#63b6e5",
 			"marker-size" : "large",
 			"marker-symbol" : "dog-park"
 		}
 	} ];
 	return geoLocations;
 }