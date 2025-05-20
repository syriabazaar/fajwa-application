package ae.gov.dubaipolice.fajwa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StandardTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static StandardType getStandardTypeSample1() {
        return new StandardType().id(1L).name("name1");
    }

    public static StandardType getStandardTypeSample2() {
        return new StandardType().id(2L).name("name2");
    }

    public static StandardType getStandardTypeRandomSampleGenerator() {
        return new StandardType().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
