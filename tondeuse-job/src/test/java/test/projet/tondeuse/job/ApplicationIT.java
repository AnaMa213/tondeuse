package test.projet.tondeuse.job;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Integration test of {@link Application}.
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ApplicationIT {
    /**
     * Mock bean of Application.
     */
    @MockBean
    Application application;
    /**
     * example been to check integrity of Application.
     */
    @Autowired
    private Job componentTest;

    /**
     * test integrity of {@link Application}.
     */
    @Test
    void testMain() {
        Assertions.assertNotNull(this.componentTest);
    }
}
