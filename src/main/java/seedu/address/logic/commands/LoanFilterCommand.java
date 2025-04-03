package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Loan;
import seedu.address.model.person.LoanPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters loans of specified person by given parameters.\n"
            + "Parameters: [personIndex] /pred [predicate type] [predicate parameters]\n"
            + "Available Predicate Types: amount, loanType, dueDate, paidStatus \n"
            + "amount parameters:   pred/ amount [< or >] [amount] \n"
            + "dueDate parameters:  pred/ dueDate [< or >] [date in yyyy-mm-dd] \n"
            + "loanType parameters: pred/ loanType [s or c] \n"
            + "isPaid parameters:   pred/ isPaid [y or n] \n"
            + "Example: " + COMMAND_WORD + "3 pred/ amount > 100.00 pred/ loanType s";

    private final Set<LoanPredicate> predicateSet;
    private final int personIndex;

    /**
     * Constructs a LoanFilterCommand.
     *
     * @param predicateSet Set of LoanPredicate which will be used for filtering
     */
    public LoanFilterCommand(int personIndex, Set<LoanPredicate> predicateSet) {
        this.personIndex = personIndex;
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
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.getIsChangeable()) {
            throw new CommandException(UniquePersonList.UNMODIFIABLE_MESSAGE);
        }
        List<Person> lastShownList = model.getFilteredPersonList();

        if (this.personIndex > lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person person = model.getFilteredPersonList().get(this.personIndex - 1);
        List<Loan> filteredLoans = person.getLoanList().getLoans(); // todo demeter

        // for all predicates, list -> filteredlist
        for (LoanPredicate pred : predicateSet) {
            filteredLoans = filterLoanList(filteredLoans, pred);
        }

        // handle empty result list
        if (filteredLoans.isEmpty()) {
            return new CommandResult("No loans found.");
        }

        // build string for result
        StringBuilder result = new StringBuilder();
        for (Loan loan : filteredLoans) {
            result.append(loan + "\n");
        }

        return new CommandResult(result.toString().trim());
    }

    private List<Loan> filterLoanList(List<Loan> loans, LoanPredicate pred) {
        List<Loan> result = new ArrayList<>();
        for (Loan loan : loans) {
            if (pred.test(loan)) {
                result.add(loan);
            }
        }
        return result;
    }

    private void printList(List<Loan> loans) {
        for (Loan loan: loans) {
            System.out.println(loan.toString() + "\n");
        }
    }

}
