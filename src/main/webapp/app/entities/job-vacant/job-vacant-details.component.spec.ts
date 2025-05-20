import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import JobVacantDetails from './job-vacant-details.vue';
import JobVacantService from './job-vacant.service';
import AlertService from '@/shared/alert/alert.service';

type JobVacantDetailsComponentType = InstanceType<typeof JobVacantDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const jobVacantSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('JobVacant Management Detail Component', () => {
    let jobVacantServiceStub: SinonStubbedInstance<JobVacantService>;
    let mountOptions: MountingOptions<JobVacantDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      jobVacantServiceStub = sinon.createStubInstance<JobVacantService>(JobVacantService);

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
          jobVacantService: () => jobVacantServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        jobVacantServiceStub.find.resolves(jobVacantSample);
        route = {
          params: {
            jobVacantId: `${123}`,
          },
        };
        const wrapper = shallowMount(JobVacantDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.jobVacant).toMatchObject(jobVacantSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        jobVacantServiceStub.find.resolves(jobVacantSample);
        const wrapper = shallowMount(JobVacantDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
