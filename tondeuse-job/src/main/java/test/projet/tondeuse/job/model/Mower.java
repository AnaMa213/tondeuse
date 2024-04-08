package test.projet.tondeuse.job.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private int posX;
    private int posY;
    @NonNull
    private Orientation orientation;
    @NonNull
    private List<Order> orders;
    @NonNull
    @Setter(AccessLevel.NONE)
    private UUID id = UUID.randomUUID();

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
        this.orders = new ArrayList<>();
        log.info("Mower {} finished orders ! It got to the position {} {} and is oriented {}. ", this.id, this.posX, this.posY, this.orientation);
    }

    private void goForward(int maxSizeX, int maxSizeY) {
        switch (this.orientation.name()) {
            case "N": {
                if (this.posY < maxSizeY) {
                    this.posY++;
                }
                break;
            }
            case "E": {
                if (this.posX < maxSizeX) {
                    this.posX++;
                }
                break;
            }
            case "W": {
                if (this.posX > 0) {
                    this.posX--;
                }
                break;
            }
            case "S": {
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
