import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import AppointmentUpdate from './appointment-update.vue';
import AppointmentService from './appointment.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import NominationService from '@/entities/nomination/nomination.service';

type AppointmentUpdateComponentType = InstanceType<typeof AppointmentUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const appointmentSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<AppointmentUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Appointment Management Update Component', () => {
    let comp: AppointmentUpdateComponentType;
    let appointmentServiceStub: SinonStubbedInstance<AppointmentService>;

    beforeEach(() => {
      route = {};
      appointmentServiceStub = sinon.createStubInstance<AppointmentService>(AppointmentService);
      appointmentServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          appointmentService: () => appointmentServiceStub,
          nominationService: () =>
            sinon.createStubInstance<NominationService>(NominationService, {
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
        const wrapper = shallowMount(AppointmentUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(AppointmentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.appointment = appointmentSample;
        appointmentServiceStub.update.resolves(appointmentSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(appointmentServiceStub.update.calledWith(appointmentSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        appointmentServiceStub.create.resolves(entity);
        const wrapper = shallowMount(AppointmentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.appointment = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(appointmentServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        appointmentServiceStub.find.resolves(appointmentSample);
        appointmentServiceStub.retrieve.resolves([appointmentSample]);

        // WHEN
        route = {
          params: {
            appointmentId: `${appointmentSample.id}`,
          },
        };
        const wrapper = shallowMount(AppointmentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.appointment).toMatchObject(appointmentSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        appointmentServiceStub.find.resolves(appointmentSample);
        const wrapper = shallowMount(AppointmentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
