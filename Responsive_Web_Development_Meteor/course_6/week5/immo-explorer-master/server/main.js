import { Meteor } from 'meteor/meteor';
const Properties = new Mongo.Collection('properties');

Meteor.startup(() => {
 
});

Meteor.methods({
    getProperties: function(boundingBox){
    	console.log("server: in getProperties, boundingBox = " + boundingBox);

		return Properties.find(
		{
			"location.coordinates.0": {
				"$gt": boundingBox._southWest.lng,
				"$lt": boundingBox._northEast.lng
			},
			"location.coordinates.1": {
				"$gt": boundingBox._southWest.lat,
				"$lt": boundingBox._northEast.lat
			}
		}).fetch();
    }
});

