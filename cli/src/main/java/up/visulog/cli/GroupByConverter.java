/*
	22015094 - Idil Saglam
*/
package up.visulog.cli;

import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;
import up.visulog.analyzer.GroupBy;

class GroupByConverter implements ITypeConverter<GroupBy> {

    /**
     * Converts the specified command line argument value to some domain object.
     *
     * @param value the command line argument String value
     * @return the resulting domain object
     * @throws Exception an exception detailing what went wrong during the conversion. Any exception
     *     thrown from this method will be caught and shown to the end user. An example error
     *     message shown to the end user could look something like this: {@code Invalid value for
     *     option '--some-option': cannot convert 'xxxinvalidinput' to SomeType
     *     (java.lang.IllegalArgumentException: Invalid format: must be 'x:y:z' but was
     *     'xxxinvalidinput')}
     * @throws TypeConversionException throw this exception to have more control over the error
     *     message that is shown to the end user when type conversion fails. An example message
     *     shown to the user could look like this: {@code Invalid value for option '--some-option':
     *     Invalid format: must be 'x:y:z' but was 'xxxinvalidinput'}
     */
    @Override
    public GroupBy convert(String value) throws Exception {
        try {
            return GroupBy.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new TypeConversionException(e.getMessage());
        }
    }
}
