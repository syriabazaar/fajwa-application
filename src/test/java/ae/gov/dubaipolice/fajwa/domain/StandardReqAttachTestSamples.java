package ae.gov.dubaipolice.fajwa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StandardReqAttachTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static StandardReqAttach getStandardReqAttachSample1() {
        return new StandardReqAttach().id(1L).name("name1").attDesc("attDesc1");
    }

    public static StandardReqAttach getStandardReqAttachSample2() {
        return new StandardReqAttach().id(2L).name("name2").attDesc("attDesc2");
    }

    public static StandardReqAttach getStandardReqAttachRandomSampleGenerator() {
        return new StandardReqAttach()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .attDesc(UUID.randomUUID().toString());
    }
}
