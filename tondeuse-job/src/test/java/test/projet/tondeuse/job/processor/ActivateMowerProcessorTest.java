package test.projet.tondeuse.job.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import test.projet.tondeuse.job.model.Mower;

/**
 * Unit case of class {@link ActivateMowerProcessor}
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ActivateMowerProcessorTest {

    /**
     * inject mock ActivateMowerProcessor.
     */
    @InjectMocks
    private ActivateMowerProcessor processor;

    /**
     * mock of step execution.
     */
    @Mock
    private StepExecution stepExecution;
    /**
     * mock of execution context.
     */
    @Mock
    private ExecutionContext executionContext;
    /**
     * mock of mower.
     */
    @Mock
    private Mower mower;

    /**
     * use case of {@link ActivateMowerProcessor#beforeStep(StepExecution)} <p>
     * Given. <p>
     * When use beforeStep <p>
     * Then assert no exceptions thrown.
     */
    @Test
    void givenStepExecution_whenBeforeStep_thenBeforeStepSuccessfully() {
        //GIVEN


        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.processor.beforeStep(this.stepExecution));
    }

    /**
     * use case of {@link ActivateMowerProcessor#process(Mower)} <p>
     * Given execution context and lawnSize context variable. <p>
     * When use process <p>
     * Then assert no exceptions thrown and verify initiateOrder invoked.
     */
    @Test
    void givenStepExecution_whenProcess_thenProcessSuccessfully() {
        //GIVEN
        Mockito.when(this.stepExecution.getExecutionContext()).thenReturn(this.executionContext);
        Mockito.when(this.executionContext.get("lawnSize")).thenReturn("5 5");

        //WHEN
        Assertions.assertDoesNotThrow(() -> this.processor.process(this.mower));

        //THEN
        Mockito.verify(this.mower, Mockito.times(1)).initiateOrder(5, 5);

    }

    /**
     * use case of {@link ActivateMowerProcessor#process(Mower)} <p>
     * Given execution context and no lawnSize context variable. <p>
     * When use process <p>
     * Then assert no exceptions thrown and verify no initiateOrder invoked.
     */
    @Test
    void givenStepExecution_whenProcess_thenProcessFailed() {
        //GIVEN
        Mockito.when(this.stepExecution.getExecutionContext()).thenReturn(this.executionContext);
        //WHEN
        Assertions.assertDoesNotThrow(() -> this.processor.process(this.mower));

        //THEN
        Mockito.verify(this.mower, Mockito.never()).initiateOrder(5, 5);

    }
}