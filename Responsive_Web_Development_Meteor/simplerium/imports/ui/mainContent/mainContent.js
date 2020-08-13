import { Meteor } from 'meteor/meteor';
import { Template } from 'meteor/templating';
import { Session } from 'meteor/session';

import { Properties } from '../../api/properties.js';

import './mainContent.html';
import './mainContent.css';
import '../propertiesGrid/propertiesGrid.js';
import '../propertyMain/propertyMain.js';


Template.mainContent.onCreated(function mainContentOnCreated() {
  Meteor.subscribe('properties');
  Session.set('content', 'properties-grid');
});


Template.mainContent.helpers({
  isPropertiesGrid(){
/*    console.log(content,session.get('content'));
*/    return Session.get('content') == 'properties-grid';
  },
  isPropertyMain(){
    return Session.get('content') == 'property-main';
  },
  getProperty(){
    const property = Properties.findOne(Session.get('propertyID'));
    console.log(property);
    return property;
  },
  properties() {
    const instance = Template.instance();
    // Return all of the properties
    // Show newest properties at the top
    return Properties.find({},{ sort: { createdAt: -1 }});
  },

});

Template.mainContent.events({
/*  'click .toggle-checked'() {
    // Set the checked property to the opposite of its current value
    Meteor.call('tasks.setChecked', this._id, !this.checked);
  },
  'click .delete'() {
    Meteor.call('tasks.remove', this._id);
  },
  'tasks.setPrivate'(taskId, setToPrivate) {
    check(taskId, String);
    check(setToPrivate, Boolean);
 
    const task = Tasks.findOne(taskId);
 
    // Make sure only the task owner can make a task private
    if (task.owner !== Meteor.userId()) {
      throw new Meteor.Error('not-authorized');
    }
 
    Tasks.update(taskId, { $set: { private: setToPrivate } });
  },*/
});