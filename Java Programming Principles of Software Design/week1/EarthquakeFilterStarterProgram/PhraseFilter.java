
/**
 * Write a description of PhraseFiler here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PhraseFilter implements Filter{
    private String where; 
    private String phrase;
    private String name;
    
    public PhraseFilter(String inputWhere, String inputPhrase, String filterName) { 
        where = inputWhere;
        phrase = inputPhrase;
        name = filterName;
    } 

    public boolean satisfies(QuakeEntry qe) { 
        String info = qe.getInfo();
        if(where.equals("any")){
            return info.contains(phrase);
        } else if(where.equals("start")){
            return info.startsWith(phrase);
        } else if(where.equals("end")){
            return info.endsWith(phrase);
        } else{
            return false;
        }
    } 
    
    public String getName(){
        return name;
    }    
    
}
