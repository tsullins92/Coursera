
/**
 * Write a description of MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    
    public MarkovWord(int order) {
        myOrder = order;
        myRandom = new Random();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
    }
    
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
        WordGram kGram = new WordGram(myText, index, myOrder);
        String key2 = myText[index + 1];
        sb.append(kGram);
        sb.append(" ");
        for(int k=0; k < numWords-myOrder; k++){
            ArrayList<String> follows = getFollows(kGram);
            // System.out.println(key+": ");
            // for(String word : follows){
                // System.out.println(word);
            // }
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            kGram = kGram.shiftAdd(next);
        }
        return sb.toString().trim();
    }
    
    private int indexOf(String[] words, WordGram target, int start){
        for(int i = start; i < words.length - myOrder - 1; ++i){
            WordGram current = new WordGram(words, i, myOrder);
            // System.out.println(target+"\t"+current+"\t"+current.equals(target));
            if(current.equals(target)){
                return i;
            }
        }
        return -1;
    }
    
    // public void testIndexOf(){
        // String text = "this is just a test yes this is a simple test";
        // String [] textArray = text.split("\\s+");
        // System.out.println("'this' starting at 0: " + indexOf(textArray, "this", 0));
        // System.out.println("'this' starting at 3: " + indexOf(textArray, "this", 3));
        // System.out.println("'frog' starting at 0: " + indexOf(textArray, "frog", 0));
        // System.out.println("'frog' starting at 5: " + indexOf(textArray, "frog", 5));
        // System.out.println("'simple' starting at 2: " + indexOf(textArray, "simple", 2));
        // System.out.println("'test' starting at 5: " + indexOf(textArray, "test", 5));
    // }
    
    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        while(pos < myText.length){
            int start = indexOf(myText, kGram, pos);
            if(start == -1){
                break;
            }
            if(start  >= myText.length-myOrder){
                break;
            }
            String next = myText[start+myOrder];
            follows.add(next);
            pos = start+1;
        }
        return follows;
    }
}
