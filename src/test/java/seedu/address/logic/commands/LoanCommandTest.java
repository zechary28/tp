package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Integration tests for LoanCommand.
 */
public class LoanCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validSimpleLoan_success() throws Exception {
        LoanCommand command = new LoanCommand(
                INDEX_FIRST_PERSON, "s", "1000", "5", "2025-12-12");

        CommandResult result = command.execute(model);

        // Check that feedback contains the person's name
        String feedback = result.getFeedbackToUser();
        String personName = model.getFilteredPersonList().get(0).getName().toString();
        assert(feedback.contains(personName));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBounds = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LoanCommand command = new LoanCommand(outOfBounds,
                "s", "1000", "5", "2025-12-12");

        assertThrows(CommandException.class, () -> command.execute(model));
    }
}
