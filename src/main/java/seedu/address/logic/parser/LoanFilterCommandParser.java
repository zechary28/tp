package seedu.address.logic.parser;

import seedu.address.logic.commands.LoanFilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code LoanFilterCommand} object.
 */
public class LoanFilterCommandParser implements Parser<LoanFilterCommand> {

    /**
     * Parses the given user input and returns a {@code LoanFilterCommand}.
     *
     * @param args User input arguments (expected: "paid" or "unpaid").
     * @return A {@code LoanFilterCommand} with the corresponding paid status filter.
     * @throws ParseException If the input does not match "paid" or "unpaid".
     */
    public LoanFilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();

        if (trimmedArgs.equals("paid")) {
            return new LoanFilterCommand(true);
        } else if (trimmedArgs.equals("unpaid")) {
            return new LoanFilterCommand(false);
        } else {
            throw new ParseException("Invalid filter keyword. Use 'paid' or 'unpaid'.");
        }
    }
}
