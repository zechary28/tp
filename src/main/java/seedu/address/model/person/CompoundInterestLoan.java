package seedu.address.model.person;


/**
 * Represents a compound interest loan.
 */
public class CompoundInterestLoan extends Loan {
    public static final String LOAN_TYPE = "C";

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
     * Constructs an {@code a CompoundInterestLoan}.
     *
     * @param strAmount cost of loan.
     * @param strAmtPaid amount paid of loan
     * @param strInterest % of yearly interest, >= 0, 1 represents 1% interest
     * @param strDueDate date which loan should be completely paid off
     * @param strDateLastPaid date which loan was last paid
     * @param strDateCreated date which loan was created
     * @param strIsPaid boolean if loan is paid
     *
     */
    public CompoundInterestLoan(String strAmount, String strAmtPaid, String strInterest, String strDueDate,
        String strDateLastPaid, String strDateCreated, String strIsPaid) {
        super(strAmount, strAmtPaid, strInterest, strDueDate, strDateLastPaid, strDateCreated, strIsPaid);
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
        return LOAN_TYPE;
    }
}
