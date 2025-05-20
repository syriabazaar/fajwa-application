package ae.gov.dubaipolice.fajwa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StandarditemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Standarditem getStandarditemSample1() {
        return new Standarditem().id(1L).name("name1");
    }

    public static Standarditem getStandarditemSample2() {
        return new Standarditem().id(2L).name("name2");
    }

    public static Standarditem getStandarditemRandomSampleGenerator() {
        return new Standarditem().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
