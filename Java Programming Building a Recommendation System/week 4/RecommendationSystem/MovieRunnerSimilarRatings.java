
/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerSimilarRatings {

    public void printSimilarRatingsByYearAfterAndMinutes(){
        String id = "314";
        int numSimilarRaters = 10;
        int minimalRaters = 5;
        Filter minutesFilter = new MinutesFilter(70, 200);
        Filter yearAfterFilter = new YearAfterFilter(1975);
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(minutesFilter);
        allFilters.addFilter(yearAfterFilter);
        FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Read in "+RaterDatabase.size()+" raters");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        ArrayList<Rating> moviesWithRatings = fourthRatings.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, allFilters);
        System.out.println(moviesWithRatings.size()+" movies with "+minimalRaters+" ratings in their top "+numSimilarRaters+" similar raters:");
        Collections.sort(moviesWithRatings, Collections.reverseOrder());
        for(Rating rating : moviesWithRatings) {
            System.out.println(rating.getValue()+" "+MovieDatabase.getYear(rating.getItem())+" "+MovieDatabase.getMinutes(rating.getItem())+" "+MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t"+MovieDatabase.getDirector(rating.getItem()));
        }
    }         
    
    public void printSimilarRatingsByGenreAndMinutes(){
        String id = "168";
        int numSimilarRaters = 10;
        int minimalRaters = 3;
        Filter minutesFilter = new MinutesFilter(80, 160);
        Filter genreFilter = new GenreFilter("Drama");
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(minutesFilter);
        allFilters.addFilter(genreFilter);
        FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Read in "+RaterDatabase.size()+" raters");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        ArrayList<Rating> moviesWithRatings = fourthRatings.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, allFilters);
        System.out.println(moviesWithRatings.size()+" movies with "+minimalRaters+" ratings in their top "+numSimilarRaters+" similar raters:");
        Collections.sort(moviesWithRatings, Collections.reverseOrder());
        for(Rating rating : moviesWithRatings) {
            System.out.println(rating.getValue()+" "+MovieDatabase.getMinutes(rating.getItem())+" "+MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t"+MovieDatabase.getGenres(rating.getItem()));
        }
    }         
    
    public void printSimilarRatingsByDirector(){
        String id = "120";
        int numSimilarRaters = 10;
        int minimalRaters = 2;
        Filter directorsFilter = new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");
        FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Read in "+RaterDatabase.size()+" raters");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        ArrayList<Rating> moviesWithRatings = fourthRatings.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, directorsFilter);
        System.out.println(moviesWithRatings.size()+" movies with "+minimalRaters+" ratings in their top "+numSimilarRaters+" similar raters:");
        Collections.sort(moviesWithRatings, Collections.reverseOrder());
        for(Rating rating : moviesWithRatings) {
            System.out.println(rating.getValue()+" "+MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t"+MovieDatabase.getDirector(rating.getItem()));
        }
    }       
    
    public void printSimilarRatingsByGenre(){
        String id = "964";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        Filter genreFilter = new GenreFilter("Mystery");
        FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Read in "+RaterDatabase.size()+" raters");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        ArrayList<Rating> moviesWithRatings = fourthRatings.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, genreFilter);
        System.out.println(moviesWithRatings.size()+" movies with "+minimalRaters+" ratings in their top "+numSimilarRaters+" similar raters:");
        Collections.sort(moviesWithRatings, Collections.reverseOrder());
        for(Rating rating : moviesWithRatings) {
            System.out.println(rating.getValue()+" "+MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t"+MovieDatabase.getGenres(rating.getItem()));
        }
    }     
    
    public void printSimilarRatings(){
        String id = "71";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Read in "+RaterDatabase.size()+" raters");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        ArrayList<Rating> moviesWithRatings = fourthRatings.getSimilarRatings(id, numSimilarRaters, minimalRaters);
        System.out.println(moviesWithRatings.size()+" movies with "+minimalRaters+" ratings in their top "+numSimilarRaters+" similar raters:");
        Collections.sort(moviesWithRatings, Collections.reverseOrder());
        for(Rating rating : moviesWithRatings) {
            System.out.println(rating.getValue()+" "+MovieDatabase.getTitle(rating.getItem()));
        }
    } 
    
    public void printAverageRatingsByYearAfterAndGenre(int minNumRatings, int yearAfter, String genre){
        FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Read in "+RaterDatabase.size()+" raters");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        Filter yearAfterFilter = new YearAfterFilter(yearAfter);
        Filter genreFilter = new GenreFilter(genre);
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(yearAfterFilter);
        allFilters.addFilter(genreFilter);
        ArrayList<Rating> moviesWithRatings = fourthRatings.getAverageRatingsByFilter(minNumRatings, allFilters);
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
    
    public void printAverageRatings(int minNumRatings){
        FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Read in "+RaterDatabase.size()+" raters");
        System.out.println("Read in "+MovieDatabase.size()+" movies");
        ArrayList<Rating> moviesWithRatings = fourthRatings.getAverageRatings(minNumRatings);
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
