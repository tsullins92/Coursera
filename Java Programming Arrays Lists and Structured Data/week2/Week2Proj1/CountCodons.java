
/**
 * Write a description of CountCodons here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
public class CountCodons {
    
    private HashMap<String,Integer> codonCountMap;
    
    public CountCodons(){
        codonCountMap = new HashMap<String,Integer>();
    }
    //build a codonCountMap given start between 0-2 and a string of DNA
    private void buildCodonMap(int start, String dna){
        codonCountMap.clear();
        for(int i = start; i < dna.length()-3; i+=3){
            String codon = dna.substring(i,i+3).toUpperCase();
            if (!codonCountMap.containsKey(codon)){
                codonCountMap.put(codon,1);
            }
            else {
                codonCountMap.put(codon,codonCountMap.get(codon)+1);
            }            
        }
    }
    //Print the codon that occurs the most
    private String getMostCommonCodon(){
        int maxCount = 0;
        String maxCountCodon = null;
        for(String codon : codonCountMap.keySet()){
            int value = codonCountMap.get(codon);
            if (value > maxCount){
                maxCount = value;
                maxCountCodon = codon;
            }
        }    
        return maxCountCodon;
    }
    //print all codons with counts between start and end inclusive
    private void printCodonCounts(int start, int end){
        for(String codon : codonCountMap.keySet()){
            int value = codonCountMap.get(codon);
            if ((value >= start)&&(value <= end)){
                System.out.println(codon+": "+value);
            }
        }           
    }
    
    public void test(){
        FileResource fr = new FileResource();
        String dna = fr.asString().trim();
        CountCodons cc = new CountCodons();
        for(int i = 0; i < 3; ++i){
            cc.buildCodonMap(i,dna);
            System.out.println("Reading Frame "+i);
            System.out.println("Unique Codons: "+cc.codonCountMap.size());
            String mostCommon = cc.getMostCommonCodon();
            System.out.println("Most common codon: "+mostCommon+" --- "+cc.codonCountMap.get(mostCommon));
            int start = 7;
            int end = 7;
            System.out.println("Codons with counts between "+start+" and "+end+" inclusive:");
            cc.printCodonCounts(start,end);
        }
    }
}
