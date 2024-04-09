package test.projet.tondeuse.job.reader;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.transform.FieldSet;
import test.projet.tondeuse.job.model.Mower;
import test.projet.tondeuse.job.model.Order;
import test.projet.tondeuse.job.model.Orientation;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit case of class {@link MultiLineMowerReader}
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
class MultiLineMowerReaderTest {

    /**
     * Inject mock of MultiLineMowerReader
     */
    @InjectMocks
    private MultiLineMowerReader reader;

    /**
     * mock of FlatFileItemReader.
     */
    @Mock
    private FlatFileItemReader<FieldSet> delegate;

    /**
     * mock of fieldSet.
     */
    @Mock
    private FieldSet line;

    /**
     * mock of execution context.
     */
    @Mock
    private ExecutionContext executionContext;

    /**
     * use case of {@link MultiLineMowerReader#read()} <p>
     * Given. mock input file line <p>
     * When use read <p>
     * Then assert line read create a mower with good elements.
     */
    @Test
    void givenOneLine_whenRead_ThenReturnMower() throws Exception {
        //GIVEN
        Mockito.when(this.delegate.read()).thenReturn(this.line);
        Mockito.when(this.line.readInt(0)).thenReturn(1);
        Mockito.when(this.line.readInt(1)).thenReturn(3);
        Mockito.when(this.line.readString(2)).thenReturn("E");
        Mockito.when(this.line.readString(0)).thenReturn("G");

        //WHEN
        Mower actualMower = this.reader.read();

        //THEN
        Mower expectedMower = new Mower();
        expectedMower.setPosX(1);
        expectedMower.setPosY(3);
        expectedMower.setOrientation(Orientation.E);
        List<Order> orders = new ArrayList<>();
        orders.add(Order.G);
        expectedMower.setOrders(orders);

        assert actualMower != null;

        Assertions.assertEquals(actualMower.getPosX(), expectedMower.getPosX());
        Assertions.assertEquals(actualMower.getPosY(), expectedMower.getPosY());
        Assertions.assertEquals(actualMower.getOrientation(), expectedMower.getOrientation());
        Assertions.assertEquals(actualMower.getOrders(), expectedMower.getOrders());


    }

    /**
     * use case of {@link MultiLineMowerReader#close()} <p>
     * Given.  <p>
     * When use close <p>
     * Then assert no exceptions thrown and delegate close invoked.
     */
    @Test
    void givenDelegateInitiate_whenClose_TheCloseSuccessfully() {
        //GIVEN

        //WHEN
        Assertions.assertDoesNotThrow(() -> this.reader.close());

        //THEN
        Mockito.verify(this.delegate, Mockito.times(1)).close();
    }

    /**
     * use case of {@link MultiLineMowerReader#open(ExecutionContext)} <p>
     * Given execution context.  <p>
     * When use open <p>
     * Then assert no exceptions thrown and delegate open invoked.
     */
    @Test
    void givenExecutionContext_whenOpen_thenOpenSuccessfully() {
        //GIVEN

        //WHEN
        Assertions.assertDoesNotThrow(() -> this.reader.open(this.executionContext));

        //THEN
        Mockito.verify(this.delegate, Mockito.times(1)).open(this.executionContext);
    }

    /**
     * use case of {@link MultiLineMowerReader#update(ExecutionContext)} <p>
     * Given.  <p>
     * When use update <p>
     * Then assert no exceptions thrown and delegate open invoked.
     */
    @Test
    void givenExecutionContext_whenUpdate_thenUpdateSuccessfully() {
        //GIVEN

        //WHEN
        Assertions.assertDoesNotThrow(() -> this.reader.update(this.executionContext));

        //THEN
        Mockito.verify(this.delegate, Mockito.times(1)).update(this.executionContext);
    }

    /**
     * use case of {@link MultiLineMowerReader#setDelegate(FlatFileItemReader)} )} <p>
     * Given.  <p>
     * When use setDelegate <p>
     * Then assert no exceptions thrown.
     */
    @Test
    void givenDelegate_WhenSetDelegate_ThenDelegateSettedSuccessfully() {
        //GIVEN

        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.reader.setDelegate(this.delegate));

    }
}