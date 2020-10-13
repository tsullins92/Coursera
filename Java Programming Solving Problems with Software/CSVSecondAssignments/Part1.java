/**
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class Part1 {
    public CSVRecord hottestHourInFile(CSVParser parser) {
        //start with largestSoFar as nothing
        CSVRecord largestSoFar = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        //The largestSoFar is the answer
        return largestSoFar;
    }

    public void testHottestInDay () {
        FileResource fr = new FileResource("data/2015/weather-2015-01-01.csv");
        CSVRecord largest = hottestHourInFile(fr.getCSVParser());
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                   " at " + largest.get("TimeEST"));
    }

    public CSVRecord hottestInManyDays() {
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            // use method to get largest in file.
            CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());
            // use method to compare two records
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        //The largestSoFar is the answer
        return largestSoFar;
    }

    public CSVRecord getLargestOfTwo (CSVRecord currentRow, CSVRecord largestSoFar) {
        //If largestSoFar is nothing
        if (largestSoFar == null) {
            largestSoFar = currentRow;
        }
        //Otherwise
        else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
            //Check if currentRow’s temperature > largestSoFar’s
            if (currentTemp > largestTemp) {
                //If so update largestSoFar to currentRow
                largestSoFar = currentRow;
            }
        }
        return largestSoFar;
    }

    public void testHottestInManyDays () {
        CSVRecord largest = hottestInManyDays();
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                   " at " + largest.get("DateUTC"));
    }
// Write a method named coldestHourInFile that has one parameter, a CSVParser named parser. This method returns the CSVRecord with the coldest temperature in the file and 
// thus all the information about the coldest temperature, such as the hour of the coldest temperature. You should also write a void method named testColdestHourInFile() to 
// test this method and print out information about that coldest temperature, such as the time of its occurrence.
// NOTE: Sometimes there was not a valid reading at a specific hour, so the temperature field says -9999. You should ignore these bogus temperature values when calculating the lowest temperature. 
    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord coldestRow = null;
        double currentTemp = 0.0;
        for(CSVRecord currentRow : parser){
            currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            coldestRow = getSmallestTempOfTwo(currentRow, coldestRow);
        }
        return coldestRow;
    }
    
    public void testColdestHourInFile(){
        FileResource fr = new FileResource();
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("coldest temperature was " + coldest.get("TemperatureF") +
                   " at " + coldest.get("DateUTC"));        
    }
    
    public CSVRecord getSmallestTempOfTwo (CSVRecord currentRow, CSVRecord smallestSoFar) {
        //If largestSoFar is nothing
        if (smallestSoFar == null) {
            smallestSoFar = currentRow;
        }
        //Otherwise
        else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
            if(currentTemp == -9999){
                return smallestSoFar;
            }
            else if (currentTemp < smallestTemp) {
                smallestSoFar = currentRow;
            }
        }
        return smallestSoFar;
    }   
    
// Write the method fileWithColdestTemperature that has no parameters. This method should return a string that is the name of the file from selected files that has the 
// coldest temperature. You should also write a void method named testFileWithColdestTemperature() to test this method. Note that after determining the filename, you 
// could call the method coldestHourInFile to determine the coldest temperature on that day. When fileWithColdestTemperature runs and selects the files for January 1–3
// in 2014, the method should print out
    public File fileWithColdestTemperature(){
        CSVRecord coldestSoFar = null;
        File coldestFile = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            if(coldestFile == null){
                coldestFile = f;
            }
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            coldestSoFar = getSmallestTempOfTwo(currentRow, coldestSoFar);
            //Check if current file is the coldest one
            if(coldestSoFar.get("TemperatureF")==currentRow.get("TemperatureF")){
                coldestFile = f;
            }
        }
        return coldestFile;        
    }

    public void testFileWithColdestTemperature(){
        File coldestFile = fileWithColdestTemperature();
        String coldestFileName = coldestFile.getName();
        System.out.println("Coldest day was in file " + coldestFileName);
        FileResource fr = new FileResource(coldestFile);
        CSVRecord coldestHour = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature on that day was " + coldestHour.get("TemperatureF"));    
        System.out.println("All the Temperatures on the coldest day were:"); 
        CSVParser parser = fr.getCSVParser();
        for(CSVRecord record : parser)
        {
            System.out.println(record.get("DateUTC")+ " " +record.get("TemperatureF"));
        }
    }
    
    
// Write a method named lowestHumidityInFile that has one parameter, a CSVParser named parser. This method returns the CSVRecord that has the lowest humidity. If there is a tie, 
// then return the first such record that was found. Note that sometimes there is not a number in the Humidity column but instead there is the string “N/A”. This only happens very rarely.
// You should check to make sure the value you get is not “N/A” before converting it to a number.
// Also note that the header for the time is either TimeEST or TimeEDT, depending on the time of year. You will instead use the DateUTC field at the right end of the data file to get 
// both the date and time of a temperature reading.
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord lowestRow = null;
        for(CSVRecord currentRow : parser){
            lowestRow = getLowestHumidityOfTwo(currentRow, lowestRow);
        }
        return lowestRow;
    }
    
    public CSVRecord getLowestHumidityOfTwo(CSVRecord currentRow, CSVRecord smallestSoFar) {
        if(currentRow.get("Humidity").equals("N/A")){
            return smallestSoFar;
        }
        else if (smallestSoFar == null) {
            smallestSoFar = currentRow;
        } 
        //Otherwise
        else {
            double currentTemp = Double.parseDouble(currentRow.get("Humidity"));
            double smallestTemp = Double.parseDouble(smallestSoFar.get("Humidity"));
            if (currentTemp < smallestTemp) {
                smallestSoFar = currentRow;
            }
        }
        return smallestSoFar;
    } 
// You should also write a void method named testLowestHumidityInFile() to test this method that starts with these lines:
    public void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);    
        System.out.println("Lowest Humidity was " + csv.get("Humidity") +
           " at " + csv.get("DateUTC")); 
    }
// Write the method lowestHumidityInManyFiles that has no parameters. This method returns a CSVRecord that has the lowest humidity over all the files. If there is a tie,
// then return the first such record that was found.  
    public CSVRecord lowestHumidityInManyFiles(){
        CSVRecord lowestHumiditySoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            lowestHumiditySoFar = getLowestHumidityOfTwo(currentRow, lowestHumiditySoFar);
        }
        return lowestHumiditySoFar;        
    }    
// You should also write a void method named testLowestHumidityInManyFiles() to test this method and to print the lowest 
// humidity AND the time the lowest humidity occurred. Be sure to test this method on two files so you can check if it is working correctly. If you run this program and 
// select the files for January 19, 2014 and January 20, 2014, you should get     
    public void testlowestHumidityInManyFiles(){
        CSVRecord record = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + record.get("Humidity") + " at " + record.get("DateUTC"));
    }
// Write the method averageTemperatureInFile that has one parameter, a CSVParser named parser. This method returns a double that represents the average temperature in the file. 
    public double averageTemperatureInFile(CSVParser parser){
        double sumTemp = 0.0;
        int tempCount = 0;
        for(CSVRecord record : parser){
            double currentTemp = Double.parseDouble(record.get("TemperatureF"));
            if(currentTemp != -9999){
                sumTemp += currentTemp;
                ++tempCount;
            }
        }
        return sumTemp/tempCount;
    }

// You should also write a void method named testAverageTemperatureInFile() to test this method. When this method runs and selects the file for January 20, 2014, the method should print out 
    public void testAverageTemperatureInFile(){
        FileResource fr = new FileResource();
        double avgTemp = averageTemperatureInFile(fr.getCSVParser());
        System.out.println("Average Temperature was " + avgTemp);
    }
    
// Write the method averageTemperatureWithHighHumidityInFile that has two parameters, a CSVParser named parser and an integer named value. This method returns a double that represents 
// the average temperature of only those temperatures when the humidity was greater than or equal to value. 
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double sumTemp = 0.0;
        int tempCount = 0;
        for(CSVRecord record : parser){
            double currentTemp = Double.parseDouble(record.get("TemperatureF"));
            double currentHum = Double.parseDouble(record.get("Humidity"));
            if((currentTemp != -9999)&&(currentHum >= value)){
                sumTemp += currentTemp;
                ++tempCount;
            }
        }
        if(tempCount == 0){
            return -11111.11111111;
        };
        return sumTemp/tempCount;        
    }


//You should also write a void method named testAverageTemperatureWithHighHumidityInFile() to test this method. When this method runs checking for humidity greater than or equal 
//to 80 and selects the file for January 20, 2014, the method should print out No temperatures with that humidity
    public void testAverageTemperatureWithHighHumidityInFile(){
        FileResource fr = new FileResource();
        double avgTemp = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(), 80);
        if(avgTemp == -11111.11111111){
            System.out.println("No temperatures with that humidity");
        } else{
            System.out.println("Average Temp when high Humidity is " + avgTemp);
        }
    }
    
    
    
}
