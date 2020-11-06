
/**
 * Write a description of GladLibMap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
public class GladLibMap {
    private HashMap<String, ArrayList<String>> myMap;
    private ArrayList<String> seenWordsList;
    private Random myRandom;
    private ArrayList<String> seenLabelsList;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    public GladLibMap(){
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }
    
    public GladLibMap(String source){
        initializeFromSource(source);
        myRandom = new Random();
    }
    
    private void initializeFromSource(String source) {
        String[] words = {"adjective","noun", "color", "country", "name", "animal", "timeframe","verb","fruit"};
        myMap = new HashMap<String, ArrayList<String>>();
        for(String word : words){
            myMap.put(word,readIt(source+"/"+word+".txt"));
        }    
        seenWordsList = new ArrayList<String>();
        seenLabelsList = new ArrayList<String>();
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    //get substitute word from myMap, and add label to seenLabelsList
    private String getSubstitute(String label) {
        if(seenLabelsList.indexOf(label)==-1){
            seenLabelsList.add(label);
        }
        if(myMap.containsKey(label)){
            return randomFrom(myMap.get(label));
        }
        else if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }         
        else{
            return "**UNKNOWN**";
        }
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        while(seenWordsList.indexOf(sub) != -1){
            sub = getSubstitute(w.substring(first+1,last));
        }
        seenWordsList.add(sub);
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    //This method returns the total number of words in all the ArrayLists in the HashMap.
    private int totalWordsInMap(){
        int totalWords = 0;
        for(String key : myMap.keySet()){
            for(String word : myMap.get(key)){
                ++totalWords;
            }
        }
        return totalWords;
    }
    //. This method returns the total number of words in the ArrayLists of the categories that were used for a particular GladLib.
    private int totalWordsConsidered(){
        int totalWords = 0;
        for(String key : myMap.keySet()){
            if(seenLabelsList.indexOf(key)!=-1){
                for(String word : myMap.get(key)){
                    ++totalWords;
                }
            }
        }
        return totalWords;        
    }    
    
    public void makeStory(){
        System.out.println("\n");
        String story = fromTemplate("datalong/madtemplate2.txt");
        printOut(story, 60);
        System.out.println("\nTotal number of words replaced: "+seenWordsList.size());
        System.out.println("\nTotal number of words to pick from: "+totalWordsInMap());
        System.out.println("\nTotal number of words considered based on labels: "+totalWordsConsidered());
    }
}
