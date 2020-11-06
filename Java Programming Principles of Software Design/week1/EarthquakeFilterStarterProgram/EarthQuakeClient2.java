import java.util.*;
import edu.duke.*;

public class EarthQuakeClient2 {
    public EarthQuakeClient2() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    } 

    public void quakesWithFilter() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        
        Filter magFilt = new MagnitudeFilter(3.5,4.5,"Magnitude Filter"); 
        Filter depthFilt = new DepthFilter(-55000.0,-20000.0,"Depth Filter");
        ArrayList<QuakeEntry> filteredQuakes  = filter(list, magFilt);
        filteredQuakes = filter(filteredQuakes, depthFilt);
        // Filter distFilt = new DistanceFilter(new Location(39.7392, -104.9903),1000000,"Distance Filter"); // location is Denver Colorado 
        // Filter phraseFilt = new PhraseFilter("end","a","Phrase Filter");
        // ArrayList<QuakeEntry> filteredQuakes  = filter(list, distFilt);
        // filteredQuakes = filter(filteredQuakes, phraseFilt);  
        System.out.println("Found "+filteredQuakes.size()+" quakes that meet the criteria");
        for (QuakeEntry qe : filteredQuakes) { 
            System.out.println(qe);
        } 
    }

    public void testMatchAllFilter(){
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        MatchAllFilter maf = new MatchAllFilter();
        Filter magFilt = new MagnitudeFilter(1.0,4.0,"Magnitude Filter"); 
        Filter depthFilt = new DepthFilter(-180000.0,-30000.0,"Depth Filter");
        Filter phraseFilt = new PhraseFilter("any","o","Phrase Filter");
        maf.addFilter(magFilt);
        maf.addFilter(depthFilt);
        maf.addFilter(phraseFilt);
        ArrayList<QuakeEntry> filteredQuakes  = filter(list, maf);
        System.out.println("Found "+filteredQuakes.size()+" quakes that meet the criteria");
        for (QuakeEntry qe : filteredQuakes) { 
            System.out.println(qe);
        }         
        System.out.println("Filters used are: "+maf.getName());
    }
    
    public void testMatchAllFilter2(){
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        MatchAllFilter maf = new MatchAllFilter();
        Filter magFilt = new MagnitudeFilter(0.0,5.0,"Magnitude Filter"); 
        Filter distFilt = new DistanceFilter(new Location(55.7308, 9.1153),3000000.0,"Distance Filter"); // location is Billund, Denmark
        Filter phraseFilt = new PhraseFilter("any","e","Phrase Filter");
        maf.addFilter(magFilt);
        maf.addFilter(distFilt);
        maf.addFilter(phraseFilt);
        ArrayList<QuakeEntry> filteredQuakes  = filter(list, maf);
        System.out.println("Found "+filteredQuakes.size()+" quakes that meet the criteria");
        for (QuakeEntry qe : filteredQuakes) { 
            System.out.println(qe);
        }         
        System.out.println("Filters used are: "+maf.getName());
    }    
    
    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list) {
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
