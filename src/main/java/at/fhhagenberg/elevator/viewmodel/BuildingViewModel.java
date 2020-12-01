package at.fhhagenberg.elevator.viewmodel;

import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class BuildingViewModel implements INotifyModelSizeChangedListener {

    @Getter
    private final List<FloorViewModel> floorViewModels = new ArrayList<>();
    @Getter
    private final List<ElevatorViewModel> elevatorViewModels = new ArrayList<>();

    private final Building building;

    private final List<INotifyModelSizeChangedListener> changeListeners= new ArrayList<>();

    public BuildingViewModel(Building building) {
        this.building = building;
        building.addChangeListener(this);
        updateFromModel();
    }

    @Override
    public void modelChanged() {
        updateFromModel();
    }

    private void updateFromModel() {
        if (Boolean.FALSE.equals(building.isEmpty())) {
            initializeViewModels();
        }
    }

    private void notifyChangeListeners(){
        changeListeners.forEach(INotifyModelSizeChangedListener::modelChanged);
    }

    public void addChangeListener(INotifyModelSizeChangedListener changeListener) {
        changeListeners.add(changeListener);
    }

    private void initializeViewModels() {
        floorViewModels.clear();
        elevatorViewModels.clear();
        for (Floor floor : building.getFloors()) {
            floorViewModels.add(new FloorViewModel(floor));
        }
        for (Elevator elevator : building.getElevators()) {
            elevatorViewModels.add(new ElevatorViewModel(elevator, building.getNumberOfFloors(),building.getFloorHeight()));
        }
        notifyChangeListeners();
    }
}
