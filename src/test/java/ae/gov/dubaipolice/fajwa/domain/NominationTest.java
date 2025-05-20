package ae.gov.dubaipolice.fajwa.domain;

import static ae.gov.dubaipolice.fajwa.domain.AppointmentTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.EmployeeTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.JobVacantTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.NominationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.fajwa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NominationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nomination.class);
        Nomination nomination1 = getNominationSample1();
        Nomination nomination2 = new Nomination();
        assertThat(nomination1).isNotEqualTo(nomination2);

        nomination2.setId(nomination1.getId());
        assertThat(nomination1).isEqualTo(nomination2);

        nomination2 = getNominationSample2();
        assertThat(nomination1).isNotEqualTo(nomination2);
    }

    @Test
    void employeeTest() {
        Nomination nomination = getNominationRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        nomination.setEmployee(employeeBack);
        assertThat(nomination.getEmployee()).isEqualTo(employeeBack);

        nomination.employee(null);
        assertThat(nomination.getEmployee()).isNull();
    }

    @Test
    void jobVacantTest() {
        Nomination nomination = getNominationRandomSampleGenerator();
        JobVacant jobVacantBack = getJobVacantRandomSampleGenerator();

        nomination.setJobVacant(jobVacantBack);
        assertThat(nomination.getJobVacant()).isEqualTo(jobVacantBack);

        nomination.jobVacant(null);
        assertThat(nomination.getJobVacant()).isNull();
    }

    @Test
    void appointmentTest() {
        Nomination nomination = getNominationRandomSampleGenerator();
        Appointment appointmentBack = getAppointmentRandomSampleGenerator();

        nomination.setAppointment(appointmentBack);
        assertThat(nomination.getAppointment()).isEqualTo(appointmentBack);
        assertThat(appointmentBack.getNomination()).isEqualTo(nomination);

        nomination.appointment(null);
        assertThat(nomination.getAppointment()).isNull();
        assertThat(appointmentBack.getNomination()).isNull();
    }
}
