package test.projet.tondeuse.job;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.ExitCodeMapper;
import org.springframework.batch.core.launch.support.SimpleJvmExitCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Date;

/**
 * Spring boot application for tondeuse test exposition.
 *
 * @author Kenan TERRISSE
 * @version $Id : $Id
 */
@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    private static final String SEPARATOR = "\n\n====================================================================================================\n\n";
    @Value("${build.artifact:?}")
    protected String buildArtefact;
    @Value("${build.group:?}")
    protected String buildGroup;
    @Value("${build.time:?}")
    protected String buildTimestamp;
    @Value("${build.version:?}")
    protected String buildVersion;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job mowerJob;

    public static void main(String[] args) {
        if (Application.log.isDebugEnabled()) {
            Application.log.debug("Application started with command-line arguments: {}.", Arrays.toString(args));
        }
        if (Application.log.isInfoEnabled()) {
            Application.log.info("To kill this application, press Ctrl + C.");
        }
        SpringApplication.run(Application.class, args);
    }

    Logger logger() {
        return Application.log;
    }

    /**
     * display version of artifact in log.
     */
    @PostConstruct
    public void postConstruct() {
        if (this.logger().isInfoEnabled()) {
            this.logger().info(Application.SEPARATOR + "{}:{} ({}) built on {}" + Application.SEPARATOR, this.buildGroup, this.buildArtefact, this.buildVersion, this.buildTimestamp);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        Application.log.info("#### Run Mower Job at {} ####", new Date());
        final ExitCodeMapper exitCodeMapper = new SimpleJvmExitCodeMapper();
        final JobExecution jobExecution = this.jobLauncher.run(this.mowerJob, new JobParameters());
        System.exit(exitCodeMapper.intValue(jobExecution.getExitStatus().getExitCode()));
    }
}
