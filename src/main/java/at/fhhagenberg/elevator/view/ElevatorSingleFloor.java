package at.fhhagenberg.elevator.view;


import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

/**
 * This is a single elevator floor for the ElevatorAnimationBackgroundPane
 * The color of the floor changes if it is the target
 * Is clickable
 */
@SuppressWarnings("java:S110")
public class ElevatorSingleFloor extends SingleFloorPane {
    public ElevatorSingleFloor(int floorNumber, ElevatorViewModel elevatorViewModel) {
        super();
        Label tempLabel = new Label("" + (floorNumber+1));
        ObjectProperty<Background> floorPaneBackground = this.backgroundProperty();
        this.getStyleClass().add("elevator-single-floor");
        this.setId("floor-" + floorNumber);
        //Bind backgroundColor to viewmodel
        floorPaneBackground.bind(Bindings.createObjectBinding(() -> {
            BackgroundFill fill = new BackgroundFill(elevatorViewModel.getElevatorFloorColor(floorNumber), CornerRadii.EMPTY, Insets.EMPTY);
            return new Background(fill);
        }, elevatorViewModel.elevatorFloorColorProperty(floorNumber)));
        //Set target in viewmodel
        this.setOnMouseClicked(e -> {
            if (Boolean.TRUE.equals(elevatorViewModel.isManualControl()))
                elevatorViewModel.setTargetString(floorNumber);
        });

        this.getChildren().add(tempLabel);
    }
}
