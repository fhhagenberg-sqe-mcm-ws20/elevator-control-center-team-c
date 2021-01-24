package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

@SuppressWarnings("java:S110")
public class ElevatorTextInfoPane extends GridPane {
    public ElevatorTextInfoPane(ElevatorViewModel elevatorViewModel) {
        Label capacityLabel = new Label("Capacity:");
        Label capacityValue = new Label("NaN");
        capacityValue.textProperty().bind(elevatorViewModel.capacityStringProperty());

        Label weightLabel = new Label("Weight:");
        Label weightValue = new Label("NaN");
        this.getStyleClass().add("elevator-text-info-pane");
        this.setId("elevator-" + elevatorViewModel.getElevatorNumber());
        weightValue.setId("weightLabel");
        weightValue.textProperty().bind(elevatorViewModel.weightStringProperty());

        Label targetLabel = new Label("Target:");
        Label targetValue = new Label("NaN");
        targetValue.textProperty().bind(elevatorViewModel.targetStringProperty());

        Label doorStatusLabel = new Label("Door Status:");
        Label doorStatusValue = new Label("NaN");
        doorStatusValue.textProperty().bind(elevatorViewModel.doorStatusProperty());

        Label speedLabel = new Label("Speed:");
        Label speedValue = new Label("NaN");
        speedValue.textProperty().bind(elevatorViewModel.speedStringProperty());

        Label accelerationLabel = new Label("Acceleration:");
        Label accelerationValue = new Label("NaN");
        accelerationValue.textProperty().bind(elevatorViewModel.accelerationStringProperty());

        this.add(capacityLabel, 1, 0);
        this.add(capacityValue, 2, 0);

        this.add(weightLabel, 1, 1);
        this.add(weightValue, 2, 1);

        this.add(targetLabel, 1, 2);
        this.add(targetValue, 2, 2);

        this.add(speedLabel, 1, 3);
        this.add(speedValue, 2, 3);

        this.add(accelerationLabel, 1, 4);
        this.add(accelerationValue, 2, 4);

        this.add(doorStatusLabel, 1, 5);
        this.add(doorStatusValue, 2, 5);
    }
}
