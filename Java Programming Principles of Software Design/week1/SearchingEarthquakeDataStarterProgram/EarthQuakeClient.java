import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for(QuakeEntry qe : quakeData){
            if(qe.getMagnitude() > magMin){
                answer.add(qe);
            }
        }
        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for(QuakeEntry qe : quakeData){
            double distance = qe.getLocation().distanceTo(from);
            if(distance < distMax){
                answer.add(qe);
            }
        }
        return answer;
    }

    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData,
    double minDepth,
    double maxDepth) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for(QuakeEntry qe : quakeData){
            double depth = qe.getDepth();
            if((depth > minDepth)&&(depth < maxDepth)){
                answer.add(qe);
            }
        }
        return answer;
    }    

    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData,
    String where,
    String phrase) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for(QuakeEntry qe : quakeData){
            String title = qe.getInfo();
            int phraseIndex = title.lastIndexOf(phrase);
            if(phraseIndex == -1){
                continue;
            }
            else if(where.equals("start")){
                if(phraseIndex == 0){
                    answer.add(qe);
                }
            } else if(where.equals("end")){
                if(title.substring(phraseIndex,title.length()).equals(phrase)){
                    answer.add(qe);
                }
            } else if(where.equals("any")){
                answer.add(qe);
            }
        }
        return answer;
    }      

    public void bigQuakes(double size) {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> filteredList = filterByMagnitude(list,size);
        System.out.println("Found "+filteredList.size()+" quakes that are greater than "+size);
        for(QuakeEntry qe : filteredList){
            System.out.println(qe);
        }        
    }

    public void closeToMe(double distance){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);
        //ArrayList<QuakeEntry> closeQuakes = filterByDistanceFrom(list, distance, city);
        //System.out.println("There are "+closeQuakes.size()+" earth quakes that have occured near "+city);
        //for(QuakeEntry qe : closeQuakes){
        //    System.out.println(qe.getInfo());
        //}
        // This location is Bridgeport, CA
        Location city =  new Location(38.17, -118.82);
        ArrayList<QuakeEntry> closeQuakes = filterByDistanceFrom(list, distance, city);
        System.out.println("There are "+closeQuakes.size()+" earth quakes that have occured near "+city);
        for(QuakeEntry qe : closeQuakes){
            System.out.println(qe.getInfo());
        }
        // TODO
    }

    public void quakesOfDepth(double min, double max){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> depthQuakes  = filterByDepth(list, min, max);
        System.out.println("There are "+depthQuakes.size()+" earth quakes with depth between "+min+" and "+max);
        for(QuakeEntry qe : depthQuakes){
            System.out.println(qe);
        }
    }

    public void quakesByPhrase(String where, String phrase){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> phraseQuakes  = filterByPhrase(list, where, phrase);
        System.out.println("There are "+phraseQuakes.size()+" earth quakes that match "+phrase+" at "+where);
        for(QuakeEntry qe : phraseQuakes){
            System.out.println(qe);
        }
    }    
    
    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
    
    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }    
    
}
