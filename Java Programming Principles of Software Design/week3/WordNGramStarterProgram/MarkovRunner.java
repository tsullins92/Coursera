
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class MarkovRunner {
    public void runModel(IMarkovModel markov, String text, int size){ 
        markov.setTraining(text); 
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runModel(IMarkovModel markov, String text, int size, int seed){ 
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void testFollows() { 
        String st = "this is just a test yes this is a simple test";
        MarkovWordOne markovWord = new MarkovWordOne(); 
        runModel(markovWord, st, 5); 
    }    
    
    public void runMarkov(int size, int seed) { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        MarkovWordOne markovWord = new MarkovWordOne();
        markovWord.setRandom(seed);
        runModel(markovWord, st, size); 
    } 

    public void runMarkovTwo(int size, int seed) { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        MarkovWordTwo markovWord = new MarkovWordTwo();
        markovWord.setRandom(seed);
        runModel(markovWord, st, size); 
    }     

    public void runMarkovWord(int size, int seed, int order) { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        MarkovWord markovWord = new MarkovWord(order);
        markovWord.setRandom(seed);
        runModel(markovWord, st, size); 
    }     
    
    public void runEfficientMarkovWord(int size, int seed, int order) { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        EfficientMarkovWord eMarkovWord = new EfficientMarkovWord(order);
        eMarkovWord.setRandom(seed);
        runModel(eMarkovWord, st, size); 
    }         
    
    public void testHashMap(int size, int seed, int order) { 
        String st = "this is a test yes this is really a test yes a test this is wow";
        EfficientMarkovWord eMarkovWord = new EfficientMarkovWord(order);
        eMarkovWord.setRandom(seed);
        runModel(eMarkovWord, st, size); 
    }  
    
    public void compareMethods(int size, int seed, int order){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        EfficientMarkovWord eMarkov = new EfficientMarkovWord(order);
        MarkovWord markov = new MarkovWord(order);
        double time1, time2 = 0.0;
        time1 = System.nanoTime();
        runModel(eMarkov, st, size, seed);  
        time2 = System.nanoTime();
        System.out.println("EfficientMarkovWord took "+(time2-time1)+"to run");
        time1 = System.nanoTime();
        runModel(markov, st, size, seed);  
        time2 = System.nanoTime();
        System.out.println("MarkovWord took "+(time2-time1)+"to run");
    }    
    
    private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println(); 
                psize = 0;
            } 
        } 
        System.out.println("\n----------------------------------");
    } 

}
