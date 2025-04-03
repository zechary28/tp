package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.LoanFilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILTER_PREDICATE);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanFilterCommand.MESSAGE_USAGE));
        }

        int personIndex = Integer.parseInt(argMultimap.getPreamble());

        Set<LoanPredicate> predicateList = ParserUtil.parseLoanPredicates(argMultimap.getAllValues(PREFIX_FILTER_PREDICATE));
        return new LoanFilterCommand(personIndex, predicateList);
    }
}
