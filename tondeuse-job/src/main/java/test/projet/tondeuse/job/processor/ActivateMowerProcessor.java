package test.projet.tondeuse.job.processor;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import test.projet.tondeuse.job.model.Mower;

@Slf4j
@StepScope
public class ActivateMowerProcessor implements ItemProcessor<Mower, Mower> {

    private StepExecution stepExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public Mower process(@NonNull Mower item) throws Exception {
        if (this.stepExecution.getExecutionContext().get("lawnSize") != null) {
            String lawnSize = (String) stepExecution.getExecutionContext().get("lawnSize");
            assert lawnSize != null;
            String[] XYLawn = lawnSize.split(" ");
            int maxSizeX = Integer.parseInt(XYLawn[0]);
            int maxSizeY = Integer.parseInt(XYLawn[1]);
            item.initiateOrder(maxSizeX, maxSizeY);
            return item;
        }
        log.warn("An error has occurred while retrieving the lawn size. The mower won't be able to move.");
        return item;

    }


}
