// Inspiration: https://www.codewars.com/kata/5629db57620258aa9d000014

import java.util.*;
import java.util.stream.Collectors;

public class StringsMix {
    public static String mix(String s1, String s2) {
        var firstStringCharacters = countCharactersOccurrence(s1);
        var secondStringCharacters = countCharactersOccurrence(s2);
        var allCharactersSet = new TreeMap<>(firstStringCharacters);
        secondStringCharacters.forEach((k, v) -> allCharactersSet.merge(k, v, Integer::max));

        var iterationSet = allCharactersSet.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        LinkedList<String> tempResult = new LinkedList<>();
        LinkedList<String> result = new LinkedList<>();

        int occurrences = Integer.MAX_VALUE;
        for (Map.Entry entry : iterationSet) {
            int occurrenceInFirstString = firstStringCharacters.getOrDefault(entry.getKey(), 0);
            int occurrenceInSecondString = secondStringCharacters.getOrDefault(entry.getKey(), 0);
            int maxOccurrences = Integer.max(occurrenceInFirstString, occurrenceInSecondString);

            if (maxOccurrences < occurrences) {
                occurrences = maxOccurrences;
                tempResult.stream().sorted().forEach(result::add);
                tempResult = new LinkedList<>();
            }

            if (Integer.max(occurrenceInFirstString, occurrenceInSecondString) > 1) {
                if (occurrenceInFirstString > occurrenceInSecondString)
                    tempResult.add("1:" + entry.getKey().toString().repeat(occurrenceInFirstString));
                else if (occurrenceInSecondString > occurrenceInFirstString)
                    tempResult.add("2:" + entry.getKey().toString().repeat(occurrenceInSecondString));
                else
                    tempResult.add("=:" + entry.getKey().toString().repeat(occurrenceInFirstString));
            }
        }

        tempResult.stream()
                .sorted()
                .forEach(result::add);

        return String.join("/", result);
    }

    private static TreeMap<Character, Integer> countCharactersOccurrence(String str) {
        TreeMap<Character, Integer> result = new TreeMap<>();

        for (char character : str.toCharArray())
            if (character >= 'a' && character <= 'z')
                result.put(character, result.getOrDefault(character, 0) + 1);

        return result;
    }
}