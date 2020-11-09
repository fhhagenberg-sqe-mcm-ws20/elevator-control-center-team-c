package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.geometry.Insets;
import javafx.scene.control.Label;


public class ElevatorModeButtonPane extends EmptyElevatorModeButtonPane {
    public ElevatorModeButtonPane(ElevatorViewModel elevatorViewModel) {
        super();
        Label switchButtonLabel = new Label("Automatic");
        switchButtonLabel.setPadding(new Insets(0,10,0,0));
        SwitchButton switchButton=new SwitchButton();
        switchButton.stateProperty().addListener((observable, oldValue, newValue) -> {
            String modeText=newValue ? "automatic" : "manual";
            System.out.println("Elevator "+elevatorViewModel.getElevatorNumber()+" switched to "+modeText+" mode");
            elevatorViewModel.setManualControl(newValue);
        });

        this.getChildren().addAll(switchButtonLabel,switchButton);
    }
}
