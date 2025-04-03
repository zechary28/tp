package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a specified {@code Loan} from a specified {@code Person}
 */
public class DeleteLoanCommand extends DeleteCommand {

    public static final String COMMAND_WORD = "delete loan";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a loan of the person identified by their respective index numbers in the displayed"
            + " person list.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) "
            + "LOAN_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 1";

    public static final String MESSAGE_DELETE_LOAN_SUCCESS = "Loan successfully deleted!";
    public static final String MESSAGE_INVALID_LOAN_INDEX = "The loan index provided is invalid.";

    private final Index personIndex;
    private final Index loanIndex;

    /**
     * Deletes a loan identified using person's and loan's displayed indices from the address book.
     */
    public DeleteLoanCommand(Index personIndex, Index loanIndex) {
        super(personIndex);
        this.personIndex = personIndex;
        this.loanIndex = loanIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDeleteLoan = lastShownList.get(personIndex.getZeroBased());

        if (loanIndex.getZeroBased() >= personToDeleteLoan.getLoanCount() || loanIndex.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INVALID_LOAN_INDEX);
        }

        try {
            personToDeleteLoan.removeLoan(loanIndex.getZeroBased());
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(MESSAGE_DELETE_LOAN_SUCCESS);
    }
}
