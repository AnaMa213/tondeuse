package test.projet.tondeuse.job;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.ExitCodeMapper;
import org.springframework.batch.core.launch.support.SimpleJvmExitCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

/**
 * Spring boot application for tondeuse test exposition.
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    private static final String SEPARATOR = "\n\n====================================================================================================\n\n";
    /**
     * build artifact id.
     */
    @Value("${build.artifact:?}")
    protected String buildArtefact;
    /**
     * build group id.
     */
    @Value("${build.group:?}")
    protected String buildGroup;
    /**
     * build timestamp.
     */
    @Value("${build.time:?}")
    protected String buildTimestamp;
    /**
     * build version.
     */
    @Value("${build.version:?}")
    protected String buildVersion;
    /**
     * job launcher to run application spring batch job.
     */
    @Autowired
    private JobLauncher jobLauncher;
    /**
     * Spring batch job.
     */
    @Autowired
    private Job mowerJob;

    /**
     * Application main method. run the mower job.
     *
     * @param args arguments to use on main method.
     */
    public static void main(String[] args) {
        if (Application.log.isDebugEnabled()) {
            Application.log.debug("Application started with command-line arguments: {}.", Arrays.toString(args));
        }
        if (Application.log.isInfoEnabled()) {
            Application.log.info("To kill this application, press Ctrl + C.");
        }
        SpringApplication.run(Application.class, args);
    }

    /**
     * method to get logger of the application.
     */
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

    /**
     * run method to run the mower job via the job launcher.
     *
     * @param args arguments passed by the main method.
     */
    @Override
    public void run(String... args) throws Exception {
        String inputFile = "input/tondeuse_instruction.txt";
        for (String arg : args) {
            if (arg.startsWith("--inputfile=")) {
                inputFile = arg.substring(12);
            }
        }
        final ExitCodeMapper exitCodeMapper = new SimpleJvmExitCodeMapper();
        final JobParameters jp = new JobParametersBuilder().addString("inputFile", inputFile).toJobParameters();
        final JobExecution jobExecution = this.jobLauncher.run(this.mowerJob, jp);
        System.exit(exitCodeMapper.intValue(jobExecution.getExitStatus().getExitCode()));
    }
}
