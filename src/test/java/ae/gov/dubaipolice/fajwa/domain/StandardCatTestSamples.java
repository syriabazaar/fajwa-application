package ae.gov.dubaipolice.fajwa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StandardCatTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static StandardCat getStandardCatSample1() {
        return new StandardCat().id(1L).name("name1");
    }

    public static StandardCat getStandardCatSample2() {
        return new StandardCat().id(2L).name("name2");
    }

    public static StandardCat getStandardCatRandomSampleGenerator() {
        return new StandardCat().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
