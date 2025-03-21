package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

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

    public static final String MESSAGE_SUCCESS = "New loan added: %1$s";

    private final String type;
    private final int amount;
    private final int interest;
    private final String dueDate;
    private final Index index;

    public LoanCommand(Index index, String type, int amount, int interest, String dueDate) {
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
        return null;
    }
}
