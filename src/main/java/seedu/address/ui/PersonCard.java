package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.person.Loan;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    public final Person person;

    private final ObservableList<Person> personList;

    private final MainWindow mainWindow;

    private final int displayedIndex;

    @FXML
    private Button personButton;
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
    private ListView<Loan> loanListView;
    @FXML
    private Label loans;

    /**
     * Creates a personcard controller
     * @param person
     * @param displayedIndex
     * @param mainWindow
     */
    public PersonCard(Person person, int displayedIndex, MainWindow mainWindow, ObservableList<Person> personList) {
        super(FXML);
        this.person = person;
        this.mainWindow = mainWindow;
        this.displayedIndex = displayedIndex;
        this.personList = personList;
        initializePersonDetails(displayedIndex);
        initializeLoanListView();
    }

    private void initializePersonDetails(int displayedIndex) {
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);

        tags.getChildren().clear();
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void initializeLoanListView() {
        loanListView.setItems(person.getLoanList().getLoans());
        if (person.getLoanList().getLoans().isEmpty()) {
            loans.setText("No loans found.");
        }
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
                setGraphic(new LoanIndivSmall(person.getLoanList().getLoans(), loan).getRoot());
            }
        }
    }


    @FXML
    private void handleCardClick() {
        // Get the MainWindow and trigger the content switch
        this.mainWindow.switchToIndividualPersonPage(personList, displayedIndex);
    }
}
