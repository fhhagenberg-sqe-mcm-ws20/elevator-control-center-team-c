package at.fhhagenberg.sqe.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Building {
    @Getter
    private List<Elevator> elevators;
    @Getter
    private List<Floor> floors;
}
