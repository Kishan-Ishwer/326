cd 
import java.util.*;



/**
 * RepeatedDigits class
 * Calculates the highest block of repeated digits
 * for a given number and given base.
 *
 * Calculates the lowest number to have repeated digits
 * given two bases
 *
 * @author Kishan Ishwer 8271570
 */
public class RepeatedDigits {

    /**
     * Main Method
     *
     * Reads Input lines and checks if they are legal inputs
     * and calls the respective Method A or B
     */
       
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            // splits the input line by whitespace into an array
            String line = scanner.nextLine();
            String[] splitLine = line.split("\\s+");
            // calls checkValid to check if input is valid
            if (checkValid(splitLine)) {
                int mode = (splitLine[0].equals("A")) ? 1 : 0;
                int b = Integer.parseInt(splitLine[1]);
                // calls respective method A or B by checking first
                // part of input
                if (mode == 1) {
                    int n = Integer.parseInt(splitLine[2]);
                    A(b,n);
                }
                else {
                    int c = Integer.parseInt(splitLine[2]);
                    B(b,c);
                }
            }
            else {
                System.out.println("Bad line: " + line);
            }
        }
        scanner.close();
    }

    /**
     * A method
     *
     * takes in a base and a integer
     * converts the integer to desirved base
     * and counts repeated digits in that num
     *
     * @param int b (the base)
     * @param int n (the number)
     */
    private static void A(int b, int n) {
        ArrayList<Integer> num = new ArrayList<Integer>();
        num.add(0);
        int blockStart = 0;
        int count = 0;
        int maxCount = 0;
        for (int i = 0; i < n; i++) {
            if (findDigitsB(num)) {
                count++;
            } else {
                if (count > maxCount) {
                    blockStart = i - count;
                    maxCount = count;
                }
                count = 0;
            }
            if (i == n-1) {
                if (count > maxCount) {
                    blockStart = i+1 - count;
                    maxCount = count;
                } 
            }
            incrementNumber(num, b);
            
        }
          
        num.clear();
        num.add(0);
        for (int j = 0; j < blockStart; j++) {
            incrementNumber(num, b);
        }
        System.out.println(blockStart + " " + maxCount);
    }

    private static void incrementNumber(ArrayList<Integer> number, int base){
        int index = 0;
        int currValue = number.get(index);
        while(currValue + 1 == base){
          number.set(index,0);
          index++;
          if (index == number.size()){
            number.add(0);
          }
          currValue = number.get(index);
        }
        number.set(index, currValue + 1);
     }

    /**
     * B method
     *
     * takes in two bases
     * and finds the lowest number that
     * has repreated digits in both
     * base b and c
     *
     * @param int b (base 1)
     * @param int c (base 2)
     */
    private static void B(int b, int c) {
        int n = 0;
        ArrayList<Integer> num1 = new ArrayList<Integer>();
        ArrayList<Integer> num2 = new ArrayList<Integer>();
        num1.add(0);
        num2.add(0);
        while (true) {
            if (findDigitsB(num1) && findDigitsB(num2)) {
               System.out.println(n);
               break; 
            }
            incrementNumber(num1, b);
            incrementNumber(num2, c);
            n++;
        }
    }

    /**
     * findDigitsB method
     *
     * returns true if there is a repeated digit in num
     *
     * @param ArrayList<Integer> num (the integer in base form)
     *
     * @return boolean (true if there is a repeated digit)
     */
    private static boolean findDigitsB(ArrayList<Integer> num) {
        // adds the num to a set if thier is a repeated digit in num
        // return true as sets cant have repeated digits.
        Set<Integer> tempSet = new HashSet<Integer>();
        for (Integer i: num) {
            if (!tempSet.add(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * checkValid method
     *
     * checks if the input string is valid
     *
     * @param String[] input (the input string in a array split at whitespace)
     *
     * @return boolean (true if the the input string is valid)
     */
    private static boolean checkValid(String[] input) {
        
        if (!(input.length == 3)) {
            return false;
        }

        if (!(input[0].equals("A") || input[0].equals("B"))) {
            return false;
        }

        if (!(tryParseInt(input[1]) && tryParseInt(input[2]))) {
            return false;
        }

        return true;
    }

    /**
     * tryParseInt method
     *
     * tries to parse a value stored in a string
     * returns true if it can be parsed
     *
     * @param String value (the value to try parse)
     *
     * @return boolean (true if value can be parsed)
     */
    public static boolean tryParseInt(String value) {  
        try {  
            Integer.parseInt(value);  
            return true;  
        } catch (NumberFormatException e) {  
            return false;  
        }  
    }
 

}
