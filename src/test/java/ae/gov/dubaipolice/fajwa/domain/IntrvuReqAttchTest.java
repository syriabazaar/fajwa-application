package ae.gov.dubaipolice.fajwa.domain;

import static ae.gov.dubaipolice.fajwa.domain.EmployeeTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.IntrvuReqAttchTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.StandardReqAttachTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.fajwa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntrvuReqAttchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntrvuReqAttch.class);
        IntrvuReqAttch intrvuReqAttch1 = getIntrvuReqAttchSample1();
        IntrvuReqAttch intrvuReqAttch2 = new IntrvuReqAttch();
        assertThat(intrvuReqAttch1).isNotEqualTo(intrvuReqAttch2);

        intrvuReqAttch2.setId(intrvuReqAttch1.getId());
        assertThat(intrvuReqAttch1).isEqualTo(intrvuReqAttch2);

        intrvuReqAttch2 = getIntrvuReqAttchSample2();
        assertThat(intrvuReqAttch1).isNotEqualTo(intrvuReqAttch2);
    }

    @Test
    void standardReqAttachTest() {
        IntrvuReqAttch intrvuReqAttch = getIntrvuReqAttchRandomSampleGenerator();
        StandardReqAttach standardReqAttachBack = getStandardReqAttachRandomSampleGenerator();

        intrvuReqAttch.setStandardReqAttach(standardReqAttachBack);
        assertThat(intrvuReqAttch.getStandardReqAttach()).isEqualTo(standardReqAttachBack);

        intrvuReqAttch.standardReqAttach(null);
        assertThat(intrvuReqAttch.getStandardReqAttach()).isNull();
    }

    @Test
    void employeeTest() {
        IntrvuReqAttch intrvuReqAttch = getIntrvuReqAttchRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        intrvuReqAttch.setEmployee(employeeBack);
        assertThat(intrvuReqAttch.getEmployee()).isEqualTo(employeeBack);

        intrvuReqAttch.employee(null);
        assertThat(intrvuReqAttch.getEmployee()).isNull();
    }
}
