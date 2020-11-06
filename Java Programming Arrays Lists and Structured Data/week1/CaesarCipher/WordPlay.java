
/**
 * Write a description of WordPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WordPlay {
//Write a method isVowel that has one Char parameter named ch. This method returns true if ch is a vowel (one of 'a', 'e', 'i', 'o', or 'u' or the uppercase versions) and 
//false otherwise. You should write a tester method to see if this method works correctly. For example, isVowel(‘F’) should return false, and isVowel(‘a’) should return true.
    public boolean isVowel(char ch){
        String vowels = "aeiouAEIOU";
        int idx = vowels.indexOf(ch);
        if(idx != -1){
            return true;
        }
        else{
            return false;
        }
    }
    public void testIsVowel(){
        System.out.println("F is a vowel: "+isVowel('F'));
        System.out.println("A is a vowel: "+isVowel('A'));
        System.out.println("z is a vowel: "+isVowel('z'));
        System.out.println("u is a vowel: "+isVowel('u'));        
    }
    
//Write a method replaceVowels that has two parameters, a String named phrase and a Char named ch. This method should return a String that is the string phrase with all the vowels 
//(uppercase or lowercase) replaced by ch. For example, the call replaceVowels(“Hello World”, ‘*’) returns the string “H*ll* W*rld”. Be sure to call the method isVowel that you 
//wrote and also test this method.    
    public String replaceVowels(String phrase, char ch){
        StringBuilder newString = new StringBuilder(phrase);
        for(int i = 0;i < newString.length(); ++i){
            char currChar = newString.charAt(i);
            if(isVowel(currChar)){
                newString.setCharAt(i, ch);
            }
        }
        return newString.toString();
    }  
    
    public void testReplaceVowels(){
        System.out.println("replaceVowels(“Hello World”, ‘*’): "+replaceVowels("Hello World", '*'));        
    }
    
//Write a method emphasize with two parameters, a String named phrase and a character named ch. This method should return a String that is the string phrase but with the 
//Char ch (upper- or lowercase) replaced by ‘*’ if it is in an odd number location in the string (e.g. the first character has an odd number location but an even index, 
//it is at index 0), or ‘+’ if it is in an even number location in the string (e.g. the second character has an even number location but an odd index, it is at index 1).  
    public String emphasize(String phrase, char ch){
        StringBuilder newString = new StringBuilder(phrase);
        for(int i = 0;i < newString.length(); ++i){
            char currChar = newString.charAt(i);
            if(Character.toLowerCase(currChar) == Character.toLowerCase(ch)){
                if((i+1)%2==0){
                    newString.setCharAt(i,'+');
                } else{
                    newString.setCharAt(i,'*');
                }
            }
        }        
        return newString.toString();
    }

    public void testEmphasize(){
        System.out.println("emphasize(“dna ctgaaactga”, ‘a’) : "+emphasize("dna ctgaaactga", 'a'));
        System.out.println("emphasize(“Mary Bella Abracadabra”, ‘a’)"+emphasize("Mary Bella Abracadabra", 'a'));
    }
     
    
}
