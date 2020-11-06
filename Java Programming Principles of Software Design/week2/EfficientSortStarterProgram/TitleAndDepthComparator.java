
/**
 * Write a description of TitleAndDepthComparator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class TitleAndDepthComparator implements Comparator<QuakeEntry>{
    public int compare(QuakeEntry q1, QuakeEntry q2){
        String title1 = q1.getInfo();
        String title2 = q2.getInfo();
        int comp = title1.compareTo(title2);
        if(comp == 0){
            comp = Double.compare(q1.getDepth(), q2.getDepth());
        }
        return comp;
    }
}
