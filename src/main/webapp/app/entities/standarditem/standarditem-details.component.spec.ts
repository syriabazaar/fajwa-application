import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StandarditemDetails from './standarditem-details.vue';
import StandarditemService from './standarditem.service';
import AlertService from '@/shared/alert/alert.service';

type StandarditemDetailsComponentType = InstanceType<typeof StandarditemDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const standarditemSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Standarditem Management Detail Component', () => {
    let standarditemServiceStub: SinonStubbedInstance<StandarditemService>;
    let mountOptions: MountingOptions<StandarditemDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      standarditemServiceStub = sinon.createStubInstance<StandarditemService>(StandarditemService);

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
          standarditemService: () => standarditemServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        standarditemServiceStub.find.resolves(standarditemSample);
        route = {
          params: {
            standarditemId: `${123}`,
          },
        };
        const wrapper = shallowMount(StandarditemDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.standarditem).toMatchObject(standarditemSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        standarditemServiceStub.find.resolves(standarditemSample);
        const wrapper = shallowMount(StandarditemDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
