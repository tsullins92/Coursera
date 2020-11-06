
/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {

    public ArrayList<Movie> loadMovies(String fileName){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        FileResource fr = new FileResource(fileName);
        for (CSVRecord rec : fr.getCSVParser()) {
            Movie movie = new Movie(rec.get("id"), rec.get("title"), rec.get("year"), rec.get("genre"), rec.get("director"), rec.get("country"), rec.get("poster"), Integer.parseInt(rec.get("minutes")));
            movies.add(movie);
        }
        return movies;
    }
    // Add code to determine the maximum number of movies by any director, and who the directors are that directed that many movies. 
    // Remember that some movies may have more than one director. In the file ratedmovies_short.csv the maximum number of movies by any
    // director is one, and there are five directors that directed one such movie.
    public void testLoadMovies(){
        ArrayList<Movie> movies = loadMovies("data/ratedmoviesfull.csv");
        System.out.println("Number of movies: "+movies.size());
        System.out.println(movies); 
        int genreCount = 0;
        int minutesCount = 0;
        HashMap<String, Integer> directorMap = new HashMap<String, Integer>();
        for(Movie movie : movies){
            if(movie.getGenres().equals("Comedy")) ++genreCount;
            if(movie.getMinutes() > 150) ++minutesCount;
            String director = movie.getDirector();
            if(directorMap.containsKey(director)) directorMap.put(director, directorMap.get(director)+1);
            else directorMap.put(director, 1);
        }
        int maxDirCount = 0;
        for(String director : directorMap.keySet()){
            int count = directorMap.get(director);
            if(count > maxDirCount) maxDirCount = count;
        }
        System.out.println("Max number of movies directed by a single director: "+maxDirCount);
        int dirCount = 0;
        ArrayList<String> maxDirectors = new ArrayList<String>();
        for(String director : directorMap.keySet()) {
            if(directorMap.get(director) == maxDirCount) maxDirectors.add(director);
        }
        System.out.println("# of directors who directed "+maxDirCount+" movies: "+maxDirectors.size());
        for(String director : maxDirectors) {
            System.out.println(director);
        }
        System.out.println("# of Comedy Movies: "+genreCount);
        System.out.println("# of Movies longer than 150 minutes: "+minutesCount);
    }
    
// In the FirstRatings class, write a method named loadRaters that has one parameter named filename. This method should process every record from the CSV file whose name is 
// filename, a file of raters and their ratings, and return an ArrayList of type Rater with all the rater data from the file.
    
    public ArrayList<Rater> loadRaters(String fileName){
        ArrayList<Rater> raters = new ArrayList<Rater>();
        FileResource fr = new FileResource(fileName);
        for (CSVRecord rec : fr.getCSVParser()) {
            boolean exists = false;
            for(Rater rater : raters){
                if(rater.getID().equals(rec.get("rater_id"))){
                    rater.addRating(rec.get("movie_id"), Double.parseDouble(rec.get("rating")));
                    exists = true;
                    continue;
                }
            }
            if(!exists){
                Rater rater = new Rater(rec.get("rater_id"));
                rater.addRating(rec.get("movie_id"), Double.parseDouble(rec.get("rating")));
                raters.add(rater);
            }
        }
        return raters;        
    }
    
    public void testLoadRaters(){
        String raterID = "193";
        String movieID = "1798709";
        ArrayList<Rater> raters = loadRaters("data/ratings.csv");
        ArrayList<String> uniqueMovies = new ArrayList<String>();
        System.out.println("Total # of raters: "+raters.size());
        int maxRatings = 0;
        int movieIDRatings = 0;
        for(Rater rater : raters) {
            if(rater.getID().equals(raterID)) System.out.println("Number of ratings for rater id "+raterID+": "+rater.numRatings());
            if(rater.numRatings()>maxRatings) maxRatings = rater.numRatings();
            if(rater.hasRating(movieID)) ++movieIDRatings;
            for(String item : rater.getItemsRated()){
                if(!uniqueMovies.contains(item)) uniqueMovies.add(item);
            }
        }        
        ArrayList<Rater> maxRaters = new ArrayList<Rater>();
        for(Rater rater : raters) {
            if(rater.numRatings() == maxRatings) maxRaters.add(rater);
        } 
        System.out.println("Max number of ratings for any rater: "+maxRatings);
        System.out.println("Raters with max number of ratings: "+maxRaters.size());
        for(Rater rater : maxRaters){
            System.out.println(rater.getID());
        }
        System.out.println("Movie ID "+movieID+" has "+movieIDRatings+" ratings");
        System.out.println(uniqueMovies.size()+" different movies have been reviewed");
        // for(Rater rater : raters){
            // System.out.println("Rater ID: "+rater.getID()+" --- # of ratings: "+rater.numRatings());
            // for(String item : rater.getItemsRated()){
                // System.out.println("Movie ID: "+item+" --- Rating: " + rater.getRating(item));
            // }
        // }
        
    }
    
}










