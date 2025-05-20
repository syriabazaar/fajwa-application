import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StandardCatDetails from './standard-cat-details.vue';
import StandardCatService from './standard-cat.service';
import AlertService from '@/shared/alert/alert.service';

type StandardCatDetailsComponentType = InstanceType<typeof StandardCatDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const standardCatSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('StandardCat Management Detail Component', () => {
    let standardCatServiceStub: SinonStubbedInstance<StandardCatService>;
    let mountOptions: MountingOptions<StandardCatDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      standardCatServiceStub = sinon.createStubInstance<StandardCatService>(StandardCatService);

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
          standardCatService: () => standardCatServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        standardCatServiceStub.find.resolves(standardCatSample);
        route = {
          params: {
            standardCatId: `${123}`,
          },
        };
        const wrapper = shallowMount(StandardCatDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.standardCat).toMatchObject(standardCatSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        standardCatServiceStub.find.resolves(standardCatSample);
        const wrapper = shallowMount(StandardCatDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
