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
        String[] splitArgs = args.split("\\s+");

        if (splitArgs.length != 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(splitArgs[0]);
            int loanIndex = Integer.parseInt(splitArgs[1]);
            String thirdArg = splitArgs[2];

            if (thirdArg.equalsIgnoreCase("all")) {
                return new PayCommand(index, loanIndex);
            } else if (thirdArg.matches("\\d+M")) {
                int months = Integer.parseInt(thirdArg.substring(0, thirdArg.length() - 1));
                return new PayCommand(index, loanIndex, months);
            } else {
                float amount = Float.parseFloat(thirdArg);
                return new PayCommand(index, loanIndex, amount);
            }
        } catch (NumberFormatException | ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE), e);
        }
    }
}
