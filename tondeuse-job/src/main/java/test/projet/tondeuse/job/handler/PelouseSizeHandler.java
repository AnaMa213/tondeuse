package test.projet.tondeuse.job.handler;

import lombok.NonNull;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.LineCallbackHandler;

public class PelouseSizeHandler implements StepExecutionListener,LineCallbackHandler {

    public StepExecution stepExecution;
    @Override
    public void handleLine(@NonNull String headerLine) {
        stepExecution.getExecutionContext().put("pelouseSize",headerLine);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        // TODO Auto-generated method stub
        return  stepExecution.getExitStatus();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // TODO Auto-generated method stub
        this.stepExecution = stepExecution;
    }
}
