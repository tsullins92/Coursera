import { Meteor } from 'meteor/meteor';
import { Template } from 'meteor/templating';
import { Session } from 'meteor/session';
 
import { Properties } from '../../api/properties.js';

import './transactionTable.html';
import './transactionTable.css';

Template.transactionTable.onCreated(function transactionTableOnCreated() {
	Meteor.subscribe('properties');
});

Template.transactionTable.helpers({
  transactions() {
    return Properties.findOne(Session.get('propertyID')).transactions;
  },
  parseDate(date){
  	return date.toLocaleDateString();
  }	
})

Template.transactionTable.events({
  'click .property'(){
    Session.set('propertyID', this._id);
    Session.set('content', 'property-main');
  }
});