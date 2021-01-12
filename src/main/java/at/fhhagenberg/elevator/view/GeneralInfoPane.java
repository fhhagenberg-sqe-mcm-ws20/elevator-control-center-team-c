package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.SystemStatus;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

@SuppressWarnings("java:S110")
public class GeneralInfoPane extends VBox {
    private TextArea errorMessagesTextArea;
    private Text systemStatusText;

    public GeneralInfoPane() {
        super();
        Label systemStatusLabel = new Label("System status");
        systemStatusText = new Text(SystemStatus.CONNECTED.name().toLowerCase());
        systemStatusText.setId("statusText");
        systemStatusText.setFill(Color.GREEN);
        TextFlow systemStatusTextFlow = new TextFlow(systemStatusText);
        Pane spacePane = new Pane();
        spacePane.setPrefHeight(500);
        Label errorMessagesLabel = new Label("Error Messages");
        errorMessagesTextArea = new TextArea();
        errorMessagesTextArea.setDisable(true);
        errorMessagesTextArea.setStyle("-fx-opacity: 1;");
        errorMessagesTextArea.setPrefSize(2000, 2000);
        this.setBackground(new Background(new BackgroundFill(Color.rgb(250, 250, 250), CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPrefSize(150, Double.MAX_VALUE);
        this.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-color: black;");
        this.getChildren().addAll(systemStatusLabel, systemStatusTextFlow, spacePane, errorMessagesLabel, errorMessagesTextArea);
    }

    public void setSystemStatus(SystemStatus status) {
        systemStatusText.setText(status.name().toLowerCase());
        switch (status) {
            case CONNECTED: {
                systemStatusText.setFill(Color.GREEN);
                break;
            }
            case CONNECTING: {
                systemStatusText.setFill(Color.RED);
                break;
            }
        }
    }
}
