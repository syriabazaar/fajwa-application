package ae.gov.dubaipolice.fajwa.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class NominationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Nomination getNominationSample1() {
        return new Nomination().id(1L);
    }

    public static Nomination getNominationSample2() {
        return new Nomination().id(2L);
    }

    public static Nomination getNominationRandomSampleGenerator() {
        return new Nomination().id(longCount.incrementAndGet());
    }
}
