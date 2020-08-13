import { Meteor } from 'meteor/meteor';
import { Template } from 'meteor/templating';
import { Session } from 'meteor/session';

import { Properties } from '../../api/properties.js';

import '../editProperties/editProperties.js';
import '../propertyDiv/propertyDiv.js';
import './propertiesGrid.html';
import './propertiesGrid.css';


Template.propertiesGrid.onCreated(function propertiesGridOnCreated() {
  this.state = new ReactiveDict();
  Meteor.subscribe('properties');
});

Template.propertiesGrid.helpers({
  properties() {
    // Return all of the properties
    // Show newest properties at the top
    return Properties.find({},{ sort: { createdAt: -1 }});
  },

});