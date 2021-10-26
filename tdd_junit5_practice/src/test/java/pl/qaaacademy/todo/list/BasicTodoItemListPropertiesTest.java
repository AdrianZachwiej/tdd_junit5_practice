package pl.qaaacademy.todo.list;

import org.junit.jupiter.api.Test;
import pl.qaaacademy.todo.exceptions.InvalidListTitleException;
import pl.qaaacademy.todo.item.ItemStatus;
import pl.qaaacademy.todo.item.TodoItem;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BasicTodoItemListPropertiesTest extends BasicListTest {

    @Test
    public void listShouldBeCreatedEmpty(){

        assertEquals(this.itemList.getSize(), 0);

    }
    @Test
    public void validateTitleAndDescription(){
        assertEquals(title,itemList.getTitle());
        assertEquals(0, itemList.getSize());
    }
    @Test
    public void listSizeShouldIncrease(){
        TodoItem newTodoItem = TodoItem.of("title", "description");
        this.itemList.addItem(newTodoItem);
        assertEquals(this.itemList.getSize(), 1);
    }
    @Test
    public void listCantBeCreatedWithEmptyTitle(){
        String emptyTitle = " ";
        assertThrows(InvalidListTitleException.class,()->TodoItemList.of(emptyTitle));
    }

    @Test
    public void shouldAddItem(){
        TodoItem newTodoItem = TodoItem.of("title", "description");
        assertTrue(itemList.addItem(newTodoItem));
        assertTrue(itemList.getItemList().contains(newTodoItem));
    }

    @Test
    public void shouldDeleteItem(){
        TodoItem item = TodoItem.of("title", "description");
        itemList.addItem(item);
        assertTrue(itemList.deleteTodoItemByTitle("title"));
        assertEquals(itemList.getSize(), 0);
    }
    @Test
    public void shouldFilterByStatus(){
        ItemStatus status = ItemStatus.IN_PROGRESS;
        TodoItem item1 = TodoItem.of("TitleA", "A");
        TodoItem item2 = TodoItem.of("TitleB", "B");
        TodoItem item3 = TodoItem.of("TitleC", "C");
        itemList.addItem(item1);
        itemList.addItem(item2);
        itemList.addItem(item3);
        item2.toggleStatus();
        item3.toggleStatus();
        List<TodoItem> filteredList = itemList.getItemList().stream()
                                    .filter(item -> item.getStatus() == status).toList();
        assertEquals(filteredList.size(), 2);
    }
    @Test
    public void shouldSortByTitle(){
        TodoItem item1 = TodoItem.of("X", "descriptionX");
        TodoItem item2 = TodoItem.of("A", "descriptionA");
        TodoItem item3 = TodoItem.of("G", "descriptionG");
        itemList.addItem(item1);
        itemList.addItem(item2);
        itemList.addItem(item3);
        List<TodoItem> list = itemList.getItemList().stream()
                        .sorted(Comparator.comparing(TodoItem::getTitle)).toList();
        itemList.setItemList(list);
        assertEquals(itemList.getItemList().get(0).getTitle(), "A");
    }
    @Test
    public void shouldNotDuplicateTitles(){
        TodoItem item1 = TodoItem.of("titledup", "description1");
        TodoItem item2 = TodoItem.of("titledup", "description2");
        itemList.addItem(item1);
        itemList.addItem(item2);
    }
}
