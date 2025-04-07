package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LoanCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Loan;

/**
 * Parses input arguments and creates a new LoanCommand object
 */
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
            "^(\\d+)\\s+" // Index: positive integer
            + "([sc])\\s+" // Interest type: 's' or 'c'
            + "(\\d+)\\s+" // Amount: positive integer only
            + "(\\d+(\\.\\d{1,2})?)\\s+" // Interest rate: up to 2 decimal places
            + "(\\d{4}-\\d{2}-\\d{2})$" // Date: YYYY-MM-DD
        )) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanCommand.MESSAGE_USAGE));
        }

        String[] arguments = args.split("\\s+");

        Index index = ParserUtil.parseIndex(arguments[0]);
        String type = arguments[1];
        String amount = arguments[2];
        String interest = arguments[3];
        String dueDate = arguments[4];

        LocalDate date = Loan.toValidLocalDate(dueDate);

        if (date == null) {
            throw new ParseException("Invalid date format! \n" + LoanCommand.MESSAGE_USAGE);
        }

        return new LoanCommand(index, type, amount, interest, dueDate);
    }
}
