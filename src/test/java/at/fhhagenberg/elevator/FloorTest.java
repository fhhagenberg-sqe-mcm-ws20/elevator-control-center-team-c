package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.model.Floor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {
    @Test
    void testEqualsIfFloorNumbersAreTheSame() {
        Floor floor1 = new Floor(1, false, false);
        Floor floor2 = new Floor(1, false, false);

        assertEquals(floor2, floor1);
    }

    @Test
    void testEqualsIfFloorNumbersAreNotTheSame() {
        Floor floor1 = new Floor(1, false, false);
        Floor floor2 = new Floor(2, false, false);

        assertNotEquals(floor1, floor2);
    }

    @Test
    void testEqualsIfNotTheSameObjectsType() {
        Floor floor1 = new Floor(1, false, false);
        Object object = new Object();
        assertNotEquals(floor1, object);
    }

    @Test
    void testEqualsIfItIsTheSameObject() {
        Floor floor1 = new Floor(1, false, false);
        assertEquals(floor1, floor1);
    }

    @Test
    void testEqualsIfItIsTheObjectIsNull() {
        Floor floor1 = new Floor(1, false, false);
        assertNotEquals(null, floor1);
    }

    @Test
    void testHashCodeIfFloorNumbersAreTheSame() {
        Floor floor1 = new Floor(1, false, false);
        Floor floor2 = new Floor(1, false, false);

        assertEquals(floor1.hashCode(), floor2.hashCode());
    }

    @Test
    void testHashCodeIfFloorNumbersAreNotTheSame() {
        Floor floor1 = new Floor(1, false, false);
        Floor floor2 = new Floor(2, false, false);

        assertNotEquals(floor1.hashCode(), floor2.hashCode());
    }

    @Test
    void testCopyValues() {
        Floor floor1 = new Floor(1, false, false);
        Floor floor2 = new Floor(2, true, true);

        floor1.copyValues(floor2);

        assertNotSame(floor1, floor2);
        assertTrue(floor1.isButtonDown());
        assertTrue(floor1.isButtonUp());
    }
}
