// this collection contains all the songs
Songs = new Mongo.Collection("songs");
// this variable will store the visualisation so we can delete it when we need to 
var visjsobj;
var vistype;
if (Meteor.isClient){

////////////////////////////
///// helper functions for the vis control form
////////////////////////////

  Template.song_viz_controls.helpers({
    // returns an array of the names of all features of the requested type
    get_feature_names : function(type){
      var feat_field;
      if (type == "single"){
        feat_field = "single_features";
      }
      // pull an example song from the database
      // - we'll use this to find the names of all the single features
      song = Songs.findOne();
      if (song != undefined){// looks good! 
        // get an array of all the song feature names 
        // (an array of strings)
        features = Object.keys(song[feat_field]);
        features_a = new Array();
        // create a new array containing
        // objects that we can send to the template
        // since we can't send an array of strings to the template
        for (var i=0;i<features.length;i++){
            features_a[i] = {name:features[i]};
        }
        return features_a;
      }
      else {// no song available, return an empty array for politeness
        return [];
      }
    },
  });

////////////////////////////
///// helper functions for the feature list display template
////// (provide the data for that list of songs)
////////////////////////////

// helper that provides an array of feature_values
// for all songs of the currently selected type
// this is used to feed the template that displays the big list of 
// numbers
  Template.song_feature_list.helpers({
    "get_all_feature_values":function(){
      if (Session.get("feature") != undefined){
        var songs = Songs.find({});
        var features = new Array();
        var ind = 0;
        // build an array of data on the fly for the 
        // template consisting of 'feature' objects
        // describing the song and the value it has for this particular feature
        songs.forEach(function(song){
          //console.log(song);
            features[ind] = {
              artist:song.metadata.tags.artist,
              title:song.metadata.tags.title, 
              value:song[Session.get("feature")["type"]][Session.get("feature")["name"]]
            };
            ind ++;
        })
        return features;
      }
      else {
        return [];
      }
    }
  })

// helper that provides an array of feature_values
// for all songs of the currently selected type
// this is used to feed the template that displays the big list of 
// numbers
  Template.song_feature_list_1.helpers({
    "get_all_feature_values_1":function(){
      if (Session.get("feature_1") != undefined){
        var songs = Songs.find({});
        var features = new Array();
        var ind = 0;
        // build an array of data on the fly for the 
        // template consisting of 'feature' objects
        // describing the song and the value it has for this particular feature
        songs.forEach(function(song){
          //console.log(song);
            features[ind] = {
              artist:song.metadata.tags.artist,
              title:song.metadata.tags.title, 
              value:song[Session.get("feature_1")["type"]][Session.get("feature_1")["name"]]
            };
            ind ++;
        })
        return features;
      }
      else {
        return [];
      }
    }
  })
////////////////////////////
///// event handlers for the viz control form
////////////////////////////

  Template.song_viz_controls.events({
    // event handler for when user changes the selected
    // option in the drop down list
    "change .js-select-single-feature":function(event){
      event.preventDefault();
      var feature = $(event.target).val();
      Session.set("feature", {name:feature, type:"single_features"});
    }, 
    // event handler for when user changes the selected
    // option in the drop down list
    "change .js-select-single-feature-1":function(event){
      event.preventDefault();
      var feature = $(event.target).val();
      Session.set("feature_1", {name:feature, type:"single_features"});
    }, 
    // event handler for when the user clicks on the 
    // blobs button
     "click .js-show-blobs":function(event){
      event.preventDefault();
      initBlobVis();
    }, 
    // event handler for when the user clicks on the 
    // timeline button
     "click .js-show-timeline":function(event){
      event.preventDefault();
      initDateVis();
    }, 
    // event handler for when the user clicks on the 
    // timeline button
     "click .js-show-3d":function(event){
      event.preventDefault();
      init3dVis();
    }, 
  }); 
}



////////////////////////////
///// functions that set up and display the visualisation
////////////////////////////


// function that creates a new timeline visualisation
function initDateVis(){
  // clear out the old visualisation if needed
  if ((visjsobj != undefined) && (vistype != '3d')){
    visjsobj.destroy();
  }
  var songs = Songs.find({});
  var ind = 0;
  // generate an array of items
  // from the songs collection
  // where each item describes a song plus the currently selected
  // feature
  var items = new Array();
  // iterate the songs collection, converting each song into a simple
  // object that the visualiser understands
  songs.forEach(function(song){
    if (song.metadata.tags.date != undefined && 
      song.metadata.tags.date[0] != undefined ){
      var label = "ind: "+ind;
      if (song.metadata.tags.title != undefined){// we have a title
        label = song.metadata.tags.artist[0] + " - " + 
        song.metadata.tags.title[0];
      }  
      var value = song[Session.get("feature")["type"]][Session.get("feature")["name"]];
      var date = song.metadata.tags.date[0] + "-01-01";
      // here we create the actual object for the visualiser
      // and put it into the items array
      items[ind] = {
        x: date, 
        y: value, 
        // slighlty hacky label -- check out the vis-label
        // class in song_data_viz.css 
        label:{content:label, className:'vis-label', xOffset:-5}, 
      };
      ind ++ ;
  }
  });
  // set up the data plotter
  var options = {
    style:'bar', 
  };
  // get the div from the DOM that we are going to 
  // put our graph into 
  var container = document.getElementById('visjs');
  // create the graph
  visjsobj = new vis.Graph2d(container, items, options);
  // tell the graph to set up its axes so all data points are shown
  visjsobj.fit();
  vistype = '2d';
}

// function that creates a new blobby visualisation
function initBlobVis(){
  // clear out the old visualisation if needed
  if ((visjsobj != undefined) && (vistype != '3d')){
    visjsobj.destroy();
  }
  // find all songs from the Songs collection
  var songs = Songs.find({});
  var nodes = new Array();
  var ind = 0;
  // iterate the songs, converting each song into 
  // a node object that the visualiser can understand
    songs.forEach(function(song){
      // set up a label with the song title and artist
     var label = "ind: "+ind;
     if (song.metadata.tags.title != undefined){// we have a title
          label = song.metadata.tags.artist[0] + " - " + 
          song.metadata.tags.title[0];
      } 
      // figure out the value of this feature for this song
      var value = song[Session.get("feature")["type"]][Session.get("feature")["name"]];
      // create the node and store it to the nodes array
        nodes[ind] = {
          id:ind, 
          label:label, 
          value:value,
        }
        ind ++;
    })
    // edges are used to connect nodes together. 
    // we don't need these for now...
    edges =[
    ];
    // this data will be used to create the visualisation
    var data = {
      nodes: nodes,
      edges: edges
    };
    // options for the visualisation
     var options = {
      nodes: {
        shape: 'dot',
      }
    };
    // get the div from the dom that we'll put the visualisation into
    container = document.getElementById('visjs');
    // create the visualisation
    visjsobj = new vis.Network(container, data, options);
    vistype = '2d';
}


function init3dVis(){
  // clear out the old visualisation if needed
  if (visjsobj != undefined){
    visjsobj.destroy();
  }
  var songs = Songs.find({});
  var ind = 0;
  // generate an array of items
  // from the songs collection
  // where each item describes a song plus the currently selected
  // feature
  var items = new Array();

  // iterate the songs collection, converting each song into a simple
  // object that the visualiser understands
  songs.forEach(function(song){
    if (song.metadata.tags.date != undefined && 
      song.metadata.tags.date[0] != undefined ){
      var id = "ind: "+ind;
      if (song.metadata.tags.title != undefined){// we have a title
        id = song.metadata.tags.artist[0] + " - " + 
        song.metadata.tags.title[0];
      }  
      var x = song[Session.get("feature")["type"]][Session.get("feature")["name"]];
      var y = song[Session.get("feature_1")["type"]][Session.get("feature_1")["name"]];
      var z = song.single_features.bpm;
      console.log(song);
      // here we create the actual object for the visualiser
      // and put it into the items array
      items[ind] = {
        x: x, 
        y: y, 
        z:z,
        style:z
        // slighlty hacky label -- check out the vis-label
        // class in song_data_viz.css 
      };
      ind ++ ;
  }
  });
   // specify options
    var options = {
        width:  '500px',
        height: '552px',

    };
  // get the div from the DOM that we are going to 
  // put our graph into 
  var container = document.getElementById('visjs');
  // create the graph
  visjsobj = new vis.Graph3d(container, items, options);
  vistype = '3d';
}


/*
    // Create and populate a data table.
    var data = new vis.DataSet();
    // create some nice looking data with sin/cos
    var counter = 0;
    var steps = 50;  // number of datapoints will be steps*steps
    var axisMax = 314;
    var axisStep = axisMax / steps;
    for (var x = 0; x < axisMax; x+=axisStep) {
        for (var y = 0; y < axisMax; y+=axisStep) {
            var value = (Math.sin(x/50) * Math.cos(y/50) * 50 + 50);
            data.add({id:counter++,x:x,y:y,z:value,style:value});
        }
    }

    // specify options
    var options = {
        width:  '500px',
        height: '552px',
        style: 'surface',
        showPerspective: true,
        showGrid: true,
        showShadow: false,
        keepAspectRatio: true,
        verticalRatio: 0.5
    };

    // Instantiate our graph object.
    var container = document.getElementById('visualization');
    var graph3d = new vis.Graph3d(container, data, options);
*/