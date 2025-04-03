package seedu.address.ui;

import java.util.Comparator;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.person.Loan;
import seedu.address.model.person.Person;

/**
 * Controller for individual person card
 */
public class IndividualPerson extends UiPart<Region> {

    private static final String FXML = "IndividualPersonCard.fxml";

    private final MainWindow mainWindow;
    private final ObservableList<Person> personList;
    private Person person;
    private final int displayedIndex;

    @FXML private Label name;
    @FXML private Label id;
    @FXML private Label phone;
    @FXML private Label address;
    @FXML private Label email;
    @FXML private FlowPane tags;
    @FXML private ListView<Loan> loanListView;

    /**
     * creates individual person controller
     * @param personList
     * @param mainWindow
     * @param displayedIndex
     */
    public IndividualPerson(ObservableList<Person> personList, MainWindow mainWindow, int displayedIndex) {
        super(FXML);
        this.mainWindow = mainWindow;
        this.personList = personList;
        this.displayedIndex = displayedIndex;
        this.person = personList.get(displayedIndex - 1); // Initialize person

        Platform.runLater(() -> initialize());
    }

    private void initialize() {
        updatePersonDetails();
        initializeLoanListView();

        personList.addListener((ListChangeListener<Person>) change -> {
            while (change.next()) {
                if (change.getFrom() <= displayedIndex - 1 && change.getTo() > displayedIndex - 1) {
                    this.person = personList.get(displayedIndex - 1);
                    updatePersonDetails();
                    loanListView.setItems(person.getLoanList().getLoans());
                }
            }
        });
    }

    private void initializeLoanListView() {
        loanListView.setItems(person.getLoanList().getLoans());
        loanListView.setCellFactory(listView -> new LoanListViewCell());
    }

    class LoanListViewCell extends ListCell<Loan> {
        @Override
        protected void updateItem(Loan loan, boolean empty) {
            super.updateItem(loan, empty);

            if (empty || loan == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LoanIndiv(person.getLoanList().getLoans(), loan).getRoot());
            }
        }
    }


    private void updatePersonDetails() {
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);

        // Update tags
        tags.getChildren().clear();
        person.getTags().stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @FXML
    private void handleBackButtonClick() {
        mainWindow.switchToPersonPage();
    }
}


