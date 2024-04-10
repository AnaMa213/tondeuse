package test.projet.tondeuse.job;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import test.projet.tondeuse.job.handler.LawnSizeHandler;

/**
 * Unit case of class {@link MowerJob}
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class MowerJobTest {

    /**
     * inject of MowerJob.
     */
    @InjectMocks
    private MowerJob mowerJob;
    /**
     * mock of LawnSizeHandler.
     */
    @Mock
    private LawnSizeHandler lawnSizeHandler;

    @Value("${file.input}")
    private Resource inputFile;

    @Value("${file.output}")
    private Resource outputFile;

    /**
     * use case of {@link MowerJob#itemReader(Resource, LawnSizeHandler)}  } <p>
     * init item reader.
     */
    @Test
    void init_itemReaderTest() {
        //GIVEN
        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.mowerJob.itemReader(this.inputFile,this.lawnSizeHandler));
    }

    /**
     * use case of {@link MowerJob#itemWriter(Resource)}  } <p>
     * init item writer.
     */
    @Test
    void init_itemWriterTest() {
        //GIVEN

        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.mowerJob.itemWriter(this.outputFile));
    }

    /**
     * use case of {@link MowerJob#lawnSizeHandler() } <p>
     * init handler.
     */
    @Test
    void init_lawnSizeHandlerTest() {
        //GIVEN

        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.mowerJob.lawnSizeHandler());
    }

    /**
     * use case of {@link MowerJob#itemProcessor()}  <p>
     * init item processor.
     */
    @Test
    void init_itemProcessorTest() {
        //GIVEN

        //WHEN / THEN
        Assertions.assertDoesNotThrow(() -> this.mowerJob.itemProcessor());
    }
}