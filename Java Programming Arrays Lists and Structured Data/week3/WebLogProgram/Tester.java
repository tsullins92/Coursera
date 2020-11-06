
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer(String fileName) {
        // complete method
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile(fileName);
        analyzer.printAll();
    }
    
    public void testCountUniqueIPs(String fileName) {
        // complete method
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile(fileName);
        System.out.println("Unique IP addresses: "+analyzer.countUniqueIPs());
    }    
    
    public void testPrintAllHigherThanNum(int statusCode, String fileName) {
        // complete method
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile(fileName);
        System.out.println("Log entries with status codes > "+statusCode+": ");
        analyzer.printAllHigherThanNum(statusCode);
    }   
    
    public void testUniqueIPVisitsOneDay(String day, String fileName) {
        // complete method
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile(fileName);
        System.out.println("Unique IPs on "+day+": ");
        for(String ip : analyzer.uniqueIPVisitsOneDay(day)){
            System.out.println(ip);
        }
    }   

    public void testCountUniqueIPsInRange(int low, int high, String fileName) {
        // complete method
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile(fileName);
        System.out.println("Number of Unique IPs with statuscode between "+low+" and "+high+": "+analyzer.countUniqueIPsInRange(low, high));
    }     
    
    public void testCountVisitsPerIP(String fileName) {
        // complete method
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile(fileName);
        System.out.println("testCountVisitsPerIP "+analyzer.countVisitsPerIP());
    }     
    
    public void testMostNumberVisitsByIP(String fileName) {
        // complete method
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile(fileName);
        HashMap<String,Integer> ipCount = analyzer.countVisitsPerIP();
        int mostNumberVisits = analyzer.mostNumberVisitsByIP(ipCount);
        System.out.println("testMostNumberVisitsByIP "+mostNumberVisits);
    }      
    
    public void testIPsMostVisits(String fileName) {
        // complete method
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile(fileName);
        HashMap<String,Integer> ipCount = analyzer.countVisitsPerIP();
        ArrayList<String> ipsMostVisits = analyzer.iPsMostVisits(ipCount);
        System.out.println("testIPsMostVisits "+ipsMostVisits);
    }      
    
    public void testIPsForDays(String fileName){
        // complete method
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile(fileName);
        HashMap<String, ArrayList<String>> dayIPs = analyzer.iPsForDays();
        System.out.println("testIPsForDays "+dayIPs);        
    }    
    
    public void testDayWithMostIPVisits(String fileName){
        // complete method
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile(fileName);
        HashMap<String, ArrayList<String>> dayIPs = analyzer.iPsForDays();
        for(String key : dayIPs.keySet()){
            System.out.println(key);
            System.out.println(dayIPs.get(key).size());
        }
        System.out.println("testDayWithMostIPVisits "+analyzer.dayWithMostIPVisits(dayIPs));         
    }
    
    public void testIPsWithMostVisitsOnDay(String day, String fileName){
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile(fileName);   
        HashMap<String, ArrayList<String>> dayIPs = analyzer.iPsForDays();
        System.out.println("testIPsWithMostVisitsOnDay "+analyzer.iPsWithMostVisitsOnDay(dayIPs, day));   
        
    }
    
    public void test(String fileName){
        
    }
}
