import edu.duke.*;
import java.io.File;
/**
 * Write a description of Part4 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part4 {
    
    public String findString(String sourceW, String findW){
        String lowerSW = sourceW.toLowerCase();
        String lowerFW = findW.toLowerCase();
        int startIndex = lowerSW.indexOf(lowerFW);
        if(startIndex == -1){
            return "";
        }
        startIndex = lowerSW.lastIndexOf("\"",startIndex);
        if(startIndex == -1){
            return "";
        }   
        int endIndex = lowerSW.indexOf("\"",startIndex+1);
        if(endIndex == -1){
            return "";
        }         
        String result = sourceW.substring(startIndex+1, endIndex);
        return result;
    }    
   
    public void printURLNames() {
        URLResource ur = new URLResource("https://www.dukelearntoprogram.com//course2/data/manylinks.html");
        for (String word : ur.words()) {
            // System.out.println(word);
            String url = findString(word,"youtube.com");
            if(url.length()>0){
                System.out.println(url);
            }
        }
    }
}
