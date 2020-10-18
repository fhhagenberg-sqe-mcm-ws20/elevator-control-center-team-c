package at.fhhagenberg.sqe.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Floor {
    @Getter
    private int number;
    @Getter
    @Setter
    private boolean buttonUp;
    @Getter
    @Setter
    private boolean buttonDown;
    @Getter
    private int height;
}
