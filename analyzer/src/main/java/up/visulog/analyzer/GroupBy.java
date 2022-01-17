/*
	22015094 - Idil Saglam
*/
package up.visulog.analyzer;

import java.util.Locale;

public enum GroupBy {
    Hour,
    Day,
    Week,
    Month,
    Quarter,
    Year;

    /**
     * Converts the given String to the GroupBy value if possible
     *
     * @param string The string to convert
     * @return GroupBy value related to the given string
     * @throws IllegalArgumentException If the given string does not match with any GroupBy values
     */
    public static GroupBy fromString(String string) throws IllegalArgumentException {
        return switch (string.trim().toLowerCase(Locale.ROOT)) {
            case "hour" -> Hour;
            case "day" -> Day;
            case "week" -> Week;
            case "month" -> Month;
            case "quarter" -> Quarter;
            case "year" -> Year;
            default -> throw new IllegalArgumentException(
                    String.format("%s is not a vlid groupBy type", string));
        };
    }
}
