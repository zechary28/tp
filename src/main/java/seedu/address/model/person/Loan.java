package seedu.address.model.person;

//import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

    public static final int NUMBER_OF_FIELDS = 8; // add 1 for loan type

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
        LocalDate currentDate = LocalDate.now();
        LocalDate date = Loan.toValidLocalDate(dueDate);
        checkArgument(Loan.isValidDueDate(date, currentDate), DATE_MESSAGE_CONSTRAINTS);
        this.dueDate = date;

        this.amtPaid = this.amount;
        this.dateLastPaid = null;
        this.dateCreated = currentDate;
    }

    /**
     * Constructs an {@code a loan}.
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
    public Loan(String strAmount, String strAmtPaid, String strInterest, String strDueDate, String strDateLastPaid,
        String strDateCreated, String strIsPaid) {
        // check interest is valid float
        checkArgument(strInterest.matches(VALIDATION_REGEX));
        this.interest = Float.parseFloat(strInterest);

        // check amount is valid float
        checkArgument(strAmount.matches(VALIDATION_REGEX));
        this.amount = Float.parseFloat(strAmount);

        // check strAmtPaid is valid float
        checkArgument(strAmtPaid.matches(VALIDATION_REGEX));
        this.amtPaid = Float.parseFloat(strAmtPaid);

        // check interest
        checkArgument(this.interest >= 0);

        // check amount
        checkArgument(this.amount > 0);

        // check amtPaid
        checkArgument(this.amtPaid >= 0);

        LocalDate currenDate = LocalDate.now();

        // check date created
        this.dateCreated = Loan.toValidLocalDate(strDateCreated);
        // date created cannot be null and cannot be in the future
        checkArgument(this.dateCreated != null && this.dateCreated.isBefore(currenDate));

        // check due date
        this.dueDate = Loan.toValidLocalDate(strDueDate);
        // due date cannot be null and cannot be before date created
        checkArgument(this.dueDate != null && this.dueDate.isAfter(dateCreated));

        // check date last paid
        this.dateLastPaid = Loan.toValidLocalDate(strDateLastPaid);
        // date last paid can be null but cannot be before date created
        checkArgument(dateLastPaid.isAfter(dateCreated));

        // check strIsPaid
        checkArgument(strIsPaid.equals("1") || strIsPaid.equals("0"));
        if (strIsPaid.equals("1")) {
            this.isPaid = true;
        } else {
            this.isPaid = false;
        }
    }

    /**
     * Convert date string to LocalDate, return null if date string is not valid
    */
    public static LocalDate toValidLocalDate(String dateString) {
        // handle null or empty input
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }

        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                LocalDate date = LocalDate.parse(dateString, formatter);
                return date;
            } catch (DateTimeParseException e) {
                continue;
            }
        }

        return null;
    }

    /**
     * Checks if due date is valid
    */
    public static boolean isValidDueDate(LocalDate dueDate, LocalDate currentDate) {
        if (dueDate == null) {
            return false;
        }

        return dueDate.isAfter(currentDate);
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
        if (dateLastPaid == null) {
            return -1;
        }

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
        if (dateLastPaid == null) {
            return -1;
        }

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
                .add("dateCreated", Loan.dateToString(dateCreated))
                .add("isPaid", isPaid)
                .toString();
    }

    /**
     * Converts a loan to a save string for storage
     */
    public String toSaveString() { // creates a string where fields are seperated by '/'
        StringBuilder saveString = new StringBuilder();
        return saveString.append(amount + '/')
            .append(amtPaid + '/')
            .append(interest + '/')
            .append(Loan.dateToString(dueDate) + '/')
            .append(Loan.dateToString(dateLastPaid) + '/')
            .append(Loan.dateToString(dateCreated) + '/')
            .append(this.getName())
            .toString();
    }

    /**
     * Converts a save string to a loan
    */
    public static Loan stringToLoan(String loanStr) {
        String[] fields = loanStr.split("/");
        if (fields.length != NUMBER_OF_FIELDS) {
            return null;
        }

        String strAmount = fields[0];
        String strAmtPaid = fields[1];
        String strInterest = fields[2];
        String strDueDate = fields[3];
        String strDateLastPaid = fields[4];
        String strDateCreated = fields[5];
        String loanType = fields[6];
        String strIsPaid = fields[7];
        if (loanType.equals(SimpleInterestLoan.LOAN_TYPE)) {
            return new SimpleInterestLoan(strAmount, strAmtPaid, strInterest, strDueDate, strDateLastPaid,
                strDateCreated, strIsPaid);
        } else if (loanType.equals(CompoundInterestLoan.LOAN_TYPE)) {
            return new CompoundInterestLoan(strAmount, strAmtPaid, strInterest, strDueDate, strDateLastPaid,
                strDateCreated, strIsPaid);
        } else {
            return null;
        }
    }

    /**
     * Returns true if a loan string is valid.
    */
    public static boolean isValidLoanString(String loanStr) {
        try {
            Loan loan = Loan.stringToLoan(loanStr);
            return loan != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // (this should be abstracted into a future LoanList Class)
    /**
     * creates a loan list string where each loan string is sepearated by ','
    */
    public static String loanListToString(List<Loan> loans) {
        StringBuilder loanList = new StringBuilder();
        for (Loan loan : loans) {
            loanList.append(loan.toSaveString()).append(',');
        }

        return loanList.toString();
    }

    public static boolean isValidLoanListString(String loanListStr) {
        return false;
    }

    public static List<Loan> stringToLoanList(String loanListStr) {
        return new ArrayList<Loan>();
    }
    // code for loan end


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
                && isPaid == otherLoan.getIsPaid()
                && this.getName().equals(otherLoan.getName());

    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, amtPaid, interest, dueDate, dateLastPaid, dateCreated, isPaid, this.getName());
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
