package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.LoanFilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
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
                ArgumentTokenizer.tokenize(args, PREFIX_FILTERPREDICATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_FILTERPREDICATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        String parameter = "hello";
        int value = 2;
        LocalDate date = new LocalDate();
        char operator = 'e';

        // todo make predicate class
        Set<Integer> PredicateList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_FILTERPREDICATE));

        if (trimmedArgs.equals("paid")) {
            return new LoanFilterCommand(true);
        } else if (trimmedArgs.equals("unpaid")) {
            return new LoanFilterCommand(false);
        } else {
            throw new ParseException("Invalid filter keyword.");
        }
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
    // todo code duplication from AddCommandParser, maybe make interface
}
