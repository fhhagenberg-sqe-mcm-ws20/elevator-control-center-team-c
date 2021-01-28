package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import lombok.Getter;

/**
 * This class is the moving elevator as overlay to the ElevatorAnimationPane
 */
@SuppressWarnings("java:S110")
public class ElevatorAnimationOverlayPane extends VBox {
    ElevatorViewModel elevatorViewModel;
    @Getter
    private int imageSize = 39;
    public ElevatorAnimationOverlayPane(ElevatorViewModel elevatorViewModel) {
        //Add image
        this.elevatorViewModel = elevatorViewModel;
        Image img = new Image("file:elevator.png", imageSize, imageSize, false, false);
        ImageView elevator = new ImageView(img);
        this.getChildren().add(elevator);

        //Bind position property to viewmodel
        elevatorViewModel.positionProperty().addListener((observable, oldValue, newValue) -> setInsets());
        this.heightProperty().addListener((observable, oldValue, newValue) -> setInsets());
        //Bind height property to viewmodel
        elevator.fitHeightProperty().addListener((observable, oldValue, newValue) -> setInsets());
        this.setPickOnBounds(false);
    }

    /**
     * Centers elevator
     */
    public void setInsets() {
        this.paddingProperty().set(new Insets(
                (this.getHeight())*elevatorViewModel.getPosition()-39,
                0,
                0,
                55
        ));
    }
}
