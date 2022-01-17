/*
	22015094 - Idil Saglam
*/
package up.visulog.analyzer;

import java.util.Locale;

public enum GraphType {
    BarChart;

    /**
     * Creates a GraphType from a String
     * @param string The input string value
     * @return The GraphType value related to the given string
     * @throws IllegalArgumentException If the given string matches with any GraphType values
     */
    public static GraphType fromString(String string) throws IllegalArgumentException {
        return switch (string.trim().toLowerCase(Locale.ROOT)) {
            case "barchart" -> BarChart;
            default -> throw new IllegalArgumentException(String.format("%s is not a valid option for GraphType enumeration", string));
        };
    }
}
