package at.fhhagenberg.elevator.viewmodel;

import at.fhhagenberg.elevator.model.Floor;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;

public class FloorViewModel {
    @Getter
    private final Floor floor;
    private final ObjectProperty<String> buttonUpColorProperty = new SimpleObjectProperty<>("file:up_filled.png");
    private final ObjectProperty<String> buttonDownColorProperty = new SimpleObjectProperty<>("file:down_filled.png");

    public ObjectProperty<String> buttonUpColorProperty() {
        return buttonUpColorProperty;
    }

    public ObjectProperty<String> buttonDownColorProperty() {
        return buttonDownColorProperty;
    }

    public FloorViewModel(Floor floor) {
        this.floor = floor;
        buttonUpColorProperty.bind(Bindings.when(
                floor.buttonUpProperty())
                .then("file:up_filled.png")
                .otherwise("file:up.png"));

        buttonDownColorProperty.bind(Bindings.when(
                floor.buttonDownProperty())
                .then("file:down_filled.png")
                .otherwise("file:down.png"));
    }

    public String getButtonUpColor() {
        return buttonUpColorProperty.get();
    }

    public String getButtonDownColor() {
        return buttonDownColorProperty.get();
    }

    public int getFloorNumber() {
        return floor.getNumber();
    }
}
