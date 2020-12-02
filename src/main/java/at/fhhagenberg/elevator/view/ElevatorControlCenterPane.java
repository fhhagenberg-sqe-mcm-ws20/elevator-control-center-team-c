package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.BuildingViewModel;
import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import at.fhhagenberg.elevator.viewmodel.INotifyModelSizeChangedListener;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

@SuppressWarnings("java:S110")
public class ElevatorControlCenterPane extends HBox implements INotifyModelSizeChangedListener {
    private BuildingViewModel buildingViewModel;
    private Stage stage;
    private GeneralInfoPane generalInfoPane = new GeneralInfoPane();

    public ElevatorControlCenterPane(BuildingViewModel buildingViewModel, Stage stage) {
        this.buildingViewModel = buildingViewModel;
        this.stage = stage;
        this.buildingViewModel.addChangeListener(this);
    }

    public void initialize() {
        updateLayout();
    }

    private void updateLayout() {
        this.getChildren().clear();
        this.getChildren().addAll(generalInfoPane);
        var floorsPane = new FloorsPane(buildingViewModel.getFloorViewModels());
        this.getChildren().add(floorsPane);
        for (ElevatorViewModel elevatorViewModel : buildingViewModel.getElevatorViewModels()) {
            this.getChildren().add(new ElevatorPane(buildingViewModel.getFloorViewModels().size(), elevatorViewModel));
        }
    }

    @Override
    public void modelChanged() {
        updateLayout();
    }
}
