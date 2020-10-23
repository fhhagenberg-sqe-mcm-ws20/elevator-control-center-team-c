package at.fhhagenberg.elevator.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Lombok constructor expecting all attributes of floor to be instantiated.
 *
 * @return Floor instance
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
/**
 * Represent the data model of a single floor
 */
public class Floor {
    /**
     * The integer number associated with that floor instance.
     *
     * @return Number of that specific floor
     */
    @Getter
    private int number;

    /**
     * Toggle indicating the upward button's activeness.
     *
     * @return Boolean value indicating if the button is currently active.
     * @param buttonUp as boolean value indicating if the button is active.
     */
    @Getter
    @Setter
    private boolean buttonUp;

    /**
     * Toggle indicating the downward button's activeness.
     *
     * @return Boolean value indicating if the button is currently active.
     * @param buttonDown as boolean value indicating if the button is active.
     */
    @Getter
    @Setter
    private boolean buttonDown;

    public void copyValues(Floor floor) {
        this.buttonUp = floor.buttonUp;
        this.buttonDown = floor.buttonDown;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Floor floor = (Floor) o;
        return number == floor.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
