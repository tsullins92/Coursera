const csv = require('csv');  
const fs = require('fs');
var myArgs = process.argv.slice(2);
const assert = require('assert');
var crypto = require('crypto');
var path = require('path');

console.log('myArgs: ', myArgs);

camelize = function camelize(str) {
	// strTemp = str;
	// console.log('camelize: ' + strTemp + ' -> ' + chr.toUpperCase() + ', match = ' + match + ', chr = ' + chr);

    return str
        .replace(/_(.)/g, function($1) { return $1.toUpperCase(); })
        .replace(/\s(.)/g, function($1) { return $1.toUpperCase(); })
        .replace(/_/g, '')
        .replace(/\s/g, '')
        .replace(/^(.)/, function($1) { return $1.toLowerCase(); });
    }

function hashString(str){
	let hash = crypto.createHash('sha1').update(str).digest("hex");

	return hash;
}

function normalizeStreet(str){

	let norm = str
		.replace("  ", " ")
		.replace(/Ê/g, "E")
		.replace(/É/g, "E")
		.replace(/È/g, "E")
		.replace(/Ë/g, "E")
		.replace(/Ç/g, "C")
		.replace(/'/g, " ")
		.replace(/-/g, " ")
		.replace("VENELLE ", "VEN ")
		.replace("AVENUE ", "AVE ")
		.replace("BOULEVARD ", "BD ")
		.replace("ALLEE ", "ALL ")
		.replace("IMPASSE ", "IMP ")
		.replace("PLACE ", "PL ")
		.replace("ROUTE ", "RTE ")
		.replace("COMMANDANT ", "CDT ")
		.replace(" D B", " DB")
	return norm;
}

function normalizeCity(str){

	let norm = str
		.replace("  ", " ")
		.replace(/Ê/g, "E")
		.replace(/É/g, "E")
		.replace(/È/g, "E")
		.replace(/Ë/g, "E")
		.replace(/Ç/g, "C")
		.replace(/'/g, " ")
		.replace(/-/g, " ")
		.replace(/ \S+ ARRONDISSEMENT/,"")

	return norm;
}

var csvData = [];
const fields = ['numero','suffixe','nom_voie','code_postal','nom_commune','nom_complementaire','lon','lat'];
//id_ban_position;id_ban_adresse;cle_interop;id_ban_group;id_fantoir;numero;suffixe;nom_voie;code_postal;nom_commune;code_insee;nom_complementaire;x;y;
//  lon;lat;typ_loc;source;date_der_maj
//ban-position-6831366a72c745d9b219cf0bc43b1c26;ban-housenumber-ac2f12df22d8412e9ae631e20a15f42f;69001_0028_5011;
//  ban-group-d11f295326b84024a663d7f45c6c253a;690010028;5011;;IMP DE LA MAIRIE;69170;Affoux;69001;;
//  808872.690981591;6528207.66493085;4.40287;45.844815;parcel;dgfip;2018-10-21



// Create the parser
const parser = csv.parse({
  delimiter: ';',
//  cast: true,
//  cast_date: true,
  columns: true
//  ['Date mutation','Nature mutation','Valeur fonciere','No voie','Type de voie','Voie','Code postal','Commune','Type local','Surface reelle bati','Nombre pieces principales','Surface terrain']
})

const inputFile = myArgs[0];
//Extract the filename:
var baseFile = path.basename(inputFile);
const outputFile = '.data/addresses/json/' + baseFile.substr(0, baseFile.lastIndexOf(".")) + ".json";
//ban-69.csv
// Use the readable stream api
var outStream = fs.createWriteStream(outputFile);

var columnFilter = csv.transform(function (record) {
	Object.keys(record).forEach(function (key) {
		if (fields.find(function(element) { 
			return element == key;
		}) ) {
			newKey = camelize(key);
			//console.log('camelize: ' + key + ' -> ' + newKey);
			record[newKey] = record[key];
			//console.log('In the loop: display record[' + key + '] = ' + record[key]);
			if (!(newKey == key)) delete record[key];
		} else {
			delete record[key];
		}
	})
	// console.log('columnFilter: ' + record.valeurFonciere);
	return record;
});

var addressFilter = csv.transform(function (record) {

// .toUpperCase()
	record['freeFormAddress'] = record.numero + ' ' + record.nomVoie + ', ' + record.codePostal + ' ' + record.nomCommune;

	record['normAddress'] = record.numero.toUpperCase() + ' ' + normalizeStreet(record.nomVoie.toUpperCase()) + ', ' + record.codePostal + ' ' + normalizeCity(record.nomCommune.toUpperCase());

	// console.log('addressFilter: ' + record.address);
	return record;
});

var coordFilter = csv.transform(function (record) {
// .toUpperCase()
	record['lon'] = parseFloat(record['lon'].toString());
	record['lat'] = parseFloat(record['lat'].toString());

	// console.log('addressFilter: ' + record.address);
	return record;
});

var hashAdder = csv.transform(function (record) {
// .toUpperCase()
	record['hash'] = hashString(record.normAddress);

	// console.log('addressFilter: ' + record.address);
	return record;
});
// create json files per dept
// var depts = ['01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','2A','2B','21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36','37','38','39','40','41','42','43','44','45','46','47','48','49','50','51','52','53','54','55','56','57','58','59','60','61','62','63','64','65','66','67','68','69','70','71','72','73','74','75','76','77','78','79','80','81','82','83','84','85','86','87','88','89','90','91','92','93','94','95','971','972','973','974','975','976','977','978','984','986','987','988','989'];
var isFirst = true;

fs.createReadStream(inputFile)
	.pipe(parser)
	.pipe(columnFilter)
	.pipe(addressFilter)
	.pipe(coordFilter)
	.pipe(hashAdder)
	.on('data', (address) => {
		//console.log(address);
		// get new transction as js object
		var addressAsString = JSON.stringify(address, null, 4);

		// get current json array from stream
		console.log(addressAsString + '\n TO WRITE IN ' + outputFile + '\n');
		if (isFirst) {
			outStream.write('[\n' + addressAsString);
			isFirst = false;
		} else {
			outStream.write(',\n' + addressAsString);
		}
	})
	.on('error', (err) => {
		//do something wiht csvData
		console.error(err.message);
	})
	.on('end', () => {
		console.log('CSV file successfully processed, closing all files');
		outStream.end('\n]\n');
	});
