package at.fhhagenberg.elevator.model;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Represent the data model of a single elevator
 */
public class Elevator {
    public Elevator(int number, int commitedDirection, int acceleration, int doorStatus, int floor, int position, int speed, int weight, int capacity, int target, List<Integer> listOfServicedFloors, List<Boolean> floorButtonStatuses) {
        if (commitedDirection != 0 && commitedDirection != 1 && commitedDirection != 2) {
            throw new IllegalArgumentException("Constructor: Commited direction has a range from 0 to 2");
        }

        if (doorStatus != 1 && doorStatus != 2 && doorStatus != 3 && doorStatus != 4) {
            throw new IllegalArgumentException("Constructor: Door status can either be 1, 2, 3 or 4");
        }
        this.number = number;
        this.commitedDirection.set(commitedDirection);
        this.acceleration.set(acceleration);
        this.doorStatus.set(doorStatus);
        this.floor.set(floor);
        this.position.set(position);
        this.speed.set(speed);
        this.weight.set(weight);
        this.capacity.set(capacity);
        this.target.set(target);
        this.listOfServicedFloors.set(listOfServicedFloors);
        for (Boolean floorButtonStatus : floorButtonStatuses) {
            this.floorButtonStatuses.add(new SimpleBooleanProperty(floorButtonStatus));
        }
    }

    /**
     * Stores the elevator's number
     */
    private int number;

    /**
     * Direction the elevator is about to move.
     */
    private IntegerProperty commitedDirection = new SimpleIntegerProperty();

    /**
     * Elevators acceleration in ft/sec^2. Downwards is negative signed.
     */
    private IntegerProperty acceleration = new SimpleIntegerProperty();

    /**
     * Indicating if the door is closed or open.
     */
    private IntegerProperty doorStatus = new SimpleIntegerProperty();

    /**
     * Position of the elevator within the building.
     */
    private IntegerProperty floor = new SimpleIntegerProperty();

    /**
     * Height of the elevator from the ground floor.
     */
    private IntegerProperty position = new SimpleIntegerProperty();

    /**
     * Speed of the elevator in m/s.
     */
    private IntegerProperty speed = new SimpleIntegerProperty();

    /**
     * How much load the elevator is facing.
     */
    private IntegerProperty weight = new SimpleIntegerProperty();

    /**
     * Elevators max. capacity.
     */
    private IntegerProperty capacity = new SimpleIntegerProperty();

    /**
     * Next Floor the elevator is about to move to.
     */
    private IntegerProperty target = new SimpleIntegerProperty();

    /**
     * List of all floors the elevators serves
     */
    private ObjectProperty<List<Integer>> listOfServicedFloors = new SimpleObjectProperty<>(new ArrayList<>());

    /**
     * The elevator requesting buttons within the building.
     */
    private List<BooleanProperty> floorButtonStatuses = new ArrayList<>();

    /**
     * True if the elevator is controlled manually.
     */
    private BooleanProperty manualControl = new SimpleBooleanProperty(false);

    /**
     * Checks if the elevator service the floor
     *
     * @param floor that should be checked
     * @return boolean wether the elevator services the floor or not
     */
    public boolean servicesFloor(int floor) {
        return listOfServicedFloors.get().contains(floor);
    }

    /**
     * Setter for commitedDirection, which checks if the value is inside the given value range
     * Else exception is thrown
     *
     * @param commitedDirection
     */
    public void setCommitedDirection(int commitedDirection) {
        if (commitedDirection == 0 || commitedDirection == 1 || commitedDirection == 2) {
            this.commitedDirection.set(commitedDirection);
        } else {
            throw new IllegalArgumentException("Set: Commited direction has a range from 0 to 2");
        }
    }

    /**
     * Setter for doorStatus, which checks if the value is inside the given value range
     * Else exception is thrown
     *
     * @param doorStatus
     */
    public void setDoorStatus(int doorStatus) {
        if (doorStatus >= 1 && doorStatus <= 4) {
            this.doorStatus.set(doorStatus);
        } else {
            throw new IllegalArgumentException("Set: Door status can either be 1 or 2");
        }
    }

    /**
     * Returns the value of the specified floor button of the elevator
     * Throws an exception if the elevator does not have that floor button
     *
     * @param floor
     * @return
     */
    public Boolean getFloorButtonStatus(int floor) {
        return floorButtonStatuses.get(floor).get();
    }

    /**
     * Copies the values of a given elevator to this elevator
     *
     * @param elevator elevator which the values are extracted from
     * @param floors   all possible floors of the elevator can be associated with
     */
    public void copyValues(Elevator elevator, List<Floor> floors) {
        this.commitedDirection.set(elevator.commitedDirection.get());
        this.acceleration.set(elevator.acceleration.get());
        this.doorStatus.set(elevator.doorStatus.get());
        this.floor.set(elevator.floor.get());
        this.position.set(elevator.position.get());
        this.speed.set(elevator.speed.get());
        this.weight.set(elevator.weight.get());
        this.capacity.set(elevator.capacity.get());
        this.target.set(elevator.target.get());
        this.listOfServicedFloors = elevator.listOfServicedFloors;
        copyButtonStatus(elevator.floorButtonStatuses);
    }

    private void copyButtonStatus(List<BooleanProperty> floorButtons) {
        for (int i = 0; i < floorButtons.size(); i++) {
            if (i < this.floorButtonStatuses.size()) {
                this.floorButtonStatuses.get(i).set(floorButtons.get(i).get());
            } else {
                this.floorButtonStatuses.add(floorButtons.get(i));
            }
        }
    }

    public int getNumber() {
        return number;
    }

    public int getCommitedDirection() {
        return commitedDirection.get();
    }

    public int getAcceleration() {
        return acceleration.get();
    }

    public IntegerProperty accelerationProperty() {
        return acceleration;
    }

    public int getDoorStatus() {
        return doorStatus.get();
    }

    public String getDoorStatusString() {
        switch (doorStatus.get()) {
            case 1: return "OPEN";
            case 2: return "CLOSED";
            case 3: return "OPENING";
            case 4: return "CLOSING";
            default: return "NaN";
        }
    }

    public int getFloor() {
        return floor.get();
    }

    public int getPosition() {
        return position.get();
    }

    public IntegerProperty positionProperty() {
        return position;
    }

    public int getSpeed() {
        return speed.get();
    }

    public IntegerProperty speedProperty() {
        return speed;
    }

    public int getWeight() {
        return weight.get();
    }

    public IntegerProperty weightProperty() {
        return weight;
    }

    public int getCapacity() {
        return capacity.get();
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }

    public int getTarget() {
        return target.get();
    }

    public IntegerProperty targetProperty() {
        return target;
    }

    public IntegerProperty doorStatusProperty() { return doorStatus; }

    public List<BooleanProperty> getFloorButtonStatuses() {
        return floorButtonStatuses;
    }

    public List<Integer> getListOfServicedFloors() {
        return listOfServicedFloors.get();
    }

    public ObjectProperty<List<Integer>> listOfServicedFloorsProperty() {
        return listOfServicedFloors;
    }

    public Boolean isManualControl() {
        return manualControl.get();
    }

    public void setFloor(int floor) {
        this.floor.set(floor);
    }

    public void setSpeed(int speed) {
        this.speed.set(speed);
    }

    public void setTarget(int target) {
        this.target.set(target);
    }

    public void setListOfServicedFloors(List<Integer> listOfServicedFloors) {
        this.listOfServicedFloors.set(listOfServicedFloors);
    }

    public void setManualControl(Boolean isManual) {
        this.manualControl.set(isManual);
    }
}
