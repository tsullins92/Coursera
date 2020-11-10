
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings firstRatings = new FirstRatings();
        myMovies = firstRatings.loadMovies(moviefile);
        myRaters = firstRatings.loadRaters(ratingsfile);
    }
    
    public int getMovieSize(){
        return myMovies.size();
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
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        for(Movie movie : myMovies){
            double avgRating = getAverageByID(movie.getID(), minimalRaters);
            if(avgRating != 0.0) avgRatings.add(new Rating(movie.getID(), avgRating));
        }
        return avgRatings;
    }

    public String getTitle(String id){
        for(Movie movie : myMovies){
            if(movie.getID().equals(id)) return movie.getTitle();
        }
        return "NO SUCH ID";
    }
 
    public String getID(String title){
        for(Movie movie : myMovies){
            if(movie.getTitle().equals(title)) return movie.getID();
        }
        return "NO SUCH TITLE";        
    }
}










