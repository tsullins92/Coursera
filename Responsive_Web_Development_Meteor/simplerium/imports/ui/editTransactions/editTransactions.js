import { Meteor } from 'meteor/meteor';
import { Template } from 'meteor/templating';
import { ReactiveDict } from 'meteor/reactive-dict';

import { Properties } from '../../api/properties.js';

import './editTransactions.html';
import './editTransactions.css';
 
Template.editTransactions.onCreated(function editTransactionsOnCreated() {});
 
Template.editTransactions.events({})

Template.editTransactions.helpers({});

Template.addTransaction.onCreated(function addTransactionsOnCreated() {
  this.state = new ReactiveDict();
  this.state.set('adding',false);
});

Template.addTransaction.events({
  'click .addTransaction-btn': function(event, instance) {
    instance.state.set('adding', !instance.state.get('adding'));
  },
  'submit form': function(event) {
    console.log("adding transaction");
    // Prevent default browser form submit
    event.preventDefault();
 
    // Get values from form element
    const propertyID = Session.get('propertyID');
    const transactionDate = new Date(event.target.addDate.value);
    const description = event.target.addDescription.value;
    const vendor = event.target.addVendor.value;
    const amount = parseFloat(event.target.addAmount.value);
    const addReceipt = event.target.addReceipt.checked;
    let receiptImage;
    if(addReceipt){
      var Buffer = require('buffer/').Buffer;
      let reader = new FileReader();
      let receiptFile = event.target.receiptImage.files[0];
      reader.onload = function(e) {
        let rawData = reader.result;
        let receiptImage = Buffer.from(rawData,'binary').toString('base64');
        Meteor.call('transactions.insert', propertyID,transactionDate,description,vendor,amount,addReceipt,receiptImage);
      }
      reader.readAsBinaryString(receiptFile);
    }
    else{
      Meteor.call('transactions.insert', propertyID,transactionDate,description,vendor,amount,addReceipt);
    }
  }
});

Template.addTransaction.helpers({
  adding(instance) {
    return Template.instance().state.get('adding');
  },
});

Template.removeTransaction.onCreated(function removeTransactionOnCreated() {
  Meteor.subscribe('properties');
  this.state = new ReactiveDict();
  this.state.set('removing',false);
});

Template.removeTransaction.helpers({
  transactions() {
    return Properties.findOne(Session.get('propertyID')).transactions;
  },
  removing(instance) {
    return Template.instance().state.get('removing');
  },
});

Template.removeTransaction.events({
  'click .removeTransaction-btn': function(event, instance) {
    instance.state.set('removing', !instance.state.get('removing'));
  },
  'submit form': function(event) {
    event.preventDefault();
    const propertyID = Session.get('propertyID');
    const transactionID = event.target.removeId.value;
    Meteor.call('transactions.remove',propertyID, transactionID);
  }
});