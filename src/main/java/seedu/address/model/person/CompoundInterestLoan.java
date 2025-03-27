package seedu.address.model.person;

import java.time.LocalDate;

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

    public float getMonthlyInterest() {
        // monthly interest = principal * (1 + (r / 12))^monthsSinceLoan - principal
        int monthsSinceLoan = this.getMonthsSinceLoan();
        float monthlyRate = (this.getInterest() / 100) / 12;
        return (float) (this.principal * Math.pow(1 + monthlyRate, monthsSinceLoan) - this.principal);
    }

    public float getTotalMonthlyCost() {
        return this.getMonthlyAveragePrincipal() + this.getMonthlyInterest();
    }

    /**
     * Pays a certain amount towards the loan.
     *
     * @param payment The amount paid.
     */
    @Override
    public void pay(float payment) {
        this.setDateLastPaid(LocalDate.now());
        this.incrementAmtPaid(payment);

        if (this.getAmtPaid() >= this.getLoanValue()) {
            this.setIsPaid(true);
        }
    }

    @Override
    float getPaymentDifference() {
        int monthsSinceLoan = this.getMonthsSinceLoan();
        float monthlyRate = (this.getInterest() / 100) / 12;
        float moneyOwed = (float) (this.principal * Math.pow(1 + monthlyRate, monthsSinceLoan)) - this.getAmtPaid();
        return Math.max(moneyOwed, 0);
    }

    @Override
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

    @Override
    int getOverDueMonths() {
        float moneyOwed = this.getPaymentDifference();
        if (moneyOwed <= 0) { // loan is not overdue
            return 0;
        }
        return (int) Math.ceil(moneyOwed / this.getTotalMonthlyCost());
    }

    @Override
    boolean isOverDue() {
        return this.getOverDueMonths() == 0;
    }

    public String getName() {
        return LOAN_TYPE;
    }

    @Override
    public float getMonthlyInstalmentAmount() {
        // TODO: implement method
        return 0;
    }
}
