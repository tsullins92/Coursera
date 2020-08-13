import { Meteor } from 'meteor/meteor';
import { Template } from 'meteor/templating';
import { ReactiveDict } from 'meteor/reactive-dict';
import { Session } from 'meteor/session';

import './body.html';
import './body.css';
import './mainContent/mainContent.js';
import './accountButtons/accountButtons.js';


Template.body.onCreated(function bodyOnCreated() {
  this.state = new ReactiveDict();
});
 
Template.body.helpers({
/*  tasks() {
    const instance = Template.instance();
    if (instance.state.get('hideCompleted')) {
      // If hide completed is checked, filter tasks
      return Tasks.find({ checked: { $ne: true } }, { sort: { createdAt: -1 } });
    }
    // Otherwise, return all of the tasks
  	// Show newest tasks at the top
    return Tasks.find({},{ sort: { createdAt: -1} });
  },
  incompleteCount() {
    return Tasks.find({ checked: { $ne: true } }).count();
  },*/
});

Template.body.events({
  'click .home-icon'(){
    Session.set('content','properties-grid');
    Session.set('propertyID','');
  }
/*  'submit .new-task'(event) {
    // Prevent default browser form submit
    event.preventDefault();
 
    // Get value from form element
    const target = event.target;
    const text = target.text.value;
 
    // Insert a task into the collection
    Meteor.call('tasks.insert', text);
 
    // Clear form
    target.text.value = '';
  },
  'change .hide-completed input'(event, instance) {
    instance.state.set('hideCompleted', event.target.checked);
  },*/
});