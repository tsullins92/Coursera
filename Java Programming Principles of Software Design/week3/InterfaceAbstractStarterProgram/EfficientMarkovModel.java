
/**
 * Write a description of EfficientMarkovModel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class EfficientMarkovModel extends AbstractMarkovModel {
    
    private int modelN;
    private HashMap<String,ArrayList<String>> keyMap;
    
    
    public EfficientMarkovModel(int n) {
        myRandom = new Random();
        modelN = n;
        keyMap = new HashMap<String,ArrayList<String>>();
    }
    
    public void setRandom(int seed){
        myRandom = new Random(seed);
    }
    
    public void setTraining(String s){
        myText = s.trim();
        buildMap();
        printHashMapInfo();
    }
    
    public String toString(){
        return "EfficientMarkovModel of order "+modelN+".";
    }
    
    public void buildMap(){
        int pos = 0;
        for(int i = 0; i <= myText.length() - modelN; ++i){
            String key = myText.substring(i,i+modelN);
            ArrayList<String> follows = new ArrayList<String>();
            if(i+modelN >= myText.length()){
                keyMap.put(key, follows);
            }
            else{
                if(keyMap.containsKey(key)){
                    keyMap.get(key).add(myText.substring(i+modelN, i+modelN+1));
                }
                else{
                    follows.add(myText.substring(i+modelN, i+modelN+1));
                    keyMap.put(key, follows);
                }                
            }

        }   
    }
    
    public ArrayList<String> getFollows(String key){
        ArrayList<String> follows = keyMap.get(key);
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
            System.out.println(key);
            int size = follows.size();
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
    
    public void printHashMapInfo(){
        int largestSz = 0;
        for(String key : keyMap.keySet()){
            if(keyMap.get(key).size() > largestSz){
                largestSz = keyMap.get(key).size();
            }
        }
        System.out.println("# of keys in keyMap: "+keyMap.size());
        System.out.println("Largest key size: "+largestSz);
        System.out.println("Keys with size of "+largestSz);
        for(String key : keyMap.keySet()){
            if(keyMap.get(key).size() == largestSz){
                System.out.println(key+": "+keyMap.get(key));
            }
        }        
        if(keyMap.size() < 50){
            System.out.println("All keys: ");
            for(String key : keyMap.keySet()){
                System.out.println(key+": "+keyMap.get(key));
            }
        }
    }
    
}















