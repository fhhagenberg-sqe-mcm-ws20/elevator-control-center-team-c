package at.fhhagenberg.elevator.viewmodel;

import at.fhhagenberg.elevator.RMIElevatorAdapter;
import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The building view model abstracts the building from the view
 * It holds the building object and has direct access to the adapter
 * Can notify the entire view to reload
 */
public class BuildingViewModel implements INotifyModelSizeChangedListener {

    @Getter
    private final List<FloorViewModel> floorViewModels = new ArrayList<>();
    @Getter
    private final List<ElevatorViewModel> elevatorViewModels = new ArrayList<>();

    private final Building building;
    private final RMIElevatorAdapter adapter;

    private final List<INotifyModelSizeChangedListener> changeListeners= new ArrayList<>();

    public BuildingViewModel(Building building, RMIElevatorAdapter adapter) {
        this.building = building;
        this.adapter = adapter;
        building.addChangeListener(this);
        updateFromModel();
    }

    /**
     * If model dimension changes, reload whole view
     */
    @Override
    public void modelChanged() {
        updateFromModel();
    }

    /**
     * Reload viewmodel
     */
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

    /**
     * Reload view model (when model dimensions changed) and notify ciew to reload too
     */
    private void initializeViewModels() {
        floorViewModels.clear();
        elevatorViewModels.clear();
        for (Floor floor : building.getFloors()) {
            floorViewModels.add(new FloorViewModel(floor));
        }
        for (Elevator elevator : building.getElevators()) {
            elevatorViewModels.add(new ElevatorViewModel(adapter, elevator, building.getNumberOfFloors(),building.getFloorHeight()));
        }
        notifyChangeListeners();
    }
}
