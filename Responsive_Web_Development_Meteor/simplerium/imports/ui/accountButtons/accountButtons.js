import { Meteor } from 'meteor/meteor';
import { Template } from 'meteor/templating';
import { ReactiveDict } from 'meteor/reactive-dict';

import './accountButtons.html';
import './accountButtons.css';
 
Template.accountButtons.onCreated(function accountButtonsOnCreated() {
  this.state = new ReactiveDict();
  this.state.set('accountBtnsClicked',false);
});
 
Template.accountButtons.events({
  'click .accountButton': function(event, instance) {
    instance.state.set('accountBtnsClicked', !instance.state.get('accountBtnsClicked'));
  },
})

Template.accountButtons.helpers({
  btnClicked(instance) {
    return Template.instance().state.get('accountBtnsClicked');
  },
})




Template.loginRegister.onCreated(function loginRegisterOnCreated() {
  this.state = new ReactiveDict();
  this.state.set('loggedin',true);
});
 
Template.loginRegister.events({
  'click .loginButton': function(event, instance) {
    instance.state.set('loggedin', !instance.state.get('loggedin'));
  },
})

Template.loginRegister.helpers({
  loggedin(instance) {
    return Template.instance().state.get('loggedin');
  },
})




Template.register.events({
  'submit form': function(event) {
     event.preventDefault();
     let username = event.target.registerUsername.value;
     //if no user name is provided, generate one from email address
     if(username == 0){username =/(^[^@]*)/.exec(email)[0]}
     var registerData = {
        email: event.target.registerEmail.value,
        username: username,
        password: event.target.registerPassword.value
     }

     Accounts.createUser(registerData, function(error) {
     
        if (Meteor.user()) {
           console.log(Meteor.userId());
        } else {
           console.log("ERROR: " + error.reason);
        }
     });
  }
});

Template.login.events({

  'submit form': function(event) {
     event.preventDefault();
     var myEmail = event.target.loginEmail.value;
     var myPassword = event.target.loginPassword.value;
  
     Meteor.loginWithPassword(myEmail, myPassword, function(error) {

        if (Meteor.user()) {
           console.log(Meteor.userId());
        } else {
           console.log("ERROR: " + error.reason);
        }
     });
  }
});

Template.home.events({

  'click .logout': function(event) {
     event.preventDefault();
  
     Meteor.logout(function(error) {

        if(error) {
           console.log("ERROR: " + error.reason);
        }
     });
  }
});