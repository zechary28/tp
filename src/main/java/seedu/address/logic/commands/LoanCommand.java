package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.CompoundInterestLoan;
import seedu.address.model.person.Loan;
import seedu.address.model.person.Person;
import seedu.address.model.person.SimpleInterestLoan;

/**
 * Adds a loan to an existing person in the address book.
 */
public class LoanCommand extends Command {

    public static final String COMMAND_WORD = "loan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a loan to the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Adds to person's loan list."
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[INTEREST TYPE (s/c)] "
            + "[AMOUNT] "
            + "[INTEREST RATE] "
            + "[DUE DATE]...\n"
            + "Example: " + COMMAND_WORD + " 1 s 1000 10 2020-10-10";

    public static final String MESSAGE_ADD_LOAN_SUCCESS = "New loan added: %1$s";
    public static final String MESSAGE_INVALID_INTEREST_TYPE = "Interest loan type must be either 's' or 'c'";

    private final String type;
    private final String amount;
    private final String interest;
    private final String dueDate;
    private final Index index;

    /**
     * @param index of the person in the filtered person list to add a loan to
     * @param type of loan to add to the person (simple or compound)
     * @param amount that was loaned by person
     * @param interest of loan annually
     * @param dueDate of loan
     */
    public LoanCommand(Index index, String type, String amount, String interest, String dueDate) {
        requireNonNull(index);
        requireNonNull(type);
        requireNonNull(dueDate);

        this.index = index;
        this.type = type;
        this.amount = amount;
        this.interest = interest;
        this.dueDate = dueDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddLoan = lastShownList.get(index.getZeroBased());
        Loan loan;

        try {
            if (type.equals("s")) {
                loan = new SimpleInterestLoan(amount, interest, dueDate);
            } else if (type.equals("c")) {
                loan = new CompoundInterestLoan(amount, interest, dueDate);
            } else {
                throw new CommandException(MESSAGE_INVALID_INTEREST_TYPE);
            }
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }

        try {
            personToAddLoan.addLoan(loan);
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_LOAN_SUCCESS, Messages.format(personToAddLoan)));
    }
}
