package ae.gov.dubaipolice.fajwa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class JobDescTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static JobDesc getJobDescSample1() {
        return new JobDesc().id(1L).jobName("jobName1");
    }

    public static JobDesc getJobDescSample2() {
        return new JobDesc().id(2L).jobName("jobName2");
    }

    public static JobDesc getJobDescRandomSampleGenerator() {
        return new JobDesc().id(longCount.incrementAndGet()).jobName(UUID.randomUUID().toString());
    }
}
