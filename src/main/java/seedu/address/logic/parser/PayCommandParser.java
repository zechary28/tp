package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PayCommand object
 */
public class PayCommandParser implements Parser<PayCommand> {

    private static final String VALIDATION_REGEX = "(\\d+)\\s+(\\d+)\\s+(\\d+(\\.\\d{1,2})?)";

    /**
     * Parses the given {@code String} of arguments in the context of the PayCommand
     * and returns a PayCommand object for execution.
     * @throws ParseException if the user does not conform the expected format
     */
    @Override
    public PayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();

        if (!args.matches(VALIDATION_REGEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));
        }

        String[] splitArgs = args.split("\\s+");

        try {
            Index index = ParserUtil.parseIndex(splitArgs[0]);
            int loanAmount = Integer.parseInt(splitArgs[1]);
            float loanRate = Float.parseFloat(splitArgs[2]);
            return new PayCommand(index, loanAmount, loanRate);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE), pe);
        }
    }
}
