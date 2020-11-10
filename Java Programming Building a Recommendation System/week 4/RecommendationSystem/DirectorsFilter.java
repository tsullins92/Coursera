
/**
 * Write a description of DirectorsFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class DirectorsFilter implements Filter{

    private ArrayList<String> myDirectors;
    
    public DirectorsFilter(String directors) {
        myDirectors = new ArrayList(Arrays.asList(directors.split(",")));
    }
    
    @Override
    public boolean satisfies(String id) {
        for(String director : myDirectors){
            if(MovieDatabase.getDirector(id).contains(director)) return true;
        }
        return false;
    }

}
