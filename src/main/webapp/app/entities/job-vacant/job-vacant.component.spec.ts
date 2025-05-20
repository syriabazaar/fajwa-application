import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import JobVacant from './job-vacant.vue';
import JobVacantService from './job-vacant.service';
import AlertService from '@/shared/alert/alert.service';

type JobVacantComponentType = InstanceType<typeof JobVacant>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('JobVacant Management Component', () => {
    let jobVacantServiceStub: SinonStubbedInstance<JobVacantService>;
    let mountOptions: MountingOptions<JobVacantComponentType>['global'];

    beforeEach(() => {
      jobVacantServiceStub = sinon.createStubInstance<JobVacantService>(JobVacantService);
      jobVacantServiceStub.retrieve.resolves({ headers: {} });

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
          jobVacantService: () => jobVacantServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        jobVacantServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(JobVacant, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(jobVacantServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.jobVacants[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: JobVacantComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(JobVacant, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        jobVacantServiceStub.retrieve.reset();
        jobVacantServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        jobVacantServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeJobVacant();
        await comp.$nextTick(); // clear components

        // THEN
        expect(jobVacantServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(jobVacantServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
