package at.fhhagenberg.viewmodel;

import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import at.fhhagenberg.elevator.viewmodel.BuildingViewModel;
import at.fhhagenberg.elevator.viewmodel.INotifyModelSizeChangedListener;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BuildingViewModelTest {

    @Test
    void testUpdateFromModelIfBuildingIsEmpty(){
        Building buildingMock = mock(Building.class);
        when(buildingMock.isEmpty()).thenReturn(true);
        BuildingViewModel buildingViewModel=new BuildingViewModel(buildingMock);
        INotifyModelSizeChangedListener changeListenerMock=mock(INotifyModelSizeChangedListener.class);
        buildingViewModel.addChangeListener(changeListenerMock);
        buildingViewModel.modelChanged();
        verify(changeListenerMock, times(0)).modelChanged();
    }

    @Test
    void testAddChangeListenerFromModel(){
        Building buildingMock = mock(Building.class);
        BuildingViewModel buildingViewModel=new BuildingViewModel(buildingMock);
        INotifyModelSizeChangedListener changeListenerMock=mock(INotifyModelSizeChangedListener.class);
        buildingViewModel.addChangeListener(changeListenerMock);
        buildingViewModel.modelChanged();
        verify(changeListenerMock, times(1)).modelChanged();
    }

    @Test
    void testModelChangedIfElevatorViewModelsAreCreated() {
        Building building = mock(Building.class);
        BuildingViewModel buildingViewModel = new BuildingViewModel(building);
        when(building.getElevators()).thenReturn(List.of(new Elevator(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, List.of(1), List.of(false))));
        buildingViewModel.modelChanged();
        assertEquals(0, buildingViewModel.getFloorViewModels().size());
        assertEquals(1, buildingViewModel.getElevatorViewModels().size());
    }

    @Test
    void testModelChangedIfFloorViewModelsAreCreated() {
        Building building = mock(Building.class);
        BuildingViewModel buildingViewModel = new BuildingViewModel(building);
        when(building.getFloors()).thenReturn(List.of(new Floor(1, false, false)));
        buildingViewModel.modelChanged();
        assertEquals(1, buildingViewModel.getFloorViewModels().size());
        assertEquals(0, buildingViewModel.getElevatorViewModels().size());
    }

    @Test
    void testModelChangedIfNoViewModelsAreCreated() {
        Building building = mock(Building.class);
        BuildingViewModel buildingViewModel = new BuildingViewModel(building);
        buildingViewModel.modelChanged();
        assertEquals(0, buildingViewModel.getFloorViewModels().size());
        assertEquals(0, buildingViewModel.getElevatorViewModels().size());
    }

    @Test
    void testConstructorIfElevatorViewModelsAreCreated() {
        Building building = mock(Building.class);
        when(building.getElevators()).thenReturn(List.of(new Elevator(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, List.of(1), List.of(false))));
        BuildingViewModel buildingViewModel = new BuildingViewModel(building);
        assertEquals(0, buildingViewModel.getFloorViewModels().size());
        assertEquals(1, buildingViewModel.getElevatorViewModels().size());
    }

    @Test
    void testConstructorIfFloorViewModelsAreCreated() {
        Building building = mock(Building.class);
        when(building.getFloors()).thenReturn(List.of(new Floor(1, false, false)));
        BuildingViewModel buildingViewModel = new BuildingViewModel(building);
        assertEquals(1, buildingViewModel.getFloorViewModels().size());
        assertEquals(0, buildingViewModel.getElevatorViewModels().size());
    }

    @Test
    void testConstructorIfNoViewModelsAreCreated() {
        Building building = mock(Building.class);
        BuildingViewModel buildingViewModel = new BuildingViewModel(building);
        assertEquals(0, buildingViewModel.getFloorViewModels().size());
        assertEquals(0, buildingViewModel.getElevatorViewModels().size());
    }
}
