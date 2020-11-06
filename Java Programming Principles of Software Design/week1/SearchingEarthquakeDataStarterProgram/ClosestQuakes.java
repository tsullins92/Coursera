
/**
 * Find N-closest quakes
 * 
 * @author Duke Software/Learn to Program
 * @version 1.0, November 2015
 */

import java.util.*;

public class ClosestQuakes {
    public ArrayList<QuakeEntry> getClosest(ArrayList<QuakeEntry> quakeData, Location current, int howMany) {
        ArrayList<QuakeEntry> ret = new ArrayList<QuakeEntry>();
        ArrayList<QuakeEntry> tempQuakes = quakeData;
        // TO DO
        for(int i = 0; i < howMany; ++i){
            int closestIndex = 0;    
            for(QuakeEntry qe : tempQuakes){
                double distance = qe.getLocation().distanceTo(current);
                double closestDistance = tempQuakes.get(closestIndex).getLocation().distanceTo(current);
                if(distance < closestDistance){
                    closestIndex = tempQuakes.indexOf(qe);
                }
            }
            ret.add(tempQuakes.get(closestIndex));
            tempQuakes.remove(closestIndex);
        }
        return ret;
    }

    public void findClosestQuakes(int howMany) {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size());

        Location jakarta  = new Location(-6.211,106.845);

        ArrayList<QuakeEntry> close = getClosest(list,jakarta,howMany);
        for(int k=0; k < close.size(); k++){
            QuakeEntry entry = close.get(k);
            double distanceInMeters = jakarta.distanceTo(entry.getLocation());
            System.out.printf("%4.2f\t %s\n", distanceInMeters/1000,entry);
        }
        System.out.println("number found: "+close.size());
    }
    
}
