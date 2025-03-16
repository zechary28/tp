package seedu.address.model.person;

import java.util.Calendar;
import java.util.Date;

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
    public SimpleInterestLoan(int amount, int interest, Date dueDate) {
        super(amount, interest, dueDate);
    }

    /**
     * Pays a certain amount towards the loan.
     *
     * @param payment The amount paid.
     */
    @Override
    void pay(int payment) {
        if (payment <= 0) {
            throw new IllegalArgumentException("Payment must be greater than zero.");
        }

        if (getRemainder() <= 0) {
            System.out.println("Loan already paid.");
            return;
        }

        // Calculate simple interest based on loan duration
        int totalInterest = calculateTotalInterest();
        int totalAmountDue = getAmount() + totalInterest;

        // Reduce the remainder
        int newRemainder = getRemainder() - payment;

        if (newRemainder <= 0) {
            System.out.println("Loan fully paid!");
            newRemainder = 0;
        } else {
            System.out.println("Remaining loan amount: " + newRemainder);
        }

        // Update loan details
        this.setRemainder(newRemainder);
        this.setDateLastPaid(new Date()); // Today's date
        this.setIsPaid(newRemainder == 0);
    }

    /**
     * Calculates the total simple interest for the loan.
     * Interest = Principal * (Rate / 100) * Time (in years)
     */
    private int calculateTotalInterest() {
        double rateDecimal = getInterest() / 100.0;
        double years = calculateLoanDurationInYears();
        return (int) Math.round(getAmount() * rateDecimal * years);
    }

    /**
     * Calculates the duration of the loan in years.
     */
    private double calculateLoanDurationInYears() {
        long diffInMillis = getDueDate().getTime() - getDateCreated().getTime();
        return diffInMillis / (1000.0 * 60 * 60 * 24 * 365); // Convert milliseconds to years
    }

    @Override
    public String toString() {
        return "Simple Interest Loan: " + super.toString();
    }
}
