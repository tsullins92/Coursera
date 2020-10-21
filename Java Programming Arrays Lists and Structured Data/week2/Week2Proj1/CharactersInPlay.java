
/**
 * Write a description of CharactersInPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.ArrayList;

public class CharactersInPlay {
    private ArrayList<String> myCharacters;
    private ArrayList<Integer> myFreqs;
    
    public CharactersInPlay() {
        myCharacters = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }

    public void update(String person){

        int index = myCharacters.indexOf(person);
        if(index == -1){
            myCharacters.add(person);
            myFreqs.add(1);
        } else{
            int freq = myFreqs.get(index);
            myFreqs.set(index,freq+1);
        }

    }    
    
    
    public void findAllCharacters(){
        myCharacters.clear();
        myFreqs.clear();
        FileResource resource = new FileResource();
        
        for(String line : resource.lines()){
            line = line.toLowerCase();
            int index = line.indexOf('.');
            if(index != -1){
                String person = line.substring(0,index);
                update(person);
            }
        }
    }
    
    public void charactersWithNumParts(int num1, int num2){
        for(int i = 0; i < myCharacters.size(); ++i){
            if((myFreqs.get(i)>=num1)&&(myFreqs.get(i)<=num2)){
                System.out.println(myCharacters.get(i));            
            }
        }
    }
    
    public int findIndexOfMax(){
        int max = myFreqs.get(0);
        int maxIndex = 0;
        for(int k=0; k < myFreqs.size(); k++){
            if (myFreqs.get(k) > max){
                max = myFreqs.get(k);
                maxIndex = k;
            }
        }
        return maxIndex;
    }    
    
    public void tester(){
        findAllCharacters();
        for(int i = 0; i < myCharacters.size(); ++i){
            if(myFreqs.get(i)>1){
                System.out.println(myCharacters.get(i)+" "+myFreqs.get(i));
            }
        }
        System.out.println("charactersWithNumParts(10,15)");
        charactersWithNumParts(10,15);
        int maxIndex = findIndexOfMax();
        System.out.println("Character with most parts: "+myCharacters.get(maxIndex)+" "+myFreqs.get(maxIndex));
    }
    
    
    
    
    
    
    
    
    
    
    
}
