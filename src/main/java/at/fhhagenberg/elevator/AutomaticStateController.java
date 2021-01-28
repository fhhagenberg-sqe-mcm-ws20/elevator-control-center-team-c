package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static sqelevator.IElevator.ELEVATOR_DOORS_OPEN;

/**
 * This class controls the elevators in automatic mode
 * It is stateful
 * It directly controlls the elevators via setting the targets
 * It should be run concurrently to the polling loop
 */
public class AutomaticStateController {
    private final ArrayList<ArrayList<Pair<Floor, Boolean>>> elevatorSpecificTargetList = new ArrayList<>();
    private final RMIElevatorAdapter rmiElevatorAdapter;
    private final Building building;

    public AutomaticStateController(RMIElevatorAdapter rmiElevatorAdapter, Building building) {
        this.rmiElevatorAdapter = rmiElevatorAdapter;
        this.building = building;
        updateState();
    }

    /**
     * Check if something changed
     */
    public void updateState() {
        for (int i = 0; i < building.getNumberOfElevators(); i++) {
            if (elevatorSpecificTargetList.size() <= i) {
                elevatorSpecificTargetList.add(new ArrayList<>());
            }
            Elevator elevator = building.getElevator(i);
            extractTargetsForButtonsInsideTheElevatorAndAddAtRightPosition(elevator);
        }
        distributeFloorButtonTargetsAcrossElevators();
        checkReachedTargetsAndOpenDoor(building);
    }

    /**
     * Try to add all elevator buttons to target list that aren't currently in the elevator specific target list
     * @param elevator current elevator
     */
    private void extractTargetsForButtonsInsideTheElevatorAndAddAtRightPosition(Elevator elevator) {
        if (Boolean.FALSE.equals(elevator.isManualControl())) {
            for (Floor floor : building.getFloors()) {
                addFloorToTargetList(elevator, floor);
            }
        } else {
            elevatorSpecificTargetList.get(elevator.getNumber()).clear();
        }
    }

    /**
     * Try to add floor at right position between targets in the list or at the end
     * @param elevator current elevator
     * @param floor target floor
     */
    private void addFloorToTargetList(Elevator elevator, Floor floor) {
        if (Boolean.TRUE.equals(elevator.getFloorButtonStatus(floor.getNumber())) && !isTargetInThisElevatorThenUpdate(elevator, floor)) {
            //If elevator list is empty, add and set target of elevator
            if (elevatorSpecificTargetList.get(elevator.getNumber()).isEmpty()) {
                elevatorSpecificTargetList.get(elevator.getNumber()).add(new Pair<>(floor, true));
                setTarget(elevator, floor);
            } else {
                //Try to add target in between, if not add it at the end
                if (!tryInsertFloorInBetweenTargetQueue(elevator, floor)) {
                    elevatorSpecificTargetList.get(elevator.getNumber()).add(new Pair<>(floor, true));
                }
            }
        }
    }

    /**
     * Try to add target between targets in the list if it fits e.g. target f3 list f2,f4 result should be list f2,f3,f4
     * Basically if elevator would drive by, stop
     * @param elevator  current elevator
     * @param floor     target floor
     * @return true if it could be inserted, else return false
     */
    private boolean tryInsertFloorInBetweenTargetQueue(Elevator elevator, Floor floor) {
        //Current position is start of comparison
        int lastFloor=elevator.getFloor();
        for (int j = 0; j < elevatorSpecificTargetList.get(elevator.getNumber()).size(); j++) {
            //Check if floor is between targets, if so add to list
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

    /**
     * Go through every floor where a button is pressed and which isn't in any target list and try to add it to a list
     */
    private void distributeFloorButtonTargetsAcrossElevators() {
        ArrayList<Floor> generalTargetList = extractAllNewTargetsFromFloorButtons(building.getFloors());

        for (Floor floor : generalTargetList) {
            insertFloorIntoElevatorQueue(floor);
        }
    }

    /**
     * Returns all floors where a button is pressed which isn't in any target list
     * @param floors all floors of the building
     * @return list of all unplanned floors
     */
    private ArrayList<Floor> extractAllNewTargetsFromFloorButtons(List<Floor> floors) {
        ArrayList<Floor> generalTargetList = new ArrayList<>();

        for (Floor floor : floors) {
            if ((floor.isButtonDown() || floor.isButtonUp()) && !isTargetAtAnyElevator(floor)) {
                generalTargetList.add(floor);
            }
        }
        return generalTargetList;
    }

    /**
     * Try to add the floor into any elevator list. Either between targets or at the end
     * @param floor
     */
    private void insertFloorIntoElevatorQueue(Floor floor) {
        //Initial lower end of insert check has to be the elevator positions
        ArrayList<Integer> lastCheckedFloors = setCurrentFloorOfAllElevatorsAsInitialLastCheckedFloor(building.getElevators());
        //Go trough all entries of the elevator lists
        for (int i = 0; i <= getMaxSize(); i++) {
            //Go through all elevator lists
            for (int j = 0; j < elevatorSpecificTargetList.size(); j++) {
                Elevator elevator = building.getElevator(j);
                if (Boolean.FALSE.equals(elevator.isManualControl()) && Boolean.TRUE.equals(elevator.servicesFloor(floor.getNumber()))) {
                    //Try to add it to any list. Is done when it fits in between or if a list is at the end
                    if (checkIfTargetCanBeInserted(elevatorSpecificTargetList.get(j), floor, i, lastCheckedFloors.get(j))) {
                        elevatorSpecificTargetList.get(j).add(i, new Pair<>(floor, false));
                        //If it is the first entry in a list set target
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

    /**
     * Check if target can be inserted
     * Can be inserted if either the checking target list is checked at the end or it can be inserted between targets in the list
     * @param elevatorSpecificTargetList specific list
     * @param floor target floor
     * @param insertPosition current list position where an insert should be tested
     * @param lastCheckedFloor last checked floor,lower bound for check
     * @return  true if it can be inserted else false
     */
    private boolean checkIfTargetCanBeInserted(ArrayList<Pair<Floor, Boolean>> elevatorSpecificTargetList, Floor floor, int insertPosition, int lastCheckedFloor) {
        return (elevatorSpecificTargetList.size() <= insertPosition || isFloorBetweenThoseFloors(lastCheckedFloor, elevatorSpecificTargetList.get(insertPosition).getKey().getNumber(), floor.getNumber()));
    }

    /**
     * Get list of current elevators positions as intial lower bound
     * @param elevators all elevators
     * @return returns list of lower bounds for every elevator
     */
    private ArrayList<Integer> setCurrentFloorOfAllElevatorsAsInitialLastCheckedFloor(List<Elevator> elevators) {
        ArrayList<Integer> lastCheckedFloors = new ArrayList<>();
        for (Elevator elevator : elevators) {
            lastCheckedFloors.add(elevator.getFloor());
        }
        return lastCheckedFloors;
    }

    /**
     * Check if a target is reached and Door is open
     * If that's the case remove target from list and set new target
     * @param building
     */
    private void checkReachedTargetsAndOpenDoor(Building building) {
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

    /**
     * If elevator reached a floor where another elevator was assigned a floor button, remove this floor from the second elevator
     * This can be the case if the second elevator was assigned with the floor, then the first elevator got the assignemnt from his inside buttons
     * The first elevator can then reach the floor before and takes all the persons waiting at the floor and the second elevator would go there for nothing
     * @param floor reached floor
     */
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

    /**
     * Set target in elevator
     * @param elevator  elevator where target should be set
     * @param floor target
     */
    private void setTarget(Elevator elevator, Floor floor) {
        rmiElevatorAdapter.setTarget(elevator.getNumber(), floor.getNumber());
    }

    /**
     * Cheks if the floor is target of any elevator
     * @param floor floor that should be checked
     * @return true if is a target, false if not
     */
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

    /**
     * Cheks if the floor is target of the defined elevator
     * @param elevator elevator target list that should be checked
     * @param floor floor that should be checked
     * @return true if is a target, false if not
     */
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

    /**
     * Check if floor3 is between floor1 and floor2
     * @param floor1
     * @param floor2
     * @param floor3
     * @return if is between
     */
    private boolean isFloorBetweenThoseFloors(int floor1, int floor2, int floor3) {
        int max = Math.max(floor1, floor2);
        int min = Math.min(floor1, floor2);

        return floor3 > min && floor3 < max;
    }

    /**
     * Return max size of all target lists
     * @return max size
     */
    private int getMaxSize() {
        int max = 0;
        for (List<Pair<Floor, Boolean>> list : elevatorSpecificTargetList) {
            max = Math.max(max, list.size());
        }
        return max;
    }
}
