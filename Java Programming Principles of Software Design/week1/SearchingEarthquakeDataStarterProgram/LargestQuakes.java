
/**
 * Write a description of LargestQuakes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LargestQuakes {

    public int indexOfLargest(ArrayList<QuakeEntry> data){
        int largestIndex = 0;
        for(QuakeEntry qe : data){
            if(qe.getMagnitude() > data.get(largestIndex).getMagnitude()){
                largestIndex = data.indexOf(qe);
            }
        }
        return largestIndex;
    }

    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany) {
        ArrayList<QuakeEntry> ret = new ArrayList<QuakeEntry>();
        ArrayList<QuakeEntry> tempQuakes = quakeData;
        // TO DO
        for(int i = 0; i < howMany; ++i){
            int largestIndex = indexOfLargest(tempQuakes);
            ret.add(tempQuakes.get(largestIndex));
            tempQuakes.remove(largestIndex);       
        }
        return ret;
    }    
    
    public void findLargestQuakes(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "data/nov20quakedata.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size());   
        int largestMagIdx = indexOfLargest(list);
        System.out.println("Index of largest magnitude: "+largestMagIdx+" ---- "+list.get(largestMagIdx));
        ArrayList<QuakeEntry> largestQuakes = getLargest(list, 50);
        System.out.println("50 largest quakes: ");
        for(QuakeEntry qe : largestQuakes){
            System.out.println(qe);
        }
    }
    
}
