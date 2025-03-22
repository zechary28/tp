package seedu.address.model.person;

import java.time.LocalDate;

/**
 * Represents a simple interest loan.
 */
public class SimpleInterestLoan extends Loan {
    public static final String LOAN_TYPE = "S";

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
     * Constructs an {@code a SimpleInterestLoan}.
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
    public SimpleInterestLoan(String strAmount, String strAmtPaid, String strInterest, String strDueDate,
        String strDateLastPaid, String strDateCreated, String strIsPaid) {
        super(strAmount, strAmtPaid, strInterest, strDueDate, strDateLastPaid, strDateCreated, strIsPaid);
    }

    public float getMonthlyInterest() {
        // monthly interest = principal amount * (r / 12) where r is annual interest rate: (6% = 0.06)
        return this.amount * ((this.getInterest() / 100) / 12);
    }

    public float getTotalMonthlyCost() {
        return this.getMontlyPrincipalPayment() + this.getMonthlyInterest();
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
        return LOAN_TYPE;
    }
}
