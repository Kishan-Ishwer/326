import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Threes class
 * 
 * Finds 2 sets of 70 x, y and z
 * that hold for an equation
 * and hold for z < x < y
 * and hold for x,y,z sharing no common factors
 * 
 * @author Kishan Ishwer - 8271570
 */
public class Threes {
    public static void main(String[] args) {
        int set1Count = 0;
        int set2Count = 0;
        
        ArrayList<double[]> firstSet = new ArrayList<double[]>();
        ArrayList<double[]> secondSet = new ArrayList<double[]>();

        double x = 2;
        // loop till 70 x y z values have been found
        while (set1Count < 70) {
            // incrment z from 1 to x - 1
            for (double z = 1; z < x; z++) {
                // if 70 have been found break;
                if (set1Count == 70) { break; }
                // calculate y with a rearanged equation
                double y = Math.sqrt((1+(z*z*z)) - (x*x));
                // check y is a whole number
                if (y % 1 == 0) {
                    // check the equation holds and no common factors are shared
                    // for this iteratioin of x y z
                    if (checkEquation(x, y, z) && checkFactors(x, y, z)) {
                        // add the set to the list and increment set count
                        firstSet.add(new double[]{x,y,z});
                        set1Count++;
                    }
                }
            }
            // increment x by 1
            x++;
        }

        double z = 1;
        while (set2Count < 70) {
            // incrment x from z+1 to y
            for (x = z+1; x < Math.sqrt(1+(z*z*z)-(x*x)); x++) {
                if (set2Count == 70) { break; }
                double y = Math.sqrt((1+(z*z*z)) - (x*x));
                if (y % 1 == 0) {
                    if (checkEquation(x, y, z) && checkFactors(x, y, z)) {
                        secondSet.add(new double[]{x,y,z});
                        set2Count++;
                    }

                }
            }
            z++;
        }

        printSet(firstSet);
        System.out.println();
        printSet(secondSet);

    }

    /**
     * prints the arraylist with its corrsponding index number
     * 
     * @param set the arraylist of the set
     */
    private static void printSet(ArrayList<double[]> set){
        for (int i = 1; i <= set.size(); i++) {
            System.out.println(i + " " + (int) set.get(i-1)[0] + " " + (int) set.get(i-1)[1] + " " + (int) set.get(i-1)[2]);
        }
    }

    /**
     * 
     * checks if the given values of x, y, z
     * are valid for the equation
     * 
     * @param x x value
     * @param y y value
     * @param z z value
     * @return ture if the equation holds for given values
     */
    private static boolean checkEquation(double x, double y, double z) {
        // check equation holds
        if (!((x*x) + (y*y) == 1 + (z*z*z))) {
            return false;
        }

        // checks to see if z < x < y holds
        if (!(z<x&&x<y)) {
            return false;
        }

        return true;

    }

    /**
     * returns true if x, y, z share no common factors other wise false
     * 
     * @param x x value
     * @param y y value
     * @param z z value
     * @return true if no common values are shared between given inputs
     */
    private static boolean checkFactors(double x, double y, double z) {

        // create a arraylist to store common factors
        ArrayList<Integer> factors = new ArrayList<Integer>();

        // finds and stores the factors for largest value z
        for (int i = 2; i < z+1; i++) {
            if (z%i == 0) {
                factors.add(i);
            }
        }

        // finds and stores the factors for second largest value x
        for (int i = 2; i < x+1; i++) {
            if (x%i == 0) {
                // if factor is already in factor list return false
                if (factors.contains(i)) {
                    return false;
                }
                factors.add(i);
            }
        }

        // checks if any of y's factors are in the factors list
        for (int i = 2; i < y+1; i++) {
            if (y%i == 0) {
                if (factors.contains(i)) {
                    return false;
                }
            }
        }

        factors.clear();

        return true;
    }
}
