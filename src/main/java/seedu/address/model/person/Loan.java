package seedu.address.model.person;

//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private Date dueDate;
    private Date dateLastPaid = null;
    private Date dateCreated;
    private Boolean isPaid = false;

    /**
     * Constructs an {@code a loan}.
     *
     * @param amount cost of loan.
     * @param interest % of interest, >= 0, 1 represents 100% interest
     * @param due_date date which loan should be completely paid off
     */
    public Loan(int amount, int interest, Date dueDate) {
        // TODO create validations

        this.amount = amount;
        this.remainder = amount;
        this.interest = interest;
        this.dueDate = dueDate;
        this.dateCreated = new Date();
    }

    /**
     * Returns true if a given date string is a valid date.
    */
    public static boolean isValidDateString(String test) {
        return true;
    }

    abstract void pay(int payment);

    private String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM yyyy");
        String formattedDate = formatter.format(date);
        return formattedDate;
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

    public Date getDueDate() {
        return this.dueDate;
    }

    public Date getDateLastPaid() {
        return this.dateLastPaid;
    }

    public Date getDateCreated() {
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

    protected void setDateLastPaid(Date dateLastPaid) {
        this.dateLastPaid = dateLastPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}

