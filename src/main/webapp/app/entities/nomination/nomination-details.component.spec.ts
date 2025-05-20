import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import NominationDetails from './nomination-details.vue';
import NominationService from './nomination.service';
import AlertService from '@/shared/alert/alert.service';

type NominationDetailsComponentType = InstanceType<typeof NominationDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const nominationSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Nomination Management Detail Component', () => {
    let nominationServiceStub: SinonStubbedInstance<NominationService>;
    let mountOptions: MountingOptions<NominationDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      nominationServiceStub = sinon.createStubInstance<NominationService>(NominationService);

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
          nominationService: () => nominationServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        nominationServiceStub.find.resolves(nominationSample);
        route = {
          params: {
            nominationId: `${123}`,
          },
        };
        const wrapper = shallowMount(NominationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.nomination).toMatchObject(nominationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        nominationServiceStub.find.resolves(nominationSample);
        const wrapper = shallowMount(NominationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
