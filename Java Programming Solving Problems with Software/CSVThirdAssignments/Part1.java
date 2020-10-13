
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {

    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                           " Gender " + rec.get(1) +
                           " Num Born " + rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int totalNames = 0;
        int totalBoysNames = 0;
        int totalGirlsNames = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            ++totalNames;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                ++ totalBoysNames;
            }
            else {
                totalGirls += numBorn;
                ++ totalGirlsNames;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);
        System.out.println("total names = " + totalNames);
        System.out.println("female girl's names = " + totalGirlsNames);
        System.out.println("male boy's names = " + totalBoysNames);
    }

    public void testTotalBirths () {
        FileResource fr = new FileResource();
        totalBirths(fr);
    }
// Write the method named getRank that has three parameters: an integer named year, a string named name, and a string named gender (F for female and M for male). 
// This method returns the rank of the name in the file for the given gender, where rank 1 is the name with the largest number of births. If the name is not in the 
// file, then -1 is returned. For example, in the file "yob2012short.csv", given the name Mason, the year 2012 and the gender ‘M’, the number returned is 2, as Mason 
// is the boys name with the second highest number of births. Given the name Mason, the year 2012 and the gender ‘F’, the number returned is -1 as Mason does not appear
// with an F in that file. 
    public int getRank(int year, String name, String gender){
        int rank = 0;
        FileResource fr = new FileResource(".\\us_babynames\\us_babynames_by_year\\yob"+year+".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            String recName = rec.get(0);
            String recGender = rec.get(1);
            if (recGender.equals(gender)) {
                ++rank;
                if(recName.equals(name)){
                    return rank;
                }
            }
        }  
        return -1;       
    }
    
    public void testGetRank() {
        String name = "Emily";
        String gender = "F";
        int rank = getRank(1960, name, gender);
        System.out.println("Name: "+name+", Gender: "+gender+", Rank: "+rank);
    }  
    
// Write the method named getName that has three parameters: an integer named year, an integer named rank, and a string named gender (F for female and M for male). 
// This method returns the name of the person in the file at this rank, for the given gender, where rank 1 is the name with the largest number of births. If the rank
 // does not exist in the file, then “NO NAME” is returned.    
    public String getName(int year, int rank, String gender){
        int curRank = 0;
        FileResource fr = new FileResource(".\\us_babynames\\us_babynames_by_year\\yob"+year+".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            String recName = rec.get(0);
            String recGender = rec.get(1);
            if (recGender.equals(gender)) {
                ++curRank;
                if(curRank == rank){
                    return recName;
                }
            }
        }  
        return "NO NAME";         
    }
    
    public void testGetName() {
        int rank = 2;
        String gender = "F";
        String name = getName(2012, rank, gender);
        System.out.println("Name: "+name+", Gender: "+gender+", Rank: "+rank);
    } 
    
// What would your name be if you were born in a different year? Write the void method named whatIsNameInYear that has four parameters: a string name, 
// an integer named year representing the year that name was born, an integer named newYear and a string named gender (F for female and M for male). 
// This method determines what name would have been named if they were born in a different year, based on the same popularity. That is, you should determine
// the rank of name in the year they were born, and then print the name born in newYear that is at the same rank and same gender.    
    public String whatIsNameInYear(String name, int year, int newYear, String gender){
        int curRank = getRank(year, name, gender);
        String newName = getName(newYear, curRank, gender);
        return newName;
    }
 
    public void testWhatIsNameInYear(){
        String name = "Isabella";
        String gender = "F";
        int year = 2012;
        int newYear = 2014;
        String newName = whatIsNameInYear(name, year, newYear, gender);
        System.out.println(name + " born in "+ year + " would be " + newName + " if she was born in " + newYear + ".");
    }    
    
// Write the method yearOfHighestRank that has two parameters: a string name, and a string named gender (F for female and M for male). This method selects a range 
// of files to process and returns an integer, the year with the highest rank for the name and gender. If the name and gender are not in any of the selected files, 
// it should return -1.    
    public int yearOfHighestRank(String name, String gender){
        int largestRank = -1;
        int largestRankYear = 0;
        Pattern p = Pattern.compile("\\d+");
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            Matcher m = p.matcher(f.getName());
            m.find();
            int year = Integer.parseInt(m.group());
            int curRank = getRank(year, name, gender);
            System.out.println(year + " -- " + curRank);
            if(((curRank < largestRank)||(largestRank == -1))&&(curRank != -1)){
                largestRank = curRank;
                largestRankYear = year;
            }
        }
        return largestRankYear;
    }
  
    public void testYearOfHighestRank(){
        String name = "Mason";
        String gender = "M";
        int year = yearOfHighestRank(name, gender);
        System.out.println("Year of highest rank for Name "+name+", Gender: "+gender+" is "+year);        
    }

//    Write the method getAverageRank that has two parameters: a string name, and a string named gender (F for female and M for male).
//   This method selects a range of files to process and returns a double representing the average rank of the name and gender over the
//    selected files. It should return -1.0 if the name is not ranked in any of the selected files.
    public double getAverageRank(String name, String gender){
        int nameCount = 0;
        int rankSum = 0;
        Pattern p = Pattern.compile("\\d+");
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            Matcher m = p.matcher(f.getName());
            m.find();
            int year = Integer.parseInt(m.group());
            int curRank = getRank(year, name, gender);
            if(curRank != -1){
                ++nameCount;
                rankSum += curRank;                
            }
        }
        double averageRank = Double.valueOf(rankSum)/nameCount;
        return averageRank;
    }

    public void testGetAverageRank(){
        String name = "Mason";
        String gender = "M";
        double rank = getAverageRank(name, gender);
        System.out.println("Average rank for Name "+name+", Gender: "+gender+" is "+rank);        
    }    

    // Write the method getTotalBirthsRankedHigher that has three parameters: an integer named year, a string named name, and a string named gender (F for female and M for male). 
    // This method returns an integer, the total number of births of those names with the same gender and same year who are ranked higher than name.
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        int nameRank = getRank(year, name, gender);
        int rank = 0;
        int births = 0;
        FileResource fr = new FileResource(".\\us_babynames\\us_babynames_by_year\\yob"+year+".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            String recName = rec.get(0);
            String recGender = rec.get(1);
            int recBirths = Integer.parseInt(rec.get(2));
            if (recGender.equals(gender)) {
                ++rank;
                if(rank < nameRank){
                    births +=recBirths;
                }
                else{
                    return births;
                }
            }
        }    
        return births;
    }
    
    public void testGetTotalBirthsRankedHigher(){
        String name = "Ethan";
        String gender = "M";
        int year = 2012;
        int births = getTotalBirthsRankedHigher(year, name, gender);
        System.out.println("Total births with rank higher than "+name+" has is "+births);  
    }
}
