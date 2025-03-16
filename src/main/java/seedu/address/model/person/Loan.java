package seedu.address.model.person;

//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person's loan in the address book.
 */
public abstract class Loan {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public static final int MONTHLY_DUE_DATE = 1; // to signifies the 1 day of every month
    public final int amount;
    private int remainder;
    private int interest;
    private LocalDate dueDate;
    private LocalDate dateLastPaid = null;
    private LocalDate dateCreated;
    private Boolean isPaid = false;

    /**
     * Constructs an {@code a loan}.
     *
     * @param amount cost of loan.
     * @param interest % of interest, >= 0, 1 represents 100% interest
     * @param due_date date which loan should be completely paid off
     */
    public Loan(int amount, int interest, String dueDate) {
        // TODO create validations

        this.amount = amount;
        this.remainder = amount;
        this.interest = interest;
        this.dueDate = LocalDate.now(); // to parse dueDate string
        this.dateCreated = LocalDate.now();
    }

    /**
     * Returns true if a given date string is a valid date.
    */
    public static boolean isValidDateString(String test) {
        return true;
    }

    abstract void pay();

    public long getOverDueDays() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get the first day of the current month
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);

        // Calculate the difference in days
        long daysBetween = ChronoUnit.DAYS.between(dateLastPaid, firstDayOfMonth);

        return daysBetween;
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
        .add("dueDate", dateToString(dueDate))
        .add("dateLastPaid", dateToString(dateLastPaid))
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
}

