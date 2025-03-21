package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LoanCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;

public class LoanCommandParser implements Parser<LoanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoanCommand
     * and returns a LoanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public LoanCommand parse(String args) throws ParseException {
        requireNonNull(args);
        return new LoanCommand(null, "s",0, 0, null);
    }
}
