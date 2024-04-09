package test.projet.tondeuse.job.listener;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Listener for mower job.
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@Component
@Slf4j
public class MowerListener implements JobExecutionListener, StepExecutionListener {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MowerListener.class);
    /**
     * new line
     */
    private static final String NEW_LINE = "\n";
    /**
     * Separator
     */
    private static final String SEPARATOR_LINE = NEW_LINE + "++++++++++++++++++++++++++++++++++++++++++++++++++";

    /**
     * afterJob method to log a report of the launched job.
     *
     * @param jobExecution job execution.
     */
    @Override
    public void afterJob(final JobExecution jobExecution) {
        final StringBuilder jobReport = new StringBuilder();
        for (final StepExecution stepExecution : jobExecution.getStepExecutions()) {
            jobReport.append(this.logStep(stepExecution));
        }
        jobReport.append(SEPARATOR_LINE + NEW_LINE);
        jobReport.append("  Report for  :").append(jobExecution.getJobInstance().getJobName()).append(NEW_LINE);
        jobReport.append("  Started     :").append(jobExecution.getStartTime()).append(NEW_LINE);
        jobReport.append("  Finished    :").append(jobExecution.getEndTime()).append(NEW_LINE);
        jobReport.append("  Exit-Code   :").append(jobExecution.getExitStatus().getExitCode()).append(NEW_LINE);
        jobReport.append("  Exit-Descr. :").append(jobExecution.getExitStatus().getExitDescription()).append(NEW_LINE);
        jobReport.append("  Status      :").append(jobExecution.getStatus()).append(NEW_LINE);
        jobReport.append("  Duration    :").append(this.logDurationMessage(jobExecution.getEndTime(), jobExecution.getStartTime()));
        jobReport.append(SEPARATOR_LINE + NEW_LINE);
        LOGGER.info("{}", jobReport);

    }

    /**
     * logStep method that create a report of a step.
     *
     * @param stepExecution step execution of the job.
     * @return a string of a report of a step
     */
    private String logStep(final StepExecution stepExecution) {
        return SEPARATOR_LINE + NEW_LINE +
                "  Step [" + stepExecution.getStepName() + "]" + NEW_LINE +
                "  Read Count   :" + stepExecution.getReadCount() + NEW_LINE +
                "  Write Count  :" + stepExecution.getWriteCount() + NEW_LINE +
                "  Skip Count   :" + stepExecution.getSkipCount() + NEW_LINE +
                "  Duration     :" + this.logDurationMessage(stepExecution.getEndTime(), stepExecution.getStartTime());
    }

    /**
     * method that calculate the duration of a step or a job between its start to its end.
     *
     * @param endTime   end time of the step or job
     * @param startTime start time of the step or job
     * @return a string of the duration.
     */
    private String logDurationMessage(final LocalDateTime endTime, final LocalDateTime startTime) {
        if (endTime != null && startTime != null) {
            final long duration = startTime.until(endTime, ChronoUnit.MINUTES);
            return DurationFormatUtils.formatDuration(duration, "HH:mm:ss", true) + NEW_LINE;
        }
        return "";
    }

    /**
     * beforeJob method that log a message to notice the user the job has started.
     *
     * @param jobExecution job execution.
     */
    @Override
    public void beforeJob(final JobExecution jobExecution) {
        LOGGER.info(SEPARATOR_LINE);
        LOGGER.info("{} STARTING ...", jobExecution.getJobInstance().getJobName());
    }

    /**
     * afterStep method that log a message to make a report of a step to the user.
     *
     * @param stepExecution step execution of the job
     * @return a {@link ExitStatus} of the step.
     */
    @Override
    public ExitStatus afterStep(final @NonNull StepExecution stepExecution) {
        LOGGER.info("{}", this.logStep(stepExecution));
        return stepExecution.getExitStatus();
    }

    /**
     * @param stepExecution step execution of the job.
     * @see StepExecutionListener#beforeStep(StepExecution)
     */
    @Override
    public void beforeStep(final @NonNull StepExecution stepExecution) {
        //nothing to do
    }

}
