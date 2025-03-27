package seedu.address.model.person;

import java.time.LocalDate;

import seedu.address.commons.exceptions.IllegalValueException;

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
        this.setAmountOwed(this.getLoanValue());
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
     */
    public CompoundInterestLoan(String strAmount, String strAmtPaid, String strInterest, String strDueDate,
                                String strDateLastPaid, String strDateCreated, String strIsPaid) {
        super(strAmount, strAmtPaid, strInterest, strDueDate, strDateLastPaid, strDateCreated, strIsPaid);
        this.setAmountOwed(this.getLoanValue());
    }

    /**
     * Calculates the monthly interest for the loan.
     */
    public float getMonthlyInterest() {
        int monthsSinceLoan = this.getMonthsSinceLoan();
        float monthlyRate = (this.getInterest() / 100) / 12;
        return (float) (this.principal * Math.pow(1 + monthlyRate, monthsSinceLoan) - this.principal);
    }

    /**
     * Calculates the total monthly cost (principal + interest).
     */
    public float getTotalMonthlyCost() {
        return this.getMonthlyAveragePrincipal() + this.getMonthlyInterest();
    }

    /**
     * Pays a certain amount towards the loan.
     */
    @Override
    public void pay(float payment) throws IllegalValueException {
        if (payment < 0 || payment > getRemainingOwed()) {
            String paidTooMuchMessage = "Payment exceeds the remaining owed!";
            throw new IllegalValueException(paidTooMuchMessage);
        }

        this.incrementAmtPaid(payment);
        this.setDateLastPaid(LocalDate.now());

        if (getRemainingOwed() == 0.0) {
            this.setIsPaid(true);
        }
    }

    /**
     * Calculates the difference between what should have been paid and what was actually paid.
     * @return The payment difference amount.
     */
    @Override
    float getPaymentDifference() {
        int monthsSinceLoan = this.getMonthsSinceLoan();
        float monthlyRate = (this.getInterest() / 100) / 12;
        float moneyOwed = (float) (this.principal * Math.pow(1 + monthlyRate, monthsSinceLoan)) - this.getAmtPaid();
        return Math.max(moneyOwed, 0);
    }

    /**
     * Calculates the total value of the loan including interest.
     * @return The total loan value.
     */
    float getLoanValue() {
        int loanLength = this.getLoanLengthMonths();
        float monthlyRate = (this.getInterest() / 100) / 12;

        // compound interest formula: A = P * (1 + r / n)^(n * t)
        float totalLoanValue = (float) (this.principal * Math.pow(1 + monthlyRate, loanLength));
        int monthsSinceLoan = this.getMonthsSinceLoan();
        if (monthsSinceLoan > loanLength) { // loan past due date
            float overdueAmount = (float) (totalLoanValue * Math.pow(1 + monthlyRate, monthsSinceLoan - loanLength));
            return totalLoanValue + overdueAmount;
        }
        return totalLoanValue;
    }

    /**
     * Calculates the number of months the loan is overdue.
     * @return The number of overdue months.
     */
    @Override
    int getOverDueMonths() {
        float moneyOwed = this.getPaymentDifference();
        if (moneyOwed <= 0) { // loan is not overdue
            return 0;
        }
        return (int) Math.ceil(moneyOwed / this.getTotalMonthlyCost());
    }

    /**
     * Checks if the loan is overdue.
     * @return true if the loan is overdue, false otherwise.
     */
    @Override
    boolean isOverDue() {
        return this.getOverDueMonths() > 0;
    }

    /**
     * Gets the type of the loan.
     * @return The loan type identifier.
     */
    public String getName() {
        return LOAN_TYPE;
    }

    /**
     * Calculates the monthly installment amount for the loan.
     * @return The monthly installment amount.
     */
    @Override
    public float getMonthlyInstalmentAmount() {
        int months = this.getMonthsUntilDueDate();
        return getRemainingOwed() / months;
    }
}