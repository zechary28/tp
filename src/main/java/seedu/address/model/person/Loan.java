package seedu.address.model.person;

//import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person's loan in the address book.
 */
public abstract class Loan {

    public static final String DATE_MESSAGE_CONSTRAINTS = "The due date of a Loan has to be a valid date string"
        + " greater than the current date.";

    public static final String INTEREST_MESSAGE_CONSTRAINTS = "The interest must be a valid float"
        + " and be greater than or equals to 0";
    public static final String AMOUNT_MESSAGE_CONSTRAINTS = "The amount must be a valid float"
        + " and be greater than or equals to 0";

    public static final int MONTHLY_DUE_DATE = 1; // to signifies the 1 day of every month

    public static final String VALIDATION_REGEX = "^(\\d+(\\.\\d{1,2})?)"; // allows floats up to 2 d.p.

    private static final List<DateTimeFormatter> DATE_FORMATTERS = Arrays.asList(
        DateTimeFormatter.ISO_LOCAL_DATE, // yyyy-MM-dd
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("MM/dd/yyyy"),
        DateTimeFormatter.ofPattern("yyyyMMdd"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
        DateTimeFormatter.ofPattern("MM-dd-yyyy"),
        DateTimeFormatter.ofPattern("EEE MMM d yyyy", Locale.ENGLISH) // Wed Dec 27 2023
    );

    public final float amount;
    private float amtPaid = 0;
    private float interest;
    private LocalDate dueDate;
    private LocalDate dateLastPaid;
    private final LocalDate dateCreated;
    private Boolean isPaid = false;

    /**
     * Constructs an {@code a loan}.
     *
     * @param strAmount cost of loan.
     * @param strInterest % of yearly interest, >= 0, 1 represents 1% interest
     * @param dueDate date which loan should be completely paid off
     */
    public Loan(String strAmount, String strInterest, String dueDate) {
        // check interest is valid float
        checkArgument(strInterest.matches(VALIDATION_REGEX), INTEREST_MESSAGE_CONSTRAINTS);
        this.interest = Float.parseFloat(strInterest);

        // check amount is valid float
        checkArgument(strAmount.matches(VALIDATION_REGEX), AMOUNT_MESSAGE_CONSTRAINTS);
        this.amount = Float.parseFloat(strAmount);

        // check interest
        checkArgument(this.interest >= 0, INTEREST_MESSAGE_CONSTRAINTS);

        // check amount
        checkArgument(this.amount > 0, AMOUNT_MESSAGE_CONSTRAINTS);

        // check due date string
        LocalDate date = Loan.toValidLocalDate(dueDate);
        checkArgument(date != null, DATE_MESSAGE_CONSTRAINTS);
        this.dueDate = date;

        LocalDate currentDate = LocalDate.now();
        this.amtPaid = this.amount;
        this.dateLastPaid = currentDate;
        this.dateCreated = currentDate;
    }

    /**
     * Convert date string to LocalDate, return null if date string is not valid
     */
    public static LocalDate toValidLocalDate(String dateString) {
        // handle null or empty input
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }

        LocalDate currentDate = LocalDate.now();
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                LocalDate date = LocalDate.parse(dateString, formatter);
                if (date.isAfter(currentDate)) {
                    return date;
                }
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
            }
        }

        return null;
    }

    /**
     * Returns true if a loan is valid.
     */
    public static boolean isValidLoan(Loan loan) {
        return loan.getAmount() > 0 && loan.getInterest() >= 0;
    }

    abstract void pay(float amt);

    abstract float getMoneyOwed();

    abstract float getLoanValue();

    abstract boolean isOverDue();

    // is an estimation (amount owed over monthly cost)
    abstract int getOverDueMonths();

    public float getMontlyPrincipalPayment() {
        return amount / this.getLoanLengthMonths();
    }

    public int getDaysSinceLastPaid() {
        // get the current date
        LocalDate currentDate = LocalDate.now();

        // get the due day of the current month
        LocalDate dueDayOfMonth = currentDate.withDayOfMonth(Loan.MONTHLY_DUE_DATE);

        // Calculate the difference in days (safe to cast as days will never overflow)
        int daysBetween = (int) ChronoUnit.DAYS.between(dateLastPaid, dueDayOfMonth);

        return daysBetween;
    }

    // when using be careful, months could be 0, in which case, you should show days
    public int getMonthsSinceLastPaid() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // get the due day of the current month
        LocalDate dueDayOfMonth = currentDate.withDayOfMonth(Loan.MONTHLY_DUE_DATE);

        // Calculate the difference in months (safe to cast as months will never overflow)
        int monthsBetween = (int) ChronoUnit.MONTHS.between(dateLastPaid, dueDayOfMonth);

        return monthsBetween;
    }

    public int getMonthsSinceLoan() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // get the due day of the current month
        LocalDate dueDayOfMonth = currentDate.withDayOfMonth(Loan.MONTHLY_DUE_DATE);

        // Calculate the difference in months (safe to cast as months will never overflow)
        int monthsBetween = (int) ChronoUnit.MONTHS.between(dateLastPaid, dueDayOfMonth);

        return monthsBetween;
    }

    public int getLoanLengthMonths() {
        // Calculate the difference in months (safe to cast as months will never overflow)
        int months = (int) ChronoUnit.MONTHS.between(dateCreated, dueDate);
        if (months == 0) {
            return 1; // case where due date is not a full month after date created
        } else {
            return months;
        }
    }

    private static String dateToString(LocalDate date) {
        return date.format(DATE_FORMATTERS.get(0)); // change to enum
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("amount", amount)
                .add("amtPaid", amtPaid)
                .add("interest", interest)
                .add("dueDate", Loan.dateToString(dueDate))
                .add("dateLastPaid", Loan.dateToString(dateLastPaid))
                .add("isPaid", isPaid)
                .toString();
    }

    /* TODO for save
    public String toSaveString() {
        StringBuilder saveString = new StringBuilder();
        saveString.append(amount)
            .ap
    }

    public static String loanListToString(List<Loan> loans) {
        StringBuilder loanList = new StringBuilder();
        for (Loan loan : loans) {
            loanList.app
        }

        return "";
    }

    public static boolean isValidLoanListString(String loanListStr) {
        return false;
    }

    public static List<Loan> stringToLoanList(String loanListStr) {
        return new ArrayList<Loan>();
    }
    */



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
                && amtPaid == otherLoan.getAmtPaid()
                && interest == otherLoan.getInterest()
                && dueDate.equals(otherLoan.getDueDate())
                && dateLastPaid.equals(otherLoan.getDateLastPaid())
                && dateCreated.equals(otherLoan.getDateCreated())
                && isPaid == otherLoan.getIsPaid();

    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, amtPaid, interest, dueDate, dateLastPaid, dateCreated, isPaid);
    }

    public float getAmtPaid() {
        return this.amtPaid;
    }

    public float getInterest() {
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

    public float getAmount() {
        return this.amount;
    }

    public void incrementAmtPaid(float pay) {
        this.amtPaid += pay;
    }

    protected void setDateLastPaid(LocalDate dateLastPaid) {
        this.dateLastPaid = dateLastPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public abstract String getName();
}
