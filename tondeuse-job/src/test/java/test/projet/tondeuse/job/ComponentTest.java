package test.projet.tondeuse.job;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class ComponentTest {
    public boolean isComponentOrNot() {
        return true;
    }
}
