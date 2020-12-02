package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

@SuppressWarnings("java:S110")
public class ElevatorAnimationOverlayPane extends VBox {
    public ElevatorAnimationOverlayPane(ElevatorViewModel elevatorViewModel) {
        Image img = new Image("file:elevator.png", 39, 39, false, false);
        ImageView elevator = new ImageView(img);
        this.getChildren().add(elevator);

        elevatorViewModel.positionProperty().addListener((observable, oldValue, newValue) -> this.paddingProperty().set(new Insets((this.getHeight() - elevator.getFitHeight()) * newValue.doubleValue(), 0, 0, 55)));
        this.heightProperty().addListener((observable, oldValue, newValue) -> this.paddingProperty().set(new Insets((newValue.doubleValue() - elevator.getFitHeight()) * elevatorViewModel.getPosition(), 0, 0, 55)));
        elevator.fitHeightProperty().addListener((observable, oldValue, newValue) -> this.paddingProperty().set(new Insets((this.getHeight() - newValue.doubleValue()) * elevatorViewModel.getPosition(), 0, 0, 55)));
        this.setPickOnBounds(false);
    }
}
