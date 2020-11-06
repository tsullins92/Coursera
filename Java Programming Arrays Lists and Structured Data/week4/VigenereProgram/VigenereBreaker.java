import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //REPLACE WITH YOUR CODE
        StringBuilder sb = new StringBuilder();
        for(int i = whichSlice; i < message.length(); i+=totalSlices){
            sb.append(message.charAt(i));
        }
        return sb.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        //WRITE YOUR CODE HERE
        for(int i = 0; i<klength;++i){
            String encryptedSlice = sliceString(encrypted,i,klength);
            CaesarCracker cracker = new CaesarCracker();
            int tempKey = cracker.getKey(encryptedSlice);
            key[i]=tempKey;
        }
        return key;
    }

    public void breakVigenere () {
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        DirectoryResource dictDR = new DirectoryResource();
        HashMap<String, HashSet<String>> languages = new HashMap<String,HashSet<String>>();
        for(File dictFile : dictDR.selectedFiles()){
            String language = dictFile.getName();
            FileResource dictFR = new FileResource(dictFile);
            HashSet<String> dictionary = readDictionary(dictFR);
            languages.put(language, dictionary);
        }
        String decrypted = breakForAllLangs(encrypted, languages);
        System.out.println(decrypted);
    }
    //The method should then return the HashSet representing the words in a dictionary
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> dictionary = new HashSet<String>();
        for(String word : fr.lines()){
            dictionary.add(word.toLowerCase());
        }
        return dictionary;
    }
    //return the integer count of how many valid words it found by splitting the message and comparing it to dictionary
    public int countWords(String message, HashSet<String> dictionary){
        int count = 0;
        for(String word : message.split("\\W+")){
            word = word.toLowerCase();
            if(dictionary.contains(word)){
                ++count;
            }
        }
        return count;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary){
        int highestCount = 0;
        String bestDecryption = null;
        int keyLength = 0;
        int[] finalKey = {};
        char mostCommonLetter = mostCommonCharIn(dictionary);
        for(int i = 1; i <= 100; ++i){
            int[] key = tryKeyLength(encrypted,i,mostCommonLetter);
            VigenereCipher vc = new VigenereCipher(key);
            String decrypted = vc.decrypt(encrypted);
            int realWords = countWords(decrypted, dictionary);
            if(realWords > highestCount){
                highestCount = realWords;
                bestDecryption = decrypted;
                keyLength = i;
                finalKey = key;
            }
        }
        System.out.println("valid words: "+highestCount);
        System.out.println("keyLength: "+keyLength);
        System.out.println("finalKey: ");
        for(int i : finalKey){
            System.out.println(i);
        }
        return bestDecryption;
    }
    //find out which character, of the letters in the English alphabet, appears most often in the words in dictionary. It should return this most commonly occurring character.
    public char mostCommonCharIn(HashSet<String> dictionary){
        HashMap<Character, Integer> letterCounts = new HashMap<Character, Integer>();
        for(String word : dictionary){
            for(int i = 0; i<word.length();++i){
                char letter = word.charAt(i);
                if(!letterCounts.containsKey(letter)){
                    letterCounts.put(letter,1);
                }else{
                    int count = letterCounts.get(letter);
                    letterCounts.put(letter, count+1);
                }
            }
        }
        int maxCount = 0;
        char mostCommon = ' ';
        for(char letter : letterCounts.keySet()){
            int count = letterCounts.get(letter);
            if(count > maxCount){
                maxCount = count;
                mostCommon = letter;
            }
        }
        return mostCommon;
    }
    
    public String breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages){
        int highestCount = 0;
        String bestDecryption = null;
        String bestLanguage = null;
        for(String language : languages.keySet()){
            System.out.println(language+": ");
            String decrypted = breakForLanguage(encrypted, languages.get(language));
            int realWords = countWords(decrypted, languages.get(language));
            if(realWords > highestCount){
                highestCount = realWords;
                bestDecryption = decrypted;
                bestLanguage = language;
            }
        }

        System.out.println("valid words: "+highestCount);
        System.out.println("best language: "+bestLanguage);
        return bestDecryption;
    }    
    
    public void test(){
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        FileResource dictFR = new FileResource();
        HashSet<String> dictionary = readDictionary(dictFR);        
        int highestCount = 0;
        String bestDecryption = null;
        int keyLength = 0;
        int[] finalKey = {};
        int[] key = tryKeyLength(encrypted,38,'e');
        VigenereCipher vc = new VigenereCipher(key);
        String decrypted = vc.decrypt(encrypted);
        int realWords = countWords(decrypted, dictionary);
        highestCount = realWords;
        bestDecryption = decrypted;
        keyLength = 38;
        finalKey = key;
        System.out.println("valid words: "+highestCount);
        System.out.println("keyLength: "+keyLength);
        System.out.println("finalKey: ");
        for(int i : finalKey){
            System.out.println(i);
        }
        System.out.println(bestDecryption);
    }    
}
