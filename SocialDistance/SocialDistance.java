import java.util.*;

/**
 * SocialDistance class
 * 
 * Finds a path through a n*m grid
 * With points on the grids
 * the path maximise the distance from
 * these points.
 */
public class SocialDistance {

    /**
     * Main method
     * 
     * reads in line by line from stdin
     * first line being the number of horitzontal and vertical pathways
     * and each line following being points to be avoided
     */
    public static void main(String[] args) {

        Scanner readLine = new Scanner(System.in);
        boolean firstLine = true;
        String[] gridSize = new String[2];
        ArrayList<String> points = new ArrayList<String>();

        while (readLine.hasNextLine()) {
            String line = readLine.nextLine();
            if (line.length() == 0) {
                firstLine = true;
                //builds the grid
                char[][] grid = buildGrid(gridSize, points);
                //finds shortest path while maximising distance
                runGrid(grid, points);
                points.clear();
                continue;
            }
            if (firstLine) {
                gridSize = line.split("\\s+");
                firstLine = false;
            }
            //stores each point in a arrayList
            else {
                points.add(line);
            }
        }
        readLine.close();

        char[][] grid = buildGrid(gridSize, points);        
        runGrid(grid, points);
        points.clear();
    }

    private static void runGrid(char[][] grid, ArrayList<String> points) {
        //printGrid(grid);
        int min = (grid.length)/2;
        int[] totalMin = new int[points.size()];

        for (int i = min; i > 0; i--) {
            char[][] testGrid = deepCopy(grid);
            testGrid = expandGrid(testGrid, i, points);
            //System.out.println();
            //printGrid(testGrid);
            char[][] pathGrid = deepCopy(testGrid);
            if (pathExists(pathGrid)) {
                grid = testGrid;     
                break;
            }
            else {
                min = i;
            }
        }

        //System.out.println();
        //printGrid(grid);

        /*
        while (true) {
            char[][] testGrid = deepCopy(grid);
            testGrid = expandGrid(testGrid, min, points);
            char[][] pathGrid = deepCopy(testGrid);
            System.out.println();
            printGrid(testGrid);
            if (pathExists(pathGrid)) {     
                grid = testGrid;
                min++;
            }
            else {
                break;
            }
        }
        */

        //printGrid(grid);

        for (int i = 0; i < totalMin.length; i++) {
            totalMin[i] = min;
        }

        while (true) {
            int expandCount = 0;
            for (int i = 0; i < points.size(); i++) {
                String[] pointS = points.get(i).split("\\s+");
                int x = Integer.parseInt(pointS[0]);
                int y = Integer.parseInt(pointS[1]);
                char[][] testGrid = deepCopy(grid);
                testGrid = expandPoint(testGrid, totalMin[i], x, y);
                char[][] pathGrid = deepCopy(testGrid);
                //System.out.println();
                //printGrid(testGrid);
                if (pathExists(pathGrid)) {
                    grid = testGrid;
                    int pointTotal = totalMin[i];
                    pointTotal++;
                    totalMin[i] = pointTotal;
                }
                else {
                    expandCount++;
                    continue;
                }
            }
            if (expandCount==points.size()) {
                break;
            }
        }
        //System.out.println();
        //printGrid(grid);
        System.out.println("min " + min + ", total " + Arrays.stream(totalMin).sum());
    }

    /**
     * Expand point
     * 
     * expands the bubble around a point
     * 
     * @param char[][] the grid
     * @param int min the size of the bubble
     * @param int x coord of point
     * @param int y coord of point
     * 
     * @return the grid with the expanded bubble of a point
     */
    private static char[][] expandPoint(char[][] grid, int min, int x, int y) {
        //increases the bubble horizontal and vertically to min
        if (isValidPoint(grid, x - min, y)) {
            grid[x-min][y] = '0';
        }

        //checks if point is valid before placing
        if (isValidPoint(grid, x + min, y)) {
            grid[x+min][y] = '0';
        }

        if (isValidPoint(grid, x, y - min)) {
            grid[x][y-min] = '0';
        }

        if (isValidPoint(grid, x, y + min)) {
            grid[x][y+min] = '0';
        }

        //if the min is over 1 
        //checks if the surronnding points to expland the bubble are
        //valid before placing them
        if (min > 1) {
            for (int i = 0; i < min; i++) {
                if (isValidPoint(grid, x+min-i, y+i)) {
                    grid[x+min-i][y+i] = '0';
                }
                if (isValidPoint(grid, x-min+i, y+i)) {
                    grid[x-min+i][y+i] = '0';
                }
                if (isValidPoint(grid, x+min-i, y-i)) {
                    grid[x+min-i][y-i] = '0';
                }
                if (isValidPoint(grid, x-min+i, y-i)) {
                    grid[x-min+i][y-i] = '0';
                }
            }
        }
        return grid;
    }

    /**
     * expandGrid method
     * 
     * expands every point on the grid to the min
     * 
     * @param char[][] the grid
     * @param int the min the points expand to
     * @param ArrayList<String> a arrayList of each point
     * 
     * @return the grid with the expanded points on the grid 
     */
    private static char[][] expandGrid(char[][] grid, int min, ArrayList<String> points) {

        //loop through each point in the points list
        for (String point: points) {
            String[] pointS = point.split("\\s+");
            int x = Integer.parseInt(pointS[0]);
            int y = Integer.parseInt(pointS[1]);

            //increases the bubble horizontal and vertically to min
            if (isValidPoint(grid, x - min, y)) {
                grid[x-min][y] = '0';
            }
    
            //checks if point is valid before placing
            if (isValidPoint(grid, x + min, y)) {
                grid[x+min][y] = '0';
            }
    
            if (isValidPoint(grid, x, y - min)) {
                grid[x][y-min] = '0';
            }
    
            if (isValidPoint(grid, x, y + min)) {
                grid[x][y+min] = '0';
            }

            //if the min is over 1 
            //checks if the surronnding points to expland the bubble are
            //valid before placing them
            if (min > 1) {
                for (int i = 0; i < min; i++) {
                    if (isValidPoint(grid, x+min-i, y+i)) {
                        grid[x+min-i][y+i] = '0';
                    }
                    if (isValidPoint(grid, x-min+i, y+i)) {
                        grid[x-min+i][y+i] = '0';
                    }
                    if (isValidPoint(grid, x+min-i, y-i)) {
                        grid[x+min-i][y-i] = '0';
                    }
                    if (isValidPoint(grid, x-min+i, y-i)) {
                        grid[x-min+i][y-i] = '0';
                    }
                }
            }
        }
        return grid;
    }

    /**
     * buildGrid method
     * 
     * builds a char grid from the input of horizontal and vertical points
     * 
     * @param String[] horizontal and vertical points
     * @param ArrayList<String> points to be avoided
     * 
     * @return the grid
     */
    private static char[][] buildGrid(String[] size, ArrayList<String> points){
        //initalize the grid from the horizontal and vertical pathways
        char[][] grid = new char[Integer.parseInt(size[0])][Integer.parseInt(size[1])];

        //place 1 in every postion on the grid
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                grid[x][y] = '1';
            }
        }

        //place a 0 at every postion on the grid to be avoided
        for (int i = 0; i < points.size(); i++) {
            String[] point = points.get(i).split("\\s+");
            grid[Integer.parseInt(point[0])][Integer.parseInt(point[1])] = '0';
        }

        //place destination point
        grid[Integer.parseInt(size[0])-1][Integer.parseInt(size[1])-1] = 'X';

        return grid;
    }

    /**
     * pathExists method
     * 
     * finds a path from start point to destination on grid
     * 
     * @param char[][] grid
     * 
     * @return boolean if a path exists
     */
    private static boolean pathExists(char[][] grid) {
        int N = grid.length;
        
        //initalize the queue list
        Deque<Node> queue = new ArrayDeque<Node>();

        if (grid[0][0] == '0') {
            return false;
        }

        //add starting node to grid
        queue.add(new Node(0, 0));
        boolean pathExists = false;

        //while the queue is not empty
        //check if destination node is in queue by poping first node in queue
        //if destination node found break
        //add neighbours of current node to the queue
        while (!queue.isEmpty()) {
            Node current = queue.removeLast();
            if (grid[current.x][current.y] == 'X') {
                pathExists = true;
                break;
            }

            grid[current.x][current.y] = '0';

            List<Node> neighbours = getNeighbours(grid, current);
            queue.addAll(neighbours);
        }
        return pathExists;
    }

    /**
     * Print grid method
     * 
     * prints to stdout the grid
     * 
     * @param char[][] grid
     */
    private static void printGrid(char[][] grid) {
        int rowCount = grid.length;
        int colCount = grid[0].length;

        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < colCount; y++) {
                System.out.print(grid[x][y]);
                if (y == colCount - 1) {
                    System.out.println("");
                }
            }
        }
    }

    /**
     * getNeigbours method
     * 
     * get direct neigbours of the current node
     * 
     * @param char[][] grid
     * @param Node of point
     */
    private static List<Node> getNeighbours(char[][] grid, Node node) {
        //initalize neigbours list
        List<Node> neighbours = new ArrayList<Node>();

        //check if neighbours are valid points and adds them to the neigbours list
        if (isValidPoint(grid, node.x - 1, node.y)) {
            neighbours.add(new Node(node.x-1, node.y));
        }

        if (isValidPoint(grid, node.x + 1, node.y)) {
            neighbours.add(new Node(node.x+1, node.y));
        }

        if (isValidPoint(grid, node.x, node.y - 1)) {
            neighbours.add(new Node(node.x, node.y - 1));
        }

        if (isValidPoint(grid, node.x, node.y + 1)) {
            neighbours.add(new Node(node.x, node.y + 1));
        }

        return neighbours;
    }

    /**
     * checks if a given point, is valid on the grid
     * 
     * @param char[][] grid
     * @param int x coord
     * @param int y coord
     * 
     * @return boolean of if point is valid
     */
    private static boolean isValidPoint(char[][] grid, int x, int y) {
        return !(x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) && (grid[x][y] != '0');
    }

    /**
     * Makes a deep copy of the grid and returns it
     * 
     * @param char[][] grid to be copied
     * 
     * @return the deep copy of input grid
     */
    private static char[][] deepCopy(char[][] oldGrid) {
        char[][] newGrid = new char[oldGrid.length][oldGrid[0].length];

        for (int i=0; i<oldGrid.length; i++) {
            for (int j=0; j<oldGrid[i].length; j++) {
                newGrid[i][j]=oldGrid[i][j];
            }
        }
        
        return newGrid;
    } 

}