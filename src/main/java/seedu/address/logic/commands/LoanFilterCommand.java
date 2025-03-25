package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.person.Loan;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class LoanFilterCommand extends Command {
    public static final String COMMAND_WORD = "filterLoan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters loans by paid status.\n"
            + "Parameters: paid | unpaid\n"
            + "Example: " + COMMAND_WORD + " paid";

    private final boolean isPaid;

    public LoanFilterCommand(boolean isPaid) {
        this.isPaid = isPaid;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        StringBuilder result = new StringBuilder();

        List<Person> persons = model.getFilteredPersonList();
        boolean foundAny = false;

        for (Person person : persons) {
            List<Loan> filteredLoans = person.getLoanList().filterLoansByPaidStatus(isPaid);
            if (!filteredLoans.isEmpty()) {
                foundAny = true;
                result.append("Loans for ").append(person.getName()).append(":\n");

                for (Loan loan : filteredLoans) {
                    result.append("  ").append(loan.toString()).append("\n");
                }

                result.append("\n"); // add spacing between people
            }
        }

        if (!foundAny) {
            return new CommandResult("No " + (isPaid ? "paid" : "unpaid") + " loans found.");
        }

        return new CommandResult(result.toString().trim());
    }
}
