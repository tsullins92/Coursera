
/**
 * Write a description of MarkovModel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MarkovModel {
    
    private String myText;
    private Random myRandom;
    private int modelN;
    
    public MarkovModel(int n) {
        myRandom = new Random();
        modelN = n;
    }
    
    public void setRandom(int seed){
        myRandom = new Random(seed);
    }
    
    public void setTraining(String s){
        myText = s.trim();
    }
    
    public ArrayList<String> getFollows(String key){
        int pos = 0;
        ArrayList<String> follows = new ArrayList<String>();
        while(pos < myText.length()){
            int start = myText.indexOf(key,pos);
            if(start == -1){
                break;
            }
            if(start + key.length()>= myText.length()){
                break;
            }
            String next = myText.substring(start+key.length(), start+key.length()+1);
            follows.add(next);
            pos = start+key.length();
        }
        return follows;
    }
    
    public String getRandomText(int numChars){
        if (myText == null){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int index = myRandom.nextInt(myText.length()-modelN);
        String key = myText.substring(index, index+modelN);
        sb.append(key);
        for(int k=0; k < numChars; k++){
            ArrayList<String> follows = getFollows(key);
            if(follows.size() == 0){
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            key = key.substring(1) + next;
        }
        return sb.toString();
    }
}
