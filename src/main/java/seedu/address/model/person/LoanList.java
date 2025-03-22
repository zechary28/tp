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
    public List<Loan> getLoanList() {
        return Collections.unmodifiableList(loanList);
    }

    /**
     * Removes a loan from loan list.
     */
    public void remove(Loan loan) {
        loanList.remove(loan);
    }

    /**
     * creates a loan list string where each loan string is sepearated by ','
    */
    public String loanListToSaveString() {
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

    public static boolean isValidLoanListString(String loanListStr) {
        return loanListStr != null && !loanListStr.trim().isEmpty();
    }

    /**
     * parses loan list string to LoanList object
    */
    public static LoanList stringToLoanList(String loanListStr) {
        LoanList loanList = new LoanList();
        if (loanListStr.equals(EMPTY_STRING)) {
            return loanList;
        }

        String[] loans = loanListStr.split(",");
        for (String loanStr : loans) {
            try {
                Loan loan = Loan.stringToLoan(loanStr);
                if (loan == null) {
                    continue;
                } else {
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
        return otherLoanList.getLoanList().equals(getLoanList());
    }
}
