package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLoanCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand or DeleteLoanCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final String DELETE_LOAN_VALIDATION_REGEX = "^loan\\s+\\d+\\s+\\d+\\s*$";
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();

        // Check if delete loan command
        if (args.matches(DELETE_LOAN_VALIDATION_REGEX)) {
            String[] splitArgs = args.split("\\s+");

            try {
                Index personIndex = ParserUtil.parseIndex(splitArgs[1]);
                Index loanIndex = ParserUtil.parseIndex(splitArgs[2]);
                return new DeleteLoanCommand(personIndex, loanIndex);
            } catch (ParseException pe) {
                throw new ParseException(String.format(
                       String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLoanCommand.MESSAGE_USAGE), pe));
            }
        } else if (args.matches("^loan.*")) {
            throw new ParseException(String.format(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLoanCommand.MESSAGE_USAGE)));
        }

        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
