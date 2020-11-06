
/**
 * Write a description of TestCaesarCypherTwo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class TestCaesarCypherTwo {
    public int getKey(String s){
        int freqs[] = countLetters(s);
        int maxIdx = maxIndex(freqs);
        int dKey = maxIdx - 4;
        if(maxIdx < 4){
            dKey = 26 - (4 - maxIdx);
        }
        return dKey;
    }
    
    public int[] countLetters(String input){
        int[] counts = new int[26];
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for(int i = 0; i < input.length(); ++i){
            int idx = alphabet.indexOf(Character.toUpperCase(input.charAt(i)));
            if(idx != -1){
                ++counts[idx];
            }
        }
        return counts;
    }
    
    public int maxIndex(int[] freqs){
        int maxIdx = 0;
        int maxCount = 0;
        for(int i = 0; i<freqs.length; ++i){
            if(freqs[i] > maxCount){
                maxIdx = i;
                maxCount = freqs[i];
            }
        }
        return maxIdx;
    }  
    
    public String halfOfString(String message, int start){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i<message.length(); ++i){
            if(i % 2 == start){
                output.append(message.charAt(i));
            }
        }
        return output.toString();
    }    
    
    public String breakCaesarCipher(String input){
        String half1 = halfOfString(input,0);
        String half2 = halfOfString(input,1);
        int dKey1 = getKey(half1);
        int dKey2 = getKey(half2);
        System.out.println(dKey1);
        System.out.println(dKey2);
        CaesarCipherTwo cc = new CaesarCipherTwo(dKey1,dKey2); 
        return cc.decrypt(input);    
    }   
        
    // Write the void method simpleTests that has no parameters. This method should read in a file as a String, create a CaesarCipherTwo object with keys 17 and 3, encrypt the String using the 
    // CaesarCipherTwo object, print the encrypted String, and decrypt the encrypted String using the decrypt method.
    public void simpleTests(){
        FileResource fr = new FileResource();
        String contents = fr.asString(); 
        // String contents = "Can you imagine life WITHOUT the internet AND computers in your pocket?"; 
        // CaesarCipherTwo cc = new CaesarCipherTwo(14,24);
        // String encryptedContents = cc.encrypt(contents);
        // System.out.println("encryptedContents: "+encryptedContents);
        // String decryptedContents = cc.decrypt("Hfs cpwewloj loks cd Hoto kyg Cyy.");
        // System.out.println("decryptedContents: "+decryptedContents);
        System.out.println("breakCaesarCipher(encryptedContents): "+breakCaesarCipher(contents));
    }    
    
}
