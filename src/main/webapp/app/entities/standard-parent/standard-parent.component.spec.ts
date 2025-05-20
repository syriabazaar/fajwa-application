import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import StandardParent from './standard-parent.vue';
import StandardParentService from './standard-parent.service';
import AlertService from '@/shared/alert/alert.service';

type StandardParentComponentType = InstanceType<typeof StandardParent>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('StandardParent Management Component', () => {
    let standardParentServiceStub: SinonStubbedInstance<StandardParentService>;
    let mountOptions: MountingOptions<StandardParentComponentType>['global'];

    beforeEach(() => {
      standardParentServiceStub = sinon.createStubInstance<StandardParentService>(StandardParentService);
      standardParentServiceStub.retrieve.resolves({ headers: {} });

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
          standardParentService: () => standardParentServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        standardParentServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(StandardParent, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(standardParentServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.standardParents[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: StandardParentComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(StandardParent, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        standardParentServiceStub.retrieve.reset();
        standardParentServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        standardParentServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeStandardParent();
        await comp.$nextTick(); // clear components

        // THEN
        expect(standardParentServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(standardParentServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
