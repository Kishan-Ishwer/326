import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Toothpicks class
 * extends jPanel
 * 
 * produces a diagram of toothpicks
 * based on a generation and size variables
 */
public class Toothpicks extends JPanel {

    private static final long serialVersionUID = 1L;
    static int generation = -1;
    static float size = 1;
    static double orginalLineLength = 1/10;
    static double windowSize = 800;

    /**
     * drawToothPicks
     * 
     * method to recursively draw toothpicks after the first generation
     * 
     * @param gen the cuurrent generation
     * @param s the size multiplier of the next line
     * @param g the graphic component
     * @param endOfLineX the x coord of the end of the previous line
     * @param endOfLineY the y coord of the end of the previous line
     * @param length the length of the line for the generation
     * @param vertical a boolean for if the lines are vertical or horizontal
     */
    public void drawToothPicks(int gen, float s, Graphics g, double endOfLineX, double endOfLineY, double length, boolean vertical){
        Graphics2D g2 = (Graphics2D) g;
        // base case
        if (gen == 0) {
            if (vertical) {
                // draws a vertical line based off the end point of the last line
                g2.draw(new Line2D.Double(endOfLineX, endOfLineY + (length/2), endOfLineX, endOfLineY - (length/2)));
            }
            else if (!vertical) {
                // draws a horizontal line based off the end point of the last line
                g2.draw(new Line2D.Double(endOfLineX - (length/2), endOfLineY, endOfLineX + (length/2), endOfLineY));
            }
        }
        // iterative step
        else {
            if (vertical) {
                g2.draw(new Line2D.Double(endOfLineX, endOfLineY + (length/2), endOfLineX, endOfLineY - (length/2)));
                // 2 recursive calls
                // one at each end of the line just drawn 
                drawToothPicks(gen-1, s, g, endOfLineX, endOfLineY + (length/2), length*s, false);
                drawToothPicks(gen-1, s, g, endOfLineX, endOfLineY - (length/2), length*s, false);
            }
            else if (!vertical) {
                g2.draw(new Line2D.Double(endOfLineX - (length/2), endOfLineY, endOfLineX + (length/2), endOfLineY));
                drawToothPicks(gen-1, s, g, endOfLineX - (length/2), endOfLineY, length*s, true);
                drawToothPicks(gen-1, s, g, endOfLineX + (length/2), endOfLineY, length*s, true);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        // draws generation zeros line
        if (generation > -1) {
            g2.draw(new Line2D.Double(windowSize/2  - (orginalLineLength/2),windowSize/2,windowSize/2 + (orginalLineLength/2), windowSize/2));
        }
        // if there is more then one generation
        // recursively draw the next generations lines
        if (generation > 0) {
            drawToothPicks(generation-1, size, g, windowSize/2 - (orginalLineLength/2), windowSize/2, orginalLineLength * size, true);
            drawToothPicks(generation-1, size, g, windowSize/2 + (orginalLineLength/2), windowSize/2, orginalLineLength * size, true);
        }
    }

    /**
     * main method
     * 
     * reads in from arg and sets the generation and size variables
     * changes the first line length based on amount of generations and size
     */
    public static void main(String[] args) {
        
        // if there is only 1 argument read in
        // we set that argument as the generation variable
        // if 2 arguments are read in
        // we set the second argument as the size variable
        if (args.length == 1) {
            generation = Integer.parseInt(args[0]);
        } else if (args.length == 2) {
            generation = Integer.parseInt(args[0]);
            size = Float.parseFloat(args[1]);
        }

        double line = orginalLineLength;
        double count = 1;
        while (true) {

            // calculates the orginal line length for generation 0
            // if the size argument is less then 1
            if (size < 1) {
                double fillY = 0;
                double fillX = 0;
                double tempLine = line;
                double upperBound = windowSize/2;
                double lowerBound = upperBound*0.9;
                
                for (int i = 0; i <= generation; i++) {
                    if (i % 2 == 0) {
                        fillX += line/2;
                        line *= size;
                    }
                    else if (i % 2 == 1) {
                        fillY += line/2;
                        line *= size;
                    }
                }
                if (fillX > windowSize/2 || fillY > windowSize/2) {
                    line = tempLine - 0.001;
                }
                // increase line length if the image is to small
                if (fillX < 360 || fillY < 360) {
                    line = tempLine + 0.01;
                }
                // optimal line length had been found break loop
                if ((fillX > 360 && fillX < windowSize/2 && fillY < windowSize/2) || (fillY > 360 && fillY <  windowSize/2  && fillX <  windowSize/2 )) {
                    orginalLineLength = tempLine;
                    break;
                }
            }

            // calculates the orginal line length for generation 0
            // if the size argument if greater than 0
            if (size > 1) {
                double fillY = 0;
                double fillX = 0;
                double tempLine = line;
                double upperBound = windowSize/2;
                double lowerBound = upperBound*0.7;
                // loops to the last generation and measures how close the current gens
                // line is to the window
                for (int i = 0; i <= generation; i++) {
                    if (i % 2 == 0) {
                        fillX += line/2;
                        line *= size;
                    }
                    else if (i % 2 == 1) {
                        fillY += line/2;
                        line *= size;
                    }
                }
                // lower line length if it will be outside the window
                if (fillX > upperBound || fillY > upperBound) {
                    line = tempLine - 0.001;
                }
                // increase line length if the image is to small
                if (fillX < lowerBound || fillY < lowerBound) {
                    line = tempLine + 0.01;
                }
                // optimal line length had been found break loop
                if ((fillX > lowerBound && fillX < upperBound && fillY < upperBound) || (fillY > lowerBound && fillY < upperBound && fillX < upperBound)) {
                    orginalLineLength = tempLine;
                    break;
                }
            }
            
            
            // calculates the orginal line length for generation 0
            // if the size argument is 1
            if (size == 1) {
                double fillY = 0;
                double fillX = 0;
                double tempLine = line;
                double upperBound = windowSize/2;
                double lowerBound = upperBound-100;

                for (int i = 0; i <= generation; i++) {
                    if (i % 2 == 0) {
                        fillX += line/2;
                        line *= size;
                    }
                    else if (i % 2 == 1) {
                        fillY += line/2;
                        line *= size;
                    }
                }
                if (fillX >  upperBound  || fillY >  upperBound ) {
                    orginalLineLength -= 0.01;
                    continue;
                }
                if (((fillX > lowerBound && fillX <  upperBound ) && fillY <  upperBound) || ((fillY > lowerBound && fillY <  upperBound) && fillX <  upperBound)) {
                    orginalLineLength = tempLine;
                    break;
                }
                else {
                    count++;
                    line = orginalLineLength;
                    for (int i = 1; i < count; i++) {
                        line += 0.2;
                    }
                }
            }

        }

        JFrame frame = new JFrame();
        Toothpicks game = new Toothpicks();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(new Dimension((int) windowSize, (int) windowSize));
        frame.add(game);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.invalidate();
    }
}


