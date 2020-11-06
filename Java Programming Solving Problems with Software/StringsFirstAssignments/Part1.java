
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {
    public String findSimpleGene(String dna){
        String result = "";
        int start = dna.indexOf("ATG");
        if(start == -1){
            return result;
        }
        int end = dna.indexOf("TAA",start+3);
        if(end == -1){
            return result;
        }
        int multiple = (end-3) % 3;
        if(multiple != 0){
            return result;
        }
        result = dna.substring(start, end+3);
        return result;
    }
    
    public void testSimpleGene(){
        String dna1= "ATGGCGTAGCCCTAA";
        System.out.println("DNA is " + dna1);
        System.out.println("Gene is " + findSimpleGene(dna1));
        String dna2= "ATCGCGTAGCCCTAA";
        System.out.println("DNA is " + dna2);
        System.out.println("Gene is " + findSimpleGene(dna2));
        String dna3= "ATGGCGTAGCCCTAG";
        System.out.println("DNA is " + dna3);
        System.out.println("Gene is " + findSimpleGene(dna3));
        String dna4= "ATGCGTAGCCCTAA";
        System.out.println("DNA is " + dna4);
        System.out.println("Gene is " + findSimpleGene(dna4));
        String dna5= "ATCGCGTAGCCCTAG";
        System.out.println("DNA is " + dna5);
        System.out.println("Gene is " + findSimpleGene(dna5));        
    }
}
