import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import StandardType from './standard-type.vue';
import StandardTypeService from './standard-type.service';
import AlertService from '@/shared/alert/alert.service';

type StandardTypeComponentType = InstanceType<typeof StandardType>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('StandardType Management Component', () => {
    let standardTypeServiceStub: SinonStubbedInstance<StandardTypeService>;
    let mountOptions: MountingOptions<StandardTypeComponentType>['global'];

    beforeEach(() => {
      standardTypeServiceStub = sinon.createStubInstance<StandardTypeService>(StandardTypeService);
      standardTypeServiceStub.retrieve.resolves({ headers: {} });

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
          standardTypeService: () => standardTypeServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        standardTypeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(StandardType, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(standardTypeServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.standardTypes[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: StandardTypeComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(StandardType, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        standardTypeServiceStub.retrieve.reset();
        standardTypeServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        standardTypeServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeStandardType();
        await comp.$nextTick(); // clear components

        // THEN
        expect(standardTypeServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(standardTypeServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
