package seedu.address.logic.parser;

import seedu.address.logic.commands.LoanFilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class LoanFilterCommandParser implements Parser<LoanFilterCommand> {
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
