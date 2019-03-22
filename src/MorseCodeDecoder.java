// Inspiration: https://www.codewars.com/kata/54b72c16cd7f5154e9000457

import java.util.*;
import java.util.stream.Collectors;

public class MorseCodeDecoder {
    private static Map<String, String> morseMapCreator() {
        Map<String, String> result = new HashMap<>();

        result.put("1", ".");
        result.put("111", "-");
        result.put("0", "");
        result.put("000", " ");
        result.put("0000000", "   ");

        return result;
    }

    private static final Map<String, String> morseMap = morseMapCreator();

    public static String decodeBits(String bits) {
        bits = removeLeadingAndTrailingZeroes(bits);
        var result = new StringBuilder();
        var lastSequence = new StringBuilder(bits.charAt(0));
        int transmittingSpeed = transmittingSpeed(bits);
        char previousChar = bits.charAt(0);

        for (char character : bits.toCharArray()) {
            if (character == previousChar)
                lastSequence.append(character);
            else {
                lastSequence.setLength(lastSequence.length() / transmittingSpeed);
                result.append(morseMap.get(lastSequence.toString()));
                lastSequence.setLength(0);
                previousChar = character;
                lastSequence.append(previousChar);
            }
        }

        lastSequence.setLength(lastSequence.length() / transmittingSpeed);
        result.append(morseMap.get(lastSequence.toString()));

        return result.toString();
    }

    /*
        MorseCode class which is used here isn't the part of the project.
        It was provided by the online compilator located at codewars.com,
        it's of type Map<String, String> where the first string is a
        character in Morse code, whereas the second string is it's
        equivalent in the Latin alphabet
     */
    public static String decodeMorse(String morseCode) {
        return Arrays.stream(morseCode.split(" {3}"))
                .map(word -> Arrays.stream(word.split(" "))
                        .map(MorseCode::get)
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining()))
                .filter(decodedWord -> !decodedWord.equals(""))
                .collect(Collectors.joining(" "));
    }

    private static String removeLeadingAndTrailingZeroes(String bits) {
        bits = bits.replaceFirst("^0+(?!$)", "");
        bits = bits.replaceFirst("0+$", "");

        return bits;
    }

    private static int transmittingSpeed(String bits) {
        int shortestSequence = 0, result = bits.length();
        char previousChar = bits.charAt(0);

        for (char character : bits.trim().toCharArray()) {
            if (character == previousChar)
                ++shortestSequence;
            else {
                result = Math.min(result, shortestSequence);
                shortestSequence = 1;
                previousChar = character;
            }
        }

        return Math.min(result, shortestSequence);
    }
}