package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.SystemStatus;
import at.fhhagenberg.elevator.viewmodel.BuildingViewModel;
import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import at.fhhagenberg.elevator.viewmodel.INotifyModelSizeChangedListener;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * This is the overal view
 * It consists of the GeneralInfoPane, FloorsPane and multiple ElevatorPane
 * It can be reloaded if the number of elevators or floors changes
 */
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

    public void setSystemStatus(SystemStatus status) {
        generalInfoPane.setSystemStatus(status);
    }

    /**
     * Creates a new view
     * Called at the beginning
     * Called when the building changes
     */
    private void updateLayout() {
        this.getChildren().clear();
        this.getChildren().addAll(generalInfoPane);

        ScrollPane scrollLayout = new ScrollPane();
        HBox layout = new HBox();
        var floorsPane = new FloorsPane(buildingViewModel.getFloorViewModels());
        layout.getChildren().add(floorsPane);
        //Add elevatorPane for every elevator
        for (ElevatorViewModel elevatorViewModel : buildingViewModel.getElevatorViewModels()) {
            layout.getChildren().add(new ElevatorPane(buildingViewModel.getFloorViewModels().size(), elevatorViewModel));
        }
        scrollLayout.setFitToHeight(true);
        scrollLayout.setFitToWidth(true);
        scrollLayout.setStyle("-fx-background-color:transparent;"); //remove border
        scrollLayout.setContent(layout);
        this.getChildren().add(scrollLayout);
    }

    @Override
    public void modelChanged() {
        updateLayout();
    }
}
