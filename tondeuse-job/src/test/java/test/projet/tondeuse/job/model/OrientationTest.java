package test.projet.tondeuse.job.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class OrientationTest {

    @Test
    void valueOfTests() {
        //GIVEN
        List<String> orientations = new ArrayList<>();
        orientations.add("N");
        orientations.add("E");
        orientations.add("W");
        orientations.add("S");
        //WHEN / THEN
        for (String orientation : orientations) {
            switch (orientation) {
                case "N":
                    Assertions.assertEquals(Orientation.N, Orientation.valueOf(orientation));
                    break;
                case "E":
                    Assertions.assertEquals(Orientation.E, Orientation.valueOf(orientation));
                    break;
                case "W":
                    Assertions.assertEquals(Orientation.W, Orientation.valueOf(orientation));
                    break;
                case "S":
                    Assertions.assertEquals(Orientation.S, Orientation.valueOf(orientation));
                    break;
                default:
                    Assertions.fail();
                    break;
            }
        }
    }

}