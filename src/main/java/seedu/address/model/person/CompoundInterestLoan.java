package seedu.address.model.person;

import java.util.Date;

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
    public CompoundInterestLoan(int amount, int interest, Date dueDate) {
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

        // Calculate compound interest
        int totalInterest = calculateTotalCompoundInterest();
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
        setRemainder(newRemainder);
        setDateLastPaid(new Date()); // Today's date
        setIsPaid(newRemainder == 0);
    }

    /**
     * Calculates the total compound interest for the loan.
     * Uses formula: A = P (1 + r/n)^(nt) - P
     * Where:
     * - P = Principal (Loan Amount)
     * - r = Annual Interest Rate (as decimal)
     * - n = Number of times interest compounds per year
     * - t = Loan duration in years
     */
    private int calculateTotalCompoundInterest() {
        double rateDecimal = getInterest() / 100.0;
        int compoundingFrequency = 12; // Assume monthly compounding
        double years = calculateLoanDurationInYears();

        double amountWithInterest = getAmount() * Math.pow((1 + rateDecimal / compoundingFrequency), (
                compoundingFrequency * years));
        return (int) Math.round(amountWithInterest - getAmount());
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
        return "Compound Interest Loan: " + super.toString();
    }
}
