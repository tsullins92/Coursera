
/**
 * Write a description of CaesarCipherTwo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class CaesarCipherTwo {

    private String alphabet;
    private String shiftedAlphabet1;
    private String shiftedAlphabet2;
    private int mainKey1;
    private int mainKey2;
    
    public CaesarCipherTwo(int key1, int key2){
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet1 = alphabet.substring(key1)+alphabet.substring(0,key1);
        shiftedAlphabet2 = alphabet.substring(key2)+alphabet.substring(0,key2);
        mainKey1 = key1;
        mainKey2 = key2;
    }
    
    public String encrypt(String input){
        //Make a StringBuilder with message (encrypted)
        StringBuilder encrypted = new StringBuilder(input);

        //Count from 0 to < length of encrypted, (call it i)
        for(int i = 0; i < encrypted.length(); i++) {
            //Look at the ith character of encrypted (call it currChar)
            char currChar = encrypted.charAt(i);
            //Find the index of currChar in the alphabet (call it idx)
            int idx = alphabet.indexOf(Character.toUpperCase(currChar));
            //If currChar is in the alphabet
            if(idx != -1){
                char newChar;
                if(i % 2 == 0){
                    newChar = shiftedAlphabet1.charAt(idx);
                } else{
                    newChar = shiftedAlphabet2.charAt(idx);
                }
                //check if currChar is uppercase
                boolean charIsUpper = Character.isUpperCase(currChar);
                //if currchar is uppercase, set it to uppercase newChar
                if(charIsUpper){
                    encrypted.setCharAt(i, newChar);
                }
                //else set it to lowercase newChar
                else{
                    encrypted.setCharAt(i, Character.toLowerCase(newChar));
                }
            }
            //Otherwise: do nothing
        }
        //Your answer is the String inside of encrypted
        return encrypted.toString();        
    }
    
    public String decrypt(String input){
        CaesarCipherTwo cc = new CaesarCipherTwo(26 - mainKey1, 26-mainKey2);
        return cc.encrypt(input);       
    }      
    
}
    
    
    