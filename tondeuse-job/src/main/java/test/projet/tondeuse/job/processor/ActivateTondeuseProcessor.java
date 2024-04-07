package test.projet.tondeuse.job.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import test.projet.tondeuse.job.model.Tondeuse;

@Slf4j
@StepScope
public class ActivateTondeuseProcessor implements ItemProcessor<Tondeuse, Tondeuse> {

    private StepExecution stepExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public Tondeuse process(Tondeuse item) throws Exception {
        String pelouseSize = (String) stepExecution.getExecutionContext().get("pelouseSize");
        String[] XYPelouse = pelouseSize.split(" ");
        int maxSizeX = Integer.parseInt(XYPelouse[0]);
        int maxSizeY = Integer.parseInt(XYPelouse[1]);
        item.initiateOrder(maxSizeX, maxSizeY);
        return item;

    }


}
