package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.converter.InterfaceToModelConverter;
import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.view.ElevatorControlCenterPane;
import at.fhhagenberg.elevator.viewmodel.BuildingViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import sqelevator.IElevator;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * JavaFX App
 */
public class App extends Application {

    private Building building=new Building();
    private BuildingViewModel buildingViewModel=new BuildingViewModel(building);
    private ElevatorControlCenterPane view;


    @Override
    public void start(Stage stage) {

        try {
            IElevator controller = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
            InterfaceToModelConverter converter = new InterfaceToModelConverter(controller);

            Runnable runnable = new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    while(true) {
                        Thread.sleep(1000);
                        Platform.runLater(() -> {
                            try {
                                converter.convert(building);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            };

            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            thread.start();

            view = new ElevatorControlCenterPane(buildingViewModel, stage);
            view.initialize();
        } catch (Exception e ){
            System.out.println("Error: "+e.getMessage());
        }


        var scene = new Scene(view, 1280, 720);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}