
/**
 * Write a description of WordsInFiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
import java.io.File;
public class WordsInFiles {
    
    private HashMap<String, ArrayList<String>> wordsFilesMap;
    
    public WordsInFiles(){
        wordsFilesMap = new HashMap<String, ArrayList<String>>();
    }
    //add all words from file as keys to wordsFilesMap. Also add/append file names to wordsFilesMap using words as keys 
    private void addWordsFromFile(File f){
        FileResource fr = new FileResource(f);
        for(String word : fr.words()){
            if(wordsFilesMap.containsKey(word)){
                ArrayList fileList = wordsFilesMap.get(word);
                if(fileList.indexOf(f.getName())==-1){
                    fileList.add(f.getName());
                    wordsFilesMap.put(word,fileList);                
                }
            } else{
                ArrayList fileList = new ArrayList();
                fileList.add(f.getName());
                wordsFilesMap.put(word,fileList);
            }
        }
    }
    //loop through files in directory, calling addWordsFromFile() on them
    private void buildWordFileMap(){
        wordsFilesMap.clear();
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            addWordsFromFile(f);
        }
    }
    //get maximum number of files that any word appears in
    private int maxNumber(){
        int maxNumber = 0;
        for(String word : wordsFilesMap.keySet()){
            int numberOfFiles = wordsFilesMap.get(word).size();
            if(numberOfFiles > maxNumber){
                maxNumber = numberOfFiles;
            }
        }
        return maxNumber;
    }
    //This method returns an ArrayList of words that appear in exactly number files.
    private ArrayList<String> wordsInNumFiles(int number){
        ArrayList<String> words = new ArrayList<String>();
        for (String word : wordsFilesMap.keySet()){
            int numberOfFiles = wordsFilesMap.get(word).size();
            if(numberOfFiles == number){
                words.add(word);
            }            
        }
        return words;
    }
    //This method prints the names of the files this word appears in, one filename per line. 
    public void printFilesIn(String word){
        ArrayList<String> fileNames = wordsFilesMap.get(word);
        for(String fileName : fileNames){
            System.out.println(fileName);
        }
    }
    
    public void tester(){
        buildWordFileMap();
        int maxNumber = maxNumber();
        System.out.println("tree appears in these files: ");
        printFilesIn("tree");
        System.out.println("Max Number of files any word appears in: "+maxNumber);
        System.out.println(wordsInNumFiles(4).size()+" Words appear in "+4+" files:");
        for(String word : wordsInNumFiles(6)){
            System.out.println(word+" appears in these files:");
            printFilesIn(word);
        }
    }
    
}
