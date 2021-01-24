package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

@SuppressWarnings("java:S110")
public class ElevatorModeButtonPane extends EmptyElevatorModeButtonPane {
    public ElevatorModeButtonPane(ElevatorViewModel elevatorViewModel) {
        super();
        this.getStyleClass().add("elevator-mode-button-pane");
        Label switchButtonLabel = new Label("Manual");
        switchButtonLabel.setPadding(new Insets(0,10,0,0));
        SwitchButton switchButton=new SwitchButton();
        switchButton.stateProperty().addListener((observable, oldValue, newValue) -> elevatorViewModel.setManualControl(newValue));

        this.getChildren().addAll(switchButtonLabel,switchButton);
    }
}
