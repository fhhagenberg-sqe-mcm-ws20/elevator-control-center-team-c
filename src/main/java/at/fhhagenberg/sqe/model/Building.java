package at.fhhagenberg.sqe.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Lombok constructor expecting all attributes of the building to be instantiated.
 *
 * @return Building instance
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
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

    public Elevator getElevator(int index) {
        return elevators.get(index);
    }

    public Floor getFloor(int index) {
        return floors.get(index);
    }
}
