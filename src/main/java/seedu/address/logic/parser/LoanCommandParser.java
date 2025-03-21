package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LoanCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class LoanCommandParser implements Parser<LoanCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LoanCommand
     * and returns a LoanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public LoanCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();
        // Validates that args is of the right format, throws ParseException otherwise
        if (!args.matches(
                "^(\\d+)\\s+" // Matches index, which must be a positive integer
                        + "([sc])\\s+" // Matches interest type, which must be either s or c
                        + "(\\d+(\\.\\d{1,2})?)\\s+" // Matches amount, allowing numbers with up to 2 d.p.
                        + "(\\d+(\\.\\d{1,2})?)\\s+" // Matches interest rate, similar to amount
                        + "(\\d{4}-\\d{2}-\\d{2})$" // Matches due date in YYYY-MM-DD format
        )) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanCommand.MESSAGE_USAGE));
        };

        String[] arguments = args.split("\\s+");

        Index index = ParserUtil.parseIndex(arguments[0]);
        String type = arguments[1];
        String amount = arguments[2];
        String interest = arguments[3];
        String dueDate = arguments[4];

        return new LoanCommand(index, type, amount, interest, dueDate);
    }
}
