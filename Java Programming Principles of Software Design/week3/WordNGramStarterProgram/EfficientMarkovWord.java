
/**
 * Write a description of EfficientMarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class EfficientMarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram,ArrayList<String>> keyMap;
    
    public EfficientMarkovWord(int order) {
        myOrder = order;
        myRandom = new Random();
        keyMap = new HashMap<WordGram,ArrayList<String>>();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
        buildMap();
        printHashMapInfo();
    }

    public void buildMap(){
        for(int i = 1; i <= myText.length - myOrder; ++i){
            WordGram kGram = new WordGram(myText, i, myOrder);
            String key = kGram.toString();
            ArrayList<String> follows = new ArrayList<String>();
            if((i+myOrder >= myText.length)&&(!keyMap.containsKey(kGram))){
                keyMap.put(kGram, follows);
            }
            else{
                if(keyMap.containsKey(kGram)){
                    keyMap.get(kGram).add(myText[i+myOrder]);
                } else{
                    follows.add(myText[i+myOrder]);
                    keyMap.put(kGram, follows);
                }           
            }
        }   
    }    
    
    private void printHashMapInfo(){
        int largestSz = 0;
        for(WordGram kGram : keyMap.keySet()){
            if(keyMap.get(kGram).size() > largestSz){
                largestSz = keyMap.get(kGram).size();
            }
        }
        System.out.println("# of keys in keyMap: "+keyMap.size());
        System.out.println("Largest key size: "+largestSz);
        System.out.println("Keys with size of "+largestSz);
        for(WordGram kGram : keyMap.keySet()){
            if(keyMap.get(kGram).size() == largestSz){
                System.out.println(kGram+": "+keyMap.get(kGram));
            }
        }        
        if(keyMap.size() < 50){
            System.out.println("All keys: ");
            for(WordGram kGram : keyMap.keySet()){
                System.out.println(kGram+": "+keyMap.get(kGram));
            }
        }    
    }
    
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
        WordGram kGram = new WordGram(myText, index, myOrder);
        sb.append(kGram);
        sb.append(" ");
        for(int i=0; i < numWords; i++){
            ArrayList<String> follows = getFollows(kGram);
            String key = kGram.toString();
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
        for(int i = start; i < words.length - myOrder; ++i){
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
        ArrayList<String> follows = keyMap.get(kGram);
        return follows;
    }
}
