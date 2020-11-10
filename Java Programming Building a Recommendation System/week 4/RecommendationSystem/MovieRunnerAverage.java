
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerAverage {

    public void printAverageRatings(int minNumRatings){
        SecondRatings secondRatings = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv");
        System.out.println("Read in "+secondRatings.getMovieSize()+" movies and "+secondRatings.getRaterSize()+" raters");
        ArrayList<Rating> moviesWithRatings = secondRatings.getAverageRatings(minNumRatings);
        System.out.println(moviesWithRatings.size()+" movies with "+minNumRatings+" ratings: ");
        Rating lowestRating = new Rating("",100.0);
        for(Rating rating : moviesWithRatings) {
            if(rating.getValue()<lowestRating.getValue()) lowestRating = rating;
            System.out.println("Average Rating: "+rating.getValue()+" "+secondRatings.getTitle(rating.getItem()));
        }
        System.out.println("Movie with lowest rating: "+secondRatings.getTitle(lowestRating.getItem())+" "+lowestRating.getValue());
    }    
    
    public void getAverageRatingOneMovie(String title){
        SecondRatings secondRatings = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv");
        String movieID = secondRatings.getID(title);
        double avgRating = secondRatings.getAverageByID(movieID, 1);
        System.out.println(avgRating+" "+title);
    }
}