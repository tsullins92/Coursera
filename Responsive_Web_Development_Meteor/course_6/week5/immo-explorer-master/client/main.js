import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';
const Properties = new Mongo.Collection('properties');

import './main.html';
import './search.html';

window.location.href = "#searchSection";
var mymap = null;
// subscribe to read data
var localProperties = Meteor.subscribe('propertiesPubSub');

// Map init function
function initMap(lat,lon) {
	if (mymap !== undefined && mymap !== null) {
		mymap.remove();
	}

	// Create "mymap" object and insert it in HTML element with ID = "map"
	mymap = L.map('map').setView([lat, lon], 17);
	// Leaflet doesn't get tiles from a default server. We have to tell it where to get them from. Here, openstreetmap.fr
	L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
		// Don't forget attribution
		attribution: 'data © <a href="//osm.org/copyright">OpenStreetMap</a>/ODbL - rendering <a href="//openstreetmap.fr">OSM France</a>',
		minZoom: 15,
		maxZoom: 20
	}).addTo(mymap);

	return true;
}

window.onload = function(){
	// Init function runs when DOM is loaded
	var latDefault = 48.8603428;
	var lonDefault = 2.3465336;
	initMap(latDefault,lonDefault);
	window.location.href = "#searchSection";

};

Template.searchTemplate.events({
	// on submit, the app must do 3 things:
	// 1. retrieve coord from address
	// 2. init map and retrieve boundingBox
	// 3. retrieve list of properties within boundingBox
	'submit .searchProperty':function(event) {
		// Prevent default browser form submit
		event.preventDefault();

		// Get value from form element
		var address;
		var surface = 0;
		var nbRooms = 0;
		var isHouse;
		var isFlat;
		address = event.target.address.value;
		surface = event.target.surface.value;
		isHouse = document.getElementById("selectHouses").checked;
		isFlat = document.getElementById("selectFlats").checked;
		var minSurface = Math.floor(surface * 0.85); // minSurface = 15% below surface
		var maxSurface = Math.ceil(surface * 1.15); // // maxSurface = 15% above surface
		nbRooms = event.target.nbRooms.value;
		console.log("address: "+address+" surface:"+surface+" nbRooms:"+nbRooms+" isHouse:"+isHouse+" isFlat:"+isFlat);

		// we are using openstreetmap's Nominatim service
		var geocode = 'https://nominatim.openstreetmap.org/search?format=json&q=' + encodeURI(address);

		$.getJSON(geocode, function(data) { // 1. retrieve coord from address
			// get lat + lon from first match
			console.log("lat: " + data[0].lat + ", lon: " + data[0].lon);
			var latlng = [data[0].lat, data[0].lon];
			if (initMap(data[0].lat,data[0].lon)) { // 2a. init mat
				var boundingBox = mymap.getBounds(); // 2b. retrieve boundingBox
				console.log("client: calling getProperties(" + boundingBox + ")" );
				Meteor.call('getProperties', boundingBox, function(err, response) { // 3. retrieve list of properties within boundingBox
					if(err) {
						console.log('getProperties Error:' + err.reason);
						return;
					}
					response.forEach(function(item,index) {
						if (((surface == 0) || ((item.surface >= minSurface) && (item.surface <= maxSurface)))
							&& ((nbRooms == 0) || (item.nbRooms == nbRooms))
							&& ((isHouse && (item.typeLocal == "Maison"))
								|| (isFlat && (item.typeLocal == "Appartement")))) {
								console.log("Found property!");
								L.marker([item.location.coordinates[1], item.location.coordinates[0]], {title: item.valeurFonciere, riseOnHover: true}).addTo(mymap);
						}
					});
				});
			}

			window.location.href = "#mapSection";
			return 
		});
  	}
});
