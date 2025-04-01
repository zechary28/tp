package seedu.address.ui;

import java.util.Comparator;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Loan;
import seedu.address.model.person.Person;

/**
 * Controller for individual person card
 */
public class IndividualPerson extends UiPart<Region> {

    private static final String FXML = "IndividualPersonCard.fxml";

    private final MainWindow mainWindow;

    private final Person person;

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox loanContainer; // Container for loan cards

    /**
     * Intializes an individualPerson controller
     * @param person
     * @param mainWindow
     * @param displayedIndex
     */
    public IndividualPerson(Person person, MainWindow mainWindow,
        int displayedIndex) {
        super(FXML);
        this.mainWindow = mainWindow;
        this.person = person;
        initializeNewPage(displayedIndex);
    }

    private void updateLoanCards() {
        loanContainer.getChildren().clear();
        person.getLoanList().stream()
                .forEach(loan ->
                    loanContainer.getChildren().add(new LoanIndiv(person.getLoanList().getLoans(), loan).getRoot()));
    }


    private void initializeNewPage(int displayedIndex) {
        Platform.runLater(() -> {
            id.setText(displayedIndex + ". ");
            name.setText(person.getName().fullName);
            phone.setText(person.getPhone().value);
            address.setText(person.getAddress().value);
            email.setText(person.getEmail().value);

            tags.getChildren().clear();
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

            updateLoanCards(); // Initial UI setup

            // add listener to update UI whenever the loan list changes
            person.getLoanList().getLoans().addListener((ListChangeListener<Loan>) change -> {
                Platform.runLater(this::updateLoanCards);
            });
        });
    }

    // Example method that handles some event, like a button click
    @FXML
    private void handleBackButtonClick() {
        mainWindow.switchToPersonPage(); // Method to switch back to the Person Page
    }
}


