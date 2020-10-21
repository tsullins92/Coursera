
/**
 * Write a description of CaesarBreaker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class CaesarBreaker {
//Write the method halfOfString in the CaesarBreaker class that has two parameters, a String parameter named message and an int parameter named start. This method 
//should return a new String that is every other character from message starting with the start position. For example, the call halfOfString(“Qbkm Zgis”, 0) returns
//the String “Qk gs” and the call halfOfString(“Qbkm Zgis”, 1) returns the String “bmZi”. Be sure to test this method with a small example.
    public String halfOfString(String message, int start){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i<message.length(); ++i){
            if(i % 2 == start){
                output.append(message.charAt(i));
            }
        }
        return output.toString();
    }
    
//Write the method getKey in the CaesarBreaker class that has one parameter, a String s. This method should call countLetters to get an array of the letter frequencies 
//in String s and then use maxIndex to calculate the index of the largest letter frequency, which is the location of the encrypted letter ‘e’, which leads to the key, 
//which is returned.    
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
    
//Write the method decryptTwoKeys in the CaesarBreaker class that has one parameter, a String parameter named encrypted that represents a String that was encrypted with 
//the two key algorithm discussed in the previous lesson. This method attempts to determine the two keys used to encrypt the message, prints the two keys, and then returns 
//the decrypted String with those two keys. More specifically, this method should:    
    // public String decryptTwoKeys(String encrypted){
        // CaesarCipher cc = new CaesarCipher();
        // String encrypted1 = halfOfString(encrypted, 0);
        // String encrypted2 = halfOfString(encrypted, 1);
        // int key1 = getKey(encrypted1);
        // int key2 = getKey(encrypted2);
        // System.out.println("key1: " + key1);
        // System.out.println("key2: " + key2);
        // String decrypted = cc.encryptTwoKeys(encrypted, 26-key1, 26-key2);
        // return decrypted;
    // };    

    // public void testDecryptTwoKeys(){
        // CaesarCipher cc = new CaesarCipher();
        // int key1 = 8;
        // int key2 = 21;
        // FileResource fr = new FileResource();
        // String message = fr.asString();
        // String encrypted = cc.encryptTwoKeys(message, key1,key2);
        // System.out.println(encrypted);
        // String decrypted = decryptTwoKeys(message);
        // System.out.println(decrypted);    
    // }    
    
    // public String decrypt(String encrypted){
        // CaesarCipher cc = new CaesarCipher(); 
        // int dKey = getKey(encrypted);
        // return cc.encrypt(encrypted,26-dKey);
       // }
// //Write a testDecrypt method in the CaesarBreaker class that prints the decrypted message, and make sure it works for encrypted messages that were encrypted with one key.    
    // public void testDecrypt(){
        // CaesarCipher cc = new CaesarCipher();
        // int key = 15;
        // FileResource fr = new FileResource();
        // String message = fr.asString();
        // String encrypted = cc.encrypt(message, key);
        // String decrypted = decrypt(encrypted);
        // System.out.println("Encrypted: "+encrypted);
        // System.out.println("Decrypted: "+decrypted);
    // }   
       
}
