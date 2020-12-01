import at.fhhagenberg.elevator.Simulator;
import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

public class SimulatorTest {
    @Test
    void testGenerateElevatorModels() {
        Building building = mock(Building.class);
        Simulator simulator=new Simulator(building);

        List<Elevator> elevators=simulator.generateElevatorModels(2,5);

        assertEquals(2,elevators.size());
        assertEquals(0,elevators.get(0).getNumber());
        assertEquals(1,elevators.get(0).getAcceleration());
        assertEquals(1,elevators.get(0).getFloor());
        assertEquals(1,elevators.get(0).getCapacity());
        assertEquals(1,elevators.get(0).getCommitedDirection());
        assertEquals(1,elevators.get(0).getDoorStatus());
        assertEquals(1,elevators.get(0).getPosition());
        assertEquals(1,elevators.get(0).getSpeed());
        assertEquals(1,elevators.get(0).getTarget());
    }

    @Test
    void testGenerateFloorModels() {
        Building building = mock(Building.class);
        Simulator simulator=new Simulator(building);

        List<Floor> floors=simulator.generateFloorModels(3);

        assertEquals(3,floors.size());
        assertEquals(0,floors.get(0).getNumber());
        assertEquals(1,floors.get(1).getNumber());
        assertEquals(2,floors.get(2).getNumber());
        assertFalse(floors.get(0).isButtonDown());
        assertFalse(floors.get(0).isButtonUp());
        assertFalse(floors.get(1).isButtonDown());
        assertFalse(floors.get(1).isButtonUp());
        assertFalse(floors.get(2).isButtonDown());
        assertFalse(floors.get(2).isButtonUp());

    }
}
