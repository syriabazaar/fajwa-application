package ae.gov.dubaipolice.fajwa.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class IntrvuReqAttchTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static IntrvuReqAttch getIntrvuReqAttchSample1() {
        return new IntrvuReqAttch().id(1L);
    }

    public static IntrvuReqAttch getIntrvuReqAttchSample2() {
        return new IntrvuReqAttch().id(2L);
    }

    public static IntrvuReqAttch getIntrvuReqAttchRandomSampleGenerator() {
        return new IntrvuReqAttch().id(longCount.incrementAndGet());
    }
}
