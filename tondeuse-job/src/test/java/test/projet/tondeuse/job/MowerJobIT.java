package test.projet.tondeuse.job;

import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;
import test.projet.tondeuse.job.handler.LawnSizeHandler;

import java.util.Collection;

/**
 * Integration test  of the {@link MowerJob}.
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBatchTest
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableAutoConfiguration
@ContextConfiguration(classes = {Application.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MowerJobIT {

    /**
     * test output file location.
     */
    @Value("${file.output}")
    private Resource testOutputTxt;


    /**
     * job launcher test.
     */
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    /**
     * job repository test.
     */
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;


    /**
     * method to clean up job repository.
     */
    @After
    public void cleanUp() {
        this.jobRepositoryTestUtils.removeJobExecutions();
    }

    /**
     * set default job parameters.
     *
     * @return default job parameters {@link JobParameters }
     */
    private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        return paramsBuilder.toJobParameters();
    }

    /**
     * Test of class and method : {@link MowerJob#activateJob(JobRepository, Step)}  <p>
     * Given expected output file <p>
     * <p>
     * When launch job.<p>
     * <p>
     * Then assert that good job launched, return COMPLETED exist status and output file is not empty.
     */
    @Test
    public void givenEmptyOutput_whenJobExecuted_thenSuccessAndOutputFill() throws Exception {
        // given
        Resource actualResult = this.testOutputTxt;

        // when
        JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(defaultJobParameters());
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        // then
        Assertions.assertEquals("startMower", actualJobInstance.getJobName());
        Assertions.assertEquals("COMPLETED", actualJobExitStatus.getExitCode());
        byte[] actual = actualResult.getContentAsByteArray();
        Assertions.assertNotNull(actual);
    }

    /**
     * Test of class and method : {@link MowerJob#readAndOrderMowerStep(JobRepository, PlatformTransactionManager, ItemProcessor, FlatFileItemWriter, LawnSizeHandler)} <p>
     * Given expected output file <p>
     * <p>
     * When launch job.<p>
     * <p>
     * Then assert that good job launched, return COMPLETED exist status and output file is not empty.
     */
    @Test
    public void givenEmptyOutput_whenStepReadAndOrderMowerStepExecuted_thenSuccessAndOutputFill() throws Exception {
        // given
        Resource actualResult = this.testOutputTxt;

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep(
                "readAndOrderMowerStep", defaultJobParameters());
        Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        // then
        Assertions.assertEquals(1, actualStepExecutions.size());
        Assertions.assertEquals("COMPLETED", actualJobExitStatus.getExitCode());
        byte[] actual = actualResult.getContentAsByteArray();
        Assertions.assertNotNull(actual);
    }

}

