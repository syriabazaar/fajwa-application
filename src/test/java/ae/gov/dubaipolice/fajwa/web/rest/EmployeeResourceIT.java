package ae.gov.dubaipolice.fajwa.web.rest;

import static ae.gov.dubaipolice.fajwa.domain.EmployeeAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.fajwa.IntegrationTest;
import ae.gov.dubaipolice.fajwa.domain.Employee;
import ae.gov.dubaipolice.fajwa.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeResourceIT {

    private static final String DEFAULT_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_FULLNAME = "BBBBBBBBBB";

    private static final Long DEFAULT_GRADE_ID = 1L;
    private static final Long UPDATED_GRADE_ID = 2L;
    private static final Long SMALLER_GRADE_ID = 1L - 1L;

    private static final String DEFAULT_GRADE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GRADE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_HOME_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_ASSIGMENT_ID = 1L;
    private static final Long UPDATED_ASSIGMENT_ID = 2L;
    private static final Long SMALLER_ASSIGMENT_ID = 1L - 1L;

    private static final String DEFAULT_DATE_OF_ASSIGNMENT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_ASSIGNMENT = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_JOB_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_ORGANIZATION_ID = 1L;
    private static final Long UPDATED_ORGANIZATION_ID = 2L;
    private static final Long SMALLER_ORGANIZATION_ID = 1L - 1L;

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONAL_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_UID = "AAAAAAAAAA";
    private static final String UPDATED_UID = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_DEPARTMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_DEPARTMENT_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_SLM_MILITARY_FLAG = 1;
    private static final Integer UPDATED_SLM_MILITARY_FLAG = 2;
    private static final Integer SMALLER_SLM_MILITARY_FLAG = 1 - 1;

    private static final String DEFAULT_MILITARY_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MILITARY_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SLM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SLM_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final Integer SMALLER_AGE = 1 - 1;

    private static final Instant DEFAULT_DATE_OF_HIRE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_HIRE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_OF_LAST_HIRE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_LAST_HIRE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final Long DEFAULT_JOB_ID = 1L;
    private static final Long UPDATED_JOB_ID = 2L;
    private static final Long SMALLER_JOB_ID = 1L - 1L;

    private static final String DEFAULT_NATIONALITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final Instant DEFAULT_PERSON_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PERSON_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ORIGINAL_DATE_OF_HIRE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORIGINAL_DATE_OF_HIRE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SUPERVISOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_SUPERVISOR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SUPERVISOR_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPERVISOR_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_DEPARTMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_DEPARTMENT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_SECTION_ID = 1L;
    private static final Long UPDATED_SECTION_ID = 2L;
    private static final Long SMALLER_SECTION_ID = 1L - 1L;

    private static final String DEFAULT_SECTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SECTION_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_ADJUSTED_SERVICE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADJUSTED_SERVICE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_QUALIFICATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_QUALIFICATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_QUALIFICATION_SPECIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_QUALIFICATION_SPECIFICATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    private Employee insertedEmployee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity() {
        return new Employee()
            .fullname(DEFAULT_FULLNAME)
            .gradeId(DEFAULT_GRADE_ID)
            .gradeName(DEFAULT_GRADE_NAME)
            .homePhone(DEFAULT_HOME_PHONE)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .assigmentId(DEFAULT_ASSIGMENT_ID)
            .dateOfAssignment(DEFAULT_DATE_OF_ASSIGNMENT)
            .jobName(DEFAULT_JOB_NAME)
            .address(DEFAULT_ADDRESS)
            .organizationId(DEFAULT_ORGANIZATION_ID)
            .organization(DEFAULT_ORGANIZATION)
            .nationalIdentifier(DEFAULT_NATIONAL_IDENTIFIER)
            .uid(DEFAULT_UID)
            .parentDepartmentId(DEFAULT_PARENT_DEPARTMENT_ID)
            .slmMilitaryFlag(DEFAULT_SLM_MILITARY_FLAG)
            .militaryFirstName(DEFAULT_MILITARY_FIRST_NAME)
            .slmName(DEFAULT_SLM_NAME)
            .age(DEFAULT_AGE)
            .dateOfHire(DEFAULT_DATE_OF_HIRE)
            .dateOfLastHire(DEFAULT_DATE_OF_LAST_HIRE)
            .gender(DEFAULT_GENDER)
            .jobId(DEFAULT_JOB_ID)
            .nationalityCode(DEFAULT_NATIONALITY_CODE)
            .nationality(DEFAULT_NATIONALITY)
            .personStartDate(DEFAULT_PERSON_START_DATE)
            .originalDateOfHire(DEFAULT_ORIGINAL_DATE_OF_HIRE)
            .supervisorId(DEFAULT_SUPERVISOR_ID)
            .supervisorFullName(DEFAULT_SUPERVISOR_FULL_NAME)
            .parentDepartmentName(DEFAULT_PARENT_DEPARTMENT_NAME)
            .sectionId(DEFAULT_SECTION_ID)
            .sectionName(DEFAULT_SECTION_NAME)
            .adjustedServiceDate(DEFAULT_ADJUSTED_SERVICE_DATE)
            .qualificationType(DEFAULT_QUALIFICATION_TYPE)
            .qualificationSpecification(DEFAULT_QUALIFICATION_SPECIFICATION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity() {
        return new Employee()
            .fullname(UPDATED_FULLNAME)
            .gradeId(UPDATED_GRADE_ID)
            .gradeName(UPDATED_GRADE_NAME)
            .homePhone(UPDATED_HOME_PHONE)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .assigmentId(UPDATED_ASSIGMENT_ID)
            .dateOfAssignment(UPDATED_DATE_OF_ASSIGNMENT)
            .jobName(UPDATED_JOB_NAME)
            .address(UPDATED_ADDRESS)
            .organizationId(UPDATED_ORGANIZATION_ID)
            .organization(UPDATED_ORGANIZATION)
            .nationalIdentifier(UPDATED_NATIONAL_IDENTIFIER)
            .uid(UPDATED_UID)
            .parentDepartmentId(UPDATED_PARENT_DEPARTMENT_ID)
            .slmMilitaryFlag(UPDATED_SLM_MILITARY_FLAG)
            .militaryFirstName(UPDATED_MILITARY_FIRST_NAME)
            .slmName(UPDATED_SLM_NAME)
            .age(UPDATED_AGE)
            .dateOfHire(UPDATED_DATE_OF_HIRE)
            .dateOfLastHire(UPDATED_DATE_OF_LAST_HIRE)
            .gender(UPDATED_GENDER)
            .jobId(UPDATED_JOB_ID)
            .nationalityCode(UPDATED_NATIONALITY_CODE)
            .nationality(UPDATED_NATIONALITY)
            .personStartDate(UPDATED_PERSON_START_DATE)
            .originalDateOfHire(UPDATED_ORIGINAL_DATE_OF_HIRE)
            .supervisorId(UPDATED_SUPERVISOR_ID)
            .supervisorFullName(UPDATED_SUPERVISOR_FULL_NAME)
            .parentDepartmentName(UPDATED_PARENT_DEPARTMENT_NAME)
            .sectionId(UPDATED_SECTION_ID)
            .sectionName(UPDATED_SECTION_NAME)
            .adjustedServiceDate(UPDATED_ADJUSTED_SERVICE_DATE)
            .qualificationType(UPDATED_QUALIFICATION_TYPE)
            .qualificationSpecification(UPDATED_QUALIFICATION_SPECIFICATION);
    }

    @BeforeEach
    void initTest() {
        employee = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEmployee != null) {
            employeeRepository.delete(insertedEmployee);
            insertedEmployee = null;
        }
    }

    @Test
    @Transactional
    void getAllEmployees() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].gradeId").value(hasItem(DEFAULT_GRADE_ID.intValue())))
            .andExpect(jsonPath("$.[*].gradeName").value(hasItem(DEFAULT_GRADE_NAME)))
            .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].assigmentId").value(hasItem(DEFAULT_ASSIGMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].dateOfAssignment").value(hasItem(DEFAULT_DATE_OF_ASSIGNMENT)))
            .andExpect(jsonPath("$.[*].jobName").value(hasItem(DEFAULT_JOB_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].organizationId").value(hasItem(DEFAULT_ORGANIZATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].nationalIdentifier").value(hasItem(DEFAULT_NATIONAL_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID)))
            .andExpect(jsonPath("$.[*].parentDepartmentId").value(hasItem(DEFAULT_PARENT_DEPARTMENT_ID)))
            .andExpect(jsonPath("$.[*].slmMilitaryFlag").value(hasItem(DEFAULT_SLM_MILITARY_FLAG)))
            .andExpect(jsonPath("$.[*].militaryFirstName").value(hasItem(DEFAULT_MILITARY_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].slmName").value(hasItem(DEFAULT_SLM_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].dateOfHire").value(hasItem(DEFAULT_DATE_OF_HIRE.toString())))
            .andExpect(jsonPath("$.[*].dateOfLastHire").value(hasItem(DEFAULT_DATE_OF_LAST_HIRE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())))
            .andExpect(jsonPath("$.[*].nationalityCode").value(hasItem(DEFAULT_NATIONALITY_CODE)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].personStartDate").value(hasItem(DEFAULT_PERSON_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].originalDateOfHire").value(hasItem(DEFAULT_ORIGINAL_DATE_OF_HIRE.toString())))
            .andExpect(jsonPath("$.[*].supervisorId").value(hasItem(DEFAULT_SUPERVISOR_ID)))
            .andExpect(jsonPath("$.[*].supervisorFullName").value(hasItem(DEFAULT_SUPERVISOR_FULL_NAME)))
            .andExpect(jsonPath("$.[*].parentDepartmentName").value(hasItem(DEFAULT_PARENT_DEPARTMENT_NAME)))
            .andExpect(jsonPath("$.[*].sectionId").value(hasItem(DEFAULT_SECTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].sectionName").value(hasItem(DEFAULT_SECTION_NAME)))
            .andExpect(jsonPath("$.[*].adjustedServiceDate").value(hasItem(DEFAULT_ADJUSTED_SERVICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].qualificationType").value(hasItem(DEFAULT_QUALIFICATION_TYPE)))
            .andExpect(jsonPath("$.[*].qualificationSpecification").value(hasItem(DEFAULT_QUALIFICATION_SPECIFICATION)));
    }

    @Test
    @Transactional
    void getEmployee() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.fullname").value(DEFAULT_FULLNAME))
            .andExpect(jsonPath("$.gradeId").value(DEFAULT_GRADE_ID.intValue()))
            .andExpect(jsonPath("$.gradeName").value(DEFAULT_GRADE_NAME))
            .andExpect(jsonPath("$.homePhone").value(DEFAULT_HOME_PHONE))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER))
            .andExpect(jsonPath("$.assigmentId").value(DEFAULT_ASSIGMENT_ID.intValue()))
            .andExpect(jsonPath("$.dateOfAssignment").value(DEFAULT_DATE_OF_ASSIGNMENT))
            .andExpect(jsonPath("$.jobName").value(DEFAULT_JOB_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.organizationId").value(DEFAULT_ORGANIZATION_ID.intValue()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION))
            .andExpect(jsonPath("$.nationalIdentifier").value(DEFAULT_NATIONAL_IDENTIFIER))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID))
            .andExpect(jsonPath("$.parentDepartmentId").value(DEFAULT_PARENT_DEPARTMENT_ID))
            .andExpect(jsonPath("$.slmMilitaryFlag").value(DEFAULT_SLM_MILITARY_FLAG))
            .andExpect(jsonPath("$.militaryFirstName").value(DEFAULT_MILITARY_FIRST_NAME))
            .andExpect(jsonPath("$.slmName").value(DEFAULT_SLM_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.dateOfHire").value(DEFAULT_DATE_OF_HIRE.toString()))
            .andExpect(jsonPath("$.dateOfLastHire").value(DEFAULT_DATE_OF_LAST_HIRE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.intValue()))
            .andExpect(jsonPath("$.nationalityCode").value(DEFAULT_NATIONALITY_CODE))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.personStartDate").value(DEFAULT_PERSON_START_DATE.toString()))
            .andExpect(jsonPath("$.originalDateOfHire").value(DEFAULT_ORIGINAL_DATE_OF_HIRE.toString()))
            .andExpect(jsonPath("$.supervisorId").value(DEFAULT_SUPERVISOR_ID))
            .andExpect(jsonPath("$.supervisorFullName").value(DEFAULT_SUPERVISOR_FULL_NAME))
            .andExpect(jsonPath("$.parentDepartmentName").value(DEFAULT_PARENT_DEPARTMENT_NAME))
            .andExpect(jsonPath("$.sectionId").value(DEFAULT_SECTION_ID.intValue()))
            .andExpect(jsonPath("$.sectionName").value(DEFAULT_SECTION_NAME))
            .andExpect(jsonPath("$.adjustedServiceDate").value(DEFAULT_ADJUSTED_SERVICE_DATE.toString()))
            .andExpect(jsonPath("$.qualificationType").value(DEFAULT_QUALIFICATION_TYPE))
            .andExpect(jsonPath("$.qualificationSpecification").value(DEFAULT_QUALIFICATION_SPECIFICATION));
    }

    @Test
    @Transactional
    void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        Long id = employee.getId();

        defaultEmployeeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultEmployeeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultEmployeeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeesByFullnameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullname equals to
        defaultEmployeeFiltering("fullname.equals=" + DEFAULT_FULLNAME, "fullname.equals=" + UPDATED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFullnameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullname in
        defaultEmployeeFiltering("fullname.in=" + DEFAULT_FULLNAME + "," + UPDATED_FULLNAME, "fullname.in=" + UPDATED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFullnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullname is not null
        defaultEmployeeFiltering("fullname.specified=true", "fullname.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByFullnameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullname contains
        defaultEmployeeFiltering("fullname.contains=" + DEFAULT_FULLNAME, "fullname.contains=" + UPDATED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFullnameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullname does not contain
        defaultEmployeeFiltering("fullname.doesNotContain=" + UPDATED_FULLNAME, "fullname.doesNotContain=" + DEFAULT_FULLNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gradeId equals to
        defaultEmployeeFiltering("gradeId.equals=" + DEFAULT_GRADE_ID, "gradeId.equals=" + UPDATED_GRADE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gradeId in
        defaultEmployeeFiltering("gradeId.in=" + DEFAULT_GRADE_ID + "," + UPDATED_GRADE_ID, "gradeId.in=" + UPDATED_GRADE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gradeId is not null
        defaultEmployeeFiltering("gradeId.specified=true", "gradeId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gradeId is greater than or equal to
        defaultEmployeeFiltering("gradeId.greaterThanOrEqual=" + DEFAULT_GRADE_ID, "gradeId.greaterThanOrEqual=" + UPDATED_GRADE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gradeId is less than or equal to
        defaultEmployeeFiltering("gradeId.lessThanOrEqual=" + DEFAULT_GRADE_ID, "gradeId.lessThanOrEqual=" + SMALLER_GRADE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gradeId is less than
        defaultEmployeeFiltering("gradeId.lessThan=" + UPDATED_GRADE_ID, "gradeId.lessThan=" + DEFAULT_GRADE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gradeId is greater than
        defaultEmployeeFiltering("gradeId.greaterThan=" + SMALLER_GRADE_ID, "gradeId.greaterThan=" + DEFAULT_GRADE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gradeName equals to
        defaultEmployeeFiltering("gradeName.equals=" + DEFAULT_GRADE_NAME, "gradeName.equals=" + UPDATED_GRADE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gradeName in
        defaultEmployeeFiltering("gradeName.in=" + DEFAULT_GRADE_NAME + "," + UPDATED_GRADE_NAME, "gradeName.in=" + UPDATED_GRADE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gradeName is not null
        defaultEmployeeFiltering("gradeName.specified=true", "gradeName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gradeName contains
        defaultEmployeeFiltering("gradeName.contains=" + DEFAULT_GRADE_NAME, "gradeName.contains=" + UPDATED_GRADE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gradeName does not contain
        defaultEmployeeFiltering("gradeName.doesNotContain=" + UPDATED_GRADE_NAME, "gradeName.doesNotContain=" + DEFAULT_GRADE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByHomePhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where homePhone equals to
        defaultEmployeeFiltering("homePhone.equals=" + DEFAULT_HOME_PHONE, "homePhone.equals=" + UPDATED_HOME_PHONE);
    }

    @Test
    @Transactional
    void getAllEmployeesByHomePhoneIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where homePhone in
        defaultEmployeeFiltering("homePhone.in=" + DEFAULT_HOME_PHONE + "," + UPDATED_HOME_PHONE, "homePhone.in=" + UPDATED_HOME_PHONE);
    }

    @Test
    @Transactional
    void getAllEmployeesByHomePhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where homePhone is not null
        defaultEmployeeFiltering("homePhone.specified=true", "homePhone.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByHomePhoneContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where homePhone contains
        defaultEmployeeFiltering("homePhone.contains=" + DEFAULT_HOME_PHONE, "homePhone.contains=" + UPDATED_HOME_PHONE);
    }

    @Test
    @Transactional
    void getAllEmployeesByHomePhoneNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where homePhone does not contain
        defaultEmployeeFiltering("homePhone.doesNotContain=" + UPDATED_HOME_PHONE, "homePhone.doesNotContain=" + DEFAULT_HOME_PHONE);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNumber equals to
        defaultEmployeeFiltering("mobileNumber.equals=" + DEFAULT_MOBILE_NUMBER, "mobileNumber.equals=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNumber in
        defaultEmployeeFiltering(
            "mobileNumber.in=" + DEFAULT_MOBILE_NUMBER + "," + UPDATED_MOBILE_NUMBER,
            "mobileNumber.in=" + UPDATED_MOBILE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNumber is not null
        defaultEmployeeFiltering("mobileNumber.specified=true", "mobileNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNumber contains
        defaultEmployeeFiltering("mobileNumber.contains=" + DEFAULT_MOBILE_NUMBER, "mobileNumber.contains=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNumber does not contain
        defaultEmployeeFiltering(
            "mobileNumber.doesNotContain=" + UPDATED_MOBILE_NUMBER,
            "mobileNumber.doesNotContain=" + DEFAULT_MOBILE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByAssigmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where assigmentId equals to
        defaultEmployeeFiltering("assigmentId.equals=" + DEFAULT_ASSIGMENT_ID, "assigmentId.equals=" + UPDATED_ASSIGMENT_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByAssigmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where assigmentId in
        defaultEmployeeFiltering(
            "assigmentId.in=" + DEFAULT_ASSIGMENT_ID + "," + UPDATED_ASSIGMENT_ID,
            "assigmentId.in=" + UPDATED_ASSIGMENT_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByAssigmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where assigmentId is not null
        defaultEmployeeFiltering("assigmentId.specified=true", "assigmentId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByAssigmentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where assigmentId is greater than or equal to
        defaultEmployeeFiltering(
            "assigmentId.greaterThanOrEqual=" + DEFAULT_ASSIGMENT_ID,
            "assigmentId.greaterThanOrEqual=" + UPDATED_ASSIGMENT_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByAssigmentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where assigmentId is less than or equal to
        defaultEmployeeFiltering(
            "assigmentId.lessThanOrEqual=" + DEFAULT_ASSIGMENT_ID,
            "assigmentId.lessThanOrEqual=" + SMALLER_ASSIGMENT_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByAssigmentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where assigmentId is less than
        defaultEmployeeFiltering("assigmentId.lessThan=" + UPDATED_ASSIGMENT_ID, "assigmentId.lessThan=" + DEFAULT_ASSIGMENT_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByAssigmentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where assigmentId is greater than
        defaultEmployeeFiltering("assigmentId.greaterThan=" + SMALLER_ASSIGMENT_ID, "assigmentId.greaterThan=" + DEFAULT_ASSIGMENT_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfAssignmentIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfAssignment equals to
        defaultEmployeeFiltering(
            "dateOfAssignment.equals=" + DEFAULT_DATE_OF_ASSIGNMENT,
            "dateOfAssignment.equals=" + UPDATED_DATE_OF_ASSIGNMENT
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfAssignmentIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfAssignment in
        defaultEmployeeFiltering(
            "dateOfAssignment.in=" + DEFAULT_DATE_OF_ASSIGNMENT + "," + UPDATED_DATE_OF_ASSIGNMENT,
            "dateOfAssignment.in=" + UPDATED_DATE_OF_ASSIGNMENT
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfAssignmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfAssignment is not null
        defaultEmployeeFiltering("dateOfAssignment.specified=true", "dateOfAssignment.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfAssignmentContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfAssignment contains
        defaultEmployeeFiltering(
            "dateOfAssignment.contains=" + DEFAULT_DATE_OF_ASSIGNMENT,
            "dateOfAssignment.contains=" + UPDATED_DATE_OF_ASSIGNMENT
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfAssignmentNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfAssignment does not contain
        defaultEmployeeFiltering(
            "dateOfAssignment.doesNotContain=" + UPDATED_DATE_OF_ASSIGNMENT,
            "dateOfAssignment.doesNotContain=" + DEFAULT_DATE_OF_ASSIGNMENT
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByJobNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobName equals to
        defaultEmployeeFiltering("jobName.equals=" + DEFAULT_JOB_NAME, "jobName.equals=" + UPDATED_JOB_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobName in
        defaultEmployeeFiltering("jobName.in=" + DEFAULT_JOB_NAME + "," + UPDATED_JOB_NAME, "jobName.in=" + UPDATED_JOB_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobName is not null
        defaultEmployeeFiltering("jobName.specified=true", "jobName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByJobNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobName contains
        defaultEmployeeFiltering("jobName.contains=" + DEFAULT_JOB_NAME, "jobName.contains=" + UPDATED_JOB_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobName does not contain
        defaultEmployeeFiltering("jobName.doesNotContain=" + UPDATED_JOB_NAME, "jobName.doesNotContain=" + DEFAULT_JOB_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where address equals to
        defaultEmployeeFiltering("address.equals=" + DEFAULT_ADDRESS, "address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEmployeesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where address in
        defaultEmployeeFiltering("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS, "address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEmployeesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where address is not null
        defaultEmployeeFiltering("address.specified=true", "address.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByAddressContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where address contains
        defaultEmployeeFiltering("address.contains=" + DEFAULT_ADDRESS, "address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEmployeesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where address does not contain
        defaultEmployeeFiltering("address.doesNotContain=" + UPDATED_ADDRESS, "address.doesNotContain=" + DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organizationId equals to
        defaultEmployeeFiltering("organizationId.equals=" + DEFAULT_ORGANIZATION_ID, "organizationId.equals=" + UPDATED_ORGANIZATION_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organizationId in
        defaultEmployeeFiltering(
            "organizationId.in=" + DEFAULT_ORGANIZATION_ID + "," + UPDATED_ORGANIZATION_ID,
            "organizationId.in=" + UPDATED_ORGANIZATION_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organizationId is not null
        defaultEmployeeFiltering("organizationId.specified=true", "organizationId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organizationId is greater than or equal to
        defaultEmployeeFiltering(
            "organizationId.greaterThanOrEqual=" + DEFAULT_ORGANIZATION_ID,
            "organizationId.greaterThanOrEqual=" + UPDATED_ORGANIZATION_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organizationId is less than or equal to
        defaultEmployeeFiltering(
            "organizationId.lessThanOrEqual=" + DEFAULT_ORGANIZATION_ID,
            "organizationId.lessThanOrEqual=" + SMALLER_ORGANIZATION_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organizationId is less than
        defaultEmployeeFiltering(
            "organizationId.lessThan=" + UPDATED_ORGANIZATION_ID,
            "organizationId.lessThan=" + DEFAULT_ORGANIZATION_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organizationId is greater than
        defaultEmployeeFiltering(
            "organizationId.greaterThan=" + SMALLER_ORGANIZATION_ID,
            "organizationId.greaterThan=" + DEFAULT_ORGANIZATION_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organization equals to
        defaultEmployeeFiltering("organization.equals=" + DEFAULT_ORGANIZATION, "organization.equals=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organization in
        defaultEmployeeFiltering(
            "organization.in=" + DEFAULT_ORGANIZATION + "," + UPDATED_ORGANIZATION,
            "organization.in=" + UPDATED_ORGANIZATION
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organization is not null
        defaultEmployeeFiltering("organization.specified=true", "organization.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organization contains
        defaultEmployeeFiltering("organization.contains=" + DEFAULT_ORGANIZATION, "organization.contains=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where organization does not contain
        defaultEmployeeFiltering(
            "organization.doesNotContain=" + UPDATED_ORGANIZATION,
            "organization.doesNotContain=" + DEFAULT_ORGANIZATION
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalIdentifierIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationalIdentifier equals to
        defaultEmployeeFiltering(
            "nationalIdentifier.equals=" + DEFAULT_NATIONAL_IDENTIFIER,
            "nationalIdentifier.equals=" + UPDATED_NATIONAL_IDENTIFIER
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalIdentifierIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationalIdentifier in
        defaultEmployeeFiltering(
            "nationalIdentifier.in=" + DEFAULT_NATIONAL_IDENTIFIER + "," + UPDATED_NATIONAL_IDENTIFIER,
            "nationalIdentifier.in=" + UPDATED_NATIONAL_IDENTIFIER
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalIdentifierIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationalIdentifier is not null
        defaultEmployeeFiltering("nationalIdentifier.specified=true", "nationalIdentifier.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalIdentifierContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationalIdentifier contains
        defaultEmployeeFiltering(
            "nationalIdentifier.contains=" + DEFAULT_NATIONAL_IDENTIFIER,
            "nationalIdentifier.contains=" + UPDATED_NATIONAL_IDENTIFIER
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalIdentifierNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationalIdentifier does not contain
        defaultEmployeeFiltering(
            "nationalIdentifier.doesNotContain=" + UPDATED_NATIONAL_IDENTIFIER,
            "nationalIdentifier.doesNotContain=" + DEFAULT_NATIONAL_IDENTIFIER
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where uid equals to
        defaultEmployeeFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllEmployeesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where uid in
        defaultEmployeeFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllEmployeesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where uid is not null
        defaultEmployeeFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByUidContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where uid contains
        defaultEmployeeFiltering("uid.contains=" + DEFAULT_UID, "uid.contains=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllEmployeesByUidNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where uid does not contain
        defaultEmployeeFiltering("uid.doesNotContain=" + UPDATED_UID, "uid.doesNotContain=" + DEFAULT_UID);
    }

    @Test
    @Transactional
    void getAllEmployeesByParentDepartmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where parentDepartmentId equals to
        defaultEmployeeFiltering(
            "parentDepartmentId.equals=" + DEFAULT_PARENT_DEPARTMENT_ID,
            "parentDepartmentId.equals=" + UPDATED_PARENT_DEPARTMENT_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByParentDepartmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where parentDepartmentId in
        defaultEmployeeFiltering(
            "parentDepartmentId.in=" + DEFAULT_PARENT_DEPARTMENT_ID + "," + UPDATED_PARENT_DEPARTMENT_ID,
            "parentDepartmentId.in=" + UPDATED_PARENT_DEPARTMENT_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByParentDepartmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where parentDepartmentId is not null
        defaultEmployeeFiltering("parentDepartmentId.specified=true", "parentDepartmentId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByParentDepartmentIdContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where parentDepartmentId contains
        defaultEmployeeFiltering(
            "parentDepartmentId.contains=" + DEFAULT_PARENT_DEPARTMENT_ID,
            "parentDepartmentId.contains=" + UPDATED_PARENT_DEPARTMENT_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByParentDepartmentIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where parentDepartmentId does not contain
        defaultEmployeeFiltering(
            "parentDepartmentId.doesNotContain=" + UPDATED_PARENT_DEPARTMENT_ID,
            "parentDepartmentId.doesNotContain=" + DEFAULT_PARENT_DEPARTMENT_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySlmMilitaryFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where slmMilitaryFlag equals to
        defaultEmployeeFiltering(
            "slmMilitaryFlag.equals=" + DEFAULT_SLM_MILITARY_FLAG,
            "slmMilitaryFlag.equals=" + UPDATED_SLM_MILITARY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySlmMilitaryFlagIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where slmMilitaryFlag in
        defaultEmployeeFiltering(
            "slmMilitaryFlag.in=" + DEFAULT_SLM_MILITARY_FLAG + "," + UPDATED_SLM_MILITARY_FLAG,
            "slmMilitaryFlag.in=" + UPDATED_SLM_MILITARY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySlmMilitaryFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where slmMilitaryFlag is not null
        defaultEmployeeFiltering("slmMilitaryFlag.specified=true", "slmMilitaryFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySlmMilitaryFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where slmMilitaryFlag is greater than or equal to
        defaultEmployeeFiltering(
            "slmMilitaryFlag.greaterThanOrEqual=" + DEFAULT_SLM_MILITARY_FLAG,
            "slmMilitaryFlag.greaterThanOrEqual=" + UPDATED_SLM_MILITARY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySlmMilitaryFlagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where slmMilitaryFlag is less than or equal to
        defaultEmployeeFiltering(
            "slmMilitaryFlag.lessThanOrEqual=" + DEFAULT_SLM_MILITARY_FLAG,
            "slmMilitaryFlag.lessThanOrEqual=" + SMALLER_SLM_MILITARY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySlmMilitaryFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where slmMilitaryFlag is less than
        defaultEmployeeFiltering(
            "slmMilitaryFlag.lessThan=" + UPDATED_SLM_MILITARY_FLAG,
            "slmMilitaryFlag.lessThan=" + DEFAULT_SLM_MILITARY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySlmMilitaryFlagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where slmMilitaryFlag is greater than
        defaultEmployeeFiltering(
            "slmMilitaryFlag.greaterThan=" + SMALLER_SLM_MILITARY_FLAG,
            "slmMilitaryFlag.greaterThan=" + DEFAULT_SLM_MILITARY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByMilitaryFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where militaryFirstName equals to
        defaultEmployeeFiltering(
            "militaryFirstName.equals=" + DEFAULT_MILITARY_FIRST_NAME,
            "militaryFirstName.equals=" + UPDATED_MILITARY_FIRST_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByMilitaryFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where militaryFirstName in
        defaultEmployeeFiltering(
            "militaryFirstName.in=" + DEFAULT_MILITARY_FIRST_NAME + "," + UPDATED_MILITARY_FIRST_NAME,
            "militaryFirstName.in=" + UPDATED_MILITARY_FIRST_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByMilitaryFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where militaryFirstName is not null
        defaultEmployeeFiltering("militaryFirstName.specified=true", "militaryFirstName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByMilitaryFirstNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where militaryFirstName contains
        defaultEmployeeFiltering(
            "militaryFirstName.contains=" + DEFAULT_MILITARY_FIRST_NAME,
            "militaryFirstName.contains=" + UPDATED_MILITARY_FIRST_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByMilitaryFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where militaryFirstName does not contain
        defaultEmployeeFiltering(
            "militaryFirstName.doesNotContain=" + UPDATED_MILITARY_FIRST_NAME,
            "militaryFirstName.doesNotContain=" + DEFAULT_MILITARY_FIRST_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySlmNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where slmName equals to
        defaultEmployeeFiltering("slmName.equals=" + DEFAULT_SLM_NAME, "slmName.equals=" + UPDATED_SLM_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesBySlmNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where slmName in
        defaultEmployeeFiltering("slmName.in=" + DEFAULT_SLM_NAME + "," + UPDATED_SLM_NAME, "slmName.in=" + UPDATED_SLM_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesBySlmNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where slmName is not null
        defaultEmployeeFiltering("slmName.specified=true", "slmName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySlmNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where slmName contains
        defaultEmployeeFiltering("slmName.contains=" + DEFAULT_SLM_NAME, "slmName.contains=" + UPDATED_SLM_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesBySlmNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where slmName does not contain
        defaultEmployeeFiltering("slmName.doesNotContain=" + UPDATED_SLM_NAME, "slmName.doesNotContain=" + DEFAULT_SLM_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age equals to
        defaultEmployeeFiltering("age.equals=" + DEFAULT_AGE, "age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age in
        defaultEmployeeFiltering("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE, "age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age is not null
        defaultEmployeeFiltering("age.specified=true", "age.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age is greater than or equal to
        defaultEmployeeFiltering("age.greaterThanOrEqual=" + DEFAULT_AGE, "age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age is less than or equal to
        defaultEmployeeFiltering("age.lessThanOrEqual=" + DEFAULT_AGE, "age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age is less than
        defaultEmployeeFiltering("age.lessThan=" + UPDATED_AGE, "age.lessThan=" + DEFAULT_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age is greater than
        defaultEmployeeFiltering("age.greaterThan=" + SMALLER_AGE, "age.greaterThan=" + DEFAULT_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfHireIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfHire equals to
        defaultEmployeeFiltering("dateOfHire.equals=" + DEFAULT_DATE_OF_HIRE, "dateOfHire.equals=" + UPDATED_DATE_OF_HIRE);
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfHireIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfHire in
        defaultEmployeeFiltering(
            "dateOfHire.in=" + DEFAULT_DATE_OF_HIRE + "," + UPDATED_DATE_OF_HIRE,
            "dateOfHire.in=" + UPDATED_DATE_OF_HIRE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfHireIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfHire is not null
        defaultEmployeeFiltering("dateOfHire.specified=true", "dateOfHire.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfLastHireIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfLastHire equals to
        defaultEmployeeFiltering(
            "dateOfLastHire.equals=" + DEFAULT_DATE_OF_LAST_HIRE,
            "dateOfLastHire.equals=" + UPDATED_DATE_OF_LAST_HIRE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfLastHireIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfLastHire in
        defaultEmployeeFiltering(
            "dateOfLastHire.in=" + DEFAULT_DATE_OF_LAST_HIRE + "," + UPDATED_DATE_OF_LAST_HIRE,
            "dateOfLastHire.in=" + UPDATED_DATE_OF_LAST_HIRE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfLastHireIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfLastHire is not null
        defaultEmployeeFiltering("dateOfLastHire.specified=true", "dateOfLastHire.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender equals to
        defaultEmployeeFiltering("gender.equals=" + DEFAULT_GENDER, "gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender in
        defaultEmployeeFiltering("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER, "gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender is not null
        defaultEmployeeFiltering("gender.specified=true", "gender.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender contains
        defaultEmployeeFiltering("gender.contains=" + DEFAULT_GENDER, "gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender does not contain
        defaultEmployeeFiltering("gender.doesNotContain=" + UPDATED_GENDER, "gender.doesNotContain=" + DEFAULT_GENDER);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobId equals to
        defaultEmployeeFiltering("jobId.equals=" + DEFAULT_JOB_ID, "jobId.equals=" + UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobId in
        defaultEmployeeFiltering("jobId.in=" + DEFAULT_JOB_ID + "," + UPDATED_JOB_ID, "jobId.in=" + UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobId is not null
        defaultEmployeeFiltering("jobId.specified=true", "jobId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByJobIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobId is greater than or equal to
        defaultEmployeeFiltering("jobId.greaterThanOrEqual=" + DEFAULT_JOB_ID, "jobId.greaterThanOrEqual=" + UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobId is less than or equal to
        defaultEmployeeFiltering("jobId.lessThanOrEqual=" + DEFAULT_JOB_ID, "jobId.lessThanOrEqual=" + SMALLER_JOB_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobId is less than
        defaultEmployeeFiltering("jobId.lessThan=" + UPDATED_JOB_ID, "jobId.lessThan=" + DEFAULT_JOB_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobId is greater than
        defaultEmployeeFiltering("jobId.greaterThan=" + SMALLER_JOB_ID, "jobId.greaterThan=" + DEFAULT_JOB_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationalityCode equals to
        defaultEmployeeFiltering(
            "nationalityCode.equals=" + DEFAULT_NATIONALITY_CODE,
            "nationalityCode.equals=" + UPDATED_NATIONALITY_CODE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationalityCode in
        defaultEmployeeFiltering(
            "nationalityCode.in=" + DEFAULT_NATIONALITY_CODE + "," + UPDATED_NATIONALITY_CODE,
            "nationalityCode.in=" + UPDATED_NATIONALITY_CODE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationalityCode is not null
        defaultEmployeeFiltering("nationalityCode.specified=true", "nationalityCode.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationalityCode contains
        defaultEmployeeFiltering(
            "nationalityCode.contains=" + DEFAULT_NATIONALITY_CODE,
            "nationalityCode.contains=" + UPDATED_NATIONALITY_CODE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationalityCode does not contain
        defaultEmployeeFiltering(
            "nationalityCode.doesNotContain=" + UPDATED_NATIONALITY_CODE,
            "nationalityCode.doesNotContain=" + DEFAULT_NATIONALITY_CODE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationality equals to
        defaultEmployeeFiltering("nationality.equals=" + DEFAULT_NATIONALITY, "nationality.equals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationality in
        defaultEmployeeFiltering(
            "nationality.in=" + DEFAULT_NATIONALITY + "," + UPDATED_NATIONALITY,
            "nationality.in=" + UPDATED_NATIONALITY
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationality is not null
        defaultEmployeeFiltering("nationality.specified=true", "nationality.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationality contains
        defaultEmployeeFiltering("nationality.contains=" + DEFAULT_NATIONALITY, "nationality.contains=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationality does not contain
        defaultEmployeeFiltering("nationality.doesNotContain=" + UPDATED_NATIONALITY, "nationality.doesNotContain=" + DEFAULT_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllEmployeesByPersonStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where personStartDate equals to
        defaultEmployeeFiltering(
            "personStartDate.equals=" + DEFAULT_PERSON_START_DATE,
            "personStartDate.equals=" + UPDATED_PERSON_START_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPersonStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where personStartDate in
        defaultEmployeeFiltering(
            "personStartDate.in=" + DEFAULT_PERSON_START_DATE + "," + UPDATED_PERSON_START_DATE,
            "personStartDate.in=" + UPDATED_PERSON_START_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPersonStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where personStartDate is not null
        defaultEmployeeFiltering("personStartDate.specified=true", "personStartDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByOriginalDateOfHireIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where originalDateOfHire equals to
        defaultEmployeeFiltering(
            "originalDateOfHire.equals=" + DEFAULT_ORIGINAL_DATE_OF_HIRE,
            "originalDateOfHire.equals=" + UPDATED_ORIGINAL_DATE_OF_HIRE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByOriginalDateOfHireIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where originalDateOfHire in
        defaultEmployeeFiltering(
            "originalDateOfHire.in=" + DEFAULT_ORIGINAL_DATE_OF_HIRE + "," + UPDATED_ORIGINAL_DATE_OF_HIRE,
            "originalDateOfHire.in=" + UPDATED_ORIGINAL_DATE_OF_HIRE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByOriginalDateOfHireIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where originalDateOfHire is not null
        defaultEmployeeFiltering("originalDateOfHire.specified=true", "originalDateOfHire.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySupervisorIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where supervisorId equals to
        defaultEmployeeFiltering("supervisorId.equals=" + DEFAULT_SUPERVISOR_ID, "supervisorId.equals=" + UPDATED_SUPERVISOR_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesBySupervisorIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where supervisorId in
        defaultEmployeeFiltering(
            "supervisorId.in=" + DEFAULT_SUPERVISOR_ID + "," + UPDATED_SUPERVISOR_ID,
            "supervisorId.in=" + UPDATED_SUPERVISOR_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySupervisorIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where supervisorId is not null
        defaultEmployeeFiltering("supervisorId.specified=true", "supervisorId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySupervisorIdContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where supervisorId contains
        defaultEmployeeFiltering("supervisorId.contains=" + DEFAULT_SUPERVISOR_ID, "supervisorId.contains=" + UPDATED_SUPERVISOR_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesBySupervisorIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where supervisorId does not contain
        defaultEmployeeFiltering(
            "supervisorId.doesNotContain=" + UPDATED_SUPERVISOR_ID,
            "supervisorId.doesNotContain=" + DEFAULT_SUPERVISOR_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySupervisorFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where supervisorFullName equals to
        defaultEmployeeFiltering(
            "supervisorFullName.equals=" + DEFAULT_SUPERVISOR_FULL_NAME,
            "supervisorFullName.equals=" + UPDATED_SUPERVISOR_FULL_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySupervisorFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where supervisorFullName in
        defaultEmployeeFiltering(
            "supervisorFullName.in=" + DEFAULT_SUPERVISOR_FULL_NAME + "," + UPDATED_SUPERVISOR_FULL_NAME,
            "supervisorFullName.in=" + UPDATED_SUPERVISOR_FULL_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySupervisorFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where supervisorFullName is not null
        defaultEmployeeFiltering("supervisorFullName.specified=true", "supervisorFullName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySupervisorFullNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where supervisorFullName contains
        defaultEmployeeFiltering(
            "supervisorFullName.contains=" + DEFAULT_SUPERVISOR_FULL_NAME,
            "supervisorFullName.contains=" + UPDATED_SUPERVISOR_FULL_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySupervisorFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where supervisorFullName does not contain
        defaultEmployeeFiltering(
            "supervisorFullName.doesNotContain=" + UPDATED_SUPERVISOR_FULL_NAME,
            "supervisorFullName.doesNotContain=" + DEFAULT_SUPERVISOR_FULL_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByParentDepartmentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where parentDepartmentName equals to
        defaultEmployeeFiltering(
            "parentDepartmentName.equals=" + DEFAULT_PARENT_DEPARTMENT_NAME,
            "parentDepartmentName.equals=" + UPDATED_PARENT_DEPARTMENT_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByParentDepartmentNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where parentDepartmentName in
        defaultEmployeeFiltering(
            "parentDepartmentName.in=" + DEFAULT_PARENT_DEPARTMENT_NAME + "," + UPDATED_PARENT_DEPARTMENT_NAME,
            "parentDepartmentName.in=" + UPDATED_PARENT_DEPARTMENT_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByParentDepartmentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where parentDepartmentName is not null
        defaultEmployeeFiltering("parentDepartmentName.specified=true", "parentDepartmentName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByParentDepartmentNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where parentDepartmentName contains
        defaultEmployeeFiltering(
            "parentDepartmentName.contains=" + DEFAULT_PARENT_DEPARTMENT_NAME,
            "parentDepartmentName.contains=" + UPDATED_PARENT_DEPARTMENT_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByParentDepartmentNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where parentDepartmentName does not contain
        defaultEmployeeFiltering(
            "parentDepartmentName.doesNotContain=" + UPDATED_PARENT_DEPARTMENT_NAME,
            "parentDepartmentName.doesNotContain=" + DEFAULT_PARENT_DEPARTMENT_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySectionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sectionId equals to
        defaultEmployeeFiltering("sectionId.equals=" + DEFAULT_SECTION_ID, "sectionId.equals=" + UPDATED_SECTION_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesBySectionIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sectionId in
        defaultEmployeeFiltering("sectionId.in=" + DEFAULT_SECTION_ID + "," + UPDATED_SECTION_ID, "sectionId.in=" + UPDATED_SECTION_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesBySectionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sectionId is not null
        defaultEmployeeFiltering("sectionId.specified=true", "sectionId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySectionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sectionId is greater than or equal to
        defaultEmployeeFiltering(
            "sectionId.greaterThanOrEqual=" + DEFAULT_SECTION_ID,
            "sectionId.greaterThanOrEqual=" + UPDATED_SECTION_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySectionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sectionId is less than or equal to
        defaultEmployeeFiltering("sectionId.lessThanOrEqual=" + DEFAULT_SECTION_ID, "sectionId.lessThanOrEqual=" + SMALLER_SECTION_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesBySectionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sectionId is less than
        defaultEmployeeFiltering("sectionId.lessThan=" + UPDATED_SECTION_ID, "sectionId.lessThan=" + DEFAULT_SECTION_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesBySectionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sectionId is greater than
        defaultEmployeeFiltering("sectionId.greaterThan=" + SMALLER_SECTION_ID, "sectionId.greaterThan=" + DEFAULT_SECTION_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesBySectionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sectionName equals to
        defaultEmployeeFiltering("sectionName.equals=" + DEFAULT_SECTION_NAME, "sectionName.equals=" + UPDATED_SECTION_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesBySectionNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sectionName in
        defaultEmployeeFiltering(
            "sectionName.in=" + DEFAULT_SECTION_NAME + "," + UPDATED_SECTION_NAME,
            "sectionName.in=" + UPDATED_SECTION_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySectionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sectionName is not null
        defaultEmployeeFiltering("sectionName.specified=true", "sectionName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySectionNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sectionName contains
        defaultEmployeeFiltering("sectionName.contains=" + DEFAULT_SECTION_NAME, "sectionName.contains=" + UPDATED_SECTION_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesBySectionNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sectionName does not contain
        defaultEmployeeFiltering(
            "sectionName.doesNotContain=" + UPDATED_SECTION_NAME,
            "sectionName.doesNotContain=" + DEFAULT_SECTION_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByAdjustedServiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where adjustedServiceDate equals to
        defaultEmployeeFiltering(
            "adjustedServiceDate.equals=" + DEFAULT_ADJUSTED_SERVICE_DATE,
            "adjustedServiceDate.equals=" + UPDATED_ADJUSTED_SERVICE_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByAdjustedServiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where adjustedServiceDate in
        defaultEmployeeFiltering(
            "adjustedServiceDate.in=" + DEFAULT_ADJUSTED_SERVICE_DATE + "," + UPDATED_ADJUSTED_SERVICE_DATE,
            "adjustedServiceDate.in=" + UPDATED_ADJUSTED_SERVICE_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByAdjustedServiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where adjustedServiceDate is not null
        defaultEmployeeFiltering("adjustedServiceDate.specified=true", "adjustedServiceDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualificationType equals to
        defaultEmployeeFiltering(
            "qualificationType.equals=" + DEFAULT_QUALIFICATION_TYPE,
            "qualificationType.equals=" + UPDATED_QUALIFICATION_TYPE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualificationType in
        defaultEmployeeFiltering(
            "qualificationType.in=" + DEFAULT_QUALIFICATION_TYPE + "," + UPDATED_QUALIFICATION_TYPE,
            "qualificationType.in=" + UPDATED_QUALIFICATION_TYPE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualificationType is not null
        defaultEmployeeFiltering("qualificationType.specified=true", "qualificationType.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationTypeContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualificationType contains
        defaultEmployeeFiltering(
            "qualificationType.contains=" + DEFAULT_QUALIFICATION_TYPE,
            "qualificationType.contains=" + UPDATED_QUALIFICATION_TYPE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationTypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualificationType does not contain
        defaultEmployeeFiltering(
            "qualificationType.doesNotContain=" + UPDATED_QUALIFICATION_TYPE,
            "qualificationType.doesNotContain=" + DEFAULT_QUALIFICATION_TYPE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationSpecificationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualificationSpecification equals to
        defaultEmployeeFiltering(
            "qualificationSpecification.equals=" + DEFAULT_QUALIFICATION_SPECIFICATION,
            "qualificationSpecification.equals=" + UPDATED_QUALIFICATION_SPECIFICATION
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationSpecificationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualificationSpecification in
        defaultEmployeeFiltering(
            "qualificationSpecification.in=" + DEFAULT_QUALIFICATION_SPECIFICATION + "," + UPDATED_QUALIFICATION_SPECIFICATION,
            "qualificationSpecification.in=" + UPDATED_QUALIFICATION_SPECIFICATION
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationSpecificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualificationSpecification is not null
        defaultEmployeeFiltering("qualificationSpecification.specified=true", "qualificationSpecification.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationSpecificationContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualificationSpecification contains
        defaultEmployeeFiltering(
            "qualificationSpecification.contains=" + DEFAULT_QUALIFICATION_SPECIFICATION,
            "qualificationSpecification.contains=" + UPDATED_QUALIFICATION_SPECIFICATION
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationSpecificationNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualificationSpecification does not contain
        defaultEmployeeFiltering(
            "qualificationSpecification.doesNotContain=" + UPDATED_QUALIFICATION_SPECIFICATION,
            "qualificationSpecification.doesNotContain=" + DEFAULT_QUALIFICATION_SPECIFICATION
        );
    }

    private void defaultEmployeeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEmployeeShouldBeFound(shouldBeFound);
        defaultEmployeeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].gradeId").value(hasItem(DEFAULT_GRADE_ID.intValue())))
            .andExpect(jsonPath("$.[*].gradeName").value(hasItem(DEFAULT_GRADE_NAME)))
            .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].assigmentId").value(hasItem(DEFAULT_ASSIGMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].dateOfAssignment").value(hasItem(DEFAULT_DATE_OF_ASSIGNMENT)))
            .andExpect(jsonPath("$.[*].jobName").value(hasItem(DEFAULT_JOB_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].organizationId").value(hasItem(DEFAULT_ORGANIZATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].nationalIdentifier").value(hasItem(DEFAULT_NATIONAL_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID)))
            .andExpect(jsonPath("$.[*].parentDepartmentId").value(hasItem(DEFAULT_PARENT_DEPARTMENT_ID)))
            .andExpect(jsonPath("$.[*].slmMilitaryFlag").value(hasItem(DEFAULT_SLM_MILITARY_FLAG)))
            .andExpect(jsonPath("$.[*].militaryFirstName").value(hasItem(DEFAULT_MILITARY_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].slmName").value(hasItem(DEFAULT_SLM_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].dateOfHire").value(hasItem(DEFAULT_DATE_OF_HIRE.toString())))
            .andExpect(jsonPath("$.[*].dateOfLastHire").value(hasItem(DEFAULT_DATE_OF_LAST_HIRE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())))
            .andExpect(jsonPath("$.[*].nationalityCode").value(hasItem(DEFAULT_NATIONALITY_CODE)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].personStartDate").value(hasItem(DEFAULT_PERSON_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].originalDateOfHire").value(hasItem(DEFAULT_ORIGINAL_DATE_OF_HIRE.toString())))
            .andExpect(jsonPath("$.[*].supervisorId").value(hasItem(DEFAULT_SUPERVISOR_ID)))
            .andExpect(jsonPath("$.[*].supervisorFullName").value(hasItem(DEFAULT_SUPERVISOR_FULL_NAME)))
            .andExpect(jsonPath("$.[*].parentDepartmentName").value(hasItem(DEFAULT_PARENT_DEPARTMENT_NAME)))
            .andExpect(jsonPath("$.[*].sectionId").value(hasItem(DEFAULT_SECTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].sectionName").value(hasItem(DEFAULT_SECTION_NAME)))
            .andExpect(jsonPath("$.[*].adjustedServiceDate").value(hasItem(DEFAULT_ADJUSTED_SERVICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].qualificationType").value(hasItem(DEFAULT_QUALIFICATION_TYPE)))
            .andExpect(jsonPath("$.[*].qualificationSpecification").value(hasItem(DEFAULT_QUALIFICATION_SPECIFICATION)));

        // Check, that the count call also returns 1
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    protected long getRepositoryCount() {
        return employeeRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Employee getPersistedEmployee(Employee employee) {
        return employeeRepository.findById(employee.getId()).orElseThrow();
    }

    protected void assertPersistedEmployeeToMatchAllProperties(Employee expectedEmployee) {
        assertEmployeeAllPropertiesEquals(expectedEmployee, getPersistedEmployee(expectedEmployee));
    }

    protected void assertPersistedEmployeeToMatchUpdatableProperties(Employee expectedEmployee) {
        assertEmployeeAllUpdatablePropertiesEquals(expectedEmployee, getPersistedEmployee(expectedEmployee));
    }
}
