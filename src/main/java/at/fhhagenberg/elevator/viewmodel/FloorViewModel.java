package at.fhhagenberg.elevator.viewmodel;

import at.fhhagenberg.elevator.model.Floor;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;

/**
 * Floor view model which abstracts floor model from the floor view
 * Every floor has such a view model
 * It translates the raw model values to view values
 */
public class FloorViewModel {
    @Getter
    private final Floor floor;
    private final ObjectProperty<String> buttonUpImageProperty = new SimpleObjectProperty<>("file:up_filled.png");
    private final ObjectProperty<String> buttonDownImageProperty = new SimpleObjectProperty<>("file:down_filled.png");

    public ObjectProperty<String> buttonUpImageProperty() {
        return buttonUpImageProperty;
    }

    public ObjectProperty<String> buttonDownImageProperty() {
        return buttonDownImageProperty;
    }

    public FloorViewModel(Floor floor) {
        this.floor = floor;
        //Bind button state properties to image property
        buttonUpImageProperty.bind(Bindings.when(
                floor.buttonUpProperty())
                .then("file:up_filled.png")
                .otherwise("file:up.png"));

        buttonDownImageProperty.bind(Bindings.when(
                floor.buttonDownProperty())
                .then("file:down_filled.png")
                .otherwise("file:down.png"));
    }

    public String getButtonUpImage() {
        return buttonUpImageProperty.get();
    }

    public String getButtonDownImage() {
        return buttonDownImageProperty.get();
    }

    public int getFloorNumber() {
        return floor.getNumber();
    }
}
