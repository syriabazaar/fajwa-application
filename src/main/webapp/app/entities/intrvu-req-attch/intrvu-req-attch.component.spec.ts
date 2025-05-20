import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import IntrvuReqAttch from './intrvu-req-attch.vue';
import IntrvuReqAttchService from './intrvu-req-attch.service';
import AlertService from '@/shared/alert/alert.service';

type IntrvuReqAttchComponentType = InstanceType<typeof IntrvuReqAttch>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('IntrvuReqAttch Management Component', () => {
    let intrvuReqAttchServiceStub: SinonStubbedInstance<IntrvuReqAttchService>;
    let mountOptions: MountingOptions<IntrvuReqAttchComponentType>['global'];

    beforeEach(() => {
      intrvuReqAttchServiceStub = sinon.createStubInstance<IntrvuReqAttchService>(IntrvuReqAttchService);
      intrvuReqAttchServiceStub.retrieve.resolves({ headers: {} });

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
          intrvuReqAttchService: () => intrvuReqAttchServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        intrvuReqAttchServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(IntrvuReqAttch, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(intrvuReqAttchServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.intrvuReqAttches[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: IntrvuReqAttchComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(IntrvuReqAttch, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        intrvuReqAttchServiceStub.retrieve.reset();
        intrvuReqAttchServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        intrvuReqAttchServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeIntrvuReqAttch();
        await comp.$nextTick(); // clear components

        // THEN
        expect(intrvuReqAttchServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(intrvuReqAttchServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
