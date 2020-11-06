
/**
 * Write a description of Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class Tester {
    public void testGetFollows(String testString){
        MarkovOne markov = new MarkovOne();
        String text = "this is a test yes this is a test.";
        markov.setTraining(text);
        ArrayList<String> result = markov.getFollows(testString);
        System.out.println("Result size: "+result.size());
        System.out.println(result);
    }
    
    public void testGetFollowsWithFile(String testString){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovOne markov = new MarkovOne();
        markov.setTraining(st);
        ArrayList<String> result = markov.getFollows(testString);
        System.out.println("Result size: "+result.size());
        System.out.println(result);
    }    
    
}
