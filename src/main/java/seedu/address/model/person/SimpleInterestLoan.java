package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a simple interest loan.
 */
public class SimpleInterestLoan extends Loan {
    public static final String LOAN_TYPE = "S";

    /**
     * Constructs a {@code SimpleInterestLoan}.
     *
     * @param principal   Loan principal.
     * @param interest Interest rate in percentage (e.g., 5 means 5%).
     * @param dueDate  Due date for full repayment.
     */
    public SimpleInterestLoan(String principal, String interest, String dueDate) {
        super(principal, interest, dueDate);
        calculateAndSetAmountOwed();
    }

    /**
     * Constructs an {@code a SimpleInterestLoan}.
     *
     * @param strPrincipal principal of loan
     * @param strAmtPaid amount paid of loan
     * @param strInterest % of yearly interest, >= 0, 1 represents 1% interest
     * @param strDueDate date which loan should be completely paid off
     * @param strDateLastPaid date which loan was last paid
     * @param strDateCreated date which loan was created
     * @param strIsPaid boolean if loan is paid
     *
    */
    public SimpleInterestLoan(String strPrincipal, String strAmtPaid, String strInterest, String strDueDate,
                              String strDateLastPaid, String strDateCreated, String strIsPaid) {
        super(strPrincipal, strAmtPaid, strInterest, strDueDate, strDateLastPaid, strDateCreated, strIsPaid);
        calculateAndSetAmountOwed();
    }

    public float getMonthlyInterest() {
        // monthly interest = principal amount * (r / 12) where r is annual interest rate: (6% = 0.06)
        return this.principal * ((this.getInterest() / 100) / 12);
    }

    public float getMonthlyInstalmentAmount() {
        return getRemainingOwed() / Math.max(Math.abs(getMonthsUntilDueDate()), 1); // Prevent division by 0
    }

    // Currently unused method, removed abstract declaration fromm loan.
    @Override
    public float getLoanValue() {
        int monthsSinceLoan = this.getMonthsSinceLoan();
        int loanLength = this.getLoanLengthMonths();

        if (loanLength > monthsSinceLoan) { // normal case
            return this.getMonthlyInstalmentAmount() * loanLength;
        } else { // loan past due date
            return (this.getMonthlyInstalmentAmount() * loanLength)
                + (monthsSinceLoan - loanLength) * this.getMonthlyInterest();
        }
    }

    @Override
    public float getPaymentDifference() {
        int monthsSinceLoan = this.getMonthsSinceLoan();
        int loanLength = this.getLoanLengthMonths();

        //money owed since start of loan - amt paid
        if (loanLength > monthsSinceLoan) {
            return this.getMonthlyInstalmentAmount() * monthsSinceLoan - this.getAmtPaid();
        } else { // loan past due date
            return (this.getMonthlyInstalmentAmount() * monthsSinceLoan) + (monthsSinceLoan - loanLength)
                * this.getMonthlyInterest() - this.getAmtPaid();
        }
    }

    /**
     * Pays a certain amount towards the loan.
     *
     * @param payment The amount paid.
     */
    @Override
    public void pay(float payment) throws IllegalValueException {

        if (payment < 0 || payment > getRemainingOwed()) {
            String paidTooMuchMessage = "Payment exceeds the remaining owed!";
            throw new IllegalValueException(paidTooMuchMessage);
        }

        this.incrementAmtPaid(payment);

        if (getRemainingOwed() == 0.0) {
            this.setIsPaid(true);
        }
    }

    public String getName() {
        return LOAN_TYPE;
    }

    private void calculateAndSetAmountOwed() {
        // Effective interest per day, assuming 365 days a year and converting from percent to float.
        float interestPerDay = getInterest() / (100 * 365);
        // Duration of loan in days
        float loanDurationDays = getLoanLengthDays();
        float amountOwed = getPrincipal() + getPrincipal() * interestPerDay * loanDurationDays;
        // Round to avoid hidden "unpayable" decimal points.
        float amountOwedRounded = Math.round(amountOwed * 100) / 100f;
        super.setAmountOwed(amountOwedRounded);
    }
}
