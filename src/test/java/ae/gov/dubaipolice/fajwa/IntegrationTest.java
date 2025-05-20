package ae.gov.dubaipolice.fajwa;

import ae.gov.dubaipolice.fajwa.config.AsyncSyncConfiguration;
import ae.gov.dubaipolice.fajwa.config.EmbeddedSQL;
import ae.gov.dubaipolice.fajwa.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { FajwaApplicationApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedSQL
public @interface IntegrationTest {
}
