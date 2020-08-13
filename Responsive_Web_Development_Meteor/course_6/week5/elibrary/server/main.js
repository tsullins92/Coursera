import {Books} from "../shared/collections.js";
Meteor.startup(function(){
	
	
	
	
	
	Meteor.setTimeout(() => {
		
		console.log(Books.find())
		
if (Books.find().count() === 0){
	
	let booksArray = []

	for(var i=0; i<=20; i++ ){
		booksArray.push({title :'example title ' +i,
					   link: 'example link ' +i})
	}
	
/*	
let booksArray	=[{
	title: 'example title 1',
	link: e
	},{
	title: 'example title 2',
	},{
	title: 'example title 3',
	},{
	title: 'example title 4',
	},{
	title: 'example title 5',
	},{
	title: 'example title 6',
	},{
	title: 'example title 7',
	},{
	title: 'example title 8',
	}, ]

*/
console.log(booksArray)
	booksArray.forEach(function(book){
		Books.insert(book);
	})	
	// end of for insert images
	// count the images!
	console.log("startup.js says: "+Books.find().count());
}// end of if have no images		
		
		
		
    },
    10000);

});
