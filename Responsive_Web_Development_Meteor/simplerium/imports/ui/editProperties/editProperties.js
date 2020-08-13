import { Meteor } from 'meteor/meteor';
import { Template } from 'meteor/templating';
import { ReactiveDict } from 'meteor/reactive-dict';

import { Properties } from '../../api/properties.js';

import './editProperties.html';
import './editProperties.css';
 
Template.editProperties.onCreated(function editPropertiesOnCreated() {
  this.state = new ReactiveDict();
  this.state.set('adding',true);
});
 
Template.editProperties.events({
  'click .addButton': function(event, instance) {
    instance.state.set('adding', true);
  },
  'click .removeButton': function(event, instance) {
    instance.state.set('adding', false);
  },
})

Template.editProperties.helpers({
  adding(instance) {
    return Template.instance().state.get('adding');
  },
})


Template.addProperty.events({
  'submit form': function(event) {
/*    const fs = require('fs');
*/   
    // Prevent default browser form submit
    event.preventDefault();
 
    // Get values from form element
    const name = event.target.addName.value;
    const address = event.target.addAddress.value;
    const file = event.target.addImage.files[0];
    var Buffer = require('buffer/').Buffer;
/*    let imgBlob = imgFile.slice(0,imgFile.size-1);
*/    
    let reader = new FileReader();
    reader.onload = function(e) {
      let rawData = reader.result;
      let image = Buffer.from(rawData,'binary').toString('base64');
      console.log(image);
      //insert a property into the collection
      Meteor.call('properties.insert', name, address, image);
    }
    reader.readAsBinaryString(file);
/*    Meteor.call('properties.insert', name, address, imgFile);
*/
    // Clear form
    event.target.addName.value = '';
    event.target.addAddress.value = '';
  }
});

Template.removeProperty.onCreated(function removePropertyOnCreated() {
  Meteor.subscribe('properties');
});

Template.removeProperty.events({
  'submit form': function(event) {
    event.preventDefault();

    const id = event.target.removeId.value;
    Meteor.call('properties.remove', id);
  }
});

Template.removeProperty.helpers({
  properties() {
    return Properties.find({},{ sort: { createdAt: -1} });
  }
});