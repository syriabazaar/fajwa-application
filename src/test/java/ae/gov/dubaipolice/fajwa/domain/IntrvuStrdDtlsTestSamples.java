package ae.gov.dubaipolice.fajwa.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class IntrvuStrdDtlsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static IntrvuStrdDtls getIntrvuStrdDtlsSample1() {
        return new IntrvuStrdDtls().id(1L);
    }

    public static IntrvuStrdDtls getIntrvuStrdDtlsSample2() {
        return new IntrvuStrdDtls().id(2L);
    }

    public static IntrvuStrdDtls getIntrvuStrdDtlsRandomSampleGenerator() {
        return new IntrvuStrdDtls().id(longCount.incrementAndGet());
    }
}
