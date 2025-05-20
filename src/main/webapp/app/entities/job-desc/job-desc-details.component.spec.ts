import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import JobDescDetails from './job-desc-details.vue';
import JobDescService from './job-desc.service';
import AlertService from '@/shared/alert/alert.service';

type JobDescDetailsComponentType = InstanceType<typeof JobDescDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const jobDescSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('JobDesc Management Detail Component', () => {
    let jobDescServiceStub: SinonStubbedInstance<JobDescService>;
    let mountOptions: MountingOptions<JobDescDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      jobDescServiceStub = sinon.createStubInstance<JobDescService>(JobDescService);

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
          jobDescService: () => jobDescServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        jobDescServiceStub.find.resolves(jobDescSample);
        route = {
          params: {
            jobDescId: `${123}`,
          },
        };
        const wrapper = shallowMount(JobDescDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.jobDesc).toMatchObject(jobDescSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        jobDescServiceStub.find.resolves(jobDescSample);
        const wrapper = shallowMount(JobDescDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
