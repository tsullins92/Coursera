
/**
 * Write a description of FourthRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class FourthRatings {
   
    public double getAverageByID(String id, int minimalRaters){
        ArrayList<Rater> raters = RaterDatabase.getRaters();
        double total = 0.0;
        int count = 0;
        for(Rater rater : raters){
            for(String item : rater.getItemsRated()){
                if(item.equals(id)) {
                    ++count;
                    total += rater.getRating(item);
                }
            }
        }
        if(count < minimalRaters){
            return 0.0;
        } return (double)total/count;
        
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
    
    private double dotProduct(Rater me, Rater r){
        ArrayList<String> meItems = me.getItemsRated();
        ArrayList<String> rItems = r.getItemsRated();
        double dotScore = 0.0;
        for(String item : meItems){
            if(rItems.contains(item)){
                double meScore = me.getRating(item) - 5;
                double rScore = r.getRating(item) - 5;
                dotScore += (meScore * rScore);
            }
        }
        return dotScore;  
    }
    
    private ArrayList<Rating> getSimilarities(String id){
        ArrayList<Rating> list = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
        for(Rater r : RaterDatabase.getRaters()){
            if(me.getID().equals(r.getID())) continue;
            double dotScore = dotProduct(me, r);
            if(dotScore >= 0) list.add(new Rating(r.getID(), dotScore));
        }
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters){
        ArrayList<Rating> similarRaters = new ArrayList(getSimilarities(id).subList(0, numSimilarRaters)); 
        ArrayList<Rating> similarRatings = new ArrayList();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for(String movie : movies){
            double score = 0.0;
            int count = 0;
            for(Rating rater : similarRaters){
                Rater actualRater = RaterDatabase.getRater(rater.getItem());
                if(actualRater.hasRating(movie)){
                    ++count;
                    score += (rater.getValue() * actualRater.getRating(movie));
                }
            }
            if(count >= minimalRaters) similarRatings.add(new Rating(movie, (score/count)));
        }
        return similarRatings;    
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> similarRaters = new ArrayList(getSimilarities(id).subList(0, numSimilarRaters)); 
        ArrayList<Rating> similarRatings = new ArrayList();
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        for(String movie : movies){
            double score = 0.0;
            int count = 0;
            for(Rating rater : similarRaters){
                Rater actualRater = RaterDatabase.getRater(rater.getItem());
                if(actualRater.hasRating(movie)){
                    ++count;
                    score += (rater.getValue() * actualRater.getRating(movie));
                }
            }
            if(count >= minimalRaters) similarRatings.add(new Rating(movie, (score/count)));
        }
        return similarRatings;    
    }    
    
}
