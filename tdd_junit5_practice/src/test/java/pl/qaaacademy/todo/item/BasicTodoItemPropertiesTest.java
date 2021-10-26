package pl.qaaacademy.todo.item;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import pl.qaaacademy.todo.exceptions.IllegalStatusChangeException;
import pl.qaaacademy.todo.exceptions.TodoItemValidationException;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static pl.qaaacademy.todo.matchers.ValidTodoItemMatcher.isValidItem;

@Tag("item")
public class BasicTodoItemPropertiesTest extends BaseTest {


    @Tag("happy")
    @Test
    public void shouldCreateTodoItemWithTitleAndDescription() {

        assertThat(item, isValidItem(title, description));
    }

    @Tag("exception")
    @Test
    public void shouldThrowAnExceptionWhileCreatingItemWithEmptyTitle() {
        String invalidTitle = "";
        String description = "Some weird description for non-existing todo item";
        assertThrows(TodoItemValidationException.class,
                () -> TodoItem.of(invalidTitle, description));
    }

    @Test
    public void shouldThrowAnExceptionWileSettingAnEmptyTitle() {
        String invalidTitle = "";
        assertThrows(TodoItemValidationException.class, () -> item.setTitle(invalidTitle));
    }

    @Test
    public void shouldToggleStatusFromPendingToInProgress() {
        item.toggleStatus();
        assertEquals(item.getStatus(), ItemStatus.IN_PROGRESS);
    }

    @Test
    public void shouldToggleStatusFromInProgressToPending() {
        TodoItem item = TodoItem.of(title, description);
        item.toggleStatus();
        item.toggleStatus();
        assertEquals(item.getStatus(), ItemStatus.PENDING);
    }

    @Test
    public void shouldCompleteATaskRepresented() {
        item.toggleStatus();
        item.complete();
        assertEquals(item.getStatus(), ItemStatus.COMPLETED);
    }

    @Tag("exception")
    @Test
    public void shouldThrowExceptionWhenTryingToCompleteAnItemThatIsPending() {
        assertThrows(IllegalStatusChangeException.class, () -> item.complete());
    }

    @Tag("exception")
    @Test
    public void shouldThrowExceptionWhenTryingToCompleteAnItemThatIsCompleted() {
        item.toggleStatus();
        item.complete();
        assertThrows(IllegalStatusChangeException.class, () -> item.complete());
    }

    @Test
    public void shouldNotToggleStatusFromCompletedTOInProgress() {
        item.toggleStatus();
        item.complete();
        assertThrows(IllegalStatusChangeException.class, () -> item.toggleStatus());
    }

    @Test
    public void shouldNotCreateATodoItemWithDescriptionLongerThan250Characters() {
        String description = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012" +
                "34567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234" +
                "5678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345" +
                "6789012345678901234567890123456789012345678901234567890";
        assertThrows(TodoItemValidationException.class, () -> TodoItem.of(title, description));

    }

    @Test
    public void shouldNotCreateATodoItemWithNullDescription() {
        String title = "Complete Java Udemy Course";
        String description = null;
        assertThrows(TodoItemValidationException.class, () -> TodoItem.of(title, description));
    }

    @Test
    public void shouldNotSetADescriptionLongerThan250Characters() {

        String description = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012" +
                "34567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234" +
                "5678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345" +
                "6789012345678901234567890123456789012345678901234567890";

        assertThrows(TodoItemValidationException.class, () -> item.setDescription(description));

    }

    @Test
    public void shouldNotSetAnEmptyDescription() {

        String description = null;

        assertThrows(TodoItemValidationException.class, () -> item.setDescription(description));

    }

}






