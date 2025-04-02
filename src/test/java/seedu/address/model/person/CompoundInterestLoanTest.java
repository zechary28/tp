package seedu.address.model.person;

// import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
public class CompoundInterestLoanTest {
    public static final String INVALID_AMT = "amount";
    public static final String INVALID_INTEREST = "interest";
    public static final String INVALID_DATE = "date";

    public static final String VALID_AMT = "2000";
    public static final String VALID_INTEREST = "20";
    public static final String VALID_DATE = "2050-12-27";
    public static final String PAST_DATE = "2025-03-30";

    @Test
    public void constructor_invalidArguments_throwsIllegalArgumentException() {
        // invalid amt
        assertThrows(IllegalArgumentException.class, () ->
            new CompoundInterestLoan(INVALID_AMT, VALID_INTEREST, VALID_DATE));

        // invalid interest
        assertThrows(IllegalArgumentException.class, () ->
            new CompoundInterestLoan(VALID_AMT, INVALID_INTEREST, VALID_DATE));

        // invalid date
        assertThrows(IllegalArgumentException.class, () ->
            new CompoundInterestLoan(VALID_AMT, VALID_INTEREST, INVALID_DATE));
    }

    @Test
    public void testIsValidLoan_withValidLoan() {
        Loan loan = new CompoundInterestLoan(VALID_AMT, VALID_INTEREST, VALID_DATE);
        assertTrue(Loan.isValidLoan(loan));
    }

    @Test
    public void testisOverDue_withOverDueLoan() {
        CompoundInterestLoan loan = new CompoundInterestLoan(VALID_AMT, VALID_INTEREST, VALID_DATE);
        loan.setDueDate(PAST_DATE);
        assertTrue(loan.isOverDue());
    }
}
