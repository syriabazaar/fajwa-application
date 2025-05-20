import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import IntrvuReqAttchDetails from './intrvu-req-attch-details.vue';
import IntrvuReqAttchService from './intrvu-req-attch.service';
import AlertService from '@/shared/alert/alert.service';

type IntrvuReqAttchDetailsComponentType = InstanceType<typeof IntrvuReqAttchDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const intrvuReqAttchSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('IntrvuReqAttch Management Detail Component', () => {
    let intrvuReqAttchServiceStub: SinonStubbedInstance<IntrvuReqAttchService>;
    let mountOptions: MountingOptions<IntrvuReqAttchDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      intrvuReqAttchServiceStub = sinon.createStubInstance<IntrvuReqAttchService>(IntrvuReqAttchService);

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
          intrvuReqAttchService: () => intrvuReqAttchServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        intrvuReqAttchServiceStub.find.resolves(intrvuReqAttchSample);
        route = {
          params: {
            intrvuReqAttchId: `${123}`,
          },
        };
        const wrapper = shallowMount(IntrvuReqAttchDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.intrvuReqAttch).toMatchObject(intrvuReqAttchSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        intrvuReqAttchServiceStub.find.resolves(intrvuReqAttchSample);
        const wrapper = shallowMount(IntrvuReqAttchDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
