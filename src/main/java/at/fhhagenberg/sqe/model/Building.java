package at.fhhagenberg.sqe.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Building {
    private List<Elevator> elevators;
    private List<Floor> floors;
}
