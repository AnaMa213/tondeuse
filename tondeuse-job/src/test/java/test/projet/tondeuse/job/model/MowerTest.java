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

/**
 * Unit Test of class {@link Mower}.
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class MowerTest {
    /**
     * Mower use for testing.
     */
    private Mower mower;
    /**
     * id of mower.
     */
    private UUID id;

    /**
     * before each use case test. <p>
     * init mower.
     */
    @BeforeEach
    void initMower() {
        this.id = UUID.randomUUID();
        List<Order> orders = Arrays.stream("GAGAGAGAADDDDDA".split("")).map(Order::valueOf).collect(Collectors.toList());
        this.mower = Mower.builder().id(this.id).posX(1).posY(2).orientation(Orientation.N).orders(orders).build();
    }

    /**
     * use case of {@link Mower#initiateOrder(int, int)} <p>
     * Given. <p>
     * When initiate order of mower with lawn size. <p>
     * Then no exceptions thrown and assert all elements of mower equal final position.
     */
    @Test
    void givenInitMower_whenInitiateOrder_thenMowerMoveSuccessfully() {
        //GIVEN

        //WHEN
        Assertions.assertDoesNotThrow(() -> this.mower.initiateOrder(5, 5));

        //THEN
        Assertions.assertEquals(this.mower.getOrders(), new ArrayList<>());
        Assertions.assertEquals(2, this.mower.getPosX());
        Assertions.assertEquals(3, this.mower.getPosY());
        Assertions.assertEquals("E", this.mower.getOrientation().name());

    }

    /**
     * use case of {@link Mower#getPosX()} <p>
     * Given. <p>
     * When get pos x of mower. <p>
     * Then  assert pos x of mower equal posX.
     */
    @Test
    void givenInitMower_whenGetPosX_thenGetPosXSuccessfully() {
        //GIVEN

        //WHEN
        int posX = this.mower.getPosX();

        //THEN
        Assertions.assertEquals(1, posX);

    }

    /**
     * use case of {@link Mower#getPosY()} <p>
     * Given. <p>
     * When get pos y of mower. <p>
     * Then  assert pos y of mower equal posY.
     */
    @Test
    void givenInitMower_whenGetPosY_thenGetPosYSuccessfully() {
        //GIVEN

        //WHEN
        int posY = this.mower.getPosY();

        //THEN
        Assertions.assertEquals(2, posY);

    }

    /**
     * use case of {@link Mower#getOrientation()} <p>
     * Given. <p>
     * When get orientation of mower. <p>
     * Then  assert orientation of mower equal orientation.
     */
    @Test
    void givenInitMower_whenGetOrientation_thenGetOrientationSuccessfully() {
        //GIVEN

        //WHEN
        Orientation orientation = this.mower.getOrientation();

        //THEN
        Assertions.assertEquals("N", orientation.name());

    }

    /**
     * use case of {@link Mower#getOrders()} <p>
     * Given. <p>
     * When get orders of mower. <p>
     * Then  assert orders of mower equal orders.
     */
    @Test
    void givenInitMower_whenGetOrders_thenGetOrdersSuccessfully() {
        //GIVEN

        //WHEN
        List<Order> orders = this.mower.getOrders();

        //THEN
        List<Order> expectedOrders = Arrays.stream("GAGAGAGAADDDDDA".split("")).map(Order::valueOf).collect(Collectors.toList());
        Assertions.assertEquals(expectedOrders, orders);

    }

    /**
     * use case of {@link Mower#getId()} <p>
     * Given. <p>
     * When get id of mower. <p>
     * Then  assert id of mower equal id.
     */
    @Test
    void givenInitMower_whenGetId_thenGetIdSuccessfully() {
        //GIVEN

        //WHEN
        UUID id = this.mower.getId();

        //THEN
        Assertions.assertEquals(this.id, id);

    }

    /**
     * use case of {@link Mower#setPosX(int)} <p>
     * Given. <p>
     * When set pos x of mower. <p>
     * Then  assert pos x of mower equal posX.
     */
    @Test
    void givenInitMower_whenSetPosX_thenSetPosXSuccessfully() {
        //GIVEN

        //WHEN
        this.mower.setPosX(2);

        //THEN
        Assertions.assertEquals(2, this.mower.getPosX());

    }

    /**
     * use case of {@link Mower#setPosY(int)} <p>
     * Given. <p>
     * When set pos y of mower. <p>
     * Then  assert pos y of mower equal posY.
     */
    @Test
    void givenInitMower_whenSetPosY_thenSetPosYSuccessfully() {
        //GIVEN

        //WHEN
        this.mower.setPosY(1);

        //THEN
        Assertions.assertEquals(1, this.mower.getPosY());

    }

    /**
     * use case of {@link Mower#setOrientation(Orientation)} <p>
     * Given. <p>
     * When set orientation of mower. <p>
     * Then  assert orientation  of mower equal orientation.
     */
    @Test
    void givenInitMower_whenSetOrientation_thenSetOrientationSuccessfully() {
        //GIVEN

        //WHEN
        this.mower.setOrientation(Orientation.S);

        //THEN
        Assertions.assertEquals(Orientation.S, this.mower.getOrientation());

    }

    /**
     * use case of {@link Mower#setOrders(List)} <p>
     * Given. <p>
     * When set orders of mower. <p>
     * Then  assert orders of mower equal orders.
     */
    @Test
    void givenInitMower_whenSetOrders_thenSetOrdersSuccessfully() {
        //GIVEN
        List<Order> expectedOrders = Arrays.stream("GA".split("")).map(Order::valueOf).toList();

        //WHEN
        this.mower.setOrders(expectedOrders);

        //THEN
        Assertions.assertEquals(expectedOrders, this.mower.getOrders());

    }

    /**
     * use case of {@link Mower#builder()} <p>
     * Given id and orders. <p>
     * When build Mower. <p>
     * Then  assert all element of mower equal mower elements chosen.
     */
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

    /**
     * use case of {@link Mower#toString()} <p>
     * Given. <p>
     * When put mower to string. <p>
     * Then assert string  equal mower to string.
     */
    @Test
    void testToString() {
        //GIVEN

        //WHEN
        String mowerString = this.mower.toString();

        //THEN
        Assertions.assertEquals("Mower(posX=" + this.mower.getPosX() + ", posY=" + this.mower.getPosY() + ", orientation=" + this.mower.getOrientation().name() + ", orders=" + this.mower.getOrders() + ", id=" + this.mower.getId() + ")", mowerString);
    }
}