import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import NominationUpdate from './nomination-update.vue';
import NominationService from './nomination.service';
import AlertService from '@/shared/alert/alert.service';

import EmployeeService from '@/entities/employee/employee.service';
import JobVacantService from '@/entities/job-vacant/job-vacant.service';

type NominationUpdateComponentType = InstanceType<typeof NominationUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const nominationSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<NominationUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Nomination Management Update Component', () => {
    let comp: NominationUpdateComponentType;
    let nominationServiceStub: SinonStubbedInstance<NominationService>;

    beforeEach(() => {
      route = {};
      nominationServiceStub = sinon.createStubInstance<NominationService>(NominationService);
      nominationServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          nominationService: () => nominationServiceStub,
          employeeService: () =>
            sinon.createStubInstance<EmployeeService>(EmployeeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          jobVacantService: () =>
            sinon.createStubInstance<JobVacantService>(JobVacantService, {
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
        const wrapper = shallowMount(NominationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.nomination = nominationSample;
        nominationServiceStub.update.resolves(nominationSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(nominationServiceStub.update.calledWith(nominationSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        nominationServiceStub.create.resolves(entity);
        const wrapper = shallowMount(NominationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.nomination = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(nominationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        nominationServiceStub.find.resolves(nominationSample);
        nominationServiceStub.retrieve.resolves([nominationSample]);

        // WHEN
        route = {
          params: {
            nominationId: `${nominationSample.id}`,
          },
        };
        const wrapper = shallowMount(NominationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.nomination).toMatchObject(nominationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        nominationServiceStub.find.resolves(nominationSample);
        const wrapper = shallowMount(NominationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
