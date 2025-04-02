package seedu.address.model.person;

// import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
public class LoanListTest {
    // date format is important as save string saves in yyyy-mm-dd
    public static final String VALID_LOAN_STRING = "1000.91/45.60/4.19/2050-12-27/2023-12-24/2004-12-24/S/0,"
        + "1002.91/45.60/4.19/2050-12-27/2023-12-24/2004-12-24/G/0," // G will be wrong
        + "3000.91/45.60/4.19/2050-12-27/2023-12-24/2004-12-24/C/1," // 1 should be updated
        + "3000.91/45.60/4.19/2050-12-29/2023-12-24/2004-12-24/C/1";

    public static final String VALID_OUTPUT_LOAN_STRING = "1000.91/45.60/4.19/2050-12-27/2023-12-24/2004-12-24/S/0,"
        + "3000.91/45.60/4.19/2050-12-27/2023-12-24/2004-12-24/C/0," // this is the correct string
        + "3000.91/45.60/4.19/2050-12-29/2023-12-24/2004-12-24/C/0";

    @Test
    public void isValidLoan() {
        LoanList loanList = LoanList.stringToLoanList(VALID_LOAN_STRING);
        assertTrue(loanList.toSaveString().equals(VALID_OUTPUT_LOAN_STRING));

        loanList = LoanList.stringToLoanList("");
        assertTrue(loanList.toSaveString().equals(LoanList.EMPTY_STRING));
    }
}
