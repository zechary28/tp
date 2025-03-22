package seedu.address.model.person;

import java.time.LocalDate;

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

    public float getMonthlyInterest() {
        // monthly interest = principal amount * (r / 12) where r is annual interest rate: (6% = 0.06)
        return this.amount * ((this.getInterest() / 100) / 12);
    }

    public float getTotalMonthlyCost() {
        return this.getMonthlyPrincipalPayment() + this.getMonthlyInterest();
    }

    public float getLoanValue() {
        int monthsSinceLoan = this.getMonthsSinceLoan();
        int loanLength = this.getLoanLengthMonths();

        if (loanLength > monthsSinceLoan) { // normal case
            return this.getTotalMonthlyCost() * loanLength;
        } else { // loan past due date
            return (this.getTotalMonthlyCost() * loanLength)
                + (monthsSinceLoan - loanLength) * this.getMonthlyInterest();
        }
    }

    @Override
    float getMoneyOwed() {
        int monthsSinceLoan = this.getMonthsSinceLoan();
        int loanLength = this.getLoanLengthMonths();

        //money owed since start of loan - amt paid
        if (loanLength > monthsSinceLoan) {
            return this.getTotalMonthlyCost() * monthsSinceLoan - this.getAmtPaid();
        } else { // loan past due date
            return (this.getTotalMonthlyCost() * monthsSinceLoan) + (monthsSinceLoan - loanLength)
                * this.getMonthlyInterest() - this.getAmtPaid();
        }
    }

    @Override
    int getOverDueMonths() {
        float moneyOwed = this.getMoneyOwed();

        if (moneyOwed <= 0) { // loan is not overdue
            return 0;
        }

        return (int) Math.ceil(this.getMoneyOwed() / this.getTotalMonthlyCost());
    }

    @Override
    boolean isOverDue() {
        return this.getOverDueMonths() == 0;
    }

    /**
     * Pays a certain amount towards the loan.
     *
     * @param payment The amount paid.
     */
    @Override
    void pay(float payment) {
        // TODO handle case where the payment is more than loan amount
        this.setDateLastPaid(LocalDate.now());

        this.incrementAmtPaid(payment);
        float amtPaid = this.getAmtPaid();

        if (amtPaid >= getLoanValue()) {
            this.setIsPaid(true);
        }
    }

    public String getName() {
        return "Simple Interest Loan";
    }
}
