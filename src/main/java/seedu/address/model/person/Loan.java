package seedu.address.model.person;

import java.util.Objects;

import java.util.Date;
import java.text.SimpleDateFormat;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

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
    public int remainder;
    public int interest;
    public Date dueDate;
    public Date dateLastPaid = null;
    public Date dateCreated;
    public Boolean paid = false;

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
        .add("paid", paid)
        .toString();

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Address)) {
            return false;
        }

        Loan otherLoan = (Loan) other;
        return interest == otherLoan.interest
                && dueDate.equals(otherLoan.dueDate)
                && dateLastPaid.equals(otherLoan.dateLastPaid)
                && dateCreated.equals(otherLoan.dateCreated)
                && paid == otherLoan.paid;

    }

    @Override
    public int hashCode() {
        return Objects.hash(interest, dueDate, dateLastPaid, dateCreated, paid);
    }
}

/*
  private int amount;
    private int interest;
    private Date dueDate;
    private Date dateLastPaid = null;
    private Date dateCreated;
    private Boolean paid = false;

 */