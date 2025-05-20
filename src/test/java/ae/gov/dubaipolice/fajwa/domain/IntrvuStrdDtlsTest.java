package ae.gov.dubaipolice.fajwa.domain;

import static ae.gov.dubaipolice.fajwa.domain.AppointmentTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.IntrvuStrdDtlsTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.StandarditemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.fajwa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntrvuStrdDtlsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntrvuStrdDtls.class);
        IntrvuStrdDtls intrvuStrdDtls1 = getIntrvuStrdDtlsSample1();
        IntrvuStrdDtls intrvuStrdDtls2 = new IntrvuStrdDtls();
        assertThat(intrvuStrdDtls1).isNotEqualTo(intrvuStrdDtls2);

        intrvuStrdDtls2.setId(intrvuStrdDtls1.getId());
        assertThat(intrvuStrdDtls1).isEqualTo(intrvuStrdDtls2);

        intrvuStrdDtls2 = getIntrvuStrdDtlsSample2();
        assertThat(intrvuStrdDtls1).isNotEqualTo(intrvuStrdDtls2);
    }

    @Test
    void standarditemTest() {
        IntrvuStrdDtls intrvuStrdDtls = getIntrvuStrdDtlsRandomSampleGenerator();
        Standarditem standarditemBack = getStandarditemRandomSampleGenerator();

        intrvuStrdDtls.setStandarditem(standarditemBack);
        assertThat(intrvuStrdDtls.getStandarditem()).isEqualTo(standarditemBack);

        intrvuStrdDtls.standarditem(null);
        assertThat(intrvuStrdDtls.getStandarditem()).isNull();
    }

    @Test
    void appointmentTest() {
        IntrvuStrdDtls intrvuStrdDtls = getIntrvuStrdDtlsRandomSampleGenerator();
        Appointment appointmentBack = getAppointmentRandomSampleGenerator();

        intrvuStrdDtls.setAppointment(appointmentBack);
        assertThat(intrvuStrdDtls.getAppointment()).isEqualTo(appointmentBack);

        intrvuStrdDtls.appointment(null);
        assertThat(intrvuStrdDtls.getAppointment()).isNull();
    }
}
