package ae.gov.dubaipolice.fajwa.domain;

import static ae.gov.dubaipolice.fajwa.domain.EmployeeTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.IntrvuReqAttchTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.fajwa.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = getEmployeeSample1();
        Employee employee2 = new Employee();
        assertThat(employee1).isNotEqualTo(employee2);

        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);

        employee2 = getEmployeeSample2();
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    void intrvuReqAttchTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        IntrvuReqAttch intrvuReqAttchBack = getIntrvuReqAttchRandomSampleGenerator();

        employee.addIntrvuReqAttch(intrvuReqAttchBack);
        assertThat(employee.getIntrvuReqAttches()).containsOnly(intrvuReqAttchBack);
        assertThat(intrvuReqAttchBack.getEmployee()).isEqualTo(employee);

        employee.removeIntrvuReqAttch(intrvuReqAttchBack);
        assertThat(employee.getIntrvuReqAttches()).doesNotContain(intrvuReqAttchBack);
        assertThat(intrvuReqAttchBack.getEmployee()).isNull();

        employee.intrvuReqAttches(new HashSet<>(Set.of(intrvuReqAttchBack)));
        assertThat(employee.getIntrvuReqAttches()).containsOnly(intrvuReqAttchBack);
        assertThat(intrvuReqAttchBack.getEmployee()).isEqualTo(employee);

        employee.setIntrvuReqAttches(new HashSet<>());
        assertThat(employee.getIntrvuReqAttches()).doesNotContain(intrvuReqAttchBack);
        assertThat(intrvuReqAttchBack.getEmployee()).isNull();
    }
}
