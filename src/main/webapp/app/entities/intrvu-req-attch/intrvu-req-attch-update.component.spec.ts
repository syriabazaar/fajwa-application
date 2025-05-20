import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import IntrvuReqAttchUpdate from './intrvu-req-attch-update.vue';
import IntrvuReqAttchService from './intrvu-req-attch.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import StandardReqAttachService from '@/entities/standard-req-attach/standard-req-attach.service';
import EmployeeService from '@/entities/employee/employee.service';

type IntrvuReqAttchUpdateComponentType = InstanceType<typeof IntrvuReqAttchUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const intrvuReqAttchSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<IntrvuReqAttchUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('IntrvuReqAttch Management Update Component', () => {
    let comp: IntrvuReqAttchUpdateComponentType;
    let intrvuReqAttchServiceStub: SinonStubbedInstance<IntrvuReqAttchService>;

    beforeEach(() => {
      route = {};
      intrvuReqAttchServiceStub = sinon.createStubInstance<IntrvuReqAttchService>(IntrvuReqAttchService);
      intrvuReqAttchServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          intrvuReqAttchService: () => intrvuReqAttchServiceStub,
          standardReqAttachService: () =>
            sinon.createStubInstance<StandardReqAttachService>(StandardReqAttachService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          employeeService: () =>
            sinon.createStubInstance<EmployeeService>(EmployeeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(IntrvuReqAttchUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(IntrvuReqAttchUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.intrvuReqAttch = intrvuReqAttchSample;
        intrvuReqAttchServiceStub.update.resolves(intrvuReqAttchSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(intrvuReqAttchServiceStub.update.calledWith(intrvuReqAttchSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        intrvuReqAttchServiceStub.create.resolves(entity);
        const wrapper = shallowMount(IntrvuReqAttchUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.intrvuReqAttch = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(intrvuReqAttchServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        intrvuReqAttchServiceStub.find.resolves(intrvuReqAttchSample);
        intrvuReqAttchServiceStub.retrieve.resolves([intrvuReqAttchSample]);

        // WHEN
        route = {
          params: {
            intrvuReqAttchId: `${intrvuReqAttchSample.id}`,
          },
        };
        const wrapper = shallowMount(IntrvuReqAttchUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.intrvuReqAttch).toMatchObject(intrvuReqAttchSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        intrvuReqAttchServiceStub.find.resolves(intrvuReqAttchSample);
        const wrapper = shallowMount(IntrvuReqAttchUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
