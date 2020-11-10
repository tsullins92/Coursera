
/**
 * Write a description of RecommendationRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class RecommendationRunner implements Recommender {
    /**
     * This method returns a list of movie IDs that will be used to look up 
     * the movies in the MovieDatabase and present them to users to rate. 
     *  
     * The movies returned in the list will be displayed on a web page, so
     * the number you choose may affect how long the page takes to load and
     * how willing users are to rate the movies.  For example, 10-20 should
     * be fine, 50 or more would be too many.
     * 
     * There are no restrictions on the method you use to generate this list
     * of movies: the most recent movies, movies from a specific genre, 
     * randomly chosen movies, or simply your favorite movies.
     * 
     * The ratings for these movies will make the profile for a new Rater 
     * that will be used to compare to for finding recommendations.
     */
    public ArrayList<String> getItemsToRate (){
        Filter directorsFilter = new DirectorsFilter("Clint Eastwood");
        return MovieDatabase.filterBy(directorsFilter);
    }

    /**
     * This method returns nothing, but prints out an HTML table of the 
     * movies recommended for the given rater.
     * 
     * The HTML printed will be displayed on a web page, so the number you
     * choose to display may affect how long the page takes to load.  For 
     * example, you may want to limit the number printed to only the top 
     * 20-50 movies recommended or to movies not rater by the given rater.
     * 
     * You may also include CSS styling for your table using the &lt;style&gt;
     * tag before you print the table.  There are no restrictions on which 
     * movies you print, what order you print them in, or what information
     * you include about each movie. 
     * 
     * @param webRaterID the ID of a new Rater that has been already added to 
     *        the RaterDatabase with ratings for the movies returned by the 
     *        method getItemsToRate
     */
    public void printRecommendationsFor (String webRaterID){
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        // System.out.println("Read in "+RaterDatabase.size()+" raters");
        // System.out.println("Read in "+MovieDatabase.size()+" movies");
        ArrayList<Rating> moviesWithRatings = fourthRatings.getSimilarRatings(webRaterID, numSimilarRaters, minimalRaters);
        // System.out.println(moviesWithRatings.size()+" movies with "+minimalRaters+" ratings in their top "+numSimilarRaters+" similar raters:");
        Collections.sort(moviesWithRatings, Collections.reverseOrder());
        // for(Rating rating : moviesWithRatings) {
            // System.out.println(rating.getValue()+" "+MovieDatabase.getTitle(rating.getItem()));
        // }
        System.out.println("<html><head><style>#movies {font-family: Arial, Helvetica, sans-serif;border-collapse: collapse;width: 100%;}#movies td, #movies th {border: 1px solid #ddd;padding: 8px;}"+
        "#movies tr:nth-child(even){background-color: #f2f2f2;}#movies tr:hover {background-color: #ddd;}#movies th {padding-top: 12px;padding-bottom: 12px;text-align: left;"+
        "background-color: #4CAF50;color: white;}</style></head><body>"+
        "<table id=\"movies\"><thead><tr><th>Score</th><th>Movie ID</th><th>Title</th><th>Directors</th><th>Year</th><th>Duration (min)</th></tr></thead><tbody>");
        for(Rating movieRating : moviesWithRatings){
            System.out.println("<tr><td>"+movieRating.getValue()+"</td>"+"<td>"+movieRating.getItem()+"</td>"+"<td>"+MovieDatabase.getTitle(movieRating.getItem())+"</td>"+
                "<td>"+MovieDatabase.getDirector(movieRating.getItem())+"</td>"+"<td>"+MovieDatabase.getYear(movieRating.getItem())+"</td>"+
                "<td>"+MovieDatabase.getMinutes(movieRating.getItem())+"</td></tr>");
        }
        System.out.println("</tbody></table>");
    }   
}
