
/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerWithFilters {
    
    public void printAverageRatingsByDirectorsAndMinutes(int minNumRatings, String directors, int minMinutes, int maxMinutes){
        ThirdRatings thirdRatings = new ThirdRatings("data/ratings.csv");
        System.out.println("Read in "+thirdRatings.getRaterSize()+" raters");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        Filter directorsFilter = new DirectorsFilter(directors);
        Filter minutesFilter = new MinutesFilter(minMinutes, maxMinutes);
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(directorsFilter);
        allFilters.addFilter(minutesFilter);
        ArrayList<Rating> moviesWithRatings = thirdRatings.getAverageRatingsByFilter(minNumRatings, allFilters);
        System.out.println(moviesWithRatings.size()+" movies with "+minNumRatings+" ratings: ");
        Collections.sort(moviesWithRatings);
        Rating lowestRating = new Rating("",100.0);
        for(Rating rating : moviesWithRatings) {
            if(rating.getValue()<lowestRating.getValue()) lowestRating = rating;
            System.out.println(rating.getValue()+" Time: "+MovieDatabase.getMinutes(rating.getItem())+" "+MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t"+MovieDatabase.getDirector(rating.getItem()));
        }
        System.out.println("Movie with lowest rating: "+MovieDatabase.getTitle(lowestRating.getItem())+" "+lowestRating.getValue());
    }     
    
    public void printAverageRatingsByYearAfterAndGenre(int minNumRatings, int yearAfter, String genre){
        ThirdRatings thirdRatings = new ThirdRatings("data/ratings.csv");
        System.out.println("Read in "+thirdRatings.getRaterSize()+" raters");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        Filter yearAfterFilter = new YearAfterFilter(yearAfter);
        Filter genreFilter = new GenreFilter(genre);
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(yearAfterFilter);
        allFilters.addFilter(genreFilter);
        ArrayList<Rating> moviesWithRatings = thirdRatings.getAverageRatingsByFilter(minNumRatings, allFilters);
        System.out.println(moviesWithRatings.size()+" movies with "+minNumRatings+" ratings: ");
        Collections.sort(moviesWithRatings);
        Rating lowestRating = new Rating("",100.0);
        for(Rating rating : moviesWithRatings) {
            if(rating.getValue()<lowestRating.getValue()) lowestRating = rating;
            System.out.println(rating.getValue()+" "+MovieDatabase.getYear(rating.getItem())+" "+MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t"+MovieDatabase.getGenres(rating.getItem()));
        }
        System.out.println("Movie with lowest rating: "+MovieDatabase.getTitle(lowestRating.getItem())+" "+lowestRating.getValue());
    }        
    
    public void printAverageRatingsByDirectors(int minNumRatings, String directors ){
        ThirdRatings thirdRatings = new ThirdRatings("data/ratings.csv");
        System.out.println("Read in "+thirdRatings.getRaterSize()+" raters");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        Filter directorsFilter = new DirectorsFilter(directors);
        ArrayList<Rating> moviesWithRatings = thirdRatings.getAverageRatingsByFilter(minNumRatings, directorsFilter);
        System.out.println(moviesWithRatings.size()+" movies with "+minNumRatings+" ratings: ");
        Collections.sort(moviesWithRatings);
        Rating lowestRating = new Rating("",100.0);
        for(Rating rating : moviesWithRatings) {
            if(rating.getValue()<lowestRating.getValue()) lowestRating = rating;
            System.out.println(rating.getValue()+" "+MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t"+MovieDatabase.getDirector(rating.getItem()));
        }
        System.out.println("Movie with lowest rating: "+MovieDatabase.getTitle(lowestRating.getItem())+" "+lowestRating.getValue());
    }        
    
    public void printAverageRatingsByMinutes(int minNumRatings, int minMinutes, int maxMinutes ){
        ThirdRatings thirdRatings = new ThirdRatings("data/ratings.csv");
        System.out.println("Read in "+thirdRatings.getRaterSize()+" raters");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        Filter minutesFilter = new MinutesFilter(minMinutes, maxMinutes);
        ArrayList<Rating> moviesWithRatings = thirdRatings.getAverageRatingsByFilter(minNumRatings, minutesFilter);
        System.out.println(moviesWithRatings.size()+" movies with "+minNumRatings+" ratings: ");
        Collections.sort(moviesWithRatings);
        Rating lowestRating = new Rating("",100.0);
        for(Rating rating : moviesWithRatings) {
            if(rating.getValue()<lowestRating.getValue()) lowestRating = rating;
            System.out.println(rating.getValue()+" "+MovieDatabase.getMinutes(rating.getItem())+" "+MovieDatabase.getTitle(rating.getItem()));
        }
        System.out.println("Movie with lowest rating: "+MovieDatabase.getTitle(lowestRating.getItem())+" "+lowestRating.getValue());
    }    
    
    public void printAverageRatingsByGenre(int minNumRatings, String genre){
        ThirdRatings thirdRatings = new ThirdRatings("data/ratings.csv");
        System.out.println("Read in "+thirdRatings.getRaterSize()+" raters");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        Filter genreFilter = new GenreFilter(genre);
        ArrayList<Rating> moviesWithRatings = thirdRatings.getAverageRatingsByFilter(minNumRatings, genreFilter);
        System.out.println(moviesWithRatings.size()+" movies with "+minNumRatings+" ratings: ");
        Collections.sort(moviesWithRatings);
        Rating lowestRating = new Rating("",100.0);
        for(Rating rating : moviesWithRatings) {
            if(rating.getValue()<lowestRating.getValue()) lowestRating = rating;
            System.out.println(rating.getValue()+" "+MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t"+MovieDatabase.getGenres(rating.getItem()));
        }
        System.out.println("Movie with lowest rating: "+MovieDatabase.getTitle(lowestRating.getItem())+" "+lowestRating.getValue());
    }    
    
    public void printAverageRatingsByYear(int minNumRatings, int yearAfter){
        ThirdRatings thirdRatings = new ThirdRatings("data/ratings.csv");
        System.out.println("Read in "+thirdRatings.getRaterSize()+" raters");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        Filter yearAfterFilter = new YearAfterFilter(yearAfter);
        ArrayList<Rating> moviesWithRatings = thirdRatings.getAverageRatingsByFilter(minNumRatings, yearAfterFilter);
        System.out.println(moviesWithRatings.size()+" movies with "+minNumRatings+" ratings: ");
        Collections.sort(moviesWithRatings);
        Rating lowestRating = new Rating("",100.0);
        for(Rating rating : moviesWithRatings) {
            if(rating.getValue()<lowestRating.getValue()) lowestRating = rating;
            System.out.println(rating.getValue()+" "+MovieDatabase.getYear(rating.getItem())+" "+MovieDatabase.getTitle(rating.getItem()));
        }
        System.out.println("Movie with lowest rating: "+MovieDatabase.getTitle(lowestRating.getItem())+" "+lowestRating.getValue());
    }
    
    public void printAverageRatings(int minNumRatings){
        ThirdRatings thirdRatings = new ThirdRatings("data/ratings.csv");
        System.out.println("Read in "+thirdRatings.getRaterSize()+" raters");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        ArrayList<Rating> moviesWithRatings = thirdRatings.getAverageRatings(minNumRatings);
        System.out.println(moviesWithRatings.size()+" movies with "+minNumRatings+" ratings: ");
        Collections.sort(moviesWithRatings);
        Rating lowestRating = new Rating("",100.0);
        for(Rating rating : moviesWithRatings) {
            if(rating.getValue()<lowestRating.getValue()) lowestRating = rating;
            System.out.println(rating.getValue()+" "+MovieDatabase.getTitle(rating.getItem()));
        }
        System.out.println("Movie with lowest rating: "+MovieDatabase.getTitle(lowestRating.getItem())+" "+lowestRating.getValue());
    } 
}
