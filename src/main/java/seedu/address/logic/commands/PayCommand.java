package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Pays a certain amount to a person's loan using their displayed indices from the address book.
 */
public class PayCommand extends Command {

    public static final String COMMAND_WORD = "pay";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pays the loan of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) LOAN_INDEX (must be a positive integer)"
            + "AMOUNT (up to 2 d.p) or MONTHS (positive number)\n"
            + "Example (Pay by amount): " + COMMAND_WORD + " 1 1 100.00\n"
            + "Example (Pay by months): " + COMMAND_WORD + " 1 1 3";

    public static final String MESSAGE_PAYMENT_SUCCESS = "Payment successful: %1$s";
    private static final String MESSAGE_INVALID_LOAN_INDEX = "The loan index provided is invalid.";
    private static final String MESSAGE_INVALID_AMOUNT = "The amount must be a positive number.";
    private static final String MESSAGE_INVALID_MONTHS = "The number of months must be a positive integer.";

    private final Index targetIndex;
    private final int loanIndex;
    private final float amount;
    private final int months;
    private final boolean isPayByMonth;

    /**
     * Pays a loan identified using person's and loan's displayed indices from the address book.
     */
    public PayCommand(Index targetIndex, int loanIndex, float amount, int months) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.loanIndex = loanIndex;
        this.amount = amount;
        this.months = months;
        this.isPayByMonth = (months > 0);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personWhoPaid = lastShownList.get(targetIndex.getZeroBased());

        if (loanIndex > personWhoPaid.getLoanCount() || loanIndex <= 0) {
            throw new CommandException(MESSAGE_INVALID_LOAN_INDEX);
        }

        try {
            float totalPayment = amount; //default: paying by amount

            if (isPayByMonth) {
                float monthlyInstallment = personWhoPaid.getLoans().get(loanIndex - 1).getMonthlyInstalmentAmount();
                totalPayment = monthlyInstallment * months;
            }

            if (totalPayment <= 0) {
                throw new CommandException(isPayByMonth ? MESSAGE_INVALID_MONTHS : MESSAGE_INVALID_AMOUNT);
            }

            personWhoPaid.payLoan(loanIndex - 1, amount);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_PAYMENT_SUCCESS, Messages.format(personWhoPaid)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PayCommand e)) {
            return false;
        }

        return targetIndex.equals(e.targetIndex)
                && loanIndex == e.loanIndex
                && amount == e.amount
                && months == e.months;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("loanIndex", loanIndex)
                .add("amount", amount)
                .add("months", months)
                .add("isPayByMonth", isPayByMonth)
                .toString();
    }
}
