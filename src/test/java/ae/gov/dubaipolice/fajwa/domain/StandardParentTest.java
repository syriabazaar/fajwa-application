package ae.gov.dubaipolice.fajwa.domain;

import static ae.gov.dubaipolice.fajwa.domain.StandardParentTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.StandardTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.fajwa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StandardParentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StandardParent.class);
        StandardParent standardParent1 = getStandardParentSample1();
        StandardParent standardParent2 = new StandardParent();
        assertThat(standardParent1).isNotEqualTo(standardParent2);

        standardParent2.setId(standardParent1.getId());
        assertThat(standardParent1).isEqualTo(standardParent2);

        standardParent2 = getStandardParentSample2();
        assertThat(standardParent1).isNotEqualTo(standardParent2);
    }

    @Test
    void standardTypeTest() {
        StandardParent standardParent = getStandardParentRandomSampleGenerator();
        StandardType standardTypeBack = getStandardTypeRandomSampleGenerator();

        standardParent.setStandardType(standardTypeBack);
        assertThat(standardParent.getStandardType()).isEqualTo(standardTypeBack);

        standardParent.standardType(null);
        assertThat(standardParent.getStandardType()).isNull();
    }
}
