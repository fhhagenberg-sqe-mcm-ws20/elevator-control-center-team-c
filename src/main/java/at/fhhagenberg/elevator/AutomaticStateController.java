package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static sqelevator.IElevator.ELEVATOR_DOORS_OPEN;

public class AutomaticStateController {
    private final ArrayList<ArrayList<Pair<Floor, Boolean>>> elevatorSpecificTargetList = new ArrayList<>();
    private final RMIElevatorAdapter rmiElevatorAdapter;
    private final Building building;

    public AutomaticStateController(RMIElevatorAdapter rmiElevatorAdapter, Building building) {
        this.rmiElevatorAdapter = rmiElevatorAdapter;
        this.building = building;
        updateState();
    }

    public void updateState() {
        for (int i = 0; i < building.getNumberOfElevators(); i++) {
            if (elevatorSpecificTargetList.size() <= i) {
                elevatorSpecificTargetList.add(new ArrayList<>());
            }
            Elevator elevator = building.getElevator(i);
            extractTargetsForButtonsInsideTheElevator(elevator);
        }
        distributeFloorButtonTargetsAcrossElevators();
        checkReachedTargets(building);
    }

    private void extractTargetsForButtonsInsideTheElevator(Elevator elevator) {
        if (Boolean.FALSE.equals(elevator.isManualControl())) {
            for (Floor floor : building.getFloors()) {
                addFloorToTargetList(elevator, floor);
            }
        } else {
            elevatorSpecificTargetList.get(elevator.getNumber()).clear();
        }
    }

    private void addFloorToTargetList(Elevator elevator, Floor floor) {
        if (Boolean.TRUE.equals(elevator.getFloorButtonStatus(floor.getNumber())) && !isTargetInThisElevatorThenUpdate(elevator, floor)) {
            if (elevatorSpecificTargetList.get(elevator.getNumber()).isEmpty()) {
                elevatorSpecificTargetList.get(elevator.getNumber()).add(new Pair<>(floor, true));
                setTarget(elevator, floor);
            } else {
                if (!tryInsertFloorInBetweenTargetQueue(elevator, floor, elevator.getFloor())) {
                    elevatorSpecificTargetList.get(elevator.getNumber()).add(new Pair<>(floor, true));
                }
            }
        }
    }

    private void distributeFloorButtonTargetsAcrossElevators() {
        ArrayList<Floor> generalTargetList = extractAllNewTargetsFromFloorButtons(building.getFloors());

        for (Floor floor : generalTargetList) {
            insertFloorIntoElevatorQueue(floor);
        }
    }

    private ArrayList<Floor> extractAllNewTargetsFromFloorButtons(List<Floor> floors) {
        ArrayList<Floor> generalTargetList = new ArrayList<>();

        for (Floor floor : floors) {
            if ((floor.isButtonDown() || floor.isButtonUp()) && !isTargetAtAnyElevator(floor)) {
                generalTargetList.add(floor);
            }
        }
        return generalTargetList;
    }

    private void insertFloorIntoElevatorQueue(Floor floor) {
        ArrayList<Integer> lastCheckedFloors = setCurrentFloorOfAllElevatorsAsInitialLastCheckedFloor(building.getElevators());
        for (int i = 0; i <= getMaxSize(); i++) {
            for (int j = 0; j < elevatorSpecificTargetList.size(); j++) {
                Elevator elevator = building.getElevator(j);
                if (Boolean.FALSE.equals(elevator.isManualControl()) && Boolean.TRUE.equals(elevator.servicesFloor(floor.getNumber()))) {
                    if (checkIfTargetCanBeInserted(elevatorSpecificTargetList.get(j), floor, i, lastCheckedFloors.get(j))) {
                        elevatorSpecificTargetList.get(j).add(i, new Pair<>(floor, false));
                        if (i == 0) {
                            setTarget(elevator, floor);
                        }
                        return;
                    }
                    lastCheckedFloors.set(j, elevatorSpecificTargetList.get(j).get(i).getKey().getNumber());
                }
            }
        }
    }

    private boolean checkIfTargetCanBeInserted(ArrayList<Pair<Floor, Boolean>> elevatorSpecificTargetList, Floor floor, int insertPosition, int lastCheckedFloor) {
        return (elevatorSpecificTargetList.size() <= insertPosition || isFloorBetweenThoseFloors(lastCheckedFloor, elevatorSpecificTargetList.get(insertPosition).getKey().getNumber(), floor.getNumber()));
    }

    private ArrayList<Integer> setCurrentFloorOfAllElevatorsAsInitialLastCheckedFloor(List<Elevator> elevators) {
        ArrayList<Integer> lastCheckedFloors = new ArrayList<>();
        for (Elevator elevator : elevators) {
            lastCheckedFloors.add(elevator.getFloor());
        }
        return lastCheckedFloors;
    }

    private boolean tryInsertFloorInBetweenTargetQueue(Elevator elevator, Floor floor, int lastFloor) {
        for (int j = 0; j < elevatorSpecificTargetList.get(elevator.getNumber()).size(); j++) {
            if (isFloorBetweenThoseFloors(lastFloor, elevatorSpecificTargetList.get(elevator.getNumber()).get(j).getKey().getNumber(), floor.getNumber())) {
                elevatorSpecificTargetList.get(elevator.getNumber()).add(j, new Pair<>(floor, false));
                if (j == 0) {
                    setTarget(elevator, floor);
                }
                return true;
            }
            lastFloor = elevatorSpecificTargetList.get(elevator.getNumber()).get(j).getKey().getNumber();
        }
        return false;
    }

    private void checkReachedTargets(Building building) {
        for (int i = 0; i < elevatorSpecificTargetList.size(); i++) {
            if (!elevatorSpecificTargetList.get(i).isEmpty()) {
                Elevator elevator = building.getElevator(i);
                if (elevatorSpecificTargetList.get(i).get(0).getKey().getNumber() == elevator.getFloor() && elevator.getDoorStatus() == ELEVATOR_DOORS_OPEN) {
                    Pair<Floor, Boolean> target = elevatorSpecificTargetList.get(i).get(0);
                    if (Boolean.TRUE.equals(target.getValue())) {
                        removeTargetOfFloorButtonFromOtherElevators(target.getKey());
                    }
                    elevatorSpecificTargetList.get(i).remove(0);
                    if (!elevatorSpecificTargetList.get(i).isEmpty()) {
                        setTarget(elevator, elevatorSpecificTargetList.get(i).get(0).getKey());
                    }
                }
            }
        }
    }

    private void removeTargetOfFloorButtonFromOtherElevators(Floor floor) {
        for (int i = 0; i < elevatorSpecificTargetList.size(); i++) {
            for (int j = 0; j < elevatorSpecificTargetList.get(i).size(); j++) {
                if (elevatorSpecificTargetList.get(i).get(j).getKey() == floor && Boolean.FALSE.equals(elevatorSpecificTargetList.get(i).get(j).getValue())) {
                    elevatorSpecificTargetList.get(i).remove(j);
                    if (j == 0 && Boolean.FALSE.equals(elevatorSpecificTargetList.get(i).isEmpty())) {
                        setTarget(building.getElevator(i), elevatorSpecificTargetList.get(i).get(0).getKey());
                    }
                }
            }
        }
    }

    private void setTarget(Elevator elevator, Floor floor) {
        rmiElevatorAdapter.setTarget(elevator.getNumber(), floor.getNumber());
    }

    private boolean isTargetAtAnyElevator(Floor floor) {
        for (List<Pair<Floor, Boolean>> elevatorTargets : elevatorSpecificTargetList) {
            for (Pair<Floor, Boolean> elevatorPair : elevatorTargets) {
                if (elevatorPair.getKey() == floor) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isTargetInThisElevatorThenUpdate(Elevator elevator, Floor floor) {
        for (int i = 0; i < elevatorSpecificTargetList.get(elevator.getNumber()).size(); i++) {
            Pair<Floor, Boolean> elevatorPair = elevatorSpecificTargetList.get(elevator.getNumber()).get(i);
            if (elevatorPair.getKey() == floor) {
                if (Boolean.FALSE.equals(elevatorPair.getValue())) {
                    elevatorSpecificTargetList.get(elevator.getNumber()).set(i, new Pair<>(elevatorPair.getKey(), true));
                }
                return true;
            }
        }
        return false;
    }

    private boolean isFloorBetweenThoseFloors(int floor1, int floor2, int floor3) {
        int max = Math.max(floor1, floor2);
        int min = Math.min(floor1, floor2);

        return floor3 > min && floor3 < max;
    }

    private int getMaxSize() {
        int max = 0;
        for (List<Pair<Floor, Boolean>> list : elevatorSpecificTargetList) {
            max = Math.max(max, list.size());
        }
        return max;
    }
}
