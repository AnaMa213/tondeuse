package test.projet.tondeuse.job.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class MowerTest {

    private Mower mower;
    private UUID id;

    @BeforeEach
    void initMower() {
        this.id = UUID.randomUUID();
        List<Order> orders = Arrays.stream("GAGAGAGAADDDDDA".split("")).map(Order::valueOf).collect(Collectors.toList());
        this.mower = Mower.builder().id(this.id).posX(1).posY(2).orientation(Orientation.N).orders(orders).build();
    }

    @Test
    void givenInitMower_whenInitiateOrder_thenMowerMoveSuccessfully() {
        //GIVEN
//        Mockito.when(this.mower.getPosX()).thenReturn(1);
//        Mockito.when(this.mower.getPosY()).thenReturn(2);
//        Mockito.when(this.mower.getOrientation()).thenReturn("N");
//        Mockito.when(this.mower.getOrders()).thenReturn(orders);

        //WHEN
        Assertions.assertDoesNotThrow(() -> this.mower.initiateOrder(5, 5));

        //THEN
        Assertions.assertEquals(this.mower.getOrders(), new ArrayList<>());
        Assertions.assertEquals(2, this.mower.getPosX());
        Assertions.assertEquals(3, this.mower.getPosY());
        Assertions.assertEquals("E", this.mower.getOrientation().name());

    }


    @Test
    void givenInitMower_whenGetPosX_thenGetPosXSuccessfully() {
        //GIVEN

        //WHEN
        int posX = this.mower.getPosX();

        //THEN
        Assertions.assertEquals(1, posX);

    }

    @Test
    void givenInitMower_whenGetPosY_thenGetPosYSuccessfully() {
        //GIVEN

        //WHEN
        int posY = this.mower.getPosY();

        //THEN
        Assertions.assertEquals(2, posY);

    }

    @Test
    void givenInitMower_whenGetOrientation_thenGetOrientationSuccessfully() {
        //GIVEN

        //WHEN
        Orientation orientation = this.mower.getOrientation();

        //THEN
        Assertions.assertEquals("N", orientation.name());

    }

    @Test
    void givenInitMower_whenGetOrders_thenGetOrdersSuccessfully() {
        //GIVEN

        //WHEN
        List<Order> orders = this.mower.getOrders();

        //THEN
        List<Order> expectedOrders = Arrays.stream("GAGAGAGAADDDDDA".split("")).map(Order::valueOf).collect(Collectors.toList());
        Assertions.assertEquals(expectedOrders, orders);

    }

    @Test
    void givenInitMower_whenGetId_thenGetIdSuccessfully() {
        //GIVEN

        //WHEN
        UUID id = this.mower.getId();

        //THEN
        Assertions.assertEquals(this.id, id);

    }

    @Test
    void givenInitMower_whenSetPosX_thenSetPosXSuccessfully() {
        //GIVEN

        //WHEN
        this.mower.setPosX(2);

        //THEN
        Assertions.assertEquals(2, this.mower.getPosX());

    }

    @Test
    void givenInitMower_whenSetPosY_thenSetPosYSuccessfully() {
        //GIVEN

        //WHEN
        this.mower.setPosY(1);

        //THEN
        Assertions.assertEquals(1, this.mower.getPosY());

    }

    @Test
    void givenInitMower_whenSetOrientation_thenSetOrientationSuccessfully() {
        //GIVEN

        //WHEN
        this.mower.setOrientation(Orientation.S);

        //THEN
        Assertions.assertEquals(Orientation.S, this.mower.getOrientation());

    }

    @Test
    void givenInitMower_whenSetOrders_thenSetOrdersSuccessfully() {
        //GIVEN
        List<Order> expectedOrders = Arrays.stream("GA".split("")).map(Order::valueOf).toList();

        //WHEN
        this.mower.setOrders(expectedOrders);

        //THEN
        Assertions.assertEquals(expectedOrders, this.mower.getOrders());

    }

    @Test
    void testBuilder() {
        //GIVEN
        final UUID id = UUID.randomUUID();
        List<Order> orders = new ArrayList<>();
        orders.add(Order.A);
        //WHEN
        Mower mower = Mower.builder().id(id).posY(1).posX(3).orientation(Orientation.S).orders(orders).build();

        //THEN
        Assertions.assertEquals(3, mower.getPosX());
        Assertions.assertEquals(1, mower.getPosY());
        Assertions.assertEquals(Orientation.S, mower.getOrientation());
        Assertions.assertEquals(orders, mower.getOrders());
    }

    @Test
    void testToString() {
        //GIVEN

        //WHEN
        String mowerString = this.mower.toString();

        //THEN
        Assertions.assertEquals("Mower(posX=" + this.mower.getPosX() + ", posY=" + this.mower.getPosY() + ", orientation=" + this.mower.getOrientation().name() + ", orders=" + this.mower.getOrders() + ", id=" + this.mower.getId() + ")", mowerString);
    }
}