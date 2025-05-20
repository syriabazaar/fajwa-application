import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import JobVacantUpdate from './job-vacant-update.vue';
import JobVacantService from './job-vacant.service';
import AlertService from '@/shared/alert/alert.service';

import DepartmentService from '@/entities/department/department.service';
import JobDescService from '@/entities/job-desc/job-desc.service';

type JobVacantUpdateComponentType = InstanceType<typeof JobVacantUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const jobVacantSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<JobVacantUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('JobVacant Management Update Component', () => {
    let comp: JobVacantUpdateComponentType;
    let jobVacantServiceStub: SinonStubbedInstance<JobVacantService>;

    beforeEach(() => {
      route = {};
      jobVacantServiceStub = sinon.createStubInstance<JobVacantService>(JobVacantService);
      jobVacantServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          jobVacantService: () => jobVacantServiceStub,
          departmentService: () =>
            sinon.createStubInstance<DepartmentService>(DepartmentService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          jobDescService: () =>
            sinon.createStubInstance<JobDescService>(JobDescService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(JobVacantUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.jobVacant = jobVacantSample;
        jobVacantServiceStub.update.resolves(jobVacantSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(jobVacantServiceStub.update.calledWith(jobVacantSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        jobVacantServiceStub.create.resolves(entity);
        const wrapper = shallowMount(JobVacantUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.jobVacant = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(jobVacantServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        jobVacantServiceStub.find.resolves(jobVacantSample);
        jobVacantServiceStub.retrieve.resolves([jobVacantSample]);

        // WHEN
        route = {
          params: {
            jobVacantId: `${jobVacantSample.id}`,
          },
        };
        const wrapper = shallowMount(JobVacantUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.jobVacant).toMatchObject(jobVacantSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        jobVacantServiceStub.find.resolves(jobVacantSample);
        const wrapper = shallowMount(JobVacantUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
