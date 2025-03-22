package seedu.address.model.person;


/**
 * Represents a simple interest loan.
 */
public class SimpleInterestLoan extends Loan {
    /**
     * Constructs a {@code SimpleInterestLoan}.
     *
     * @param amount   Loan amount.
     * @param interest Interest rate in percentage (e.g., 5 means 5%).
     * @param dueDate  Due date for full repayment.
     */
    public SimpleInterestLoan(String amount, String interest, String dueDate) {
        super(amount, interest, dueDate);
    }

    /**
     * Pays a certain amount towards the loan.
     *
     * @param payment The amount paid.
     */
    @Override
    void pay(int payment) {
    }

    @Override
    int getMoneyOwed() {
        return 1;
    }

    public String getName() {
        return "Simple Interest Loan";
    }
}
