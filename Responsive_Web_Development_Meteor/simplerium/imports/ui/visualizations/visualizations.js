import { Meteor } from 'meteor/meteor';
import { Template } from 'meteor/templating';
import { Session } from 'meteor/session';
import Plotly from 'plotly.js-dist';

import { Properties } from '../../api/properties.js';

import './visualizations.html';
import './visualizations.css';

Template.visualizations.onCreated(function visualizationsOnCreated() {
	Meteor.subscribe('properties');
});

Template.visualizations.onRendered(function visualizationsOnRendered() {
  linePlotBalance();
});

Template.visualizations.helpers({
  transactions() {
    return Properties.findOne(Session.get('propertyID')).transactions;
  },
  parseDate(date){
  	return date.toLocaleDateString();
  }	
})

Template.visualizations.events({
  'click .property'(){
    Session.set('propertyID', this._id);
    Session.set('content', 'property-main');
  }
});

function linePlotBalance(){
  let sortedTransactions = Properties.findOne(Session.get('propertyID')).transactions.sort((currentTransaction,nextTransaction)=>{return currentTransaction.transactionDate-nextTransaction.transactionDate});
  let dates = sortedTransactions.map((transaction)=>{return transaction.transactionDate});
  let balances = sortedTransactions.map((transaction,index)=>{return sortedTransactions.filter((transaction1)=>{return transaction.transactionDate>=transaction1.transactionDate}).map((transaction2)=>{return transaction2.amount}).reduce((current,total)=>{return current+total})});
  Plotly.plot( "balancePlot", [{
    x: dates,
    y: balances }], {
    margin: { t: 0 } } );
}