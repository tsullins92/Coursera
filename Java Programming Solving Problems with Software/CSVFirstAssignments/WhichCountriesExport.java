/**
 * Reads a chosen CSV file of country exports and prints each country that exports coffee.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class WhichCountriesExport {
    public void listExporters(CSVParser parser, String exportOfInterest) {
        //for each row in the CSV File
    for (CSVRecord record : parser){
        //Look at the "Exports" column
        String export = record.get("Exports");
        //Check if it contains exportOfInterest
        if(export.contains(exportOfInterest)){
            String country = record.get("Country");
            //If so, write down the "Country" from that row
        System.out.println(country);
            }
        }
    }
    
// Write a method named countryInfo that has two parameters, parser is a CSVParser and country is a String. This method returns a string of information about the country or 
// returns “NOT FOUND” if there is no information about the country. The format of the string returned is the country, followed by “: “, followed by a list of the countries’ 
// exports, followed by “: “, followed by the countries export value. For example, using the file exports_small.csv and the country Germany, the program returns the string:    
    public String countryInfo(CSVParser parser, String country){
        String countryInformation = "NOT FOUND";
    for (CSVRecord record : parser){
        String countryRecord = record.get("Country");
        if(countryRecord.equals(country)){
            countryInformation = countryRecord + ": " + record.get("Exports") + ": " + record.get("Value (dollars)");
            }
        }     
        return countryInformation;
    }
    
// Write a void method named listExportersTwoProducts that has three parameters, parser is a CSVParser, exportItem1 is a String and exportItem2 is a String. This method prints the names of all 
// the countries that have both exportItem1 and exportItem2 as export items. For example, using the file exports_small.csv, this method called with the items “gold” and “diamonds” would print 
// the countries
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2){
    for (CSVRecord record : parser){
        String exports = record.get("Exports");
        if(exports.contains(exportItem1) && exports.contains(exportItem2)){
            String country = record.get("Country");
            System.out.println(country);
            }
        }        
    }
    
// Write a method named numberOfExporters, which has two parameters, parser is a CSVParser, and exportItem is a String. This method returns the number of countries 
// that export exportItem. For example, using the file exports_small.csv, this method called with the item “gold” would return 3.
    public int numberOfExporters(CSVParser parser, String exportItem){
        int numExporters = 0;
        for(CSVRecord record : parser){
            if(record.get("Exports").contains(exportItem)){
                ++numExporters;
            }
        }
        return numExporters;
    }

// Write a void method named bigExporters that has two parameters, parser is a CSVParser, and amount is a String in the format of a dollar sign, followed by an integer number with 
// a comma separator every three digits from the right. An example of such a string might be “$400,000,000”. This method prints the names of countries and their Value amount for all
// countries whose Value (dollars) string is longer than the amount string. You do not need to parse either string value as an integer, just compare the lengths of the strings. 
// For example, if bigExporters is called with the file exports_small.csv and amount with the string $999,999,999, then this method would print eight countries and their export 
// values shown here:    
    public void bigExporters(CSVParser parser, String amount){
    for (CSVRecord record : parser){
        String recordAmount = record.get("Value (dollars)");
        if(recordAmount.length() > amount.length()){
            System.out.println(record.get("Country") + ": " + recordAmount);
            }
        }             
    }
 
    public void tester() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        System.out.println("countryInfo(parser,Nauru): " + countryInfo(parser, "Nauru"));
        parser = fr.getCSVParser();
        System.out.println("listExportersTwoProducts(parser,cotton, flowers): ");
        listExportersTwoProducts(parser,"cotton", "flowers");
        parser = fr.getCSVParser();
        System.out.println("numberOfExporters(parser, cocoa): " + numberOfExporters(parser,"cocoa"));
        parser = fr.getCSVParser();
        System.out.println("bigExporters(parser,$999,999,999,999): ");
        bigExporters(parser,"$999,999,999,999");          
    }
}
