import { defineComponent, provide } from 'vue';

import EmployeeService from './employee/employee.service';
import StandardTypeService from './standard-type/standard-type.service';
import StandardParentService from './standard-parent/standard-parent.service';
import StandardCatService from './standard-cat/standard-cat.service';
import StandarditemService from './standarditem/standarditem.service';
import StandardReqAttachService from './standard-req-attach/standard-req-attach.service';
import IntrvuStrdDtlsService from './intrvu-strd-dtls/intrvu-strd-dtls.service';
import IntrvuReqAttchService from './intrvu-req-attch/intrvu-req-attch.service';
import NominationService from './nomination/nomination.service';
import AppointmentService from './appointment/appointment.service';
import JobVacantService from './job-vacant/job-vacant.service';
import JobDescService from './job-desc/job-desc.service';
import DepartmentService from './department/department.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('employeeService', () => new EmployeeService());
    provide('standardTypeService', () => new StandardTypeService());
    provide('standardParentService', () => new StandardParentService());
    provide('standardCatService', () => new StandardCatService());
    provide('standarditemService', () => new StandarditemService());
    provide('standardReqAttachService', () => new StandardReqAttachService());
    provide('intrvuStrdDtlsService', () => new IntrvuStrdDtlsService());
    provide('intrvuReqAttchService', () => new IntrvuReqAttchService());
    provide('nominationService', () => new NominationService());
    provide('appointmentService', () => new AppointmentService());
    provide('jobVacantService', () => new JobVacantService());
    provide('jobDescService', () => new JobDescService());
    provide('departmentService', () => new DepartmentService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
