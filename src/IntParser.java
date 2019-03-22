// Inspiration: https://www.codewars.com/kata/525c7c5ab6aecef16e0001a5

import java.util.HashMap;

public class IntParser {
    private static final HashMap<String, Integer> DICTIONARY = new HashMap<>() {
        {
            put("zero", 0);
            put("one", 1);      put("eleven", 11);       put("ten", 10);
            put("two", 2);      put("twelve", 12);       put("twenty", 20);
            put("three", 3);    put("thirteen", 13);     put("thirty", 30);
            put("four", 4);     put("fourteen", 14);     put("forty", 40);
            put("five", 5);     put("fifteen", 15);      put("fifty", 50);
            put("six", 6);      put("sixteen", 16);      put("sixty", 60);
            put("seven", 7);    put("seventeen", 17);    put("seventy", 70);
            put("eight", 8);    put("eighteen", 18);     put("eighty", 80);
            put("nine", 9);     put("nineteen", 19);     put("ninety", 90);
        }
    };

    private static final HashMap<String, Integer> MULTIPLIERS = new HashMap<>() {
        {
            put("hundred", 100);
            put("thousand", 1000);
            put("million", 1000000);
        }
    };

    public static int parseInt(String numStr) {
        String[] numTab = numStr.split(" ");
        Integer result = 0;
        Integer tempResult = 0;
        Integer maxValue = 0;

        for(String x : numTab) {
            for(String elem : x.split("-")){
                if(elem.equals("and")) continue;

                else if(MULTIPLIERS.containsKey(elem)) {
                    if(MULTIPLIERS.get(elem) > maxValue){
                        result = (result + tempResult) * MULTIPLIERS.get(elem);
                        tempResult = 0;
                        maxValue = MULTIPLIERS.get(elem);
                    } else {
                        tempResult *= MULTIPLIERS.get(elem);
                    }
                } else
                    tempResult += DICTIONARY.get(elem);
            }
        }

        return result + tempResult;
    }
}
