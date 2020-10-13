
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part3 {
    public boolean twoOccurrences(String stringa, String stringb){
        int index = 0;
        index = stringb.indexOf(stringa);
        if(index == -1){return false;}
        index = stringb.indexOf(stringa,index + stringa.length());
        if(index == -1){return false;}
        return true;
    }
    
    public String lastPart(String stringa, String stringb){
        int index = 0;
        index = stringb.indexOf(stringa);
        if(index == -1){return stringb;}
        else{
            return stringb.substring(index + stringa.length(), stringb.length());
        }
    }
    
    public void testing(){
        String stringa = "abc";
        String stringb = "abcdefabcd";
        System.out.println("stringa: "+stringa+", stringb: "+stringb+
        ", Occurs Twice?: "+twoOccurrences(stringa,stringb));
        stringa = "abc";
        stringb = "abcdefabdd";
        System.out.println("stringa: "+stringa+", stringb: "+stringb+
        ", Occurs Twice?: "+twoOccurrences(stringa,stringb));    
        
        stringa = "an";
        stringb = "banana";
        System.out.println("The part of the string after "+stringa+" in "+stringb+
        " is "+lastPart(stringa,stringb));
        stringa = "zoo";
        stringb = "forest";
        System.out.println("The part of the string after "+stringa+" in "+stringb+
        " is "+lastPart(stringa,stringb));     
    }
}
