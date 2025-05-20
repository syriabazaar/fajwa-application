package ae.gov.dubaipolice.fajwa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EmployeeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Employee getEmployeeSample1() {
        return new Employee()
            .id(1L)
            .fullname("fullname1")
            .gradeId(1L)
            .gradeName("gradeName1")
            .homePhone("homePhone1")
            .mobileNumber("mobileNumber1")
            .assigmentId(1L)
            .dateOfAssignment("dateOfAssignment1")
            .jobName("jobName1")
            .address("address1")
            .organizationId(1L)
            .organization("organization1")
            .nationalIdentifier("nationalIdentifier1")
            .uid("uid1")
            .parentDepartmentId("parentDepartmentId1")
            .slmMilitaryFlag(1)
            .militaryFirstName("militaryFirstName1")
            .slmName("slmName1")
            .age(1)
            .gender("gender1")
            .jobId(1L)
            .nationalityCode("nationalityCode1")
            .nationality("nationality1")
            .supervisorId("supervisorId1")
            .supervisorFullName("supervisorFullName1")
            .parentDepartmentName("parentDepartmentName1")
            .sectionId(1L)
            .sectionName("sectionName1")
            .qualificationType("qualificationType1")
            .qualificationSpecification("qualificationSpecification1");
    }

    public static Employee getEmployeeSample2() {
        return new Employee()
            .id(2L)
            .fullname("fullname2")
            .gradeId(2L)
            .gradeName("gradeName2")
            .homePhone("homePhone2")
            .mobileNumber("mobileNumber2")
            .assigmentId(2L)
            .dateOfAssignment("dateOfAssignment2")
            .jobName("jobName2")
            .address("address2")
            .organizationId(2L)
            .organization("organization2")
            .nationalIdentifier("nationalIdentifier2")
            .uid("uid2")
            .parentDepartmentId("parentDepartmentId2")
            .slmMilitaryFlag(2)
            .militaryFirstName("militaryFirstName2")
            .slmName("slmName2")
            .age(2)
            .gender("gender2")
            .jobId(2L)
            .nationalityCode("nationalityCode2")
            .nationality("nationality2")
            .supervisorId("supervisorId2")
            .supervisorFullName("supervisorFullName2")
            .parentDepartmentName("parentDepartmentName2")
            .sectionId(2L)
            .sectionName("sectionName2")
            .qualificationType("qualificationType2")
            .qualificationSpecification("qualificationSpecification2");
    }

    public static Employee getEmployeeRandomSampleGenerator() {
        return new Employee()
            .id(longCount.incrementAndGet())
            .fullname(UUID.randomUUID().toString())
            .gradeId(longCount.incrementAndGet())
            .gradeName(UUID.randomUUID().toString())
            .homePhone(UUID.randomUUID().toString())
            .mobileNumber(UUID.randomUUID().toString())
            .assigmentId(longCount.incrementAndGet())
            .dateOfAssignment(UUID.randomUUID().toString())
            .jobName(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .organizationId(longCount.incrementAndGet())
            .organization(UUID.randomUUID().toString())
            .nationalIdentifier(UUID.randomUUID().toString())
            .uid(UUID.randomUUID().toString())
            .parentDepartmentId(UUID.randomUUID().toString())
            .slmMilitaryFlag(intCount.incrementAndGet())
            .militaryFirstName(UUID.randomUUID().toString())
            .slmName(UUID.randomUUID().toString())
            .age(intCount.incrementAndGet())
            .gender(UUID.randomUUID().toString())
            .jobId(longCount.incrementAndGet())
            .nationalityCode(UUID.randomUUID().toString())
            .nationality(UUID.randomUUID().toString())
            .supervisorId(UUID.randomUUID().toString())
            .supervisorFullName(UUID.randomUUID().toString())
            .parentDepartmentName(UUID.randomUUID().toString())
            .sectionId(longCount.incrementAndGet())
            .sectionName(UUID.randomUUID().toString())
            .qualificationType(UUID.randomUUID().toString())
            .qualificationSpecification(UUID.randomUUID().toString());
    }
}
