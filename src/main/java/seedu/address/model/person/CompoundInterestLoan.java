package seedu.address.model.person;

import java.time.LocalDate;

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

    public float getMonthlyInterest() {
        // monthly interest = principal * (1 + (r / 12))^monthsSinceLoan - principal
        int monthsSinceLoan = this.getMonthsSinceLoan();
        float monthlyRate = (this.getInterest() / 100) / 12;
        return (float) (this.amount * Math.pow(1 + monthlyRate, monthsSinceLoan) - this.amount);
    }

    public float getTotalMonthlyCost() {
        return this.getMonthlyPrincipalPayment() + this.getMonthlyInterest();
    }

    /**
     * Pays a certain amount towards the loan.
     *
     * @param payment The amount paid.
     */
    @Override
    void pay(float payment) {
        this.setDateLastPaid(LocalDate.now());
        this.incrementAmtPaid(payment);

        if (this.getAmtPaid() >= this.getLoanValue()) {
            this.setIsPaid(true);
        }
    }

    @Override
    float getMoneyOwed() {
        int monthsSinceLoan = this.getMonthsSinceLoan();
        float monthlyRate = (this.getInterest() / 100) / 12;
        float moneyOwed = (float) (this.amount * Math.pow(1 + monthlyRate, monthsSinceLoan)) - this.getAmtPaid();
        return Math.max(moneyOwed, 0);
    }

    @Override
    float getLoanValue() {
        int loanLength = this.getLoanLengthMonths();
        float monthlyRate = (this.getInterest() / 100) / 12;

        // compound interest formula: A = P * (1 + r / n)^(n * t)
        float totalLoanValue = (float) (this.amount * Math.pow(1 + monthlyRate, loanLength));
        int monthsSinceLoan = this.getMonthsSinceLoan();
        if (monthsSinceLoan > loanLength) { // loan past due date
            float overdueAmount = (float) (totalLoanValue * Math.pow(1 + monthlyRate, monthsSinceLoan - loanLength));
            return totalLoanValue + overdueAmount;
        }
        return totalLoanValue;
    }

    @Override
    int getOverDueMonths() {
        float moneyOwed = this.getMoneyOwed();
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
        return "Compound Interest Loan";
    }
}
