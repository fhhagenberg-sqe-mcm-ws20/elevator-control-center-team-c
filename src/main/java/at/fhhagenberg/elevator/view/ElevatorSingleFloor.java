package at.fhhagenberg.elevator.view;


import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

@SuppressWarnings("java:S110")
public class ElevatorSingleFloor extends SingleFloorPane {
    public ElevatorSingleFloor(int floorNumber, ElevatorViewModel elevatorViewModel) {
        super();
        Label tempLabel = new Label("" + floorNumber);
        ObjectProperty<Background> floorPaneBackground = this.backgroundProperty();
        floorPaneBackground.bind(Bindings.createObjectBinding(() -> {
            BackgroundFill fill = new BackgroundFill(elevatorViewModel.getElevatorFloorColor(floorNumber), CornerRadii.EMPTY, Insets.EMPTY);
            return new Background(fill);
        }, elevatorViewModel.elevatorFloorColorProperty(floorNumber)));
        this.setOnMouseClicked(e -> {
            if (elevatorViewModel.isManualControl())
                System.out.println("Floor clicked! Elevator: " + elevatorViewModel.getElevatorNumber() + " FloorNumber: " + floorNumber);
        });

        this.getChildren().add(tempLabel);
    }
}
