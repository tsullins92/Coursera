
/**
 * Write a description of WordGram here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class WordGram {

        private String[] myWords;
        
        public WordGram(String[] source, int start, int size){
            myWords = new String[size];
            System.arraycopy(source, start, myWords, 0, size);
        }
        
        public String wordAt(int index){
            if(index < 0 || index >= myWords.length){
                throw new IndexOutOfBoundsException("bad index " + index);
            }
            return myWords[index];
        }
        
        public int length(){
            return myWords.length;
        }
        
        public String toString(){
            String text = "";
            for(int i = 0; i < myWords.length; i++){
                text += myWords[i] + " ";
            }
            return text.trim();
        }
        
        public boolean equals(Object o){
            WordGram other = (WordGram) o;
            if((length() != other.length()) || (!toString().equals(other.toString()))) return false;
            return true;
        }
        
        public WordGram shiftAdd(String word){
            List<String> listSource = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(myWords, 1, length())));
            listSource.add(word);
            String[] arraySource = new String[listSource.size()];
            arraySource = listSource.toArray(arraySource);
            WordGram ret = new WordGram(arraySource,0,arraySource.length);
            return ret;
        }
        
        public int hashCode(){
            return toString().hashCode();
        }
    
}
