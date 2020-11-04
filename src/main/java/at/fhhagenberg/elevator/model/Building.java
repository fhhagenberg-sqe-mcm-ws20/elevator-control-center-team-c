package at.fhhagenberg.elevator.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Building class represents one Building where the elevator system is deployed
 * It consists of two lists for the main objects, elevators and floors
 */
public class Building {
    /**
     * Stores the elevators within the building.
     *
     * @return List object containing all the elevators of that building.
     */
    @Setter
    @Getter
    private List<Elevator> elevators;

    /**
     * Stores the floors within the building.
     *
     * @return List object containing all the floors of that building.
     */
    @Setter
    @Getter
    private List<Floor> floors;

    @Getter
    private int floorHeight;

    /**
     * Constructor for the object
     *
     * @param elevators   list of all elevators of the building
     * @param floors      list of all floors of the building
     * @param floorHeight unifrom floorheight of the whole building
     */
    public Building(List<Elevator> elevators, List<Floor> floors, int floorHeight) {
        if (floorHeight < 0) {
            throw new IllegalArgumentException("Constructor: Floor height can't be negative");
        }
        this.elevators = elevators;
        this.floors = floors;
        this.floorHeight = floorHeight;
    }

    /**
     * Empty constructor
     * The isEmpty() function returns true with this setup
     */
    public Building() {
        this.elevators = new ArrayList<>();
        this.floors = new ArrayList<>();
        this.floorHeight = 0;
    }

    /**
     * Returns the elevator of the given index
     *
     * @param index
     * @return
     */
    public Elevator getElevator(int index) {
        return elevators.get(index);
    }

    /**
     * Returns the floor of the given index
     *
     * @param index
     * @return
     */
    public Floor getFloor(int index) {
        return floors.get(index);
    }

    /**
     * Returns number of elevators in the elevator list
     *
     * @return
     */
    public int getNumberOfElevators() {
        return elevators.size();
    }

    /**
     * Returns number of florrs in the floors list
     *
     * @return
     */
    public int getNumberOfFloors() {
        return floors.size();
    }

    /**
     * Sets the floorheight and checks if it is bigger than 0
     * Else throws an exception
     *
     * @param floorHeight
     */
    public void setFloorHeight(int floorHeight) {
        if (floorHeight < 0) {
            throw new IllegalArgumentException("Setter: Floor height can't be negative");
        }
        this.floorHeight = floorHeight;
    }

    /**
     * Copies the values of a given building to this building using the copy function of all elevators and floors
     *
     * @param building
     */
    public void copyValues(Building building) {
        for (int i = 0; i < this.floors.size(); i++) {
            floors.get(i).copyValues(building.floors.get(i));
        }
        for (int i = 0; i < this.elevators.size(); i++) {
            elevators.get(i).copyValues(building.elevators.get(i), this.floors);
        }
        this.floorHeight = building.floorHeight;
    }

    /**
     * Checks if the building is empty and was just created with the empty constructor
     *
     * @return true if the list or empty and the floorheight is 0, else returns true
     */
    public Boolean isEmpty() {
        if (elevators.size() == 0 && floors.size() == 0 && floorHeight == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Static function to retrieve the given floor with the same floornumber from a list of floors
     * If there isn't such a floor, an exception is thrown
     *
     * @param floor  given floor
     * @param floors floor list
     * @return floor with the same floornumber as the given floor from the floors list
     */
    public static Floor getFloorFromFloors(Floor floor, List<Floor> floors) {
        for (Floor tempFloor : floors) {
            if (floor.equals(tempFloor)) {
                return tempFloor;
            }
        }
        throw new IllegalArgumentException("Floor isn't in List");
    }
}
