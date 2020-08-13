import { Meteor } from 'meteor/meteor';
import '../imports/api/tasks.js';
import '../imports/api/properties.js';
import { Properties } from '../imports/api/properties.js';

Meteor.startup(function(){
    var globalObject=Meteor.isClient?window:global;
    for(var property in globalObject){
        var object=globalObject[property];
        if(object instanceof Meteor.Collection){
            object.remove({});
        }
    }
});
