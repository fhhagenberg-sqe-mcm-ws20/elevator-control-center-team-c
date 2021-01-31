package at.fhhagenberg.elevator.viewmodel;

/**
 * Interface used to notify dimension changes across all entities
 * Maybe not needed/overkill because number of elevators and number of floors shouldn't change in a building
 */
public interface INotifyModelSizeChangedListener {
    void modelChanged();
}
