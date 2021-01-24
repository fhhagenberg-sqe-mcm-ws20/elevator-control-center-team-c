package at.fhhagenberg.elevator.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;

import java.util.Objects;

/**
 * Lombok constructor expecting all attributes of floor to be instantiated.
 *
 * @return Floor instance
 */

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
     */
    private BooleanProperty buttonUp = new SimpleBooleanProperty();

    /**
     * Toggle indicating the downward button's activeness.
     */
    private BooleanProperty buttonDown = new SimpleBooleanProperty();

    public Floor(int number, boolean buttonUp, boolean buttonDown) {
        this.number = number;
        this.buttonUp.set(buttonUp);
        this.buttonDown.set(buttonDown);
    }

    /**
     * Copies the value of a given floor to this floor
     *
     * @param floor floor which hold the values that should be copied
     */
    public void copyValues(Floor floor) {
        this.number = floor.number;
        this.buttonUp.set(floor.buttonUp.get());
        this.buttonDown.set(floor.buttonDown.get());
    }

    /**
     * Overriding the equals method
     * A Floor is the same floor if they have the same floor number
     *
     * @param o object that should be checked with this object
     * @return if it is the dame floor
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Floor floor = (Floor) o;
        return number == floor.number;
    }

    /**
     * Overriding hashcode for uniformity with the equals function
     * The hashcode of the object is the hashed floor number
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public boolean isButtonDown() {
        return this.buttonDown.get();
    }

    public boolean isButtonUp() {
        return this.buttonUp.get();
    }

    public BooleanProperty buttonUpProperty() {
        return buttonUp;
    }

    public BooleanProperty buttonDownProperty() {
        return buttonDown;
    }
}
