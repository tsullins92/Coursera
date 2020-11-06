
/**
 * Write a description of TestCaesarCypher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class TestCaesarCypher {

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
    
// Write the method breakCaesarCipher that has one String parameter named input. This method should figure out which key was used to encrypt this message 
// (in a similar manner as the previous lesson), then create a CaesarCipher object with that key and decrypt the message.
    
    public String breakCaesarCipher(String input){
        int dKey = getKey(input);
        CaesarCipher cc = new CaesarCipher(dKey); 
        return cc.decrypt(input);    
    }

// Write the void method simpleTests that has no parameters. This method should read in a file as a String, create a CaesarCipher object with key 18, 
// encrypt the String read in using the CaesarCipher object, print the encrypted String, and decrypt the encrypted String using the decrypt method.    
    public void simpleTests(){
        FileResource fr = new FileResource();
        String contents = fr.asString(); 
        CaesarCipher cc = new CaesarCipher(18);
        String encryptedContents = cc.encrypt(contents);
        System.out.println("encryptedContents: "+encryptedContents);
        String decryptedContents = cc.decrypt(encryptedContents);
        System.out.println("decryptedContents: "+decryptedContents);
        System.out.println("breakCaesarCipher(encryptedContents): "+breakCaesarCipher(encryptedContents));
    }
    
}
