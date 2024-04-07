package test.projet.tondeuse.infrastructure;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner
{
    private static final String SEPARATOR = "\n\n====================================================================================================\n\n";

    public static void main( String[] args )
    {
        if (Application.log.isDebugEnabled()) {
            Application.log.debug("Application started with command-line arguments: {}.", Arrays.toString(args));
        }
        if (Application.log.isInfoEnabled()) {
            Application.log.info("To kill this application, press Ctrl + C.");
        }
    }

    @Value("${build.artifact:?}")
    protected String buildArtefact;
    @Value("${build.group:?}")
    protected String buildGroup;
    @Value("${build.time:?}")
    protected String buildTimestamp;
    @Value("${build.version:?}")
    protected String buildVersion;

    Logger logger() {
        return Application.log;
    }

    @PostConstruct
    public void postConstruct() {
        if (this.logger().isInfoEnabled()) {
            this.logger().info(Application.SEPARATOR + "{}:{} ({}) built on {}" + Application.SEPARATOR, this.buildGroup, this.buildArtefact, this.buildVersion, this.buildTimestamp);
        }
    }
    @Override
    public void run(String... args) throws Exception {

    }
}
