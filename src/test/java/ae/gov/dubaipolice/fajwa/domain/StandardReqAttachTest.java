package ae.gov.dubaipolice.fajwa.domain;

import static ae.gov.dubaipolice.fajwa.domain.StandardReqAttachTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.fajwa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StandardReqAttachTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StandardReqAttach.class);
        StandardReqAttach standardReqAttach1 = getStandardReqAttachSample1();
        StandardReqAttach standardReqAttach2 = new StandardReqAttach();
        assertThat(standardReqAttach1).isNotEqualTo(standardReqAttach2);

        standardReqAttach2.setId(standardReqAttach1.getId());
        assertThat(standardReqAttach1).isEqualTo(standardReqAttach2);

        standardReqAttach2 = getStandardReqAttachSample2();
        assertThat(standardReqAttach1).isNotEqualTo(standardReqAttach2);
    }
}
