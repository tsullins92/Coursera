import { Meteor } from 'meteor/meteor';
import { Template } from 'meteor/templating';
import { Session } from 'meteor/session';
 
import './propertyDiv.html';
import './propertyDiv.css';

Template.propertyDiv.onCreated(function propertyDivOnCreated() {});

Template.propertyDiv.helpers({
  total(){
    return this.transactions.map((transaction)=>{return transaction.amount}).reduce((amount,total)=>{return amount+total});
  },
});  

Template.propertyDiv.events({
  'click .property'(){
    Session.set('propertyID', this._id);
    Session.set('content', 'property-main');
  }
});