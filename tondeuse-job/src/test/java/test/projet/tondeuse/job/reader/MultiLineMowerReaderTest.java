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

@ExtendWith(MockitoExtension.class)
@Slf4j
class MultiLineMowerReaderTest {

    @InjectMocks
    private MultiLineMowerReader reader;

    @Mock
    private FlatFileItemReader<FieldSet> delegate;

    @Mock
    private FieldSet line;

    @Mock
    private ExecutionContext executionContext;


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

    @Test
    void givenDelegateInitiate_whenClose_TheCloseSuccessfully() {
        //GIVEN

        //WHEN
        Assertions.assertDoesNotThrow(() -> this.reader.close());

        //THEN
        Mockito.verify(this.delegate, Mockito.times(1)).close();
    }

    @Test
    void givenExecutionContext_whenOpen_thenOpenSuccessfully() {
        //GIVEN

        //WHEN
        Assertions.assertDoesNotThrow(() -> this.reader.open(this.executionContext));

        //THEN
        Mockito.verify(this.delegate, Mockito.times(1)).open(this.executionContext);
    }

    @Test
    void givenExecutionContext_whenUpdate_thenUpdateSuccessfully() {
        //GIVEN

        //WHEN
        Assertions.assertDoesNotThrow(() -> this.reader.update(this.executionContext));

        //THEN
        Mockito.verify(this.delegate, Mockito.times(1)).update(this.executionContext);
    }

    @Test
    void givenDelegate_WhenSetDelegate_ThenDelegateSettedSuccessfully() {
        //GIVEN

        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.reader.setDelegate(this.delegate));

    }
}