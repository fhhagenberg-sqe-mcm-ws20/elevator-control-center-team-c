package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;

import java.util.ArrayList;
import java.util.List;

import static sqelevator.IElevator.ELEVATOR_DOORS_OPEN;

public class AutomaticStateController {
    private final ArrayList<ArrayList<Floor>> elevatorSpecificTargetList = new ArrayList<>();
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
            extractTargetsForButtonsinsideTheElevator(elevator);
        }
        distributeFloorButtonTargetsAcrossElevators();
        checkReachedTargets(building);
    }

    private void distributeFloorButtonTargetsAcrossElevators() {
        ArrayList<Floor> generalTargetList = extractAllNewTargetsFromFloorButtons(building.getFloors());

        for (Floor floor : generalTargetList) {
            insertFloorIntoElevatorQueue(floor);
        }
    }

    private void insertFloorIntoElevatorQueue(Floor floor) {
        ArrayList<Integer> lastCheckedFloors = setCurrentFloorOfAllElevatorsAsInitialLastCheckedFloor(building.getElevators());
        for (int i = 0; i <= getMaxSize(); i++) {
            for (int j = 0; j < elevatorSpecificTargetList.size(); j++) {
                Elevator elevator = building.getElevator(j);
                if (Boolean.FALSE.equals(elevator.isManualControl())) {
                    if (checkIfTargetCanBeInserted(elevatorSpecificTargetList.get(j), floor, i, lastCheckedFloors.get(j))) {
                        elevatorSpecificTargetList.get(j).add(i, floor);
                        if (i == 0) {
                            setTarget(elevator, floor);
                        }
                        return;
                    }
                    lastCheckedFloors.set(j, elevatorSpecificTargetList.get(j).get(i).getNumber());
                }
            }
        }
    }

    private boolean checkIfTargetCanBeInserted(ArrayList<Floor> elevatorSpecificTargetList, Floor floor, int insertPosition, int lastCheckedFloor) {
        return (elevatorSpecificTargetList.size() <= insertPosition || isFloorBetweenThoseFloors(lastCheckedFloor, elevatorSpecificTargetList.get(insertPosition).getNumber(), floor.getNumber()));
    }

    private ArrayList<Integer> setCurrentFloorOfAllElevatorsAsInitialLastCheckedFloor(List<Elevator> elevators) {
        ArrayList<Integer> lastCheckedFloors = new ArrayList<>();
        for (Elevator elevator : elevators) {
            lastCheckedFloors.add(elevator.getFloor());
        }
        return lastCheckedFloors;
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

    private void extractTargetsForButtonsinsideTheElevator(Elevator elevator) {
        if (Boolean.FALSE.equals(elevator.isManualControl())) {
            for (Floor floor : building.getFloors()) {
                addFloorToTargetList(elevator, floor);
            }
        } else {
            elevatorSpecificTargetList.get(elevator.getNumber()).clear();
        }
    }

    private boolean tryInsertFloorInBetweenTargetQueue(Elevator elevator, Floor floor, int lastFloor) {
        for (int j = 0; j < elevatorSpecificTargetList.get(elevator.getNumber()).size(); j++) {
            if (isFloorBetweenThoseFloors(lastFloor, elevatorSpecificTargetList.get(elevator.getNumber()).get(j).getNumber(), floor.getNumber())) {
                elevatorSpecificTargetList.get(elevator.getNumber()).add(j, floor);
                if (j == 0) {
                    setTarget(elevator, floor);
                }
                return true;
            }
            lastFloor = elevatorSpecificTargetList.get(elevator.getNumber()).get(j).getNumber();
        }
        return false;
    }

    private void addFloorToTargetList(Elevator elevator, Floor floor) {
        if (!isTargetInThisElevator(elevator, floor) && Boolean.TRUE.equals(elevator.getFloorButtonStatus(floor.getNumber()))) {
            if (elevatorSpecificTargetList.get(elevator.getNumber()).isEmpty()) {
                elevatorSpecificTargetList.get(elevator.getNumber()).add(floor);
                setTarget(elevator, floor);
            } else {
                if (!tryInsertFloorInBetweenTargetQueue(elevator, floor, elevator.getFloor())) {
                    elevatorSpecificTargetList.get(elevator.getNumber()).add(floor);
                }
            }
        }
    }

    private void checkReachedTargets(Building building) {
        for (int i = 0; i < elevatorSpecificTargetList.size(); i++) {
            if (!elevatorSpecificTargetList.get(i).isEmpty()) {
                Elevator elevator = building.getElevator(i);
                if (elevatorSpecificTargetList.get(i).get(0).getNumber() == elevator.getFloor() && elevator.getDoorStatus() == ELEVATOR_DOORS_OPEN) {
                    elevatorSpecificTargetList.get(i).remove(0);
                    if (!elevatorSpecificTargetList.get(i).isEmpty()) {
                        setTarget(elevator, elevatorSpecificTargetList.get(i).get(0));
                    }
                }
            }
        }
    }

    private void setTarget(Elevator elevator, Floor floor) {
        rmiElevatorAdapter.setTarget(elevator.getNumber(), floor.getNumber());
    }

    private boolean isTargetAtAnyElevator(Floor floor) {
        for (List<Floor> elevatorTargets : elevatorSpecificTargetList) {
            if (elevatorTargets.contains(floor)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTargetInThisElevator(Elevator elevator, Floor floor) {
        return elevatorSpecificTargetList.get(elevator.getNumber()).contains(floor);
    }

    private boolean isFloorBetweenThoseFloors(int floor1, int floor2, int floor3) {
        int max = Math.max(floor1, floor2);
        int min = Math.min(floor1, floor2);

        return floor3 > min && floor3 < max;
    }

    private int getMaxSize() {
        int max = 0;
        for (List<Floor> list : elevatorSpecificTargetList) {
            max = Math.max(max, list.size());
        }
        return max;
    }
}
