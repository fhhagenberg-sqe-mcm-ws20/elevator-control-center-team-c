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

    public Building(List<Elevator> elevators, List<Floor> floors, int floorHeight) {
        if (floorHeight < 0) {
            throw new IllegalArgumentException("Constructor: Floor height can't be negative");
        }
        this.elevators = elevators;
        this.floors = floors;
        this.floorHeight = floorHeight;
    }

    public Building() {
        this.elevators = new ArrayList<>();
        this.floors = new ArrayList<>();
        this.floorHeight = 0;
    }

    public Elevator getElevator(int index) {
        return elevators.get(index);
    }

    public Floor getFloor(int index) {
        return floors.get(index);
    }

    public int getNumberOfElevators() {
        return elevators.size();
    }

    public int getNumberOfFloors() {
        return floors.size();
    }

    public void setFloorHeight(int floorHeight) {
        if (floorHeight < 0) {
            throw new IllegalArgumentException("Setter: Floor height can't be negative");
        }
        this.floorHeight = floorHeight;
    }

    public void copyValues(Building building) {
        for (int i = 0; i < this.floors.size(); i++) {
            floors.get(i).copyValues(building.floors.get(i));
        }
        for (int i = 0; i < this.elevators.size(); i++) {
            elevators.get(i).copyValues(building.elevators.get(i), this.floors);
        }
        this.floorHeight = building.floorHeight;
    }

    public Boolean isEmpty() {
        if (elevators.size() == 0 && floors.size() == 0 && floorHeight == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static Floor getFloorFromFloors(Floor floor, List<Floor>floors){
        for(Floor tempFloor :floors){
            if(floor.equals(tempFloor)){
                return tempFloor;
            }
        }
        throw new IllegalArgumentException("Floor isn't in List");
    }
}
