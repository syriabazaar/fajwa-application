import { Authority } from '@/shared/security/authority';
const Entities = () => import('@/entities/entities.vue');

const Employee = () => import('@/entities/employee/employee.vue');
const EmployeeDetails = () => import('@/entities/employee/employee-details.vue');

const StandardType = () => import('@/entities/standard-type/standard-type.vue');
const StandardTypeUpdate = () => import('@/entities/standard-type/standard-type-update.vue');
const StandardTypeDetails = () => import('@/entities/standard-type/standard-type-details.vue');

const StandardParent = () => import('@/entities/standard-parent/standard-parent.vue');
const StandardParentUpdate = () => import('@/entities/standard-parent/standard-parent-update.vue');
const StandardParentDetails = () => import('@/entities/standard-parent/standard-parent-details.vue');

const StandardCat = () => import('@/entities/standard-cat/standard-cat.vue');
const StandardCatUpdate = () => import('@/entities/standard-cat/standard-cat-update.vue');
const StandardCatDetails = () => import('@/entities/standard-cat/standard-cat-details.vue');

const Standarditem = () => import('@/entities/standarditem/standarditem.vue');
const StandarditemUpdate = () => import('@/entities/standarditem/standarditem-update.vue');
const StandarditemDetails = () => import('@/entities/standarditem/standarditem-details.vue');

const StandardReqAttach = () => import('@/entities/standard-req-attach/standard-req-attach.vue');
const StandardReqAttachUpdate = () => import('@/entities/standard-req-attach/standard-req-attach-update.vue');
const StandardReqAttachDetails = () => import('@/entities/standard-req-attach/standard-req-attach-details.vue');

const IntrvuStrdDtls = () => import('@/entities/intrvu-strd-dtls/intrvu-strd-dtls.vue');
const IntrvuStrdDtlsUpdate = () => import('@/entities/intrvu-strd-dtls/intrvu-strd-dtls-update.vue');
const IntrvuStrdDtlsDetails = () => import('@/entities/intrvu-strd-dtls/intrvu-strd-dtls-details.vue');

const IntrvuReqAttch = () => import('@/entities/intrvu-req-attch/intrvu-req-attch.vue');
const IntrvuReqAttchUpdate = () => import('@/entities/intrvu-req-attch/intrvu-req-attch-update.vue');
const IntrvuReqAttchDetails = () => import('@/entities/intrvu-req-attch/intrvu-req-attch-details.vue');

const Nomination = () => import('@/entities/nomination/nomination.vue');
const NominationUpdate = () => import('@/entities/nomination/nomination-update.vue');
const NominationDetails = () => import('@/entities/nomination/nomination-details.vue');

const Appointment = () => import('@/entities/appointment/appointment.vue');
const AppointmentUpdate = () => import('@/entities/appointment/appointment-update.vue');
const AppointmentDetails = () => import('@/entities/appointment/appointment-details.vue');

const JobVacant = () => import('@/entities/job-vacant/job-vacant.vue');
const JobVacantUpdate = () => import('@/entities/job-vacant/job-vacant-update.vue');
const JobVacantDetails = () => import('@/entities/job-vacant/job-vacant-details.vue');

const JobDesc = () => import('@/entities/job-desc/job-desc.vue');
const JobDescDetails = () => import('@/entities/job-desc/job-desc-details.vue');

const Department = () => import('@/entities/department/department.vue');
const DepartmentUpdate = () => import('@/entities/department/department-update.vue');
const DepartmentDetails = () => import('@/entities/department/department-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'employee',
      name: 'Employee',
      component: Employee,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'employee/:employeeId/view',
      name: 'EmployeeView',
      component: EmployeeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-type',
      name: 'StandardType',
      component: StandardType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-type/new',
      name: 'StandardTypeCreate',
      component: StandardTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-type/:standardTypeId/edit',
      name: 'StandardTypeEdit',
      component: StandardTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-type/:standardTypeId/view',
      name: 'StandardTypeView',
      component: StandardTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-parent',
      name: 'StandardParent',
      component: StandardParent,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-parent/new',
      name: 'StandardParentCreate',
      component: StandardParentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-parent/:standardParentId/edit',
      name: 'StandardParentEdit',
      component: StandardParentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-parent/:standardParentId/view',
      name: 'StandardParentView',
      component: StandardParentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-cat',
      name: 'StandardCat',
      component: StandardCat,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-cat/new',
      name: 'StandardCatCreate',
      component: StandardCatUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-cat/:standardCatId/edit',
      name: 'StandardCatEdit',
      component: StandardCatUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-cat/:standardCatId/view',
      name: 'StandardCatView',
      component: StandardCatDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standarditem',
      name: 'Standarditem',
      component: Standarditem,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standarditem/new',
      name: 'StandarditemCreate',
      component: StandarditemUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standarditem/:standarditemId/edit',
      name: 'StandarditemEdit',
      component: StandarditemUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standarditem/:standarditemId/view',
      name: 'StandarditemView',
      component: StandarditemDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-req-attach',
      name: 'StandardReqAttach',
      component: StandardReqAttach,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-req-attach/new',
      name: 'StandardReqAttachCreate',
      component: StandardReqAttachUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-req-attach/:standardReqAttachId/edit',
      name: 'StandardReqAttachEdit',
      component: StandardReqAttachUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'standard-req-attach/:standardReqAttachId/view',
      name: 'StandardReqAttachView',
      component: StandardReqAttachDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'intrvu-strd-dtls',
      name: 'IntrvuStrdDtls',
      component: IntrvuStrdDtls,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'intrvu-strd-dtls/new',
      name: 'IntrvuStrdDtlsCreate',
      component: IntrvuStrdDtlsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'intrvu-strd-dtls/:intrvuStrdDtlsId/edit',
      name: 'IntrvuStrdDtlsEdit',
      component: IntrvuStrdDtlsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'intrvu-strd-dtls/:intrvuStrdDtlsId/view',
      name: 'IntrvuStrdDtlsView',
      component: IntrvuStrdDtlsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'intrvu-req-attch',
      name: 'IntrvuReqAttch',
      component: IntrvuReqAttch,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'intrvu-req-attch/new',
      name: 'IntrvuReqAttchCreate',
      component: IntrvuReqAttchUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'intrvu-req-attch/:intrvuReqAttchId/edit',
      name: 'IntrvuReqAttchEdit',
      component: IntrvuReqAttchUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'intrvu-req-attch/:intrvuReqAttchId/view',
      name: 'IntrvuReqAttchView',
      component: IntrvuReqAttchDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nomination',
      name: 'Nomination',
      component: Nomination,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nomination/new',
      name: 'NominationCreate',
      component: NominationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nomination/:nominationId/edit',
      name: 'NominationEdit',
      component: NominationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nomination/:nominationId/view',
      name: 'NominationView',
      component: NominationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appointment',
      name: 'Appointment',
      component: Appointment,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appointment/new',
      name: 'AppointmentCreate',
      component: AppointmentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appointment/:appointmentId/edit',
      name: 'AppointmentEdit',
      component: AppointmentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appointment/:appointmentId/view',
      name: 'AppointmentView',
      component: AppointmentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-vacant',
      name: 'JobVacant',
      component: JobVacant,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-vacant/new',
      name: 'JobVacantCreate',
      component: JobVacantUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-vacant/:jobVacantId/edit',
      name: 'JobVacantEdit',
      component: JobVacantUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-vacant/:jobVacantId/view',
      name: 'JobVacantView',
      component: JobVacantDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-desc',
      name: 'JobDesc',
      component: JobDesc,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-desc/:jobDescId/view',
      name: 'JobDescView',
      component: JobDescDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'department',
      name: 'Department',
      component: Department,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'department/new',
      name: 'DepartmentCreate',
      component: DepartmentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'department/:departmentId/edit',
      name: 'DepartmentEdit',
      component: DepartmentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'department/:departmentId/view',
      name: 'DepartmentView',
      component: DepartmentDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
