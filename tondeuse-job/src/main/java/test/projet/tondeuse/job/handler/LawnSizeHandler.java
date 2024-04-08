package test.projet.tondeuse.job.handler;

import lombok.NonNull;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.LineCallbackHandler;

public class LawnSizeHandler implements StepExecutionListener, LineCallbackHandler {

    private StepExecution stepExecution;

    @Override
    public void handleLine(@NonNull String headerLine) {
        stepExecution.getExecutionContext().put("lawnSize", headerLine);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return stepExecution.getExitStatus();
    }

    @Override
    public void beforeStep(@NonNull StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
