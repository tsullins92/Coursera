
/**
 * Write a description of DistanceFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DistanceFilter implements Filter {
    private Location location; 
    private double maxDist;
    private String name;
    
    public DistanceFilter(Location loc, double max, String filterName) { 
        location = loc;
        maxDist = max;
        name = filterName;
    } 

    public boolean satisfies(QuakeEntry qe) { 
        double distance = qe.getLocation().distanceTo(location);
        return distance < maxDist; 
    } 
    
    public String getName(){
        return name;
    }    
    
}
