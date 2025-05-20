import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import IntrvuStrdDtlsDetails from './intrvu-strd-dtls-details.vue';
import IntrvuStrdDtlsService from './intrvu-strd-dtls.service';
import AlertService from '@/shared/alert/alert.service';

type IntrvuStrdDtlsDetailsComponentType = InstanceType<typeof IntrvuStrdDtlsDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const intrvuStrdDtlsSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('IntrvuStrdDtls Management Detail Component', () => {
    let intrvuStrdDtlsServiceStub: SinonStubbedInstance<IntrvuStrdDtlsService>;
    let mountOptions: MountingOptions<IntrvuStrdDtlsDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      intrvuStrdDtlsServiceStub = sinon.createStubInstance<IntrvuStrdDtlsService>(IntrvuStrdDtlsService);

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
          intrvuStrdDtlsService: () => intrvuStrdDtlsServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        intrvuStrdDtlsServiceStub.find.resolves(intrvuStrdDtlsSample);
        route = {
          params: {
            intrvuStrdDtlsId: `${123}`,
          },
        };
        const wrapper = shallowMount(IntrvuStrdDtlsDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.intrvuStrdDtls).toMatchObject(intrvuStrdDtlsSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        intrvuStrdDtlsServiceStub.find.resolves(intrvuStrdDtlsSample);
        const wrapper = shallowMount(IntrvuStrdDtlsDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
