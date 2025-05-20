import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AppointmentDetails from './appointment-details.vue';
import AppointmentService from './appointment.service';
import AlertService from '@/shared/alert/alert.service';

type AppointmentDetailsComponentType = InstanceType<typeof AppointmentDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const appointmentSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Appointment Management Detail Component', () => {
    let appointmentServiceStub: SinonStubbedInstance<AppointmentService>;
    let mountOptions: MountingOptions<AppointmentDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      appointmentServiceStub = sinon.createStubInstance<AppointmentService>(AppointmentService);

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          appointmentService: () => appointmentServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        appointmentServiceStub.find.resolves(appointmentSample);
        route = {
          params: {
            appointmentId: `${123}`,
          },
        };
        const wrapper = shallowMount(AppointmentDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.appointment).toMatchObject(appointmentSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        appointmentServiceStub.find.resolves(appointmentSample);
        const wrapper = shallowMount(AppointmentDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
