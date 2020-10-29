package at.fhhagenberg.elevator.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Represent the data model of a single elevator
 */
public class Elevator {
    public Elevator(int number, int commitedDirection, int acceleration, int doorStatus, Floor floor, int position, int speed, int weight, int capacity, Floor target, HashMap<Floor, Boolean> floorButtons) {
        if (commitedDirection != 0 && commitedDirection != 1 && commitedDirection != 2) {
            throw new IllegalArgumentException("Constructor: Commited direction has a range from 0 to 2");
        }

        if (doorStatus != 1 && doorStatus != 2) {
            throw new IllegalArgumentException("Constructor: Door status can either be 1 or 2");
        }
        this.number = number;
        this.commitedDirection = commitedDirection;
        this.acceleration = acceleration;
        this.doorStatus = doorStatus;
        this.floor = floor;
        this.position = position;
        this.speed = speed;
        this.weight = weight;
        this.capacity = capacity;
        this.target = target;
        this.floorButtons = floorButtons;
    }

    /**
     * Stores the elevator's number
     *
     * @return Number of that elevator instance.
     */
    @Getter
    private int number;

    /**
     * Direction the elevator is about to move.
     *
     * @return 0 if no direction, 1 if direction is upwards, 2 if direction is downwards.
     * @oaram Integer value representing the corresponding commitedDirection.
     */
    @Getter
    private int commitedDirection;

    /**
     * Elevators acceleration in m/sec^2. Downwards is negative signed.
     *
     * @return Current acceleration value in m/sec^2.
     * @oaram Current acceleration in m/sec^2
     */
    @Getter
    @Setter
    private int acceleration;

    /**
     * Indicating if the door is closed or open.
     *
     * @return 0 if door is closed, 1 if door is open.
     * @oaram Status, 0 or 1 depending of the doors current status.
     */
    @Getter
    private int doorStatus;

    /**
     * Position of the elevator within the building.
     *
     * @return Floor the elevator is currently in.
     * @oaram Floor object that represents the elevators position.
     */
    @Getter
    @Setter
    private Floor floor;

    /**
     * Height of the elevator from the ground floor.
     *
     * @return Provides the current location of the specified elevator in feet from the bottom of the building.
     * @oaram Current location in feet.
     */
    @Getter
    @Setter
    private int position;

    /**
     * Speed of the elevator in m/s.
     *
     * @return Current speed.
     * @oaram Current speed.
     */
    @Getter
    @Setter
    private int speed;

    /**
     * How much load the elevator is facing.
     *
     * @return Load in kg.
     * @oaram Load in kg.
     */
    @Getter
    @Setter
    private int weight;

    /**
     * Elevators max. capacity in kg.
     *
     * @return the max. capacity.
     */
    @Getter
    private int capacity;

    /**
     * Next Floor the elevator is about to move to.
     *
     * @return The destination floor.
     * @oaram The destination floor.
     */
    @Getter
    @Setter
    private Floor target;

    /**
     * The elevator requesting buttons within the building.
     */
    private HashMap<Floor, Boolean> floorButtons;

    /**
     * Status on the services floors of the building by that elevator.
     *
     * @return List of floors which are serviced within the building.
     */
    public List<Floor> getServicedFloors() {
        return new ArrayList<Floor>(floorButtons.keySet());
    }

    /**
     * Checks if the elevator service the floor
     *
     * @param floor that should be checked
     * @return boolean wether the elevator services the floor or not
     */
    public boolean servicesFloor(Floor floor) {
        return floorButtons.containsKey(floor);
    }

    /**
     * Setter for commitedDirection, which checks if the value is inside the given value range
     * Else exception is thrown
     *
     * @param commitedDirection
     */
    public void setCommitedDirection(int commitedDirection) {
        if (commitedDirection == 0 || commitedDirection == 1 || commitedDirection == 2) {
            this.commitedDirection = commitedDirection;
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
        if (doorStatus == 1 || doorStatus == 2) {
            this.doorStatus = doorStatus;
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
    public Boolean getFloorButtonStatus(Floor floor) {
        if (floorButtons.containsKey(floor)) {
            return floorButtons.get(floor);
        } else {
            throw new IllegalArgumentException("Floor Button is not present in this elevator");
        }
    }

    /**
     * Copies the values of a given elevator to this elevator
     *
     * @param elevator elevator which the values are extracted from
     * @param floors   all possible floors of the elevator can be associated with
     */
    public void copyValues(Elevator elevator, List<Floor> floors) {
        this.commitedDirection = elevator.commitedDirection;
        this.acceleration = elevator.acceleration;
        this.doorStatus = elevator.doorStatus;
        this.floor = Building.getFloorFromFloors(elevator.floor, floors);
        this.position = elevator.position;
        this.speed = elevator.speed;
        this.weight = elevator.weight;
        this.capacity = elevator.capacity;
        this.target = Building.getFloorFromFloors(elevator.target, floors);
        this.floorButtons = elevator.floorButtons;
    }
}
