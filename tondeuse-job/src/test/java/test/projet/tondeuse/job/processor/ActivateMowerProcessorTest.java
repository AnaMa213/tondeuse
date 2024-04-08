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

@ExtendWith(MockitoExtension.class)
class ActivateMowerProcessorTest {

    @InjectMocks
    private ActivateMowerProcessor processor;

    @Mock
    private StepExecution stepExecution;
    @Mock
    private ExecutionContext executionContext;
    @Mock
    private Mower mower;

    @Test
    void givenStepExecution_whenBeforeStep_thenBeforeStepSuccessfully() {
        //GIVEN


        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.processor.beforeStep(this.stepExecution));
    }

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