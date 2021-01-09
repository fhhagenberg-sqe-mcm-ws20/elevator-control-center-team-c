package at.fhhagenberg.elevator.viewmodel;

import at.fhhagenberg.elevator.RMIElevatorAdapter;
import at.fhhagenberg.elevator.model.Elevator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ElevatorViewModel {

    private final Elevator elevator;
    private final RMIElevatorAdapter simulator;


    private final StringProperty capacityString = new SimpleStringProperty("NaN");
    private final StringProperty weightString = new SimpleStringProperty("NaN");
    private final StringProperty targetString = new SimpleStringProperty("NaN");
    private final StringProperty speedString = new SimpleStringProperty("NaN");
    private final StringProperty accelerationString = new SimpleStringProperty("NaN");
    private final List<ObjectProperty<Color>> elevatorFloorColors = new ArrayList<>();
    private final List<ObjectProperty<Color>> elevatorFloorButtonColors = new ArrayList<>();
    private final DoubleProperty position = new SimpleDoubleProperty(0);
    private final int buildingHeight;
    private final int floorHeight;

    public ElevatorViewModel(RMIElevatorAdapter simulator, Elevator elevator, int numberOfFloors, int floorHeight) {
        this.buildingHeight = floorHeight * numberOfFloors;
        this.floorHeight = floorHeight;
        capacityString.bind(Bindings.createStringBinding(() -> (elevator.getCapacity() + " kg"), elevator.capacityProperty()));
        weightString.bind(Bindings.createStringBinding(() -> (elevator.getWeight() + " kg"), elevator.weightProperty()));
        targetString.bind(Bindings.createStringBinding(() -> (elevator.getTarget() + ""), elevator.targetProperty()));
        speedString.bind(Bindings.createStringBinding(() -> (elevator.getSpeed() + " m/s"), elevator.speedProperty()));
        accelerationString.bind(Bindings.createStringBinding(() -> (elevator.getAcceleration() + " m/s2"), elevator.accelerationProperty()));

        for (BooleanProperty floorButtonStatus : elevator.getFloorButtonStatuses()) {
            ObjectProperty<Color> floorButtonColorProperty = new SimpleObjectProperty<>(Color.WHITE);
            floorButtonColorProperty.bind(Bindings.when(
                    floorButtonStatus)
                    .then(Color.GREEN)
                    .otherwise(Color.WHITE));
            elevatorFloorButtonColors.add(floorButtonColorProperty);
        }

        this.elevator = elevator;
        this.simulator = simulator;

        for (int i = 0; i < numberOfFloors; i++) {
            elevatorFloorColors.add(new SimpleObjectProperty<>(Color.WHITE));
        }
        position.set(1-((double)elevator.getPosition() / buildingHeight));
        elevator.positionProperty().addListener((observable, oldValue, newValue) -> position.set(1-(newValue.doubleValue()) / buildingHeight));

        elevator.targetProperty().addListener((observable, oldValue, newValue) -> updateFloors());

        elevator.listOfServicedFloorsProperty().addListener((observable, oldValue, newValue) -> updateFloors());

        updateFloors();
    }

    private void updateFloors() {
        for (int i = 0; i < elevatorFloorColors.size(); i++) {
            if (i == elevator.getTarget()) {
                elevatorFloorColors.get(i).set(Color.GREEN);
            } else if (elevator.servicesFloor(i)) {
                elevatorFloorColors.get(i).set(Color.WHITE);
            } else {
                elevatorFloorColors.get(i).set(Color.GRAY);
            }
        }
    }

    public int getElevatorNumber() {
        return elevator.getNumber();
    }

    public String getCapacityString() {
        return capacityString.get();
    }

    public StringProperty capacityStringProperty() {
        return capacityString;
    }

    public String getWeightString() {
        return weightString.get();
    }

    public StringProperty weightStringProperty() {
        return weightString;
    }

    public String getTargetString() {
        return targetString.get();
    }

    public StringProperty targetStringProperty() {
        return targetString;
    }

    public String getSpeedString() {
        return speedString.get();
    }

    public StringProperty speedStringProperty() {
        return speedString;
    }

    public String getAccelerationString() {
        return accelerationString.get();
    }

    public StringProperty accelerationStringProperty() {
        return accelerationString;
    }

    public Color getElevatorFloorColor(int index) {
        return elevatorFloorColors.get(index).get();
    }

    public ObjectProperty<Color> elevatorFloorColorProperty(int index) {
        return elevatorFloorColors.get(index);
    }

    public Color getElevatorFloorButtonColor(int index) {
        return elevatorFloorButtonColors.get(index).get();
    }

    public ObjectProperty<Color> elevatorFloorButtonColorProperty(int index) {
        return elevatorFloorButtonColors.get(index);
    }

    public double getPosition() {
        return position.get();
    }

    public DoubleProperty positionProperty() {
        return position;
    }

    public void setManualControl(Boolean isManual) {
        elevator.setManualControl(isManual);
    }

    public Boolean isManualControl() {
        return elevator.isManualControl();
    }

    public void setTargetString(int target) {
        simulator.setTarget(getElevatorNumber(), target);
    }

}


