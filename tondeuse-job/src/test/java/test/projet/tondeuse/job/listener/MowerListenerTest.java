package test.projet.tondeuse.job.listener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

/**
 * Unit case of class {@link MowerListener}
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class MowerListenerTest {
    /**
     * time.
     */
    private final LocalDateTime time = LocalDateTime.now();

    /**
     * inject mock of MowerListener.
     */
    @InjectMocks
    private MowerListener mowerListener;
    /**
     * mock of job execution.
     */
    @Mock
    private JobExecution jobExecution;
    /**
     * mock of step execution.
     */
    @Mock
    private StepExecution stepExecution;
    /**
     * mock of job instance.
     */
    @Mock
    private JobInstance jobInstance;

    /**
     * use case of {@link MowerListener#afterJob(JobExecution)} <p>
     * Given job execution context and variable. <p>
     * When use afterJob <p>
     * Then assert no exceptions thrown.
     */
    @Test
    void givenJobExecutionCreated_whenAfterJob_thenLogSuccessfully() {
        //GIVEN
        Collection<StepExecution> stepExecutions = new HashSet<>();
        stepExecutions.add(this.stepExecution);
        Mockito.when(this.jobExecution.getStepExecutions()).thenReturn(stepExecutions);
        Mockito.when(this.stepExecution.getStepName()).thenReturn("reader");
        Mockito.when(this.stepExecution.getReadCount()).thenReturn((long) 1);
        Mockito.when(this.stepExecution.getWriteCount()).thenReturn((long) 1);
        Mockito.when(this.stepExecution.getSkipCount()).thenReturn((long) 1);
        Mockito.when(this.stepExecution.getEndTime()).thenReturn(time);
        Mockito.when(this.stepExecution.getStartTime()).thenReturn(time);
        Mockito.when(this.jobExecution.getEndTime()).thenReturn(time);
        Mockito.when(this.jobExecution.getStartTime()).thenReturn(time);
        Mockito.when(this.jobExecution.getJobInstance()).thenReturn(this.jobInstance);
        Mockito.when(this.jobInstance.getJobName()).thenReturn("job");
        Mockito.when(this.jobExecution.getExitStatus()).thenReturn(ExitStatus.COMPLETED);
        Mockito.when(this.jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);

        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.mowerListener.afterJob(this.jobExecution));


    }

    /**
     * use case of {@link MowerListener#afterStep(StepExecution)} <p>
     * Given step execution context and variable. <p>
     * When use afterStep <p>
     * Then assert no exceptions thrown.
     */
    @Test
    void givenStepExecutionCreated_whenAfterStep_thenLogSuccessfully() {
        //GIVEN
        Mockito.when(this.stepExecution.getStepName()).thenReturn("reader");
        Mockito.when(this.stepExecution.getReadCount()).thenReturn((long) 1);
        Mockito.when(this.stepExecution.getWriteCount()).thenReturn((long) 1);
        Mockito.when(this.stepExecution.getSkipCount()).thenReturn((long) 1);
        Mockito.when(this.stepExecution.getEndTime()).thenReturn(time);
        Mockito.when(this.stepExecution.getStartTime()).thenReturn(time);

        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.mowerListener.afterStep(this.stepExecution));

    }

    /**
     * use case of {@link MowerListener#beforeJob(JobExecution)} <p>
     * Given job execution context and variable. <p>
     * When use beforeJob <p>
     * Then assert no exceptions thrown.
     */
    @Test
    void givenJobExecutionCreated_whenBeforeJob_thenLogSuccessfully() {
        //GIVEN
        Mockito.when(this.jobExecution.getJobInstance()).thenReturn(this.jobInstance);
        Mockito.when(this.jobInstance.getJobName()).thenReturn("job");

        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.mowerListener.beforeJob(this.jobExecution));


    }

}