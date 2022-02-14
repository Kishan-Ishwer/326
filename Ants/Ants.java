import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
* Class ants that contains all the accessors, gettors and methods to run
* @author Mathew Waters 6830773
* @author Kishan Ishwer 8271570
 */
public class Ants {

    private int x = 0;
    private int y = 0;
    private int direction = 0;
    private String[] gNome;
    private Hashtable<String, String> table = new Hashtable<String, String>();


    Ants(String[] DNA) {
        this.gNome = DNA;
    }

    /**
    * Sets ant current direction. 
    * @param direction an int input 
    */
    public void setDirection(int direction){
        this.direction = direction;
    }

    /**
    * @return x coordinate. 
    */
    public int getX(){
        return this.x;
    }

    /**
    * @return y coordinate. 
    */
    public int getY(){
        return this.y;
    }

    /**
    * @return ant gNome coordinate. 
    */
    public String[] getGNome(){
        return this.gNome;
    }

    /**
    * method that checks what direction the ant is facing, and makes a decision  based on this.
    *
    * @param steps is the total amount of steps the ant will take
    * @param gNome the entire dna sequence separated into an array 
    */
    public void move(int steps, String[] gNome) {

        // if the hash table is empty, add in the first coordinate 0,0
        if (table.isEmpty()) {
            table.put("0 0", gNome[0]);
        }

        String value;
        String moveDirection;
        String coord = Integer.toString(x) + " " + Integer.toString(y);
        
        // amount is the amount of sequences there are in the total DNA
        int amount = (gNome.length-1)/9;
        
        for (int i = 0; i < steps; i++) {
            for (int j = 0; j < amount; j++) {
                if (table.get(coord).equals(gNome[j*9])) {
                    if (direction == 0) { // If facing north
                        value = gNome[(j*9)+5]; // Sets value of the block 
                        table.put(coord, value); // puts coordinate and value into hash table
                        moveDirection = gNome[(j*9)+1]; // Gets the move direction
                        coord = moveFunction(moveDirection);
                        break;
                    }

                    else if (direction == 1) { // If facing east
                        value = gNome[(j*9)+6];
                        table.put(coord, value);
                        moveDirection = gNome[(j*9)+2];
                        coord = moveFunction(moveDirection);
                        break;
                    }

                    else if (direction == 2) { // If facing south
                        value = gNome[(j*9)+7];
                        table.put(coord, value);
                        moveDirection = gNome[(j*9)+3];
                        coord = moveFunction(moveDirection);
                        break;
                    }

                    else if (direction == 3) { // If facing west
                        value = gNome[(j*9)+8];
                        table.put(coord, value);
                        moveDirection = gNome[(j*9)+4];
                        coord = moveFunction(moveDirection);
                        break;
                    }
                }

            }
        }
    }

    /** 
    * Method that does the next move of the ant. 
    *
    * @param moveDirection
    * @return returns the new coordinates
    */

    public String moveFunction(String moveDirection) {
        String coord = Integer.toString(x) + " " + Integer.toString(y);
        if (moveDirection.equals("N")) {
            direction = 0; // sets new direction
            y++; // increases vertical coordinate
            System.out.println("CURRENT: " + x + ", " + y);
            coord = Integer.toString(x) + " " + Integer.toString(y);
             // if the table doesn't contain this coord then it puts it in
            if(!table.containsKey(coord)) {
                table.put(coord, gNome[0]);
            }
               
            return coord;
        }
        else if (moveDirection.equals("E")) {
            direction = 1;
            x++; // increase horizontal coordinate
            System.out.println("CURRENT: " + x + ", " + y);
            coord = Integer.toString(x) + " " + Integer.toString(y);
            if (!table.containsKey(coord)) {
                table.put(coord, gNome[0]);
                //System.out.println(table);
            }
            return coord;
        }
        else if (moveDirection.equals("S")) {
            direction = 2;
            y--; // decrease vertical coordinate
            System.out.println("CURRENT: " + x + ", " + y);
            coord = Integer.toString(x) + " " + Integer.toString(y);
            if (!table.containsKey(coord)) {
                table.put(coord, gNome[0]);
               
            }
            return coord;
        }
        else if (moveDirection.equals("W")) {
            direction = 3;
            x--; // decreases horizontal coordinate
            System.out.println("CURRENT: " + x + ", " + y);
            coord = Integer.toString(x) + " " + Integer.toString(y);
            if (!table.containsKey(coord)) {
                table.put(coord, gNome[0]);                
            }
            return coord;
        }
        else {
            return coord;
        }
        
            
    }


}
