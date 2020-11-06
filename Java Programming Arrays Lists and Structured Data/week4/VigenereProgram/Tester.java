
/**
 * Write a description of Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
public class Tester {

    public void testCaesarCipher(){
        CaesarCipher cc = new CaesarCipher(2);
        FileResource fr = new FileResource();
        String message = fr.asString();
        String encryptedMessage = cc.encrypt(message);
        String decryptedMessage = cc.decrypt(encryptedMessage);
        System.out.println("message: "+message);
        System.out.println("encryptedMessage: "+encryptedMessage);
        System.out.println("decryptedMessage: "+decryptedMessage);
    }
    
        public void testCaesarCracker(){
        CaesarCracker cracker = new CaesarCracker('e');
        FileResource fr = new FileResource();
        String message = fr.asString();
        int key = cracker.getKey(message);
        CaesarCipher cipher = new CaesarCipher(key);
        String decryptedMessage = cipher.decrypt(message);
        System.out.println("key: "+key);
        System.out.println("message: "+message);
        System.out.println("decryptedMessage: "+decryptedMessage);
    }
    
    public void testVigenereCipher(int[] key){
        VigenereCipher vc = new VigenereCipher(key);
        FileResource fr = new FileResource();
        String message = fr.asString();
        String encryptedMessage = vc.encrypt(message);
        String decryptedMessage = vc.decrypt(encryptedMessage);
        System.out.println("message: "+message);
        System.out.println("encryptedMessage: "+encryptedMessage);
        System.out.println("decryptedMessage: "+decryptedMessage);
    }    
    
    public void testVigenereBreaker(String message, int whichSlice, int totalSlices){
        VigenereBreaker vb = new VigenereBreaker();
        System.out.println("sliceString("+message+", "+whichSlice+", "+totalSlices+") returns "+vb.sliceString(message,whichSlice,totalSlices));
    }      
    
    public void testTryKeyLength(int keyLength){
        VigenereBreaker vb = new VigenereBreaker();
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        int[] key = vb.tryKeyLength(encrypted,keyLength,'e');
        System.out.println("key: ");
        for(int k : key){
            System.out.println(k);
        }
    }     
    
    public void testBreakVigenere () {
        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere();
    }   
    
    public void test() {
        VigenereBreaker vb = new VigenereBreaker();
        vb.test();
    }      
   
}
