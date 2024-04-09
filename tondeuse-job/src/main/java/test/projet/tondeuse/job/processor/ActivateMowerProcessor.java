package test.projet.tondeuse.job.processor;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import test.projet.tondeuse.job.model.Mower;

/**
 * Processor to initiate the list order of the mower processed on a precise lawn size.
 * <p>
 * Please see {@link org.springframework.batch.item.ItemProcessor}  to see the implemented class.
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@Slf4j
@StepScope
public class ActivateMowerProcessor implements ItemProcessor<Mower, Mower> {

    /**
     * step execution context of our job.
     */
    private StepExecution stepExecution;

    /**
     * Init the step execution context.
     *
     * @param stepExecution step execution context of our job
     */
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    /**
     * process method used to initiate the orders of a mower to make it move.
     *
     * @param item mower to process.
     * @return mower ({@link test.projet.tondeuse.job.model.Mower}) with its final position and orientation on the lawn.
     */
    @Override
    public Mower process(@NonNull Mower item) {
        if (this.stepExecution.getExecutionContext().get("lawnSize") != null) {
            //get lawn size from step context variable.
            String lawnSize = (String) stepExecution.getExecutionContext().get("lawnSize");
            assert lawnSize != null;
            // get the lawn size into good format and separate into a length and width variable.
            String[] XYLawn = lawnSize.split(" ");
            int maxSizeX = Integer.parseInt(XYLawn[0]);
            int maxSizeY = Integer.parseInt(XYLawn[1]);
            //Initiate order of the mower processed
            item.initiateOrder(maxSizeX, maxSizeY);
            return item;
        }
        log.warn("An error has occurred while retrieving the lawn size. The mower won't be able to move.");
        return item;

    }


}
