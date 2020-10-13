
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
    public String findSimpleGene(String dna, String startCodon, String stopCodon){
        String result = "";
        if(Character.isUpperCase(dna.charAt(0))){
            startCodon = startCodon.toUpperCase();
            stopCodon = stopCodon.toUpperCase();
            dna = dna.toUpperCase();
        }else{
            startCodon = startCodon.toLowerCase();
            stopCodon = stopCodon.toLowerCase();
            dna = dna.toLowerCase();            
        }
        int start = dna.indexOf(startCodon);
        if(start == -1){
            return result;
        }
        int end = dna.indexOf(stopCodon,start+3);
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
        System.out.println("Gene is " + findSimpleGene(dna1,"ATG","TAA"));
        String dna2= "ATCGCGTAGCCCTAA";
        System.out.println("DNA is " + dna2);
        System.out.println("Gene is " + findSimpleGene(dna2,"ATG","TAA"));
        String dna3= "ATGGCGTAGCCCTAG";
        System.out.println("DNA is " + dna3);
        System.out.println("Gene is " + findSimpleGene(dna3,"ATG","TAA"));
        String dna4= "ATGCGTAGCCCTAA";
        System.out.println("DNA is " + dna4);
        System.out.println("Gene is " + findSimpleGene(dna4,"ATG","TAA"));
        String dna5= "ATCGCGTAGCCCTAG";
        System.out.println("DNA is " + dna5);
        System.out.println("Gene is " + findSimpleGene(dna5,"ATG","TAA"));        
    }
}
