
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {
    public int findStopCodon(String dnaStr, 
                                int startIndex,
                                String stopCodon){
        int currIndex = dnaStr.indexOf(stopCodon, startIndex + 3);
        while(currIndex != -1){
            int diff = currIndex - startIndex;
            if(diff % 3 == 0){
                return currIndex;
            }
            else{
                currIndex = dnaStr.indexOf(stopCodon, currIndex + 1);
            }
        }
        return -1;
    }

    public String findGene(String dna, int where){
        int startIndex = dna.indexOf("ATG", where);
        if(startIndex == -1){
            return "";
        }
        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");
        //int temp = Math.min(taaIndex,tagIndex);
        //int minIndex = Math.min(temp, tgaIndex);
        int minIndex = 0;
        if(taaIndex == -1 || 
          (tgaIndex != -1 && tgaIndex < taaIndex)){
             minIndex = tgaIndex; 
        }
        else{
            minIndex = taaIndex;
        }
        if(minIndex == -1 ||
          (tagIndex != -1 && tagIndex < minIndex)){
             minIndex = tagIndex;  
        }
        if(minIndex == -1){
            return "";
        }
        return dna.substring(startIndex, minIndex + 3);
    }    
    
    public void printAllGenes(String dna){
        int startIndex = 0;
        while(true){
            String currentGene = findGene(dna, startIndex);
            if(currentGene.isEmpty()){
                break;
            }
            System.out.println(currentGene);
            startIndex = dna.indexOf(currentGene, startIndex) + 
                currentGene.length();
        }
    }
    
    public void testOn(String dna){
        System.out.println("Testing printAllGenes on " + dna);
        printAllGenes(dna);
    }
    
    public void test(){
        testOn("ATGATCTAATTAATGCTGCAACGGTGAAGA");
        testOn("");
        testOn("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
    }
 
    
    public void testFindStopCodon(){
        String dna1= "ATGGCGTAGCCCTAA";
        System.out.println("DNA is " + dna1);
        System.out.println("stopCodon is " + findStopCodon(dna1, 0, "TAA"));
        String dna2= "ATCGCGTAGCCCTAA";
        System.out.println("DNA is " + dna2);
        System.out.println("stopCodon is " + findStopCodon(dna2, 0, "TAG"));
        String dna3= "ATGGCGTGACCCTAG";
        System.out.println("DNA is " + dna3);
        System.out.println("stopCodon is " + findStopCodon(dna3, 0, "TGA"));
        String dna4= "ATGCGTAGCCCTAA";
        System.out.println("DNA is " + dna4);
        System.out.println("stopCodon is " + findStopCodon(dna4, 0, "TGA"));
        String dna5= "ATCGCGATGCCCTAG";
        System.out.println("DNA is " + dna5);
        System.out.println("stopCodon is " + findStopCodon(dna5, 6, "TAA"));
    }  
    
}
