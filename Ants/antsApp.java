import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/** 
* A main class to run ants
*
* @author Mathew Waters 6830773
* @author Kishan Ishwer 8271570
*/

class antsApp {
    public static void main(String[] args){
        int count = 0;
        // List that is manipulated
        List <String> arrOfDna = new ArrayList<String>();
        // List that store the original dna sequence
        List <String> originalDna = new ArrayList<String>();

            Scanner myReader = new Scanner(System.in);

            while (myReader.hasNextLine()){
                String readLine = myReader.nextLine();
                int lineLength = readLine.length();

                
                // Skips the line if it containts a # in the first position of the line
                if (readLine.indexOf("#") == 0){
                    continue;
                }

                // If the line is a number (the steps), it signifies end of DNA sequence, so creates the ant construcotr.
                 else if (readLine.matches(".*\\d.*") && lineLength <= 8) {
                // else if (lineLength < 5) {
                    //System.out.println("GOT STEPS: " + readLine);
                    arrOfDna.add(readLine);  
                    originalDna.add(readLine);

                    // Removes all spaces
                    arrOfDna.removeAll(Arrays.asList("", null));
                    arrOfDna.removeAll(Arrays.asList(" ", null));
                    String[] strarray = arrOfDna.toArray(new String[0]);

                    
                    // creates Ants constructor and sends through the "DNA"
                    Ants ant = new Ants(strarray);
                    // Gets the position of the steps value
                    int stepsPos = strarray.length;
                    // Saves steps into a variable steps
                    int steps = Integer.parseInt(strarray[stepsPos-1]);
                        
                    // Sends steps and DNA
                    ant.move(steps, strarray);

                    // Gets the end x and y coordinates
                    int x = ant.getX();
                    int y = ant.getY();

                    // Outputs DNA and the x and y coordinates
                    
                    for(int i = 0; i < originalDna.size(); i++){
                        System.out.println(originalDna.get(i));
                    }
                    System.out.println("# " + x + " " + y);
                    
                    // Clears list in preparation for next ant
                    arrOfDna.clear();
                    originalDna.clear();
                    continue;

                } 
                // Adds line to the array
                else {
                    //splits into an array of character strings
                    String[] addChar = readLine.split("(?!^)");        
                    Collections.addAll(arrOfDna, addChar);
                    originalDna.add(readLine);

                }       
                
            }
            myReader.close();

    }

}
