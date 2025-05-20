package ae.gov.dubaipolice.fajwa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StandardParentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static StandardParent getStandardParentSample1() {
        return new StandardParent().id(1L).name("name1");
    }

    public static StandardParent getStandardParentSample2() {
        return new StandardParent().id(2L).name("name2");
    }

    public static StandardParent getStandardParentRandomSampleGenerator() {
        return new StandardParent().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
