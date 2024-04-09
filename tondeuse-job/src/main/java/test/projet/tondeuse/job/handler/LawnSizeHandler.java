package test.projet.tondeuse.job.handler;

import lombok.NonNull;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.LineCallbackHandler;

/**
 * Handler to add the lawn's size to our job context.
 * <p>
 * Please see {@link org.springframework.batch.core.StepExecutionListener} and {@link org.springframework.batch.item.file.LineCallbackHandler} to see the implemented class.
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
public class LawnSizeHandler implements StepExecutionListener, LineCallbackHandler {

    /**
     * step execution context to use.
     */
    private StepExecution stepExecution;

    /**
     * Set the lawn's size into the step execution context of the job.
     * The headerline argument must specify an absolute <a href="#{@link}">{@link java.lang.String}</a> and cannot be Null.
     * <p>
     *
     * @param headerLine header skipped line of mower file representing the lawn's size.
     */
    @Override
    public void handleLine(@NonNull String headerLine) {
        // Set context variable lawnSize for processor and writer class of our step.
        stepExecution.getExecutionContext().put("lawnSize", headerLine);
    }

    /**
     * At the end of the step, return its exist status.
     * The stepExecution argument must specify an absolute <a href="#{@link}">{@link org.springframework.batch.core.StepExecution}</a>.
     * <p>
     *
     * @param stepExecution step execution context of the job.
     * @return the step's exist status.
     */
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return stepExecution.getExitStatus();
    }

    /**
     * before each step, initialize its step execution.
     * The stepExecution argument must specify an absolute <a href="#{@link}">{@link org.springframework.batch.core.StepExecution}</a>.
     * <p>
     *
     * @param stepExecution step execution context of the job.
     */
    @Override
    public void beforeStep(@NonNull StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
