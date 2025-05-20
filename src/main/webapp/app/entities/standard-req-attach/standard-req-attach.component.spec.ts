import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import StandardReqAttach from './standard-req-attach.vue';
import StandardReqAttachService from './standard-req-attach.service';
import AlertService from '@/shared/alert/alert.service';

type StandardReqAttachComponentType = InstanceType<typeof StandardReqAttach>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('StandardReqAttach Management Component', () => {
    let standardReqAttachServiceStub: SinonStubbedInstance<StandardReqAttachService>;
    let mountOptions: MountingOptions<StandardReqAttachComponentType>['global'];

    beforeEach(() => {
      standardReqAttachServiceStub = sinon.createStubInstance<StandardReqAttachService>(StandardReqAttachService);
      standardReqAttachServiceStub.retrieve.resolves({ headers: {} });

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
          standardReqAttachService: () => standardReqAttachServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        standardReqAttachServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(StandardReqAttach, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(standardReqAttachServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.standardReqAttaches[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: StandardReqAttachComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(StandardReqAttach, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        standardReqAttachServiceStub.retrieve.reset();
        standardReqAttachServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        standardReqAttachServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeStandardReqAttach();
        await comp.$nextTick(); // clear components

        // THEN
        expect(standardReqAttachServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(standardReqAttachServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
