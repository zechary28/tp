package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Loan;

/**
 * UI component that represents a single Loan in the UI.
 */
public class LoanIndivSmall extends UiPart<VBox> {
    private static final String FXML = "LoanSmallCard.fxml";

    public final Loan loan;

    private final ObservableList<Loan> loans;

    @FXML
    private Label loanType;
    @FXML
    private Label remainingOwed;
    @FXML
    private Label amtPaid;
    @FXML
    private Label dueDate;
    @FXML
    private Label lastPaid;

    /**
     * Create a loanindiv controller
     */
    public LoanIndivSmall(ObservableList<Loan> loans, Loan loan) {
        super(FXML);
        this.loan = loan;
        this.loans = loans;
        Platform.runLater(() -> {
            int index = this.loans.indexOf(loan) + 1;
            loanType.setText(index + ". " + "Loan Type: " + loan.getName());

            // 1st 4
            remainingOwed.setText("Remaining owed: $" + String.format("%.2f", loan.getRemainingOwed()));
            dueDate.setText("Due Date: " + loan.getDueDate());

            //3rd 4
            lastPaid.setText("Last Paid: "
                + (loan.getDateLastPaid() != null ? loan.getDateLastPaid().toString() : "Not Paid Yet"));
            amtPaid.setText("Amount Paid: $" + String.format("%.2f", loan.getAmtPaid()));
        });
    }
}
