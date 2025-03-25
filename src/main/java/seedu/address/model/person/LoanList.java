package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a loan list containing the loans a person has.
 */
public class LoanList {
    public static final String EMPTY_STRING = "EMPTY";

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
    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loanList);
    }

    /**
     * Removes a loan from loan list.
     */
    public void remove(Loan loan) {
        loanList.remove(loan);
    }

    /**
     * Returns a list of loans filtered by their paid status.
     *
     * @param isPaid If true, returns loans that are marked as paid;
     *               if false, returns loans that are not fully paid.
     * @return A list of loans matching the specified paid status.
     */
    public List<Loan> filterLoansByPaidStatus(boolean isPaid) {
        List<Loan> filteredLoans = new ArrayList<>();
        for (Loan loan : loanList) {
            if (loan.getIsPaid() == isPaid) {
                filteredLoans.add(loan);
            }
        }
        return filteredLoans;
    }

    /**
     * creates a loan list string where each loan string is sepearated by ','
    */
    public String toSaveString() {
        if (this.loanList.isEmpty()) {
            return EMPTY_STRING;
        }

        StringBuilder loanList = new StringBuilder();
        for (Loan loan : this.loanList) {
            loanList.append(loan.toSaveString()).append(',');
        }

        // remove the trailing comma
        if (loanList.length() > 0) {
            loanList.deleteCharAt(loanList.length() - 1);
        }

        return loanList.toString();
    }

    // might not be needed
    public static boolean isValidLoanListString(String loanListStr) {
        return loanListStr != null;
    }

    /**
     * parses loan list string to LoanList object
    */
    public static LoanList stringToLoanList(String loanListStr) {
        LoanList loanList = new LoanList();
        if (loanListStr == null || loanListStr.equals(EMPTY_STRING) || loanListStr.trim().isEmpty()) {
            return loanList;
        }

        String[] loans = loanListStr.split(",");
        for (String loanStr : loans) {
            try {
                Loan loan = Loan.stringToLoan(loanStr);
                if (loan != null) {
                    loan.updateIsPaid();
                    loanList.add(loan);
                }
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
        return loanList;
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
        return otherLoanList.getLoans().equals(getLoans());
    }
}
