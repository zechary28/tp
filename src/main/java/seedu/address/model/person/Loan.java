package seedu.address.model.person;

//import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person's loan in the address book.
 */
public abstract class Loan {

    public static final String MESSAGE_CONSTRAINTS = "The due date of a Loan has to be a valid date string"
        + "greater than the current date.";

    public static final int MONTHLY_DUE_DATE = 1; // to signifies the 1 day of every month

    public final int amount;
    private int remainder;
    private int interest;
    private LocalDate dueDate;
    private LocalDate dateLastPaid;
    private LocalDate dateCreated;
    private Boolean isPaid = false;

    /**
     * Constructs an {@code a loan}.
     *
     * @param amount cost of loan.
     * @param interest % of interest, >= 0, 1 represents 100% interest
     * @param dueDate date which loan should be completely paid off
     */
    public Loan(int amount, int interest, String dueDate) {
        // validations
        checkArgument(Loan.isValidDateString(dueDate), MESSAGE_CONSTRAINTS);
        this.dueDate = LocalDate.parse(dueDate, DateTimeFormatter.ISO_LOCAL_DATE);

        LocalDate currentDate = LocalDate.now();
        this.amount = amount;
        this.remainder = amount;
        this.interest = interest;
        this.dateLastPaid = currentDate;
        this.dateCreated = currentDate;
    }

    /**
     * Returns true if a given date string is a valid date.
     */
    public static boolean isValidDateString(String dateString) {
        try {
            LocalDate currentDate = LocalDate.now();
            // parse the string to LocalDate
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
            return date.isAfter(currentDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    abstract void pay(int amt);

    abstract int getMoneyOwed();

    public int getOverDueDays() {
        // get the current date
        LocalDate currentDate = LocalDate.now();

        // get the due day of the current month
        LocalDate dueDayOfMonth = currentDate.withDayOfMonth(Loan.MONTHLY_DUE_DATE);

        // Calculate the difference in days (safe to cast as days will never overflow)
        int daysBetween = (int) ChronoUnit.DAYS.between(dateLastPaid, dueDayOfMonth);

        return daysBetween;
    }

    public int getOverDueMonths() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // get the due day of the current month
        LocalDate dueDayOfMonth = currentDate.withDayOfMonth(Loan.MONTHLY_DUE_DATE);

        // Calculate the difference in months (safe to cast as months will never overflow)
        int monthsBetween = (int) ChronoUnit.MONTHS.between(dateLastPaid, dueDayOfMonth);

        return monthsBetween;
    }

    private static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM yyyy", Locale.ENGLISH);
        return date.format(formatter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("amount", amount)
                .add("remainder", remainder)
                .add("interest", interest)
                .add("dueDate", Loan.dateToString(dueDate))
                .add("dateLastPaid", Loan.dateToString(dateLastPaid))
                .add("isPaid", isPaid)
                .toString();

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Loan)) {
            return false;
        }

        Loan otherLoan = (Loan) other;
        return amount == otherLoan.amount
                && remainder == otherLoan.getRemainder()
                && interest == otherLoan.getInterest()
                && dueDate.equals(otherLoan.getDueDate())
                && dateLastPaid.equals(otherLoan.getDateLastPaid())
                && dateCreated.equals(otherLoan.getDateCreated())
                && isPaid == otherLoan.getIsPaid();

    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, remainder, interest, dueDate, dateLastPaid, dateCreated, isPaid);
    }

    public int getRemainder() {
        return this.remainder;
    }

    public int getInterest() {
        return this.interest;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public LocalDate getDateLastPaid() {
        return this.dateLastPaid;
    }

    public LocalDate getDateCreated() {
        return this.dateCreated;
    }

    public Boolean getIsPaid() {
        return this.isPaid;
    }

    public int getAmount() {
        return this.amount;
    }

    protected void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    protected void setDateLastPaid(LocalDate dateLastPaid) {
        this.dateLastPaid = dateLastPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}
