package at.fhhagenberg.sqe.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Elevator {
    @Getter
    private int number;
    @Getter
    @Setter
    private int commitedDirection;
    @Getter
    @Setter
    private int acceleration;
    @Getter
    @Setter
    private int doorStatus;
    @Getter
    @Setter
    private Floor floor;
    @Getter
    @Setter
    private int position;
    @Getter
    @Setter
    private int speed;
    @Getter
    @Setter
    private int weight;
    @Getter
    private int capacity;
    @Getter
    @Setter
    private Floor Target;

    private HashMap<Floor, Boolean> floorButtons;

    public List<Floor> getServicedFloors() {
        return new ArrayList<Floor>(floorButtons.keySet());
    }
}
