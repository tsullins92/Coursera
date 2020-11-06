
/**
 * Write a description of MatchAllFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MatchAllFilter implements Filter {

    private ArrayList<Filter> filters;
    
    public MatchAllFilter(){
        filters = new ArrayList<Filter>();
    }
    
    public void addFilter(Filter newFilt){
        filters.add(newFilt);
    }
    
    public boolean satisfies(QuakeEntry qe){
        for(Filter filt : filters){
            if(!filt.satisfies(qe)){
                return false;
            }
        }
        return true;
    }
    
    public String getName(){
        String name = "";
        for(Filter filter : filters){
            name += " " + filter.getName();
        }
        name.trim();
        return name;
    }    
    
}
