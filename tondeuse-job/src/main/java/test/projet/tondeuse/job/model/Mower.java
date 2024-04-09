package test.projet.tondeuse.job.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Mower class and used as record for our {@link test.projet.tondeuse.job.MowerJob}.
 * <p>
 * This class represents a mower in its position and orientation. It also has a list of order ready to be followed to change its position.
 * <p>
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@Getter
@Setter
@Slf4j
@ToString
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mower {


    public static final String ORIENTATION_NOT_CHANGED_BECAUSE_NOT_RECOGNIZED = "Orientation {} not changed because not recognized.";

    /**
     * position x of the mower in a specific lawn.
     */
    private int posX;
    /**
     * position y of the mower in a specific lawn.
     */
    private int posY;

    /**
     * {@link Orientation} of the mower. it can be set to North, East, West, South.
     */
    @NonNull
    private Orientation orientation;

    /**
     * list of {@link Order} given for the mower. it can be set to A (Forward), G(Rotate left), D(Rotate right).
     */
    @NonNull
    private List<Order> orders;

    /**
     * id of the given mower.
     */
    @NonNull
    @Setter(AccessLevel.NONE)
    private UUID id = UUID.randomUUID();

    /**
     * Initiate the list of order of the mower. <p>
     * The size of the lawn must be specified with the two argument maxSizeX et maxSizeY both absolute <a href="#{@link}">{@link int}</a> and cannot be Null.
     * Those two arguments represents the length and width of the lawn.
     * <p>
     *
     * @param maxSizeX length of the lawn.
     * @param maxSizeY width of the lawn.
     */
    public void initiateOrder(int maxSizeX, int maxSizeY) {
        for (Order order : this.orders) {
            switch (order.name()) {
                case "A":
                    this.goForward(maxSizeX, maxSizeY);
                    break;
                case "D":
                    this.rotateRight();
                    break;
                case "G":
                    this.rotateLeft();
                    break;
                default:
                    log.warn("Order {} skipped because not recognized.", order);
                    break;
            }

        }
        // All orders have been proceeded, empty orders list.
        this.orders = new ArrayList<>();
        log.info("Mower {} finished orders ! It got to the position {} {} and is oriented {}. ", this.id, this.posX, this.posY, this.orientation);
    }

    /**
     * Initiate the order "move forward" on the mower. <p>
     * The size of the lawn must be specified with the two argument maxSizeX et maxSizeY both absolute <a href="#{@link}">{@link int}</a> and cannot be Null.
     * Those two arguments represents the length and width of the lawn.
     * <p>
     * Depending on the orientation of the mower, it will go up, right, left or down.
     * <p>
     *
     * @param maxSizeX length of the lawn.
     * @param maxSizeY width of the lawn.
     */
    private void goForward(int maxSizeX, int maxSizeY) {
        switch (this.orientation.name()) {
            case "N": {
                // If the position of the mower not on the top edge of the lawn, move up.
                if (this.posY < maxSizeY) {
                    this.posY++;
                }
                break;
            }
            case "E": {
                // If the position of the mower not on the right edge of the lawn, move right.
                if (this.posX < maxSizeX) {
                    this.posX++;
                }
                break;
            }
            case "W": {
                // If the position of the mower not on the left edge of the lawn, move left.
                if (this.posX > 0) {
                    this.posX--;
                }
                break;
            }
            case "S": {
                // If the position of the mower not on the lower edge of the lawn, move down.
                if (this.posY > 0) {
                    this.posY--;
                }
                break;
            }
            default:
                log.warn(ORIENTATION_NOT_CHANGED_BECAUSE_NOT_RECOGNIZED, this.orientation);
                break;

        }
    }

    /**
     * Initiate the order "rotate right" on the mower. <p>
     * <p>
     * Depending on the initial orientation of the mower, it will rotate in orientation north, east, west or south.
     * <p>
     */
    private void rotateRight() {
        switch (this.orientation.name()) {
            case "N": {
                this.orientation = Orientation.E;
                break;
            }
            case "E": {
                this.orientation = Orientation.S;
                break;
            }
            case "W": {
                this.orientation = Orientation.N;
                break;
            }
            case "S": {
                this.orientation = Orientation.W;
                break;
            }
            default:
                log.warn(ORIENTATION_NOT_CHANGED_BECAUSE_NOT_RECOGNIZED, this.orientation);
                break;
        }
    }

    /**
     * Initiate the order "rotate right" on the mower. <p>
     * <p>
     * Depending on the initial orientation of the mower, it will rotate in orientation north, east, west or south.
     * <p>
     */
    private void rotateLeft() {
        switch (this.orientation.name()) {
            case "N": {
                this.orientation = Orientation.W;
                break;
            }
            case "E": {
                this.orientation = Orientation.N;
                break;
            }
            case "W": {
                this.orientation = Orientation.S;
                break;
            }
            case "S": {
                this.orientation = Orientation.E;
                break;
            }
            default:
                log.warn(ORIENTATION_NOT_CHANGED_BECAUSE_NOT_RECOGNIZED, this.orientation);
                break;
        }
    }
}
