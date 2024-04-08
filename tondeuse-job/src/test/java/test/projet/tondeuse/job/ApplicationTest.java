package test.projet.tondeuse.job;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Unit test for simple App.
 */
@ExtendWith(SpringExtension.class)
class ApplicationTest {
    private Application application;
    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        this.application = Mockito.spy(new Application());
        Mockito.when(this.application.logger()).thenReturn(this.logger);
    }

    @Test
    void testPostConstructWhenInfoDisabled() {
        Mockito.when(this.logger.isInfoEnabled()).thenReturn(false);
        this.application.postConstruct();
        Mockito.verify(this.logger).isInfoEnabled();
    }

    @Test
    void testPostConstructWhenInfoEnabled() {
        //GIVEN
        final String group = "group";
        final String artefact = "artefact";
        final String version = "version";
        final String buildTime = "buildTime";
        this.application.buildGroup = group;
        this.application.buildArtefact = artefact;
        this.application.buildVersion = version;
        this.application.buildTimestamp = buildTime;
        Mockito.when(this.logger.isInfoEnabled()).thenReturn(true);
        final String separator = "\n\n====================================================================================================\n\n";
        //THEN
        this.application.postConstruct();
        //WHEN
        Mockito.verify(this.logger).isInfoEnabled();
        Mockito.verify(this.logger).info(separator + "{}:{} ({}) built on {}" + separator, group, artefact, version, buildTime);
    }
}
