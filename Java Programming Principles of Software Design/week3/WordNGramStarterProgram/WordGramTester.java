
/**
 * Write a description of WordGramTester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class WordGramTester {

    public void testWordGram(){
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        int size = 4;
        for(int index = 0; index <= words.length - size; ++index){
            WordGram wg = new WordGram(words, index, size);
            System.out.println(index+"\t"+wg.length()+"\t"+wg);
        }
    }
    
    public void testWordGramEquals(){
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        ArrayList<WordGram> list = new ArrayList<WordGram>();
        int size = 4;
        for(int index = 0; index <= words.length - size; ++index){
            WordGram wg = new WordGram(words,index,size);
            list.add(wg);
        }
        WordGram first = list.get(0);
        System.out.println("checking "+first);
        for(int i = 0; i < list.size(); ++i){
            if(first.equals(list.get(i))){
                System.out.println("matched at "+i+" "+list.get(i));
            }
        }
    } 
    
    public void testWordGramShiftAdd(){
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        int size = 4;
        WordGram wg = new WordGram(words, 0, size);
        WordGram shifted = wg.shiftAdd("yes");
        System.out.println("Initial\t"+wg);
        System.out.println("Shifted\t"+shifted);
    }    
    
    
}
