package test.projet.tondeuse.job.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OrderTest {

    @Test
    void valueOfTests() {
        //GIVEN
        List<String> orders = new ArrayList<>();
        orders.add("A");
        orders.add("G");
        orders.add("D");
        //WHEN / THEN
        for (String order : orders) {
            switch (order) {
                case "A":
                    Assertions.assertEquals(Order.A, Order.valueOf(order));
                    break;
                case "G":
                    Assertions.assertEquals(Order.G, Order.valueOf(order));
                    break;
                case "D":
                    Assertions.assertEquals(Order.D, Order.valueOf(order));
                    break;
                default:
                    Assertions.fail();
                    break;
            }
        }
    }
}