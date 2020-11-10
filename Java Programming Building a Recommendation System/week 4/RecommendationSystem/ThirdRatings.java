
/**
 * Write a description of ThirdRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }
    
    public ThirdRatings(String ratingsfile) {
        FirstRatings firstRatings = new FirstRatings();
        myRaters = firstRatings.loadRaters(ratingsfile);
    }
    
    public int getRaterSize(){
        return myRaters.size();
    }
    
    public double getAverageByID(String id, int minimalRaters){
        ArrayList<Double> ratings = new ArrayList<Double>();
        double total = 0.0;
        for(Rater rater : myRaters){
            for(String item : rater.getItemsRated()){
                if(item.equals(id)) {
                    ratings.add(rater.getRating(item));
                    total += rater.getRating(item);
                }
            }
        }
        if(ratings.size() < minimalRaters){
            return 0.0;
        } return (double)total/ratings.size();
        
    }
        
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        for(String movie : movies){
            double avgRating = getAverageByID(movie, minimalRaters);
            if(avgRating != 0.0) avgRatings.add(new Rating(movie, avgRating));
        }
        return avgRatings;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        for(String movie : movies){
            double avgRating = getAverageByID(movie, minimalRaters);
            if(avgRating != 0.0) avgRatings.add(new Rating(movie, avgRating));
        }
        return avgRatings;
    }    
    

}
