package ae.gov.dubaipolice.fajwa.domain;

import static ae.gov.dubaipolice.fajwa.domain.JobDescTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.StandardTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.fajwa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobDescTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobDesc.class);
        JobDesc jobDesc1 = getJobDescSample1();
        JobDesc jobDesc2 = new JobDesc();
        assertThat(jobDesc1).isNotEqualTo(jobDesc2);

        jobDesc2.setId(jobDesc1.getId());
        assertThat(jobDesc1).isEqualTo(jobDesc2);

        jobDesc2 = getJobDescSample2();
        assertThat(jobDesc1).isNotEqualTo(jobDesc2);
    }

    @Test
    void standardTypeTest() {
        JobDesc jobDesc = getJobDescRandomSampleGenerator();
        StandardType standardTypeBack = getStandardTypeRandomSampleGenerator();

        jobDesc.setStandardType(standardTypeBack);
        assertThat(jobDesc.getStandardType()).isEqualTo(standardTypeBack);

        jobDesc.standardType(null);
        assertThat(jobDesc.getStandardType()).isNull();
    }
}
