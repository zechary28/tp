package seedu.address.model.person;


/**
 * Represents a compound interest loan.
 */
public class CompoundInterestLoan extends Loan {

    /**
     * Constructs a {@code CompoundInterestLoan}.
     *
     * @param amount   Loan amount (principal).
     * @param interest Interest rate per compounding period (e.g., 5 means 5%).
     * @param dueDate  Due date for full repayment.
     */
    public CompoundInterestLoan(String amount, String interest, String dueDate) {
        super(amount, interest, dueDate);
    }

    /**
     * Pays a certain amount towards the loan.
     *
     * @param payment The amount paid.
     */
    @Override
    void pay(float payment) {
    }

    @Override
    float getMoneyOwed() {
        return 1;
    }

    @Override
    float getLoanValue() {
        return 1;
    }

    @Override
    int getOverDueMonths() {
        return 1;
    }

    @Override
    boolean isOverDue() {
        return true;
    }

    public String getName() {
        return "Compound Interest Loan";
    }
}
