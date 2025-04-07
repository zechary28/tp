package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SortCommand} object
*/
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
    * and returns a {@code SortCommand} object for execution.
    * @throws ParseException if the user input does not conform the expected format
    */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT, PREFIX_ORDER);

        String sort = argMultimap.getValue(PREFIX_SORT).orElse(SortCommand.AMOUNT).toUpperCase();
        String order = argMultimap.getValue(PREFIX_ORDER).orElse(SortCommand.DESC).toUpperCase();

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SORT, PREFIX_ORDER);

        if (!SortCommand.isValidSort(sort) || !SortCommand.isValidOrder(order)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(sort, order);
    }
}


