package at.fhhagenberg.view;

import at.fhhagenberg.elevator.view.ElevatorTextInfoPane;
import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class ElevatorTextInfoPaneTest {
    @Test
    void testConstructor() {
        ElevatorViewModel elevatorViewModelMock = mock(ElevatorViewModel.class);
        when(elevatorViewModelMock.capacityStringProperty()).thenReturn(new SimpleStringProperty("test1"));
        when(elevatorViewModelMock.weightStringProperty()).thenReturn(new SimpleStringProperty("test2"));
        when(elevatorViewModelMock.targetStringProperty()).thenReturn(new SimpleStringProperty("test3"));
        when(elevatorViewModelMock.speedStringProperty()).thenReturn(new SimpleStringProperty("test4"));
        when(elevatorViewModelMock.accelerationStringProperty()).thenReturn(new SimpleStringProperty("test5"));
        when(elevatorViewModelMock.doorStatusProperty()).thenReturn(new SimpleStringProperty("test6"));

        ElevatorTextInfoPane elevatorTextInfoPane = new ElevatorTextInfoPane(elevatorViewModelMock);
        assertEquals("test1", ((Label) elevatorTextInfoPane.getChildren().get(1)).getText());
        assertEquals("test2", ((Label) elevatorTextInfoPane.getChildren().get(3)).getText());
        assertEquals("test3", ((Label) elevatorTextInfoPane.getChildren().get(5)).getText());
        assertEquals("test4", ((Label) elevatorTextInfoPane.getChildren().get(7)).getText());
        assertEquals("test5", ((Label) elevatorTextInfoPane.getChildren().get(9)).getText());
        assertEquals("test6", ((Label) elevatorTextInfoPane.getChildren().get(11)).getText());
    }

    @Test
    void testPropertyChange() {
        ElevatorViewModel elevatorViewModelMock = mock(ElevatorViewModel.class);
        SimpleStringProperty testProperty1 = new SimpleStringProperty("test1");
        SimpleStringProperty testProperty2 = new SimpleStringProperty("test2");
        SimpleStringProperty testProperty3 = new SimpleStringProperty("test3");
        SimpleStringProperty testProperty4 = new SimpleStringProperty("test4");
        SimpleStringProperty testProperty5 = new SimpleStringProperty("test5");
        SimpleStringProperty testProperty6 = new SimpleStringProperty("test6");
        when(elevatorViewModelMock.capacityStringProperty()).thenReturn(testProperty1);
        when(elevatorViewModelMock.weightStringProperty()).thenReturn(testProperty2);
        when(elevatorViewModelMock.targetStringProperty()).thenReturn(testProperty3);
        when(elevatorViewModelMock.speedStringProperty()).thenReturn(testProperty4);
        when(elevatorViewModelMock.accelerationStringProperty()).thenReturn(testProperty5);
        when(elevatorViewModelMock.doorStatusProperty()).thenReturn(testProperty6);
        ElevatorTextInfoPane elevatorTextInfoPane = new ElevatorTextInfoPane(elevatorViewModelMock);

        testProperty1.set("test6");
        testProperty2.set("test7");
        testProperty3.set("test8");
        testProperty4.set("test9");
        testProperty5.set("test10");
        testProperty6.set("test11");

        assertEquals("test6", ((Label) elevatorTextInfoPane.getChildren().get(1)).getText());
        assertEquals("test7", ((Label) elevatorTextInfoPane.getChildren().get(3)).getText());
        assertEquals("test8", ((Label) elevatorTextInfoPane.getChildren().get(5)).getText());
        assertEquals("test9", ((Label) elevatorTextInfoPane.getChildren().get(7)).getText());
        assertEquals("test10", ((Label) elevatorTextInfoPane.getChildren().get(9)).getText());
        assertEquals("test11", ((Label) elevatorTextInfoPane.getChildren().get(11)).getText());
    }
}
