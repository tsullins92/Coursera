import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';
import { Mongo } from 'meteor/mongo';
import './main.html';
import {Books} from "../shared/collections.js";
import { Session } from 'meteor/session'

Session.set('Books', null);

Template.hello.onCreated(function helloOnCreated() {
  // counter starts at 0
  this.counter = new ReactiveVar(0);
});

Template.hello.helpers({
  counter() {
    return Template.instance().counter.get();
  },
});

Template.hello.events({
  'click button'(event, instance) {
    // increment the counter when button is clicked
    instance.counter.set(instance.counter.get() + 1);
  },
});

Template.main.helpers({
	books: () => Session.get('Books')
	
})

Template.main.events({
	'click button':function(e){
		e.preventDefault();
		const query = document.getElementById('searchInput').value
		const results = Books.find({title: query}).fetch();
		Session.set('Books', results);
		console.log(Session.get('Books'))
	},
})
