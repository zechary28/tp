package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_PREDICATE;

import java.util.Set;

import seedu.address.logic.commands.LoanFilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.LoanPredicate;

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
        // clear filter command
        if (args.toLowerCase().strip().equals(LoanFilterCommand.CLEAR)) {
            return new LoanFilterCommand(-1, true);
        }

        // get filter predicates
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILTER_PREDICATE);

        Set<LoanPredicate> preds = ParserUtil.parseLoanPredicates(argMultimap.getAllValues(PREFIX_FILTER_PREDICATE));

        if (preds.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanFilterCommand.MESSAGE_USAGE));
        }

        int personIndex;

        try {
            // filter loans from specific person
            personIndex = Integer.parseInt(argMultimap.getPreamble());
            if (personIndex < 1) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LoanFilterCommand.MESSAGE_USAGE));
            }
        } catch (NumberFormatException e) {
            // filter all loans
            personIndex = -1;
        }

        return new LoanFilterCommand(personIndex, preds);
    }
}
