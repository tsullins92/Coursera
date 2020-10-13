import edu.duke.*;
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
        String dnaLower = dna.toLowerCase();
        int startIndex = dnaLower.indexOf("atg", where);
        if(startIndex == -1){
            return "";
        }
        int taaIndex = findStopCodon(dnaLower, startIndex, "taa");
        int tagIndex = findStopCodon(dnaLower, startIndex, "tag");
        int tgaIndex = findStopCodon(dnaLower, startIndex, "tga");
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

    public StorageResource getAllGenes(String dna){
        int startIndex = 0;
        StorageResource geneList = new StorageResource();
        while(true){
            String currentGene = findGene(dna, startIndex);
            if(currentGene.isEmpty()){
                break;
            }
            geneList.add(currentGene);
            startIndex = dna.indexOf(currentGene, startIndex) + 
                currentGene.length();
        }
        return geneList;
    }    
    
    //Write a method countCTG that has one String parameter dna, and returns the number of times the codon CTG appears in dna.    
    public int countCTG(String dna){
        String dnaLower = dna.toLowerCase();
        int ctgCount = 0;
        int ctgIndex = -1; //set to -1 so that first indexOf() call takes a 0
        while(true){
            ctgIndex = dnaLower.indexOf("ctg", ctgIndex + 1);
            if(ctgIndex == -1){
                break;
            }
            ++ctgCount;
        }
        return ctgCount;
    }
    
    public double cgRatio(String dna){
        String dnaLower = dna.toLowerCase();
        int cCount = 0;
        int cIndex = -1; //set to -1 so that first indexOf() call takes a 0
        while(true){
            cIndex = dnaLower.indexOf("c", cIndex + 1);
            if(cIndex == -1){
                break;
            }
            ++cCount;
        }
        int gCount = 0;
        int gIndex = -1; //set to -1 so that first indexOf() call takes a 0
        while(true){
            gIndex = dnaLower.indexOf("g", gIndex + 1);
            if(gIndex == -1){
                break;
            }
            ++gCount;
        }
        double cgRatio = (double)cCount/(double)gCount;
        return cgRatio;
    }
    
    // Write the void method processGenes that has one parameter sr, which is a StorageResource of strings. This method processes all the strings in sr to 
    // find out information about them. Specifically, it should:
    // print all the Strings in sr that are longer than 9 characters
    // print the number of Strings in sr that are longer than 9 characters
    // print the Strings in sr whose C-G-ratio is higher than 0.35
    // print the number of strings in sr whose C-G-ratio is higher than 0.35
    // print the length of the longest gene in sr
    
    public void processGenes(StorageResource sr){
        int strSixtyCt = 0;
        System.out.println("Length > 60: ");
        for(String s : sr.data()){
            if(s.length() > 60){
                System.out.println(s);
                ++strSixtyCt;
            }
        }
        System.out.println("Number of genes with count > 60 --- " + strSixtyCt);
        int cgRatCt = 0;
        int longestGeneLth = 0;
        System.out.println("c/g Ratio > 0.35: ");
        for(String s : sr.data()){
            double cgRat = cgRatio(s);
            if(cgRat > 0.35){
                System.out.println(s);
                ++cgRatCt;
            }
            if(s.length() > longestGeneLth){
                longestGeneLth = s.length();
            }
        }
        System.out.println("Number of genes with c/g ratio > 0.35 --- " + cgRatCt);
        System.out.println("Longest Gene Length --- " + longestGeneLth);
        System.out.println("");
    }    
    
    // Write a method testProcessGenes. This method will call your processGenes method on different test cases. Think of five DNA strings to use as test cases. 
    // These should include: one DNA string that has some genes longer than 9 characters, one DNA string that has no genes longer than 9 characters, one DNA 
    // string that has some genes whose C-G-ratio is higher than 0.35, and one DNA string that has some genes whose C-G-ratio is lower than 0.35. Write code in 
    // testProcessGenes to call processGenes five times with StorageResources made from each of your five DNA string test cases.
    
    public void testProcessGenes(){
        FileResource fr = new FileResource("testDNA.fa");
        String dna = fr.asString(); 
        System.out.println(dna);
        StorageResource genes = getAllGenes(dna);
        System.out.println("Number of genes --- " + genes.size());
        processGenes(genes);
        // genes = getAllGenes("ATGATCGGGCGTTGCAGCGGGTAATTAATGCTGCAACGGTGAAGA"); //Some genes longer than 9 characters
        // processGenes(genes);
        // genes = getAllGenes("");
        // processGenes(genes);
        // genes = getAllGenes("ATGATCTAGGCCATGGTCTAG"); //No genes longer than 9 characters
        // processGenes(genes);
        // genes = getAllGenes("ATGCCCATCTCCTAGCCCATCCTCTAG"); //Some genes whose C-G-ratio is higher than 0.35
        // processGenes(genes);
        // genes = getAllGenes("ATGGGGATGTGGTAGGGGATGGTCTAG"); //Some genes whose C-G-ratio is lower than 0.35
        // processGenes(genes);
    }
    
    public void testOn(){
        FileResource fr = new FileResource("testDNA.fa");
        String dna = fr.asString(); 
        System.out.println("Testing printAllGenes on " + dna);
        System.out.println("CTG Count --- " + countCTG(dna));
        //printAllGenes(dna);
        StorageResource genes = getAllGenes(dna);
        for(String gene : genes.data()){
            System.out.println("Gene --- " + gene);
            System.out.println("CG Ratio --- " + cgRatio(gene));
        }
        
    }
    
    public void test(){
        // testOn("ATGATCTAATTAATGCTGCAACGGTGAAGA");
        // testOn("");
        // testOn("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
        // testOn("CTGATCATAAGAAGATACTGGAGGGCCATGCTG");
    }
 
}
