package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.FloorViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;


@SuppressWarnings("java:S110")
public class SingleFilledFloorPane extends SingleFloorPane {

    private int buttonSize = 17;

    public SingleFilledFloorPane(FloorViewModel floorViewModel) {
        super();
        Image img = new Image("file:up.png", buttonSize, buttonSize, false, false);
        ImageView upImg = new ImageView(img);
        ObjectProperty<Image> upLabelBackground = upImg.imageProperty();
        upLabelBackground.bind(Bindings.createObjectBinding(() -> new Image(floorViewModel.getButtonUpImage(), buttonSize, buttonSize, false, false), floorViewModel.buttonUpImageProperty()));

        Label floorNumberLabel = new Label("" + (floorViewModel.getFloorNumber()+1));
        floorNumberLabel.setPrefWidth(35);
        floorNumberLabel.setAlignment(Pos.CENTER);
        floorNumberLabel.setTextAlignment(TextAlignment.CENTER);

        img = new Image("file:down.png", buttonSize, buttonSize, false, false);
        ImageView downImg = new ImageView(img);
        ObjectProperty<Image> downLabelBackground = downImg.imageProperty();
        downLabelBackground.bind(Bindings.createObjectBinding(() -> new Image(floorViewModel.getButtonDownImage(), buttonSize, buttonSize, false, false), floorViewModel.buttonDownImageProperty()));

        this.getChildren().addAll(upImg, floorNumberLabel, downImg);

    }
}
