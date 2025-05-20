package ae.gov.dubaipolice.fajwa.domain;

import static ae.gov.dubaipolice.fajwa.domain.AppointmentTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.IntrvuStrdDtlsTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.NominationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.fajwa.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AppointmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appointment.class);
        Appointment appointment1 = getAppointmentSample1();
        Appointment appointment2 = new Appointment();
        assertThat(appointment1).isNotEqualTo(appointment2);

        appointment2.setId(appointment1.getId());
        assertThat(appointment1).isEqualTo(appointment2);

        appointment2 = getAppointmentSample2();
        assertThat(appointment1).isNotEqualTo(appointment2);
    }

    @Test
    void nominationTest() {
        Appointment appointment = getAppointmentRandomSampleGenerator();
        Nomination nominationBack = getNominationRandomSampleGenerator();

        appointment.setNomination(nominationBack);
        assertThat(appointment.getNomination()).isEqualTo(nominationBack);

        appointment.nomination(null);
        assertThat(appointment.getNomination()).isNull();
    }

    @Test
    void intrvuStrdDtlsTest() {
        Appointment appointment = getAppointmentRandomSampleGenerator();
        IntrvuStrdDtls intrvuStrdDtlsBack = getIntrvuStrdDtlsRandomSampleGenerator();

        appointment.addIntrvuStrdDtls(intrvuStrdDtlsBack);
        assertThat(appointment.getIntrvuStrdDtls()).containsOnly(intrvuStrdDtlsBack);
        assertThat(intrvuStrdDtlsBack.getAppointment()).isEqualTo(appointment);

        appointment.removeIntrvuStrdDtls(intrvuStrdDtlsBack);
        assertThat(appointment.getIntrvuStrdDtls()).doesNotContain(intrvuStrdDtlsBack);
        assertThat(intrvuStrdDtlsBack.getAppointment()).isNull();

        appointment.intrvuStrdDtls(new HashSet<>(Set.of(intrvuStrdDtlsBack)));
        assertThat(appointment.getIntrvuStrdDtls()).containsOnly(intrvuStrdDtlsBack);
        assertThat(intrvuStrdDtlsBack.getAppointment()).isEqualTo(appointment);

        appointment.setIntrvuStrdDtls(new HashSet<>());
        assertThat(appointment.getIntrvuStrdDtls()).doesNotContain(intrvuStrdDtlsBack);
        assertThat(intrvuStrdDtlsBack.getAppointment()).isNull();
    }
}
