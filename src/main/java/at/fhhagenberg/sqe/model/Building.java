package at.fhhagenberg.sqe.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Lombok constructor expecting all attributes of the building to be instantiated.
 * @return Building instance
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)

public class Building {
    /**
     * Stores the elevators within the building.
     * @return List object containing all the elevators of that building.
     */
    @Getter
    private List<Elevator> elevators;

    /**
     * Stores the floors within the building.
     * @return List object containing all the floors of that building.
     */
    @Getter
    private List<Floor> floors;
}
