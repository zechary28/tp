package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a loan list containing the loans a person has.
 */
public class LoanList {
    private final ArrayList<Loan> loanList = new ArrayList<>();

    /**
     * Initializes a LoanList.
     */
    public LoanList() {}

    /**
     * Adds a loan to the loan list.
     */
    public void add(Loan loan) {
        loanList.add(loan);
    }

    /**
     * Returns an immutable loan list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Loan> getLoanList() {
        return Collections.unmodifiableList(loanList);
    }

    /**
     * Removes a loan from loan list.
     */
    public void remove(Loan loan) {
        loanList.remove(loan);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (loanList.isEmpty()) {
            builder.append("No loans found. \n");
        } else {
            builder.append("Loans: \n");
        }

        for (int i = 0; i < loanList.size(); i++) {
            Loan loan = loanList.get(i);
            builder.append(String.format((i + 1) + ". "));
            builder.append(loan.getName());
            builder.append(": [Amount: $");
            builder.append(loan.getAmount());
            builder.append(", Interest Rate: ");
            builder.append(loan.getInterest());
            builder.append("%, Due Date: ");
            builder.append(loan.getDueDate());
            builder.append("]\n");
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LoanList otherLoanList)) {
            return false;
        }
        return otherLoanList.getLoanList().equals(getLoanList());
    }
}
