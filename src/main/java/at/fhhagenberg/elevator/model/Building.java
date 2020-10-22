package at.fhhagenberg.elevator.model;

import lombok.Getter;

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
    private List<Elevator> elevators;

    /**
     * Stores the floors within the building.
     *
     * @return List object containing all the floors of that building.
     */
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
}
