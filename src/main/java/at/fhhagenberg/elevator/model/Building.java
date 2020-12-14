package at.fhhagenberg.elevator.model;

import at.fhhagenberg.elevator.viewmodel.INotifyModelSizeChangedListener;
import lombok.Getter;

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
    @Getter
    private List<Elevator> elevators;

    /**
     * Stores the floors within the building.
     *
     * @return List object containing all the floors of that building.
     */
    @Getter
    private List<Floor> floors;

    @Getter
    private int floorHeight;

    private final List<INotifyModelSizeChangedListener> changeListeners = new ArrayList<>();

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
     * Adds new floors and elevators if there are new ones in the parameter building object
     *
     * @param building
     */
    public void copyValues(Building building) {
        boolean reload = false;
        for (int i = 0; i < building.floors.size(); i++) {
            if (i < this.floors.size()) {
                this.floors.get(i).copyValues(building.floors.get(i));
            } else {
                this.floors.add(building.getFloor(i));
                reload = true;
            }
        }

        int oldSize = this.floors.size();
        //remove floors if new building has less floors
        for (int i=building.floors.size(); i < oldSize; i++){
            this.floors.remove(i);
            reload = true;
        }

        for (int i = 0; i < building.elevators.size(); i++) {
            if (i < this.elevators.size()) {
                this.elevators.get(i).copyValues(building.elevators.get(i), this.floors);
            } else {
                this.elevators.add(building.getElevator(i));
                reload = true;
            }
        }

        oldSize = this.elevators.size();
        //remove elevators if new building has less elevators
        for (int i=building.elevators.size(); i < oldSize; i++){
            this.elevators.remove(i);
            reload = true;
        }

        this.floorHeight = building.floorHeight;
        if (reload) {
            notifyChangeListeners();
        }
    }

    /**
     * Checks if the building is empty and was just created with the empty constructor
     *
     * @return true if the list or empty and the floorheight is 0, else returns true
     */
    public Boolean isEmpty() {
        return elevators.isEmpty() && floors.isEmpty() && floorHeight == 0;
    }

    private void notifyChangeListeners() {
        changeListeners.forEach(INotifyModelSizeChangedListener::modelChanged);
    }

    public void addChangeListener(INotifyModelSizeChangedListener changeListener) {
        changeListeners.add(changeListener);
    }
}
