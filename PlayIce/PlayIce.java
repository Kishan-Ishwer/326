import java.util.*;

public class PlayIce {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean dictionaryFound = false;
        boolean restrictionsFound = false;
        ArrayList<Character> dictionary = new ArrayList<Character>();
        ArrayList<String[]> restrictions = new ArrayList<String[]>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!dictionaryFound) {
                for (char c: line.toCharArray()) {
                    dictionary.add(c);
                }
                dictionaryFound = true;
            }
            else if (line.length() == 0) {
                restrictionsFound = true;
            }
            else if (dictionaryFound && !restrictionsFound) {
                restrictions.add(line.split("\\s+"));
            }
            else {
                if (tryParseInt(line)) {
                    System.out.println(Integer.parseInt(line));
                } else {
                    String answer = checkValid(dictionary, restrictions, line);
                    System.out.println(answer);
                }
            }
        }
        scanner.close();
    }

    private static String checkValid(ArrayList<Character> dict, ArrayList<String[]> rules, String input) {
        for (int i = 0; i < input.length(); i++) {
            if (!checkDictionary(input.charAt(i), dict)) {
                return "Invalid";
            }
            if (!checkRestrictions(input, i, rules)) {
                return "Invalid";
            }
        }
        return "Valid";
    }

    private static boolean checkRestrictions(String input, int pos, ArrayList<String[]> rules) {
        for (int i = 0; i < rules.size(); i++) {
            //System.out.println(Arrays.toString(rules.get(i)));
            if (rules.get(i).length == 1) {
                int count = 0;
                for (int j = 0; j < rules.get(i)[0].length(); j++) {
                    //System.out.println(count);
                    if (count == rules.get(i)[0].length()) {
                        return false;
                    }
                    if (pos+rules.get(i)[0].length()-1 > input.length()-1) {
                        break;
                    }
                    if (Character.valueOf(input.charAt(pos+j)).compareTo(Character.valueOf(rules.get(i)[0].charAt(j))) == 0) {
                        count++;
                        //System.out.println(count);
                    }
                }
                if (count == rules.get(i)[0].length()) {
                    return false;
                }
            }
            else if (rules.get(i).length > 1) {
                int count = 0;
                for (int j = 0; j < rules.get(i)[0].length(); j++) {
                    //System.out.println(count);
                    if (count == rules.get(i)[0].length()) {
                        for (int n = 1; n < rules.get(i).length; n++) {
                            if (pos-rules.get(i)[n].length()+1 < 0) {
                                return false;
                            }
                            if (input.substring(pos-rules.get(i)[n].length(), pos).equals(rules.get(i)[n])) {
                                continue;
                            } 
                        }
                    }
                    if (pos+rules.get(i)[0].length()-1 > input.length()-1) {
                        break;
                    }
                    if (Character.valueOf(input.charAt(pos+j)).compareTo(Character.valueOf(rules.get(i)[0].charAt(j))) == 0) {
                        count++;
                        //System.out.println(count);
                    }
                }
                if (count == rules.get(i)[0].length()) {
                    for (int n = 1; n < rules.get(i).length; n++) {
                        if (pos-rules.get(i)[n].length() < 0) {
                            return false;
                        }
                        if (input.substring(pos-rules.get(i)[n].length(), pos).equals(rules.get(i)[n])) {
                            continue;
                        } 
                    }
                }
            }
        }
        return true;
    }

    private static boolean checkDictionary(Character input, ArrayList<Character> dict) {
        for (int i = 0; i < dict.size(); i++) {
            if (Character.valueOf(input).compareTo(Character.valueOf(dict.get(i))) == 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);  
            return true;  
        } catch (NumberFormatException e) {  
            return false;  
        }
    }
}