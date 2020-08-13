import { Meteor } from 'meteor/meteor';
import { Template } from 'meteor/templating';
import { Session } from 'meteor/session';
import { ReactiveDict } from 'meteor/reactive-dict';

import { Properties } from '../../api/properties.js';

import './propertyMain.html';
import './propertyMain.css';
import '../propertiesGrid/propertiesGrid.js';
import '../editTransactions/editTransactions.js';
import '../transactionTable/transactionTable.js';
import '../visualizations/visualizations.js';

Template.propertyMain.onCreated(function propertyMainOnCreated() {
  this.state = new ReactiveDict();
  this.state.set('propertyDisplay', 'ledger');
  Meteor.subscribe('properties');
});


Template.propertyMain.helpers({
  transactions(){
    if(this.transactions!=null){
      return this.transactions;
    }
  },
  property(){
    return Properties.findOne(Session.get('propertyID'));
  },
  displayLedger(){
    const instance = Template.instance();
    return instance.state.get('propertyDisplay')=='ledger';
  },
  displayVisualizations(){
    const instance = Template.instance();
    return instance.state.get('propertyDisplay')=='visualizations';
  }
/*  content(){return session.get('content')},
  properties() {
    const instance = Template.instance();
    // Return all of the properties
    // Show newest properties at the top
    return Properties.find({},{ sort: { createdAt: -1 }});
  },*/

});

Template.propertyMain.events({
  'click .ledger-btn'(event,instance){
    instance.state.set('propertyDisplay', 'ledger');
  },
  'click .visualizations-btn'(event,instance){
    instance.state.set('propertyDisplay', 'visualizations')
  },  
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