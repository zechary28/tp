package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.person.Loan;
import seedu.address.model.person.LoanPredicate;
import seedu.address.model.person.Person;

/**
 * Filters and displays all loans across all persons in the address book
 * based on their given predicates based on loan parameters:
 * person (shows loans for this person)
 * amount (less than | greater than or equals)
 * loanType (simple | compound)
 * dueDate (earlier than | later than or equals)
 * paidStatus (paid | unpaid).
 */
public class LoanFilterCommand extends Command {
    public static final String COMMAND_WORD = "filterLoan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters loans by paid status.\n"
            + "Parameters: /pred [predicate type] [predicate parameters]\n"
            + "Available Predicate Types: person, amount, loanType, dueDate, paidStatus \n"
            + "person parameters:   pred/ person [personIndex] \n"
            + "amount parameters:   pred/ amount [< or >] [amount] \n"
            + "loanType parameters: pred/ loanType [s or c] \n"
            + "isPaid parameters:   pred/ isPaid [y or n] \n"
            + "dueDate parameters:  pred/ dueDate [< or >] [date in yyyy-mm-dd] \n"
            + "Example: " + COMMAND_WORD + " pred/ person 2 pred/ amount > 100.00 pred/ loanType s";

    private final Set<LoanPredicate> predicateSet;

    /**
     * Constructs a LoanFilterCommand.
     *
     * @param predicateSet Set of LoanPredicate which will be used for filtering
     */
    public LoanFilterCommand(Set<LoanPredicate> predicateSet) {
        this.predicateSet = predicateSet;
    }

    /**
     * Executes the filterLoan command, displaying all loans that match the paid status.
     * For each person, any matching loans are displayed under their name.
     *
     * @param model The model containing the list of persons and their loans.
     * @return A CommandResult with the filtered loan information or a message if none found.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        StringBuilder result = new StringBuilder();

        switch (parameter) {
        case "person": {
            Person person = model.getFilteredPersonList().get(this.index);
            boolean foundAny = false;
            List<Loan> filteredLoans = person.getLoanList().getLoans(); // todo demeter
            if (!filteredLoans.isEmpty()) {
                foundAny = true;
                result.append("Loans for ").append(person.getName()).append(":\n");

                for (Loan loan : filteredLoans) {
                    result.append("  ").append(loan.toString()).append("\n");
                }

                result.append("\n"); // add spacing between people
            }
            if (!foundAny) { // todo code duplication
                return new CommandResult("No " + " loans found.");
            }
        }
        case "isPaid": {
            List<Person> persons = model.getFilteredPersonList();
            boolean foundAny = false;
            boolean isPaid = operator == 'y';

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
                return new CommandResult("No " + " loans found.");
            }
        }
        default:
            // TODO handle invalid command
        }
        return new CommandResult(result.toString().trim());
    }
}
