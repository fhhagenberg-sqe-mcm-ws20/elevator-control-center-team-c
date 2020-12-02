package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import javafx.application.Platform;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulator {
    Building building;
    public Simulator(Building building) {
        this.building=building;
    }

    public void run() {
        Thread thread = new Thread() {
            Random random = new Random();

            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    List<Floor> newFloors = new ArrayList<>();
                    List<Elevator> newElevators = new ArrayList<>();

                    Thread.sleep(1000);

                    for (Floor floor : building.getFloors()) {
                        newFloors.add(new Floor(floor.getNumber(), random.nextBoolean(), random.nextBoolean()));
                    }

                    for (Elevator elevator : building.getElevators()) {
                        List<Boolean> newfloorButtonStatuses = new ArrayList<>();

                        for (int i = 0; i < building.getNumberOfFloors(); i++) {
                            newfloorButtonStatuses.add(random.nextBoolean());
                        }
                        newElevators.add(new Elevator(elevator.getNumber(), random.nextInt(2), random.nextInt(200), 1, random.nextInt(building.getNumberOfFloors()), (elevator.getPosition() + 25) % 900, random.nextInt(200), random.nextInt(200), random.nextInt(200), random.nextInt(building.getNumberOfFloors()), new ArrayList<>(List.of(random.nextInt(building.getNumberOfFloors()), random.nextInt(building.getNumberOfFloors()), random.nextInt(building.getNumberOfFloors()))), newfloorButtonStatuses));
                    }
                    Platform.runLater(() -> building.copyValues(new Building(newElevators, newFloors, 100)));
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    //Only for testing layout
    public List<Elevator> generateElevatorModels(int number, int numberOfFloors) {
        List<Elevator> elevators = new ArrayList<>();
        List<Boolean> floorButtonStatuses = new ArrayList<>();
        for (int j = 0; j < numberOfFloors; j++) {
            floorButtonStatuses.add(false);
        }
        for (int i = 0; i < number; i++) {
            Elevator elevator = new Elevator(i, 1, 1, 1, 1, 1, 1, 1, 1, 1, new ArrayList<>(), floorButtonStatuses);
            elevators.add(elevator);
        }
        return elevators;
    }

    //Only for testing layout
    public List<Floor> generateFloorModels(int number) {
        List<Floor> floors = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Floor floor = new Floor(i, false, false);
            floors.add(floor);
        }
        return floors;
    }
}
