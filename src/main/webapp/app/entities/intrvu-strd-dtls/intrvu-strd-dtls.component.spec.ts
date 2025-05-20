import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import IntrvuStrdDtls from './intrvu-strd-dtls.vue';
import IntrvuStrdDtlsService from './intrvu-strd-dtls.service';
import AlertService from '@/shared/alert/alert.service';

type IntrvuStrdDtlsComponentType = InstanceType<typeof IntrvuStrdDtls>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('IntrvuStrdDtls Management Component', () => {
    let intrvuStrdDtlsServiceStub: SinonStubbedInstance<IntrvuStrdDtlsService>;
    let mountOptions: MountingOptions<IntrvuStrdDtlsComponentType>['global'];

    beforeEach(() => {
      intrvuStrdDtlsServiceStub = sinon.createStubInstance<IntrvuStrdDtlsService>(IntrvuStrdDtlsService);
      intrvuStrdDtlsServiceStub.retrieve.resolves({ headers: {} });

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
          intrvuStrdDtlsService: () => intrvuStrdDtlsServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        intrvuStrdDtlsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(IntrvuStrdDtls, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(intrvuStrdDtlsServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.intrvuStrdDtls[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: IntrvuStrdDtlsComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(IntrvuStrdDtls, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        intrvuStrdDtlsServiceStub.retrieve.reset();
        intrvuStrdDtlsServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        intrvuStrdDtlsServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeIntrvuStrdDtls();
        await comp.$nextTick(); // clear components

        // THEN
        expect(intrvuStrdDtlsServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(intrvuStrdDtlsServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
