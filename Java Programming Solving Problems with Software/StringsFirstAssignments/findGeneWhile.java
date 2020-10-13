
/**
 * Write a description of findGeneWhile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class findGeneWhile {
    
    public String findGene(String dna){
        int startIndex = dna.indexOf("ATG");
        int currIndex = dna.indexOf("TAA", startIndex + 3);
        while(currIndex != -1){
            if((currIndex - startIndex) % 3 == 0){
                return dna.substring(startIndex, currIndex + 3);
            }
            else{
                currIndex = dna.indexOf("TAA", currIndex + 3);
            }
        }
        return "";
    }
   
    public void testFindGene(){
        String dna1= "ATGGCGTAGCCCTAA";
        System.out.println("DNA is " + dna1);
        System.out.println("Gene is " + findGene(dna1));
        String dna2= "ATCGCGTAGCCCTAA";
        System.out.println("DNA is " + dna2);
        System.out.println("Gene is " + findGene(dna2));
        String dna3= "ATGGCGTAGCCCTAG";
        System.out.println("DNA is " + dna3);
        System.out.println("Gene is " + findGene(dna3));
        String dna4= "ATGCGTAGCCCTAA";
        System.out.println("DNA is " + dna4);
        System.out.println("Gene is " + findGene(dna4));
        String dna5= "ATCGCGTAGCCCTAG";
        System.out.println("DNA is " + dna5);
        System.out.println("Gene is " + findGene(dna5));        
    }    
    
}
