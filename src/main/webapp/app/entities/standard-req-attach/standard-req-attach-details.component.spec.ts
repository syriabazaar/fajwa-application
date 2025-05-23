import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StandardReqAttachDetails from './standard-req-attach-details.vue';
import StandardReqAttachService from './standard-req-attach.service';
import AlertService from '@/shared/alert/alert.service';

type StandardReqAttachDetailsComponentType = InstanceType<typeof StandardReqAttachDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const standardReqAttachSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('StandardReqAttach Management Detail Component', () => {
    let standardReqAttachServiceStub: SinonStubbedInstance<StandardReqAttachService>;
    let mountOptions: MountingOptions<StandardReqAttachDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      standardReqAttachServiceStub = sinon.createStubInstance<StandardReqAttachService>(StandardReqAttachService);

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
          standardReqAttachService: () => standardReqAttachServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        standardReqAttachServiceStub.find.resolves(standardReqAttachSample);
        route = {
          params: {
            standardReqAttachId: `${123}`,
          },
        };
        const wrapper = shallowMount(StandardReqAttachDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.standardReqAttach).toMatchObject(standardReqAttachSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        standardReqAttachServiceStub.find.resolves(standardReqAttachSample);
        const wrapper = shallowMount(StandardReqAttachDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
