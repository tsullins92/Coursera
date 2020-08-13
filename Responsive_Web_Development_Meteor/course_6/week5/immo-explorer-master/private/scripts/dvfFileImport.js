const csv = require('csv');
const fs = require('fs');
var myArgs = process.argv.slice(2);
const assert = require('assert');
var crypto = require('crypto');

// script has one parameter: the DVF file to import (.txt)
console.log('myArgs: ', myArgs);
const inputFile = myArgs[0];

// format string to camelCase
camelize = function camelize(str) {
    // strTemp = str;
    // console.log('camelize: ' + strTemp + ' -> ' + chr.toUpperCase() + ', match = ' + match + ', chr = ' + chr);
    return str.replace(/\s(.)/g, function($1) {
        return $1.toUpperCase();
    }).replace(/\s/g, '').replace(/^(.)/, function($1) {
        return $1.toLowerCase();
    });
}

// generate sha1 hash from string
function hashString(str) {
    let hash = crypto.createHash('sha1').update(str).digest("hex");
    return hash;
}

// filter/normalize address string
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

// filter/normalize city string
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
                .replace(/ [0-9]+$/,"")

        return norm;
}

// var csvData = [];

// fields to keep
const fields = ['Date mutation', 'Nature mutation', 'Valeur fonciere', 'No voie', 'Type de voie', 'Voie', 'B/T/Q', 'Code postal', 'Commune', 'Code departement', 'Type local', 'Surface reelle bati', 'Nombre pieces principales', 'Surface terrain'];

// create the pipeline from dvf txt input to list of json output (one per dept)
// use the readable stream api

// create the parser from DVF input (which is in csv format)
const parser = csv.parse({
    delimiter: '|',
    columns: true
})

// filter out fields not in 'fields' array
var columnFilter = csv.transform(function(record) {
    Object.keys(record).forEach(function(key) {
        if (fields.find(function(element) {
                return element == key;
            })) {
            newKey = camelize(key);
            // console.log('camelize: ' + key + ' -> ' + newKey);
            record[newKey] = record[key];
            //console.log('In the loop: display record[' + key + '] = ' + record[key]);
        }
        delete record[key];
    })
    // console.log('columnFilter: ' + record.valeurFonciere);
    return record;
});

// exclude property types other than House or Flat
var appartAndHouseFilter = csv.transform(function(record) {
    if (record.typeLocal == 'Maison' || record.typeLocal == 'Appartement') {
        // console.log('appartAndHouseFilter: ' + record.typeLocal);
        return record;
    }
});

// generate normAddress (=normalized address) field
var addressFilter = csv.transform(function(record) {
    record['normAddress'] = record.noVoie + ' ' + normalizeStreet(record.typeDeVoie.toUpperCase()) + ' ' + normalizeStreet(record.voie.toUpperCase()) + ', ' + record.codePostal + ' ' + normalizeCity(record.commune.toUpperCase());
    // console.log('addressFilter: ' + record.address);
    return record;
});

// valeurFonciere (= price) field is a float
var priceFilter = csv.transform(function(record) {
    //console.log('priceFilter: typeof(val fonciere) = ' typeof)
    var newValeurFonciereArray = record['valeurFonciere'].toString().split(",");
    var newValeurFonciere;
    if (newValeurFonciereArray.length == 2) newValeurFonciere = parseInt(newValeurFonciereArray[0]);
    else newValeurFonciere = parseInt(record.valeurFonciere);
    // console.log('priceFilter: newValeurFonciere = ' + newValeurFonciere);
    record.valeurFonciere = newValeurFonciere;
    // console.log('addressFilter: ' + record.address);
    return record;
});

var dateConversion = csv.transform(function(record) {
    var splitDate = record["dateMutation"].split("/");
    var usDate = "";
    if (splitDate.length == 3) {
        usDate = splitDate[1] + "/" + splitDate[0] + "/" + splitDate[2];
    } else {
        console.log('dateMutation: ' + record['dateMutation'].toString() + ' -> usDate: ' + usDate);
    }
    var sellDate = new Date(usDate);
    record.sellDate = sellDate.toJSON();
    console.log('dateMutation: ' + record['dateMutation'].toString() + ' -> sellDate: ' + record.sellDate);
    return record;
});

var surfaceConversion = csv.transform(function(record) {
    var surface = parseInt(record['surfaceReelleBati'].toString());

    record.surface = surface;
    console.log('surfaceReelleBati: ' + record['surfaceReelleBati'].toString() + ' -> surface: ' + record.surface);
    return record;
});

var nbRoomsConversion = csv.transform(function(record) {
    var nbRooms = parseInt(record['nombrePiecesPrincipales'].toString());

    record.nbRooms = nbRooms;
    console.log('nombrePiecesPrincipales: ' + record['nombrePiecesPrincipales'].toString() + ' -> nbRooms: ' + record.nbRooms);
    return record;
});

var landSurfaceConversion = csv.transform(function(record) {
    var landSurface = parseInt(record['surfaceTerrain'].toString());

    record.landSurface = landSurface;
    console.log('surfaceTerrain: ' + record['surfaceTerrain'].toString() + ' -> landSurface: ' + record.landSurface);
    return record;
});

// generate a new field: hash, which contains the hash of normAddress field
var hashAdder = csv.transform(function(record) {
    // .toUpperCase()
    record['hash'] = hashString(record.normAddress);
    // console.log('addressFilter: ' + record.address);
    return record;
});

// create json files per dept
var depts = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '2A', '2B', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33', '34', '35', '36', '37', '38', '39', '40', '41', '42', '43', '44', '45', '46', '47', '48', '49', '50', '51', '52', '53', '54', '55', '56', '57', '58', '59', '60', '61', '62', '63', '64', '65', '66', '67', '68', '69', '70', '71', '72', '73', '74', '75', '76', '77', '78', '79', '80', '81', '82', '83', '84', '85', '86', '87', '88', '89', '90', '91', '92', '93', '94', '95', '971', '972', '973', '974', '975', '976', '977', '978', '984', '986', '987', '988', '989'];
var deptFiles = {};
depts.forEach(function(dept) {
    deptFiles[dept] = fs.createWriteStream("transactionsPerDept" + dept + ".json", {
        'flags': 'a'
    });
    //console.log('created file transactionsPerDept' + dept + '.json' + ' for dept ' + dept);
});

// stack all filters and geznerate the list of json files (one per dept)
fs.createReadStream(inputFile)
    .pipe(parser)
    .pipe(columnFilter)
    .pipe(appartAndHouseFilter)
    .pipe(addressFilter)
    .pipe(priceFilter)
    .pipe(dateConversion)
    .pipe(surfaceConversion)
    .pipe(nbRoomsConversion)
    .pipe(landSurfaceConversion)
    .pipe(hashAdder).on('data', (trans) => {
    // get new transction as js object
    var dept = trans.codeDepartement;
    var transAsString = JSON.stringify(trans, null, 4);
    // get current json array from stream
    // console.log(transAsString + '\n TO WRITE IN transactionsPerDept' + dept + '.json\n');
    deptFiles[dept].write(',\n' + transAsString);
    // var jsonfile = require('jsonfile');
    // jsonfile.writeFile('loop.json', "id :" + i + " square :" + i*i);
}).on('error', (err) => {
    //do something with csvData
    console.error(err.message);
}).on('end', () => {
    console.log('CSV file successfully processed, closing all files');
    depts.forEach(function(dept) {
        deptFiles[dept].end('\n');
    })
});
