// Inspiration: https://www.codewars.com/kata/540d0fdd3b6532e5c3000b5b

public class BinominalExpansion {
    public static String expand(String expr) {
        var splittedExpr = expr.split("\\^");
        String expression = splittedExpr[0]
                .replaceAll("[()]", "");
        int exponent = Integer.parseInt(splittedExpr[1]);

        if (exponent == 0) return "1";
        else if (exponent == 1) return expression;
        else {
            int sign = 1;
            if (expression.charAt(0) == '-') {
                sign = -1;
                expression = expression.substring(1);
            }
			//example

            String[] coefficientsTab;
            if (expression.contains("-"))
                coefficientsTab = expression.split("(?=-)");
            else
                coefficientsTab = expression.split("\\+");

            String unknown = coefficientsTab[0].replaceAll("\\d+", "");
            String coefficientByUnknownAsString = coefficientsTab[0].replaceAll("\\D+", "");
            long coefficientByUnknown = coefficientByUnknownAsString.equals("") ? sign : Integer.parseInt(coefficientByUnknownAsString) * sign;
            long secondCoefficient = Integer.parseInt(coefficientsTab[1]);

            var result = new StringBuilder();
            String firstElement = calculateCoefficient(coefficientByUnknown, secondCoefficient, exponent, 0, unknown);
            if (firstElement.charAt(0) == '-')
                result.append(firstElement);
            else
                result.append(firstElement.substring(1));

            for (int i = 1; i <= exponent; ++i)
                result.append(calculateCoefficient(coefficientByUnknown, secondCoefficient, exponent, i, unknown));

            return result.toString();
        }
    }

    private static long binomialCoefficient(int n, int k) {
        if (k == 0 || k == n)
            return 1;
        else
            return binomialCoefficient(n - 1, k - 1) + binomialCoefficient(n - 1, k);
    }

    private static String calculateCoefficient(long coefficientByUnknown, long secondCoefficient, int exponent, int iterator, String unknown) {
        long coefficient =
                ((long) Math.pow(coefficientByUnknown, exponent - iterator) *
                (long) Math.pow(secondCoefficient, iterator)) *
                binomialCoefficient(exponent, iterator);

        String result = coefficient < 0 ? "" : "+";

        if (Math.abs(coefficient) == 1) {
            if (exponent - iterator == 0)
                result += coefficient;
            else
            if (coefficient == -1)
                result += "-" + unknown;
            else
                result += unknown;
        } else if (coefficient == 0) {
            return "";
        } else {
            if (exponent - iterator == 0) {
                result += Long.toString(coefficient);
            } else {
                result += coefficient + unknown;
            }
        }

        if (unknown.charAt(0) == result.charAt(result.length() - 1) && exponent - iterator > 1)
            result += "^" + (exponent - iterator);

        return result;
    }
}