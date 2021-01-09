package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import lombok.Getter;

@SuppressWarnings("java:S110")
public class ElevatorAnimationOverlayPane extends VBox {
    ElevatorViewModel elevatorViewModel;
    @Getter
    private int imageSize = 39;
    public ElevatorAnimationOverlayPane(ElevatorViewModel elevatorViewModel) {
        this.elevatorViewModel = elevatorViewModel;
        Image img = new Image("file:elevator.png", imageSize, imageSize, false, false);
        ImageView elevator = new ImageView(img);
        this.getChildren().add(elevator);

        elevatorViewModel.positionProperty().addListener((observable, oldValue, newValue) -> setInsets());
        this.heightProperty().addListener((observable, oldValue, newValue) -> setInsets());
        elevator.fitHeightProperty().addListener((observable, oldValue, newValue) -> setInsets());
        this.setPickOnBounds(false);
    }

    public void setInsets() {
        this.paddingProperty().set(new Insets(
                (this.getHeight())*elevatorViewModel.getPosition()-39,
                0,
                0,
                55
        ));
    }
}
