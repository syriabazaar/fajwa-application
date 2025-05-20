package ae.gov.dubaipolice.fajwa.domain;

import static ae.gov.dubaipolice.fajwa.domain.StandardCatTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.StandardParentTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.StandardReqAttachTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.fajwa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StandardCatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StandardCat.class);
        StandardCat standardCat1 = getStandardCatSample1();
        StandardCat standardCat2 = new StandardCat();
        assertThat(standardCat1).isNotEqualTo(standardCat2);

        standardCat2.setId(standardCat1.getId());
        assertThat(standardCat1).isEqualTo(standardCat2);

        standardCat2 = getStandardCatSample2();
        assertThat(standardCat1).isNotEqualTo(standardCat2);
    }

    @Test
    void standardParentTest() {
        StandardCat standardCat = getStandardCatRandomSampleGenerator();
        StandardParent standardParentBack = getStandardParentRandomSampleGenerator();

        standardCat.setStandardParent(standardParentBack);
        assertThat(standardCat.getStandardParent()).isEqualTo(standardParentBack);

        standardCat.standardParent(null);
        assertThat(standardCat.getStandardParent()).isNull();
    }

    @Test
    void reqAttachmentTest() {
        StandardCat standardCat = getStandardCatRandomSampleGenerator();
        StandardReqAttach standardReqAttachBack = getStandardReqAttachRandomSampleGenerator();

        standardCat.setReqAttachment(standardReqAttachBack);
        assertThat(standardCat.getReqAttachment()).isEqualTo(standardReqAttachBack);

        standardCat.reqAttachment(null);
        assertThat(standardCat.getReqAttachment()).isNull();
    }
}
