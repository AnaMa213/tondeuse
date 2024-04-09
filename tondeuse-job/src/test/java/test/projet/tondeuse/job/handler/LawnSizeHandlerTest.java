package test.projet.tondeuse.job.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;

/**
 * Unit test of class {@link LawnSizeHandler}.
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class LawnSizeHandlerTest {

    /**
     * Inject handler mocks.
     */
    @InjectMocks
    private LawnSizeHandler handler;
    /**
     * mock of step execution .
     */
    @Mock
    private StepExecution stepExecution;
    /**
     * mock of execution context.
     */
    @Mock
    private ExecutionContext executionContext;

    /**
     * Use case of {@link LawnSizeHandler#handleLine(String)}. <p>
     * Given mock step execution. <p>
     * When handler handleLine with a lawn size. <p>
     * Then verify lawn size variable put in context. <p>
     */
    @Test
    void givenStepExecution_whenHandleLine_thenHandleLineSuccessfully() {
        //GIVEN
        Mockito.when(this.stepExecution.getExecutionContext()).thenReturn(this.executionContext);

        //WHEN
        Assertions.assertDoesNotThrow(() -> this.handler.handleLine("5 5"));

        //THEN
        Mockito.verify(this.executionContext, Mockito.times(1)).put("lawnSize", "5 5");

    }

    /**
     * Use case of {@link LawnSizeHandler#afterStep(StepExecution)}. <p>
     * Given mock step execution. <p>
     * When handler afterStep. <p>
     * Then verify no exceptions thrown. <p>
     */
    @Test
    void givenStepExecution_whenAfterStep_thenAfterStepSuccessfully() {
        //GIVEN
        Mockito.when(this.stepExecution.getExitStatus()).thenReturn(ExitStatus.COMPLETED);
        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.handler.afterStep(this.stepExecution));


    }

    /**
     * Use case of {@link LawnSizeHandler#afterStep(StepExecution)}. <p>
     * Given. <p>
     * When handler beforeStep. <p>
     * Then verify no exceptions thrown. <p>
     */
    @Test
    void givenStepExecution_whenBeforeStep_thenBeforeStepSuccessfully() {
        //GIVEN

        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.handler.beforeStep(this.stepExecution));


    }
}