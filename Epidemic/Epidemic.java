import java.util.*;

/**
 * Epidemic Class
 * 
 * Takes in a universe of consisting of . (uninfected), S (Sick), I (Immune).
 * Immune cells cannot become sick.
 * Uninfected cells become Sick cells if they have 2 or more Sick neighbours.
 * 
 * modes:
 * 1: The universe is printed in its final state
 * 2: A universe of Immune and uninfected cells is the input
 *    A universe is outputed with the Minimum ammount of sick cells required
 *    to infect the whole Universe.
 *  
 */
public class Epidemic {

    static private int mode = 1;

    /**
     * Input is read line by line
     * the universe is then converted into a 2d matrix
     * depending on what mode is slected from args
     * the method runUniverse or runSimulation is called
     * @param args
     */
    public static void main(String[] args) {   
        if (args.length == 1) {
            if (args[0].equals("-1")) {
                mode = 1;
            }
            if (args[0].equals("-2")) {
                mode = 2;
            }
        }

        Scanner readLine = new Scanner(System.in);
        ArrayList<String> temp = new ArrayList<String>();

        int colCount = 0;
        int rowCount = 0;

        while (readLine.hasNextLine()) {
            String line = readLine.nextLine();
            if (rowCount == 0) {
                colCount = line.length();
            }
            temp.add(line);
            if (line.length() == 0) {
                temp.remove(temp.size()-1);
                int[][] universe = buildUniverse(temp);
                if (mode == 1) {
                    int[][] finalUniverse = runUniverse(universe);
                    System.out.println();
                    printUniverse(finalUniverse);
                }
                if (mode == 2) {
                    runSimulation(universe);
                }
                colCount = 0;
                rowCount = 0;
                temp.clear();
            }
            else {
                rowCount++;
            }
        }
        int[][] universe = buildUniverse(temp);
        if (mode == 1) {
            int[][] finalUniverse = runUniverse(universe);
            System.out.println();
            printUniverse(finalUniverse);
        }
        if (mode == 2) {
            runSimulation(universe);
        }
        readLine.close();

    }

    /**
     * runs the universe from the intial universe to the final state that that universe can be
     * 
     * @param uni
     * @return the universe after it has run and sick have infected what is possible to infect
     */
    private static int[][] runUniverse(int[][] uni) {
        int rowCount = uni.length;
        int colCount = uni[0].length;
        int[][] finalGen = new int[colCount][rowCount];
        int count = 0;

        // check if any cells can be changed if not
        // the universe is in it final state and it returns that univverse
        while (true) {
            count = 0;
            for (int x = 0; x < rowCount; x++) {
                for (int y = 0; y < colCount; y++) {
                    if (uni[x][y] == 0) {
                        if (checkNeighbours(uni, x, y, 1) > 1) {
                            uni[x][y] = 1;
                            count++;
                        }
                    }
                }
            }
            if (count == 0) {
                break;
            }
        }
        finalGen = uni;
        return finalGen;

    }

    /**
     * Run Simulation
     * Take in a universe as a 2d matrix of only immune and uninfected cells
     * based of the universe the amount of initial sick cells required is estimated
     * Cells that we work out need to be made sick are made sick
     * the rest of the estimated sick cells required are place randomly
     * till the universe is able to be fully infected excluding immune cells
     * 
     * @param uni
     */
    private static void runSimulation(int[][] uni) {
        Random random = new Random();
        int rowCount = uni.length;
        int colCount = uni[0].length;
        int i = 0;
        int n = 0;

        // estimate the cells that are randomly need to be made sick
        int count = (int) Math.ceil((((double) rowCount*2)+((double) colCount*2))/4);
        int immuneNeigbourCount = 0;

        // calculate the amount of sick cell that need to be place near immune cells
        // based on the count of uninfected cells are around the immune cells
        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < colCount; y++) {
                if (uni[x][y] == -1) {
                    immuneNeigbourCount += checkNeighbours(uni, x, y, 0);
                }
            }
        }
        immuneNeigbourCount = (int) Math.ceil(immuneNeigbourCount/4);

        // case for if the universe is only 1 tile 
        if(colCount == 1 && rowCount == 1) {
            if (uni[0][0] == -1 || uni[0][0] == 1) {
                System.out.println();
                System.out.println(0);
                printUniverse(uni);
                return;
            }
        }

        int[][] testCopyS1 = deepCopy(uni);
        int[][] testCopyS2 = deepCopy(uni);
        int[][] testCopyFirstGen = deepCopy(uni);
        
        int[][] testCopyComplete = runUniverse(testCopyS1);

        // check if the the universe is in its final state
        if (checkComplete(testCopyComplete)) {
            System.out.println();
            System.out.println(0);
            printUniverse(uni);
            return;
        }
        testCopyS1 = deepCopy(uni);
         
        // finds the best cell arround immune cells
        // to place a sick cell on
        while (n < immuneNeigbourCount) {
            int[] bestCell = new int[3];
            bestCell[2] = 10;
            for (int x = 0; x < rowCount; x++) {
                for (int y = 0; y < colCount; y++) {
                    if (testCopyS1[x][y] == -1) {
                        int[] cell = findBestImmuneNeighbour(x,y, testCopyS1);
                        if (bestCell[2] > cell[2]) {
                            bestCell = cell;
                        } 
                    }
                }
            }
            testCopyS1[bestCell[0]][bestCell[1]] = 1;
            testCopyFirstGen[bestCell[0]][bestCell[1]] = 1;
            runUniverse(testCopyS1);
            n++;
            //System.out.println();
        }

        // till the universe is fully infected
        // we loop and place sick cells randomly up to the estiamte
        // we calulated earliar
        // if the universe cannot reach its final state with the estimate
        // of randomly place sick cells after 1000 iterations
        // we increase our estimate by 1
        testCopyS1 = deepCopy(testCopyFirstGen);
        int increaseCount = 1;
        while (true) {
            i = 0;
            int upperBound = rowCount * colCount;
            testCopyS2 = deepCopy(testCopyS1);
            testCopyFirstGen = deepCopy(testCopyS1);
            if (increaseCount == 1000) {
                count++;
                increaseCount = 0;
            }
            while (i < count) {

                int sick = random.nextInt(upperBound - 0) + 0;
                int x = sick/colCount;
                int y = sick%colCount;
                if (testCopyS2[x][y] == 0) {
                    testCopyFirstGen[x][y] = 1;
                    testCopyS2[x][y] = 1;
                    i++;
                }
                testCopyComplete = runUniverse(testCopyS2);
                // test to see if the universe is complete
                if (checkComplete(testCopyComplete)) {
                    testCopyFirstGen = simplifyUniverse(testCopyFirstGen);
                    System.out.println();
                    int sickCells = countSick(testCopyFirstGen);
                    System.out.println(sickCells);
                    printUniverse(testCopyFirstGen);
                    break;
                }
            }
            if (checkComplete(testCopyComplete)) {
                break; 
            }
            increaseCount++;
        }
    }

    /**
     * Counts the number of sick cells in a universe
     * @param uni
     * @return count of sick cells
     */
    private static int countSick(int[][] uni) {
        int rowCount = uni.length;
        int colCount = uni[0].length;
        int count = 0;

        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < colCount; y++) {
                if (uni[x][y] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Method to check if removing a subset of Sick Cells
     * Would still allow the universe to be completed
     * @param uni
     * @return the universe with any subset of unneed Sick cells removed 
     */
    private static int[][] simplifyUniverse(int[][] uni) {
        int[][] testCopy = deepCopy(uni);
        int rowCount = uni.length;
        int colCount = uni[0].length;

        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < colCount; y++) {
                if (testCopy[x][y] == 1) {
                    testCopy[x][y] = 0;
                    int[][] testCopyRemove = deepCopy(testCopy);
                    runUniverse(testCopyRemove);
                    if (!checkComplete(testCopyRemove)) {
                        testCopy[x][y] = 1;
                    }
                }
            }
        }
        return testCopy;
    }

    /**
     * method to find the best cell next to an immune cell
     * to place a sick cell in
     * 
     * the best tile is decided by the less amount of uninfected cells are touching the cell
     * and a cell that does not have any sick cells next to it
     * 
     * @param x
     * @param y
     * @param uni
     * @return returns a array in form {x,y,count}, which is the best cell next to the immune cell 
     */
    private static int[] findBestImmuneNeighbour(int x, int y, int[][] uni) {
        int MIN_X = 0;
        int MAX_X = uni.length-1;
        int MIN_Y = 0;
        int MAX_Y = uni[0].length-1;

        int count = 0;
        int[] bestCell = new int[3];
        bestCell[2] = 10;

        int startPosX = (x - 1 < MIN_X) ? x : x-1;
        int startPosY = (y - 1 < MIN_Y) ? y : y-1;
        int endPosX =   (x + 1 > MAX_X) ? x : x+1;
        int endPosY =   (y + 1 > MAX_Y) ? y : y+1;

        for (int rowNum=startPosX; rowNum<=endPosX; rowNum++) {
            for (int colNum=startPosY; colNum<=endPosY; colNum++) {
                // check for sick cells next to current cell
                if (checkNeighbours(uni, rowNum, colNum, 1) > 0) {
                    continue;
                }
                // sick infected, immune and the cell it self
                if (x == rowNum && y == colNum) {
                    continue;
                }
                if (uni[rowNum][colNum] == -1) {
                    continue;
                }
                if (uni[rowNum][colNum] == 1) {
                    continue;
                }
                // count the amount of cells next to the current cell
                // if it is uninfectd    
                if (uni[rowNum][colNum] == 0) {
                    count = checkNeighbours(uni, rowNum, colNum, 0);
                    count -= (checkNeighbours(uni, rowNum, colNum, 1))/2;
                }
                if (count < bestCell[2]) {
                    bestCell[0] = rowNum;
                    bestCell[1] = colNum;
                    bestCell[2] = count;
                }
            }
        }
        return bestCell;
    }

    /**
     * checkComplete method to chack if the universe still has any uninfected cells
     * 
     * @param uni
     * @return true or false if the table has any uninfected cells left
     */
    private static boolean checkComplete(int[][] uni) {
        for (int x = 0; x < uni.length; x++) {
            for (int y = 0; y < uni[0].length; y++) {
                if (uni[x][y] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * makes a deep copy of the universe
     * 
     * @param orginal
     * @return a deep copy of the universe
     */
    private static int[][] deepCopy(int[][] orginal) {
        if (orginal == null) {
            return null;
        }
        int[][] copy = new int[orginal.length][];
        for (int i = 0; i < orginal.length; i++) {
            copy[i] = Arrays.copyOf(orginal[i], orginal[i].length);
        }
        return copy;
    }

    /**
     * returns a count of the tiles of specifeid type next to the specifeid tile
     * 
     * @param uni
     * @param x
     * @param y
     * @param type
     * @return the count of neighbours of specided type
     */
    private static int checkNeighbours(int[][] uni, int x, int y, int type) {
        int MIN_X = 0;
        int MAX_X = uni.length-1;
        int MIN_Y = 0;
        int MAX_Y = uni[0].length-1;

        int count = 0;

        int startPosX = (x - 1 < MIN_X) ? x : x-1;
        int startPosY = (y - 1 < MIN_Y) ? y : y-1;
        int endPosX =   (x + 1 > MAX_X) ? x : x+1;
        int endPosY =   (y + 1 > MAX_Y) ? y : y+1;

        for (int rowNum=startPosX; rowNum<=endPosX; rowNum++) {
            for (int colNum=startPosY; colNum<=endPosY; colNum++) {
                // doesnt count tiles that are diagnol to the specified tile
                if (x-1 == rowNum && y-1 == colNum) {
                    continue;
                }
                if (x-1 == rowNum && y+1 == colNum) {
                    continue;
                }
                if (x+1 == rowNum && y+1 == colNum) {
                    continue;
                }
                if (x+1 == rowNum && y-1 == colNum) {
                    continue;
                }
                if (x == rowNum && y == colNum) {
                    continue;
                }
                
                if (uni[rowNum][colNum] == type) {
                    count++;
                }
            }
        }
        return count;
    }

    
    /**
     * print out the universe to stdout
     * @param uni
     */
    private static void printUniverse(int[][] uni) {
        int rowCount = uni.length;
        int colCount = uni[0].length;

        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < colCount; y++) {
                if (uni[x][y] == 1) {
                    System.out.print("S");
                }
                if (uni[x][y] == -1) {
                    System.out.print("I");
                }
                if (uni[x][y] == 0) {
                    System.out.print(".");
                }
                if (y == colCount - 1) {
                    System.out.println("");
                }
            }
        }
    }

    /**
     * Converts the universe from ArrayList<String> to int[][]
     * where:
     *      Sick = -1
     *      Immune = 1
     *      Uninfected = 0
     * @param charUni
     * @return the universe as a 2d array
     */
    private static int[][] buildUniverse(ArrayList<String> charUni) {
        int colCount = charUni.get(0).length();
        int rowCount = charUni.size();
        int[][] uni = new int[rowCount][colCount];

        Character S = 'S';
        Character I = 'I';
        String dot = ".";

        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < colCount; y++) {
                if (Character.compare(charUni.get(x).charAt(y), S) == 0) {
                    uni[x][y] = 1;
                }
                if (Character.compare(charUni.get(x).charAt(y), I) == 0) {
                    uni[x][y] = -1;
                }
                if (dot.equals(String.valueOf(charUni.get(x).charAt(y)))) {
                    uni[x][y] = 0;
                }
            }
        }
        return uni;
    }
}