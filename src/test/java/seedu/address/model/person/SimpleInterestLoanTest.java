package seedu.address.model.person;

// import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
public class SimpleInterestLoanTest {
    public static final String INVALID_AMT = "amount";
    public static final String INVALID_INTEREST = "interest";
    public static final String INVALID_DATE = "date";

    public static final String VALID_AMT = "2000";
    public static final String VALID_INTEREST = "20";
    public static final String VALID_DATE = "27/12/2050";

    public static final String VALID_LOAN_STRING = "1000.91/45.6/4.19/27-12-2050/24-12-2023/24-12-2004/S/0";

    @Test
    public void constructor_invalidArguments_throwsIllegalArgumentException() {
        // invalid amt
        assertThrows(IllegalArgumentException.class, () ->
            new SimpleInterestLoan(INVALID_AMT, VALID_INTEREST, VALID_DATE));

        // invalid interest
        assertThrows(IllegalArgumentException.class, () ->
            new SimpleInterestLoan(VALID_AMT, INVALID_INTEREST, VALID_DATE));

        // invalid date
        assertThrows(IllegalArgumentException.class, () ->
            new SimpleInterestLoan(VALID_AMT, VALID_INTEREST, INVALID_DATE));
    }

    @Test
    public void isValidLoan() {
        Loan loan = new SimpleInterestLoan(VALID_AMT, VALID_INTEREST, VALID_DATE);
        assertTrue(Loan.isValidLoan(loan));

        assertTrue(Loan.isValidLoanString(VALID_LOAN_STRING));
    }
}
