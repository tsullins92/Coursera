
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
    private ArrayList<LogEntry> records;
    
    public LogAnalyzer() {
        // complete constructor
        records = new ArrayList<LogEntry>();
    }
        
    public void readFile(String filename) {
        // complete method
        FileResource fr = new FileResource(filename);
        for (String line : fr.lines()){
            WebLogParser parser = new WebLogParser();
            LogEntry entry = parser.parseEntry(line);
            records.add(entry);
        }
    }
     //return an integer representing the number of unique IP addresses   
    public int countUniqueIPs(){
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        for(LogEntry le : records){
            String ipAddr = le.getIpAddress();
            if(!uniqueIPs.contains(ipAddr)){
                uniqueIPs.add(ipAddr);
            }
        }
        return uniqueIPs.size();
    }
     //examine all the web log entries in records and print those LogEntrys that have a status code greater than num
    public void printAllHigherThanNum(int num){
        for (LogEntry le : records) {
            if (le.getStatusCode() > num) {
                System.out.println(le);
            }
        }
    }
    
     //returns an ArrayList of Strings of unique IP addresses that had access on the given day.
    public ArrayList<String> uniqueIPVisitsOneDay(String someday){
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        for(LogEntry le : records){
            String accessTime = le.getAccessTime().toString().substring(4,10);
            if(accessTime.equals(someday)){
                String ip = le.getIpAddress();
                if(!uniqueIPs.contains(ip)){
                    uniqueIPs.add(ip);
                }
            }
        } 
        return uniqueIPs;
    }
    //returns the number of unique IP addresses in records that have a status code in the range from low to high, inclusive.
    public int countUniqueIPsInRange(int low, int high){
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        int count = 0;
        for(LogEntry le : records){
            int sc = le.getStatusCode();
            String ip = le.getIpAddress();
            if((sc >= low)&&(sc <= high)&&(!uniqueIPs.contains(ip))){
                uniqueIPs.add(ip);
                ++count;
            }
        }
        return count;
    }     
    //eturns a HashMap<String, Integer> that maps an IP address to the number of times that IP address appears in records
    public HashMap<String,Integer> countVisitsPerIP(){
        HashMap<String,Integer> ipCounts = new HashMap<String,Integer>();
        for(LogEntry le : records){
            String ip = le.getIpAddress();
            if(!ipCounts.containsKey(ip)){
                ipCounts.put(ip,1);
            } else{
                ipCounts.put(ip,ipCounts.get(ip)+1);
            }
        }
        return ipCounts;
    }
    //returns the maximum number of visits to this website by a single IP address
    public int mostNumberVisitsByIP(HashMap<String,Integer> ipCounts){
        int maxVisits = 0;
        for(String key : ipCounts.keySet()){
            int ipCount = ipCounts.get(key);
            if(ipCount>maxVisits){
                maxVisits = ipCount;
            }
        }
        return maxVisits;
    }
    //returns an ArrayList of Strings of IP addresses that all have the maximum number of visits to this website.
    public ArrayList<String> iPsMostVisits(HashMap<String,Integer> ipCounts){
        int maxVisits = mostNumberVisitsByIP(ipCounts);
        ArrayList<String> ips = new ArrayList<String>();
        for(String key : ipCounts.keySet()){
            int ipCount = ipCounts.get(key);
            if(ipCount==maxVisits){
                ips.add(key);
            }
        }
        return ips;        
    }
    //This method returns a HashMap<String, ArrayList<String>> that uses records and maps days from web logs to an ArrayList of IP addresses that occurred on that day (including repeated IP addresses).
    public HashMap<String,ArrayList<String>> iPsForDays(){
        HashMap<String, ArrayList<String>> dayIPs = new HashMap<String, ArrayList<String>>();
        for(LogEntry le : records){
            String accessTime = le.getAccessTime().toString().substring(4,10);
            String ip = le.getIpAddress();
            if(!dayIPs.containsKey(accessTime)){
                ArrayList<String> ips = new ArrayList<String>();
                ips.add(ip);
                dayIPs.put(accessTime,ips);
            }else{
                ArrayList<String> ips = dayIPs.get(accessTime);
                ips.add(ip);
                dayIPs.put(accessTime,ips);
            }
        }
        return dayIPs;
    }
    //returns the day that has the most IP address visits
    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> dayIPs){
        String dayMostVisits = null;
        int mostVisits = 0;
        for(String key : dayIPs.keySet()){
            int visits = dayIPs.get(key).size();
            if(visits > mostVisits){
                dayMostVisits = key;
                mostVisits = visits;
            }
        }
        return dayMostVisits;
    }
    //returns an ArrayList<String> of IP addresses that had the most accesses on the given day
    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> dayIPs, String day){
        HashMap<String,Integer> ipCounts = new HashMap<String,Integer>();

        for(String dayIP : dayIPs.get(day)){
            if(!ipCounts.containsKey(dayIP)){
                ipCounts.put(dayIP,1);
            } else{
                ipCounts.put(dayIP,ipCounts.get(dayIP)+1);
            }
        }
        ArrayList<String> ipsMostVisits = iPsMostVisits(ipCounts);
        return ipsMostVisits;        
    }
    
    public void printAll() {
        for (LogEntry le : records) {
            System.out.println(le);
        }
    }
     
     
}
