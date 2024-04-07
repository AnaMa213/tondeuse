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
@NoArgsConstructor
@AllArgsConstructor
public class Tondeuse {
    public int posX;
    public int posY;
    @NonNull
    public String orientation;
    @NonNull
    public List<String> orders;
    private UUID id = UUID.randomUUID();

    public void initiateOrder(int maxSizeX, int maxSizeY) {
        for (String order : this.orders) {
            switch (order) {
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
                    //TODO LOG WARNING
                    break;
            }

        }
        this.orders = new ArrayList<>();
        log.info("Tondeuse {} finished orders ! It got to the position {} {} and is oriented {}. ", this.id, this.posX, this.posY, this.orientation);
    }

    public void goForward(int maxSizeX, int maxSizeY) {
        switch (this.orientation) {
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
                //TODO LOG WARNING
                break;

        }
    }

    public void rotateRight() {
        String orientation = this.orientation;
        switch (orientation) {
            case "N": {
                this.orientation = "E";
                break;
            }
            case "E": {
                this.orientation = "S";
                break;
            }
            case "W": {
                this.orientation = "N";
                break;
            }
            case "S": {
                this.orientation = "W";
                break;
            }
            default:
                //TODO LOG WARNING
                break;
        }
    }

    public void rotateLeft() {
        String orientation = this.orientation;
        switch (orientation) {
            case "N": {
                this.orientation = "W";
                break;
            }
            case "E": {
                this.orientation = "N";
                break;
            }
            case "W": {
                this.orientation = "S";
                break;
            }
            case "S": {
                this.orientation = "E";
                break;
            }
            default:
                //TODO LOG WARNING
                break;
        }
    }
}
