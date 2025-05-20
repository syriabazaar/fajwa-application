import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import JobDesc from './job-desc.vue';
import JobDescService from './job-desc.service';
import AlertService from '@/shared/alert/alert.service';

type JobDescComponentType = InstanceType<typeof JobDesc>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('JobDesc Management Component', () => {
    let jobDescServiceStub: SinonStubbedInstance<JobDescService>;
    let mountOptions: MountingOptions<JobDescComponentType>['global'];

    beforeEach(() => {
      jobDescServiceStub = sinon.createStubInstance<JobDescService>(JobDescService);
      jobDescServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          jobDescService: () => jobDescServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        jobDescServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(JobDesc, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(jobDescServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.jobDescs[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: JobDescComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(JobDesc, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        jobDescServiceStub.retrieve.reset();
        jobDescServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });
    });
  });
});
