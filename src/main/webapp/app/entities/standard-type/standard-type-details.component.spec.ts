import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StandardTypeDetails from './standard-type-details.vue';
import StandardTypeService from './standard-type.service';
import AlertService from '@/shared/alert/alert.service';

type StandardTypeDetailsComponentType = InstanceType<typeof StandardTypeDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const standardTypeSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('StandardType Management Detail Component', () => {
    let standardTypeServiceStub: SinonStubbedInstance<StandardTypeService>;
    let mountOptions: MountingOptions<StandardTypeDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      standardTypeServiceStub = sinon.createStubInstance<StandardTypeService>(StandardTypeService);

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
          standardTypeService: () => standardTypeServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        standardTypeServiceStub.find.resolves(standardTypeSample);
        route = {
          params: {
            standardTypeId: `${123}`,
          },
        };
        const wrapper = shallowMount(StandardTypeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.standardType).toMatchObject(standardTypeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        standardTypeServiceStub.find.resolves(standardTypeSample);
        const wrapper = shallowMount(StandardTypeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
