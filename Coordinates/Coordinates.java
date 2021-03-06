import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Class Coordinates
 * Takes in a String from the input line
 * which will be coordniates in diffrent forms
 * And processes it to return coordinates
 * in standard form
 *
 * @author Kishan Ishwer 8271570
 */
public class Coordinates {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // reads in line from standard input
        while (input.hasNextLine()){
            // splits the input at the whitespace
            // and stores each split into a list
            String line = input.nextLine();
            String[] coordList = line.split(" ");

            // loop through the each part fo the coordinate list
            // and remove any commas
            for (int i = 0; i < coordList.length; i++) {
                int index = coordList[i].indexOf(",");
                if (index > -1){
                    coordList[i] = coordList[i].substring(0, index);
                    continue;
                }
            }

            int length = coordList.length;
            String finalLatLong;

            // based on the the length of the coordniate list
            // which we have stored
            // we call diffrent methods to process the coordinates

            // each of these method being called below return a string
            // of the final processed coordinate in standard form
            if (length == 2) {
                finalLatLong = length02(coordList);
                if (finalLatLong != null) {
                    System.out.println(finalLatLong);
                }
                else {
                    System.out.println("Unable to process: " + line);
                }
            }

            else if (length == 4) {
                finalLatLong = length04(coordList);
                if (finalLatLong != null) {
                    System.out.println(finalLatLong);
                }
                else {
                    System.out.println("Unable to process: " + line);
                }
            }

            else if (length == 6) {
                finalLatLong = length06(coordList);
                if (finalLatLong != null) {
                    System.out.println(finalLatLong);
                }
                else {
                    System.out.println("Unable to process: " + line);
                }
            }

            else if (length == 8) {
                finalLatLong = length08(coordList);
                if (finalLatLong != null) {
                    System.out.println(finalLatLong);
                }
                else {
                    System.out.println("Unable to process: " + line);
                }
            }

            else if (length == 10) {
                finalLatLong = length10(coordList);
                if (finalLatLong != null) {
                    System.out.println(finalLatLong);
                }
                else {
                    System.out.println("Unable to process: " + line);
                }
            }

            else if (length == 14) {
                finalLatLong = length14(coordList);
                if (finalLatLong != null) {
                    System.out.println(finalLatLong);
                }
                else {
                    System.out.println("Unable to process: " + line);
                }
            }

            else {
                System.out.println("Unable to process: " + line); 
            }
        }
    }

    /**
     * method called if the coordList has a length of 2 
     *
     * checks if the input is valid and calls the formating method if
     * the input is valid
     *
     * @param coordList which is a list of strings, that contain the split upp input
     * @return String of the final processed coordinate in standard form
     */
    public static String length02(String[] coordList) {
        boolean floatCheckL2 = false;
        boolean floatCheck2L2 = false;
        // checks if the first part fo the list is able to be parsed to a float from a string
        if (tryParseFloat(coordList[0])) {
            floatCheckL2 = 90 >= Float.parseFloat(coordList[0]) && -90 <= Float.parseFloat(coordList[0]);
        }
        else return null;
        // checks if the second part of the input can be parsed to float from string
        if (tryParseFloat(coordList[1])) {
            floatCheck2L2 = true;
        }
        else return null;
        if (floatCheck2L2 && floatCheckL2) {
            // retrive the latitude and longitude from the coordList
            // and call the formating method
            float lat = Float.parseFloat(coordList[0]);
            float longitude = Float.parseFloat(coordList[1]);
            String latLong = formatLatLong(lat, longitude);
            return latLong;
        }
        else if (coordList[0].contains("??")) {
            // retrive the latitude and longitude from the coordList
            // if they have degree symbols we remove them
            // and call the formating method
            int index01 = coordList[0].indexOf("??");
            float lat = Float.parseFloat(coordList[0].substring(0, index01));
            int index02 = coordList[1].indexOf("??");
            float longitude = Float.parseFloat(coordList[3].substring(0, index02));
            String latLong = formatLatLong(lat, longitude);
            //System.out.println(latLong);
            return latLong;
        }
        else return null;
    }

    /**
     * method called if the coordList has a length of 4.
     *
     * checks if the input is valid and calls the formating method if
     * the input is valid.
     *
     * @param coordList which is a list of strings, that contain the split upp input
     * @return String of the final processed coordinate in standard form
     */
    public static String length04(String[] coordList) {
        boolean floatCheck = false;
        boolean leftToRight = true;
        // checks if the the compass direction indicators are correct
        // so one north or south and one eash and west
        if ((coordList[1].equals("N") || coordList[1].equals("n") || coordList[1].equals("S") || coordList[1].equals("s") || coordList[1].equals("North") || coordList[1].equals("north") || coordList[1].equals("South") || coordList[1].equals("south")) && (coordList[3].equals("W") || coordList[3].equals("w") || coordList[3].equals("E") || coordList[3].equals("e") || coordList[3].equals("West") || coordList[3].equals("west") || coordList[3].equals("East") || coordList[3].equals("east"))) {
            leftToRight = true;
            // checks if the latitude is between -90 and 90
            if (tryParseFloat(coordList[0])) {
                floatCheck = (90 >= Float.parseFloat(coordList[0])) && (0 <= Float.parseFloat(coordList[0]));
            } else {
                return null;
            }
            if (tryParseFloat(coordList[2])) {
                if (Float.parseFloat(coordList[2]) < 0) {
                    return null;
                }
            } else {
                return null;
            }
            
            
        }
        else if ((coordList[3].equals("N") || coordList[3].equals("n") || coordList[3].equals("S") || coordList[3].equals("s") || coordList[3].equals("North") || coordList[3].equals("north") || coordList[3].equals("South") || coordList[3].equals("south")) && (coordList[1].equals("W") || coordList[1].equals("w") || coordList[1].equals("E") || coordList[1].equals("e") || coordList[1].equals("West") || coordList[1].equals("west") || coordList[1].equals("East") || coordList[1].equals("east"))) {
            if (tryParseFloat(coordList[0])) {
                floatCheck = (90 >= Float.parseFloat(coordList[2])) && (-90 <= Float.parseFloat(coordList[2]));
                leftToRight = false;
            } else {
                return null;
            }
        }
        
        else return null;
            
        boolean floatCheck2 = tryParseFloat(coordList[2]);

        // gives the latitude value the correct sign based on the north or south and west and east values
        if (floatCheck2 && floatCheck) {
            float degree01 = Float.parseFloat(coordList[0]);
            float degree02 = Float.parseFloat(coordList[2]);
            if (coordList[1].equals("S") || coordList[1].equals("s") || coordList[1].equals("W") || coordList[1].equals("w") || coordList[1].equals("South") || coordList[1].equals("south") || coordList[1].equals("West") || coordList[1].equals("west")) {
                degree01 = -degree01;
            }
            if (coordList[3].equals("S") || coordList[3].equals("s") || coordList[3].equals("W") || coordList[3].equals("w") || coordList[3].equals("South") || coordList[3].equals("south") || coordList[3].equals("West") || coordList[3].equals("west")) {
                degree02 = -degree02;
            }
            if (leftToRight) {
                String latLong = formatLatLong(degree01, degree02);
                return latLong;
            } else {
                String latLong = formatLatLong(degree02, degree01);
                return latLong;
            }
        }
        // if the number value has a degrees symbol process removes that before processing
        else if (coordList[0].contains("??")) {
            int index01 = coordList[0].indexOf("??");
            float degree01 = Float.parseFloat(coordList[0].substring(0, index01));
            int index02 = coordList[2].indexOf("??");
            float degree02 = Float.parseFloat(coordList[3].substring(0, index02));
            if (coordList[1].equals("S") || coordList[1].equals("s") || coordList[1].equals("W") || coordList[1].equals("w") || coordList[1].equals("South") || coordList[1].equals("south") || coordList[1].equals("West") || coordList[1].equals("west")) {
                degree01 = -degree01;
            }
            if (coordList[3].equals("S") || coordList[3].equals("s") || coordList[3].equals("W") || coordList[3].equals("w") || coordList[3].equals("South") || coordList[3].equals("south") || coordList[3].equals("West") || coordList[3].equals("west")) {
                degree02 = -degree02;
            }
            if (leftToRight) {
                String latLong = formatLatLong(degree01, degree02);
                return latLong;
            } else {
                String latLong = formatLatLong(degree02, degree01);
                return latLong;
            }
        }
        
        else return null;
    }

    /**
     * method called if the coordList has a length of 6.
     *
     * checks if the input is valid and calls the formating method if
     * the input is valid.
     *
     * @param coordList which is a list of strings, that contain the split upp input
     * @return String of the final processed coordinate in standard form
     */
    public static String length06(String[] coordList) {
        boolean leftToRight = true;
        boolean parseCheck1 = tryParseFloat(coordList[0]) && tryParseFloat(coordList[1]); 
        boolean parseCheck2 = tryParseFloat(coordList[3]) && tryParseFloat(coordList[4]);
        // checks if the the compass direction indicators are correct
        // so one north or south and one eash and west
        if ((coordList[2].equals("N") || coordList[2].equals("n") || coordList[2].equals("S") || coordList[2].equals("s") || coordList[2].equals("North") || coordList[2].equals("north") || coordList[2].equals("South") || coordList[2].equals("south")) && (coordList[5].equals("W") || coordList[5].equals("w") || coordList[5].equals("E") || coordList[5].equals("e") || coordList[5].equals("West") || coordList[5].equals("west") || coordList[5].equals("East") || coordList[5].equals("east"))) {
            leftToRight = true;
        }
        else if ((coordList[5].equals("N") || coordList[5].equals("n") || coordList[5].equals("S") || coordList[5].equals("s") || coordList[5].equals("North") || coordList[5].equals("north") || coordList[5].equals("South") || coordList[5].equals("south")) && (coordList[2].equals("W") || coordList[2].equals("w") || coordList[2].equals("E") || coordList[2].equals("e") || coordList[2].equals("West") || coordList[2].equals("west") || coordList[2].equals("East") || coordList[2].equals("east"))) {
            leftToRight = false;
        }
        else return null;

        // removes the degrees and minutes signs and converts
        // from degrees minutes to latidue and longitude value 
        if (coordList[0].contains("??")) {
            int index01 = coordList[0].indexOf("??");
            int degree01 = Integer.parseInt(coordList[0].substring(0, index01));
            int index02 = coordList[3].indexOf("??");
            int degree02 = Integer.parseInt(coordList[3].substring(0, index02));
            if (degree01 < 0 || degree02 < 0) {
                return null;
            }
            if (coordList[1].contains("???")) {
                index01 = coordList[1].indexOf("???");
                Float minutes01 = Float.parseFloat(coordList[1].substring(0, index01));
                index02 = coordList[4].indexOf("???");
                Float minutes02 = Float.parseFloat(coordList[4].substring(0, index02));
                if (((minutes01 > 60) || (minutes01 < 0)) || ((minutes02 > 60) || (minutes02 < 0))) {
                    return null;
                }
                if (leftToRight) {
                    if (degree01 + (minutes01/60) < 90) {
                        Float lat = degree01 + (minutes01/60);
                        Float longitude = degree02 + (minutes02/60);
                        if (coordList[2].equals("S") || coordList[2].equals("s") || coordList[2].equals("W") || coordList[2].equals("w") || coordList[2].equals("South") || coordList[2].equals("south") || coordList[2].equals("West") || coordList[2].equals("west")) {
                            lat = -lat;
                        }
                        if (coordList[5].equals("S") || coordList[5].equals("s") || coordList[5].equals("W") || coordList[5].equals("w") || coordList[5].equals("South") || coordList[5].equals("south") || coordList[5].equals("West") || coordList[5].equals("west")) {
                            longitude = -longitude;
                        }
                        String latLong = formatLatLong(lat, longitude);
                        return latLong;
                    }
                    else return null;
                } else {
                    if (degree02 + (minutes02/60) < 90) {
                        Float lat = degree02 + (minutes02/60);
                        Float longitude = degree01 + (minutes01/60);
                        if (coordList[2].equals("S") || coordList[2].equals("s") || coordList[2].equals("W") || coordList[2].equals("w") || coordList[2].equals("South") || coordList[2].equals("south") || coordList[2].equals("West") || coordList[2].equals("west")) {
                            lat = -lat;
                        }
                        if (coordList[5].equals("S") || coordList[5].equals("s") || coordList[5].equals("W") || coordList[5].equals("w") || coordList[5].equals("South") || coordList[5].equals("south") || coordList[5].equals("West") || coordList[5].equals("west")) {
                            longitude = -longitude;
                        }
                        String latLong = formatLatLong(lat, longitude);
                        return latLong;
                    }
                    else return null;
                }
            }
            else return null;    
        }
        // if the values do not have degrees or minutes on them
        // we process the same as above
        else if (parseCheck1 && parseCheck2) {
            int degree01 = Integer.parseInt(coordList[0]);
            int degree02 = Integer.parseInt(coordList[3]);
            Float minutes01 = Float.parseFloat(coordList[1]);
            Float minutes02 = Float.parseFloat(coordList[4]);
            if (((minutes01 > 60) || (minutes01 < 0)) || ((minutes02 > 60) || (minutes02 < 0))) {
                return null;
            }
            if (leftToRight) {
                if (degree01 + (minutes01/60) < 90) {
                    Float lat = degree01 + (minutes01/60);
                    Float longitude = degree02 + (minutes02/60);
                    if (coordList[2].equals("S") || coordList[2].equals("s") || coordList[2].equals("W") || coordList[2].equals("w") || coordList[2].equals("South") || coordList[2].equals("south") || coordList[2].equals("West") || coordList[2].equals("west")) {
                        lat = -lat;
                    }
                    if (coordList[5].equals("S") || coordList[5].equals("s") || coordList[5].equals("W") || coordList[5].equals("w") || coordList[5].equals("South") || coordList[5].equals("south") || coordList[5].equals("West") || coordList[5].equals("west")) {
                        longitude = -longitude;
                    }
                    String latLong = formatLatLong(lat, longitude);
                    return latLong;
                }
                else return null;
            } else {
                if (degree02 + (minutes02/60) < 90) {
                    Float lat = degree02 + (minutes02/60);
                    Float longitude = degree01 + (minutes01/60);
                    if (coordList[2].equals("S") || coordList[2].equals("s") || coordList[2].equals("W") || coordList[2].equals("w") || coordList[2].equals("South") || coordList[2].equals("south") || coordList[2].equals("West") || coordList[2].equals("west")) {
                        lat = -lat;
                    }
                    if (coordList[5].equals("S") || coordList[5].equals("s") || coordList[5].equals("W") || coordList[5].equals("w") || coordList[5].equals("South") || coordList[5].equals("south") || coordList[5].equals("West") || coordList[5].equals("west")) {
                        longitude = -longitude;
                    }
                    String latLong = formatLatLong(lat, longitude);
                    return latLong;
                }
                else return null;
            }
        }
        else return null;
    }

    /**
     * method called if the coordList has a length of 8.
     *
     * checks if the input is valid and calls the formating method if
     * the input is valid.
     *
     * @param coordList which is a list of strings, that contain the split upp input
     * @return String of the final processed coordinate in standard form
     */
    public static String length08(String[] coordList) {
        boolean parseCheck1 = tryParseFloat(coordList[0]) && tryParseFloat(coordList[1]) && tryParseFloat(coordList[2]); 
        boolean parseCheck2 = tryParseFloat(coordList[4]) && tryParseFloat(coordList[5]) && tryParseFloat(coordList[6]);
        boolean leftToRight = true;
        if ((coordList[3].equals("N") || coordList[3].equals("n") || coordList[3].equals("S") || coordList[3].equals("s") || coordList[3].equals("North") || coordList[3].equals("north") || coordList[3].equals("South") || coordList[3].equals("south")) && (coordList[7].equals("W") || coordList[7].equals("w") || coordList[7].equals("E") || coordList[7].equals("e") || coordList[7].equals("West") || coordList[7].equals("west") || coordList[7].equals("East") || coordList[7].equals("east"))) {
            leftToRight = true;
        }
        else if ((coordList[7].equals("N") || coordList[7].equals("n") || coordList[7].equals("S") || coordList[7].equals("s") || coordList[7].equals("North") || coordList[7].equals("north") || coordList[7].equals("South") || coordList[7].equals("south")) && (coordList[3].equals("W") || coordList[3].equals("w") || coordList[3].equals("E") || coordList[3].equals("e") || coordList[3].equals("West") || coordList[3].equals("west") || coordList[3].equals("East") || coordList[3].equals("east"))) {
            leftToRight = false;
        }
        else return null;
        // pretty much exactly similar to the length06 method
        // only takes into account seconds now
        if (coordList[0].contains("??")) {
            int index01 = coordList[0].indexOf("??");
            Float degree01 = Float.parseFloat(coordList[0].substring(0, index01));
            int index02 = coordList[4].indexOf("??");
            Float degree02 = Float.parseFloat(coordList[4].substring(0, index02));
            if (coordList[1].contains("???")) {
                index01 = coordList[1].indexOf("???");
                Float minutes01 = Float.parseFloat(coordList[1].substring(0, index01));
                index02 = coordList[5].indexOf("???");
                Float minutes02 = Float.parseFloat(coordList[5].substring(0, index02));
                if (((minutes01 > 60) || (minutes01 < 0)) || ((minutes02 > 60) || (minutes02 < 0))) {
                    return null;
                }
                if (coordList[2].contains("???")) {
                    index01 = coordList[2].indexOf("???");
                    Float seconds01 = Float.parseFloat(coordList[2].substring(0, index01));
                    index02 = coordList[6].indexOf("???");
                    Float seconds02 = Float.parseFloat(coordList[6].substring(0, index02));
                    if (((seconds01 > 60) || (seconds01 < 0)) || ((seconds02 > 60) || (seconds02 < 0))) {
                        return null;
                    }
                    if (leftToRight) {
                        Float lat = degree01 + (minutes01/60) + (seconds01/3600);
                        Float longitude = degree02 + (minutes02/60) + (seconds02/3600);
                        if (lat < 90) {
                            if (coordList[3].equals("S") || coordList[3].equals("s") || coordList[3].equals("W") || coordList[3].equals("w") || coordList[3].equals("South") || coordList[3].equals("south") || coordList[3].equals("West") || coordList[3].equals("west")) {
                                lat = -lat;
                            }
                            if (coordList[7].equals("S") || coordList[7].equals("s") || coordList[7].equals("W") || coordList[7].equals("w") || coordList[7].equals("South") || coordList[7].equals("south") || coordList[7].equals("West") || coordList[7].equals("west")) {
                                longitude = -longitude;
                            }
                            String latLong = formatLatLong(lat, longitude);
                            return latLong;
                        }
                        else return null;
                    } else {
                        Float lat = degree02 + (minutes02/60) + (seconds02/3600);
                        Float longitude = degree01 + (minutes01/60) + (seconds01/3600);
                        if (lat < 90) {
                            if (coordList[3].equals("S") || coordList[3].equals("s") || coordList[3].equals("W") || coordList[3].equals("w") || coordList[3].equals("South") || coordList[3].equals("south") || coordList[3].equals("West") || coordList[3].equals("west")) {
                                lat = -lat;
                            }
                            if (coordList[7].equals("S") || coordList[7].equals("s") || coordList[7].equals("W") || coordList[7].equals("w") || coordList[7].equals("South") || coordList[7].equals("south") || coordList[7].equals("West") || coordList[7].equals("west")) {
                                longitude = -longitude;
                            }
                            String latLong = formatLatLong(lat, longitude);
                            return latLong;
                        }
                        else return null;
                    }
                }
                else return null;
            }
            else return null;
        }
        else if(parseCheck1 && parseCheck2) {
            Float degree01 = Float.parseFloat(coordList[0]);
            Float degree02 = Float.parseFloat(coordList[4]);
            Float minutes01 = Float.parseFloat(coordList[1]);
            Float minutes02 = Float.parseFloat(coordList[5]);
            Float seconds01 = Float.parseFloat(coordList[2]);
            Float seconds02 = Float.parseFloat(coordList[6]);
            if (((minutes01 > 60) || (minutes01 < 0)) || ((minutes02 > 60) || (minutes02 < 0))) {
                return null;
            }
            if (((seconds01 > 60) || (seconds01 < 0)) || ((seconds02 > 60) || (seconds02 < 0))) {
                return null;
            }
            if (leftToRight) {
                Float lat = degree01 + (minutes01/60) + (seconds01/3600);
                Float longitude = degree02 + (minutes02/60) + (seconds02/3600);
                if (lat < 90) {
                    if (coordList[3].equals("S") || coordList[3].equals("s") || coordList[3].equals("W") || coordList[3].equals("w") || coordList[3].equals("South") || coordList[3].equals("south") || coordList[3].equals("West") || coordList[3].equals("west")) {
                        lat = -lat;
                    }
                    if (coordList[7].equals("S") || coordList[7].equals("s") || coordList[7].equals("W") || coordList[7].equals("w") || coordList[7].equals("South") || coordList[7].equals("south") || coordList[7].equals("West") || coordList[7].equals("west")) {
                        longitude = -longitude;
                    }
                    String latLong = formatLatLong(lat, longitude);
                    return latLong;
                }
                else return null;
            } else {
                Float lat = degree02 + (minutes02/60) + (seconds02/3600);
                Float longitude = degree01 + (minutes01/60) + (seconds01/3600);
                if (lat < 90) {
                    if (coordList[3].equals("S") || coordList[3].equals("s") || coordList[3].equals("W") || coordList[3].equals("w") || coordList[3].equals("South") || coordList[3].equals("south") || coordList[3].equals("West") || coordList[3].equals("west")) {
                        lat = -lat;
                    }
                    if (coordList[7].equals("S") || coordList[7].equals("s") || coordList[7].equals("W") || coordList[7].equals("w") || coordList[7].equals("South") || coordList[7].equals("south") || coordList[7].equals("West") || coordList[7].equals("west")) {
                        longitude = -longitude;
                    }
                    String latLong = formatLatLong(lat, longitude);
                    return latLong;
                }
                else return null;
            } 
        }
        else return null;     
    }

    /**
     * method called if the coordList has a length of 8.
     *
     * checks if the input is valid and calls the formating method if
     * the input is valid.
     *
     * @param coordList which is a list of strings, that contain the split upp input
     * @return String of the final processed coordinate in standard form
     */  
    public static String length10(String[] coordList) {
        boolean leftToRight = true;
        boolean formatCheck1 = Pattern.matches("^[Dd]$", coordList[1]) && Pattern.matches("^[Mm]$", coordList[3]);
        boolean formatCheck2 = Pattern.matches("^[Dd]$", coordList[6]) && Pattern.matches("^[Mm]$", coordList[8]);
        boolean parseCheck1 = tryParseFloat(coordList[0]) && tryParseFloat(coordList[2]); 
        boolean parseCheck2 = tryParseFloat(coordList[5]) && tryParseFloat(coordList[7]);
        
        if ((coordList[4].equals("N") || coordList[4].equals("n") || coordList[4].equals("S") || coordList[4].equals("s") || coordList[4].equals("North") || coordList[4].equals("north") || coordList[4].equals("South") || coordList[4].equals("south")) && (coordList[9].equals("W") || coordList[9].equals("w") || coordList[9].equals("E") || coordList[9].equals("e") || coordList[9].equals("West") || coordList[9].equals("west") || coordList[9].equals("East") || coordList[9].equals("east"))) {
            leftToRight = true;
        }
        else if ((coordList[9].equals("N") || coordList[9].equals("n") || coordList[9].equals("S") || coordList[9].equals("s") || coordList[9].equals("North") || coordList[9].equals("north") || coordList[9].equals("South") || coordList[9].equals("south")) && (coordList[4].equals("W") || coordList[4].equals("w") || coordList[4].equals("E") || coordList[4].equals("e") || coordList[4].equals("West") || coordList[4].equals("west") || coordList[4].equals("East") || coordList[4].equals("east"))) {
            leftToRight = false;
        }
        else return null;
        // process simliar to the length06 method but if the input came in with d m  rather than
        // symbols
        if (formatCheck1 && formatCheck2) {
            if (parseCheck1 && parseCheck2) {
                float degree01 = Float.parseFloat(coordList[0]);
                float degree02 = Float.parseFloat(coordList[5]);
                float minutes01 = Float.parseFloat(coordList[2]);
                float minutes02 = Float.parseFloat(coordList[7]);
                if (((minutes01 > 60) || (minutes01 < 0)) || ((minutes02 > 60) || (minutes02 < 0))) {
                    return null;
                }
                if (leftToRight) {
                    
                    Float lat = degree01 + (minutes01/60);
                    Float longitude= degree02 + (minutes02/60);
                    if (lat < 90) {
                        if (coordList[4].equals("S") || coordList[4].equals("s") || coordList[4].equals("W") || coordList[4].equals("w") || coordList[4].equals("South") || coordList[4].equals("south") || coordList[4].equals("West") || coordList[4].equals("west")) {
                            lat = -lat;
                        }
                        if (coordList[9].equals("S") || coordList[9].equals("s") || coordList[9].equals("W") || coordList[9].equals("w") || coordList[9].equals("South") || coordList[9].equals("south") || coordList[9].equals("West") || coordList[9].equals("west")) {
                            longitude = -longitude;
                        }
                        String latLong = formatLatLong(lat, longitude);
                        return latLong;
                    }
                    else return null;
                } else {
                    Float lat = degree02 + (minutes02/60);
                    Float longitude = degree01 + (minutes01/60);
                    if (lat < 90) {
                        if (coordList[4].equals("S") || coordList[4].equals("s") || coordList[4].equals("W") || coordList[4].equals("w") || coordList[4].equals("South") || coordList[4].equals("south") || coordList[4].equals("West") || coordList[4].equals("west")) {
                            lat = -lat;
                        }
                        if (coordList[9].equals("S") || coordList[9].equals("s") || coordList[9].equals("W") || coordList[9].equals("w") || coordList[9].equals("South") || coordList[9].equals("south") || coordList[9].equals("West") || coordList[9].equals("west")) {
                            longitude = -longitude;
                        }
                        String latLong = formatLatLong(lat, longitude);
                        return latLong;
                    }
                    else return null;
                }
            }
            else return null;
        }
        else return null;
        
    }
    
    /**
     * method called if the coordList has a length of 14.
     *
     * checks if the input is valid and calls the formating method if
     * the input is valid.
     *
     * @param coordList which is a list of strings, that contain the split upp input
     * @return String of the final processed coordinate in standard form
     */
    public static String length14(String[] coordList) {
        boolean leftToRight = true;
        boolean parseCheck1 = tryParseFloat(coordList[0]) && tryParseFloat(coordList[2]) && tryParseFloat(coordList[4]); 
        boolean parseCheck2 = tryParseFloat(coordList[7]) && tryParseFloat(coordList[9]) && tryParseFloat(coordList[11]);
        boolean formatCheck1 = Pattern.matches("^[Dd]$", coordList[1]) && Pattern.matches("^[Mm]$", coordList[3]) && Pattern.matches("^[Ss]$", coordList[5]);
        boolean formatCheck2 = Pattern.matches("^[Dd]$", coordList[8]) && Pattern.matches("^[Mm]$", coordList[10]) && Pattern.matches("^[Ss]$", coordList[12]);

        
        if ((coordList[6].equals("N") || coordList[6].equals("n") || coordList[6].equals("S") || coordList[6].equals("s") || coordList[6].equals("North") || coordList[6].equals("north") || coordList[6].equals("South") || coordList[6].equals("south")) && (coordList[13].equals("W") || coordList[13].equals("w") || coordList[13].equals("E") || coordList[13].equals("e") || coordList[13].equals("West") || coordList[13].equals("west") || coordList[13].equals("East") || coordList[13].equals("east"))) {
            leftToRight = true;
        }
        else if ((coordList[13].equals("N") || coordList[13].equals("n") || coordList[13].equals("S") || coordList[13].equals("s") || coordList[13].equals("North") || coordList[13].equals("north") || coordList[13].equals("South") || coordList[13].equals("south")) && (coordList[6].equals("W") || coordList[6].equals("w") || coordList[6].equals("E") || coordList[6].equals("e") || coordList[6].equals("West") || coordList[6].equals("west") || coordList[6].equals("East") || coordList[6].equals("east"))) {
            leftToRight = false;
        }
        else return null;
        // process simliar to the length06 method but if the input came in with d m s rather than
        // symbols
        if (parseCheck1 && parseCheck2) {
            float degree01 = Float.parseFloat(coordList[0]);
            float degree02 = Float.parseFloat(coordList[7]);
            float minutes01 = Float.parseFloat(coordList[2]);
            float minutes02 = Float.parseFloat(coordList[9]);
            float seconds01 = Float.parseFloat(coordList[4]);
            float seconds02 = Float.parseFloat(coordList[11]);
            if (((minutes01 > 60) || (minutes01 < 0)) || ((minutes02 > 60) || (minutes02 < 0))) {
                return null;
            }
            if (((seconds01 > 60) || (seconds01 < 0)) || ((seconds02 > 60) || (seconds02 < 0))) {
                return null;
            }
            
            if (formatCheck2 && formatCheck1) {
                if (leftToRight) {
                    Float lat = degree01 + (minutes01/60) + (seconds01/3600);
                    Float longitude= degree02 + (minutes02/60) + (seconds02/3600);
                    if (lat < 90) {
                        if (coordList[6].equals("S") || coordList[6].equals("s") || coordList[6].equals("W") || coordList[6].equals("w") || coordList[6].equals("South") || coordList[6].equals("south") || coordList[6].equals("West") || coordList[6].equals("west")) {
                            lat = -lat;
                        }
                        if (coordList[13].equals("S") || coordList[13].equals("s") || coordList[13].equals("W") || coordList[13].equals("w") || coordList[13].equals("South") || coordList[13].equals("south") || coordList[13].equals("West") || coordList[13].equals("west")) {
                            longitude = -longitude;
                        }
                        String latLong = formatLatLong(lat, longitude);
                        return latLong;
                    }
                    else return null;
                } else {
                    Float lat = degree02 + (minutes02/60) + (seconds02/3600);
                    Float longitude = degree01 + (minutes01/60) + (seconds01/3600);
                    if (lat < 90) {
                        if (coordList[6].equals("S") || coordList[6].equals("s") || coordList[6].equals("W") || coordList[6].equals("w") || coordList[6].equals("South") || coordList[6].equals("south") || coordList[6].equals("West") || coordList[6].equals("west")) {
                            lat = -lat;
                        }
                        if (coordList[13].equals("S") || coordList[13].equals("s") || coordList[13].equals("W") || coordList[13].equals("w") || coordList[13].equals("South") || coordList[13].equals("south") || coordList[13].equals("West") || coordList[13].equals("west")) {
                            longitude = -longitude;
                        }
                        String latLong = formatLatLong(lat, longitude);
                        return latLong;
                    }
                    else return null;
                }
            }
            else return null;
        }
        else return null;
    }

    /**
     * A method that is sent 2 values of latitude and longitude
     * and processes those parameters into the standard form
     * of latitude and longitude.
     *
     * @param latitude value as a float
     * @param longitude value as a float
     * @return String. the final coordinates in standard form
     */
    public static String formatLatLong(float latitude, float longitude) {
        String lat = String.valueOf(latitude);
        int decimalIndexLat = lat.indexOf(".");

        // add the trailing zeros if required to latitude
        // and add the appropriate sign
        for (int i = lat.length()-1; i-decimalIndexLat < 6; i++) {
            lat = lat.concat("0");
            
        }
        
        String log = String.valueOf(longitude);
        float remainder = longitude%180;
        float total = (longitude-remainder) / 180;
        // calculate the longitude position
        // and add the correct sign
        if (total % 2 == 0) {
            log = String.valueOf(remainder);
        }
        else if (total % 2 > 0) {
            log = String.valueOf(remainder-180);
        }
        else if (total % 2 < 0) {
            log = String.valueOf(remainder+180);
        }

        int decimalIndexLong = log.indexOf(".");
        // add trailing zeros to the end of longitude
        for (int i = log.length()-1; i-decimalIndexLong < 6; i++) {
            log = log.concat("0"); 
        }
            
        //return the final latitude and longitude in standard form
        return(lat + ", " + log);
    }

    /**
     * a method to check if we can parse a string to a float
     *
     * @param String value of what we are trying to parse to float
     * @return boolean if we were able to parse the string to a float
     */
    public static boolean tryParseFloat(String value) {  
        try {  
            Float.parseFloat(value);  
            return true;  
        } catch (NumberFormatException e) {  
            return false;  
        }  
    }
}
