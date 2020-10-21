
/**
 * Write a description of WordLengths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class WordLengths {
//Write a void method countWordLengths that has two parameters, a FileResource named resource and an integer array named counts. This method should read in the words
// from resource and count the number of words of each length for all the words in resource, storing these counts in the array counts.
    public void countWordLengths(FileResource resource, int[] counts){
	int countsLength = counts.length; 
	for(String s : resource.words()){
	   int sLength = s.length();
	   if(!Character.isLetter(s.charAt(sLength-1))){
	       --sLength;
	   }
	   if(!Character.isLetter(s.charAt(0))){
	       --sLength;
	   }
	   if(sLength >= countsLength - 1){
	       sLength = countsLength - 1;
	   }
	   if(sLength < 0){
	       sLength = 0;
	   }
	   ++counts[sLength];   
        }
	return;        
    }

//Write a void method testCountWordLengths that creates a FileResource so you can select a file, and creates a counts integer array of size 31. This method should 
//call countWordLengths with a file and then print the number of words of each length. Test it on the small file smallHamlet.txt shown below.
    public void testCountWordLengths(){
        FileResource fr = new FileResource();
        int[] counts = new int[31];
        System.out.println("Testing smallHamlet.txt: ");
        countWordLengths(fr, counts);
        for(int i = 0;i<counts.length;++i){
             System.out.println("counts["+i+"]: "+counts[i]);
        }
    }
}
