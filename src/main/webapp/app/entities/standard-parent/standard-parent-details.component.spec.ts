import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StandardParentDetails from './standard-parent-details.vue';
import StandardParentService from './standard-parent.service';
import AlertService from '@/shared/alert/alert.service';

type StandardParentDetailsComponentType = InstanceType<typeof StandardParentDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const standardParentSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('StandardParent Management Detail Component', () => {
    let standardParentServiceStub: SinonStubbedInstance<StandardParentService>;
    let mountOptions: MountingOptions<StandardParentDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      standardParentServiceStub = sinon.createStubInstance<StandardParentService>(StandardParentService);

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
          standardParentService: () => standardParentServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        standardParentServiceStub.find.resolves(standardParentSample);
        route = {
          params: {
            standardParentId: `${123}`,
          },
        };
        const wrapper = shallowMount(StandardParentDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.standardParent).toMatchObject(standardParentSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        standardParentServiceStub.find.resolves(standardParentSample);
        const wrapper = shallowMount(StandardParentDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
