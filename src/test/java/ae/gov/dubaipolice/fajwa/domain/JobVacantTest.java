package ae.gov.dubaipolice.fajwa.domain;

import static ae.gov.dubaipolice.fajwa.domain.DepartmentTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.JobDescTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.JobVacantTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.NominationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.fajwa.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class JobVacantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobVacant.class);
        JobVacant jobVacant1 = getJobVacantSample1();
        JobVacant jobVacant2 = new JobVacant();
        assertThat(jobVacant1).isNotEqualTo(jobVacant2);

        jobVacant2.setId(jobVacant1.getId());
        assertThat(jobVacant1).isEqualTo(jobVacant2);

        jobVacant2 = getJobVacantSample2();
        assertThat(jobVacant1).isNotEqualTo(jobVacant2);
    }

    @Test
    void departmentTest() {
        JobVacant jobVacant = getJobVacantRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        jobVacant.setDepartment(departmentBack);
        assertThat(jobVacant.getDepartment()).isEqualTo(departmentBack);

        jobVacant.department(null);
        assertThat(jobVacant.getDepartment()).isNull();
    }

    @Test
    void jobDescTest() {
        JobVacant jobVacant = getJobVacantRandomSampleGenerator();
        JobDesc jobDescBack = getJobDescRandomSampleGenerator();

        jobVacant.setJobDesc(jobDescBack);
        assertThat(jobVacant.getJobDesc()).isEqualTo(jobDescBack);

        jobVacant.jobDesc(null);
        assertThat(jobVacant.getJobDesc()).isNull();
    }

    @Test
    void nominationTest() {
        JobVacant jobVacant = getJobVacantRandomSampleGenerator();
        Nomination nominationBack = getNominationRandomSampleGenerator();

        jobVacant.addNomination(nominationBack);
        assertThat(jobVacant.getNominations()).containsOnly(nominationBack);
        assertThat(nominationBack.getJobVacant()).isEqualTo(jobVacant);

        jobVacant.removeNomination(nominationBack);
        assertThat(jobVacant.getNominations()).doesNotContain(nominationBack);
        assertThat(nominationBack.getJobVacant()).isNull();

        jobVacant.nominations(new HashSet<>(Set.of(nominationBack)));
        assertThat(jobVacant.getNominations()).containsOnly(nominationBack);
        assertThat(nominationBack.getJobVacant()).isEqualTo(jobVacant);

        jobVacant.setNominations(new HashSet<>());
        assertThat(jobVacant.getNominations()).doesNotContain(nominationBack);
        assertThat(nominationBack.getJobVacant()).isNull();
    }
}
