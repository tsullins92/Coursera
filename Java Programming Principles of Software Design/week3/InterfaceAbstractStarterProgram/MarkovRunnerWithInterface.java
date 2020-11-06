
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author Duke Software
 * @version 1.0
 */

import edu.duke.*; 

public class MarkovRunnerWithInterface {
    
    public void runModel(IMarkovModel markov, String text, int size, int seed) {
        markov.setTraining(text);
        markov.setRandom(seed);
        System.out.println("running with " + markov);
        for(int k=0; k < 3; k++){
            String st= markov.getRandomText(size);
            printOut(st);
        }
    }
    
    public void runMarkov(int size, int seed, int order) {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        
        EfficientMarkovModel m = new EfficientMarkovModel(order);
        runModel(m, st, size, seed);
    }

    public void compareMethods(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 1000;
        int seed = 42;  
        EfficientMarkovModel eModel = new EfficientMarkovModel(4);
        MarkovModel mModel = new MarkovModel(4);
        double time1, time2 = 0.0;
        time1 = System.nanoTime();
        runModel(mModel, st, size, seed);  
        time2 = System.nanoTime();
        System.out.println("MarkovModel took "+(time2-time1)+"to run");
        time1 = System.nanoTime();
        runModel(eModel, st, size, seed);  
        time2 = System.nanoTime();
        System.out.println("EfficientMarkovModel took "+(time2-time1)+"to run");
        
    }
    
    public void testHashMap(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 5;
        int seed = 615;  
        EfficientMarkovModel eModel = new EfficientMarkovModel(5);
        runModel(eModel, st, size, seed);
        // eModel.printHashMapInfo();
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
