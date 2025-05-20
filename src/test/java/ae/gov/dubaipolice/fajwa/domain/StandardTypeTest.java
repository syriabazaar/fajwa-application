package ae.gov.dubaipolice.fajwa.domain;

import static ae.gov.dubaipolice.fajwa.domain.JobDescTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.StandardTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.fajwa.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class StandardTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StandardType.class);
        StandardType standardType1 = getStandardTypeSample1();
        StandardType standardType2 = new StandardType();
        assertThat(standardType1).isNotEqualTo(standardType2);

        standardType2.setId(standardType1.getId());
        assertThat(standardType1).isEqualTo(standardType2);

        standardType2 = getStandardTypeSample2();
        assertThat(standardType1).isNotEqualTo(standardType2);
    }

    @Test
    void jobDescTest() {
        StandardType standardType = getStandardTypeRandomSampleGenerator();
        JobDesc jobDescBack = getJobDescRandomSampleGenerator();

        standardType.addJobDesc(jobDescBack);
        assertThat(standardType.getJobDescs()).containsOnly(jobDescBack);
        assertThat(jobDescBack.getStandardType()).isEqualTo(standardType);

        standardType.removeJobDesc(jobDescBack);
        assertThat(standardType.getJobDescs()).doesNotContain(jobDescBack);
        assertThat(jobDescBack.getStandardType()).isNull();

        standardType.jobDescs(new HashSet<>(Set.of(jobDescBack)));
        assertThat(standardType.getJobDescs()).containsOnly(jobDescBack);
        assertThat(jobDescBack.getStandardType()).isEqualTo(standardType);

        standardType.setJobDescs(new HashSet<>());
        assertThat(standardType.getJobDescs()).doesNotContain(jobDescBack);
        assertThat(jobDescBack.getStandardType()).isNull();
    }
}
