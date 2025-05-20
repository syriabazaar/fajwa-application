package ae.gov.dubaipolice.fajwa.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class JobVacantTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static JobVacant getJobVacantSample1() {
        return new JobVacant().id(1L);
    }

    public static JobVacant getJobVacantSample2() {
        return new JobVacant().id(2L);
    }

    public static JobVacant getJobVacantRandomSampleGenerator() {
        return new JobVacant().id(longCount.incrementAndGet());
    }
}
