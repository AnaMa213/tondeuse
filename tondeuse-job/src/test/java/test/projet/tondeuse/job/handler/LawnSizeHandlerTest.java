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

@ExtendWith(MockitoExtension.class)
class LawnSizeHandlerTest {

    @InjectMocks
    private LawnSizeHandler handler;
    @Mock
    private StepExecution stepExecution;
    @Mock
    private ExecutionContext executionContext;

    @Test
    void givenStepExecution_whenHandleLine_thenHandleLineSuccessfully() {
        //GIVEN
        Mockito.when(this.stepExecution.getExecutionContext()).thenReturn(this.executionContext);

        //WHEN
        Assertions.assertDoesNotThrow(() -> this.handler.handleLine("5 5"));

        //THEN
        Mockito.verify(this.executionContext, Mockito.times(1)).put("lawnSize", "5 5");

    }

    @Test
    void givenStepExecution_whenAfterStep_thenAfterStepSuccessfully() {
        //GIVEN
        Mockito.when(this.stepExecution.getExitStatus()).thenReturn(ExitStatus.COMPLETED);
        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.handler.afterStep(this.stepExecution));


    }

    @Test
    void givenStepExecution_whenBeforeStep_thenBeforeStepSuccessfully() {
        //GIVEN

        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.handler.beforeStep(this.stepExecution));


    }
}