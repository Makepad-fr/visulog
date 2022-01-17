/*
	22015094 - Idil Saglam
*/
package up.visulog.analyzer;

import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.util.Locale;

public enum GroupBy {
    Hour,
    Day,
    Week,
    Month,
    Quarter,
    Year;

    /**
     * Get the representation of the given ZonedDateTime in the current groupBy configuration
     *
     * @param zdt The ZonedDateTime object to format
     * @return The String representation of the given ZonedDateTime instance in given GroupBy
     *     configuration
     */
    public String formatDateTime(ZonedDateTime zdt) {
        return switch (this) {
            case Year -> "" + zdt.getYear();
            case Quarter -> String.format(
                    "Q%d of %d", zdt.get(IsoFields.QUARTER_OF_YEAR), zdt.getYear());
            case Month -> String.format("%s %d", zdt.getMonth(), zdt.getYear());
            case Week -> String.format(
                    "Week %d of %d", zdt.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR), zdt.getYear());
            case Day -> String.format(
                    "%d/%d/%d", zdt.getMonthValue(), zdt.getDayOfMonth(), zdt.getYear());
            case Hour -> String.format(
                    "%d %d/%d/%d",
                    zdt.getHour(), zdt.getDayOfMonth(), zdt.getMonthValue(), zdt.getYear());
        };
    }

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

    @Override
    public String toString() {
        return switch (this) {
            case Hour -> "hour";
            case Day -> "day";
            case Week -> "week";
            case Month -> "month";
            case Quarter -> "quarter";
            case Year -> "year";
        };
    }
}
