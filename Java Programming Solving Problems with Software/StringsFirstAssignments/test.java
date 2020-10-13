
/**
 * Write a description of test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class test {
    public void findAbc(String input) {
        int index = input.indexOf("abc");
        while (true) {
            if (index == -1 || 
                (index > input.length()-4)) {
                break;
            }
            String found = input.substring(index+1, index+4);
            System.out.println(found);
            System.out.println(index);
            index = input.indexOf("abc", index+4);
            System.out.println(index);
        }
    }
       public void test() {
           //findAbc("abcd");
           findAbc("abcdabc");
    }
}
