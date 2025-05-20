import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import StandardCat from './standard-cat.vue';
import StandardCatService from './standard-cat.service';
import AlertService from '@/shared/alert/alert.service';

type StandardCatComponentType = InstanceType<typeof StandardCat>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('StandardCat Management Component', () => {
    let standardCatServiceStub: SinonStubbedInstance<StandardCatService>;
    let mountOptions: MountingOptions<StandardCatComponentType>['global'];

    beforeEach(() => {
      standardCatServiceStub = sinon.createStubInstance<StandardCatService>(StandardCatService);
      standardCatServiceStub.retrieve.resolves({ headers: {} });

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
          standardCatService: () => standardCatServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        standardCatServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(StandardCat, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(standardCatServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.standardCats[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: StandardCatComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(StandardCat, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        standardCatServiceStub.retrieve.reset();
        standardCatServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        standardCatServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeStandardCat();
        await comp.$nextTick(); // clear components

        // THEN
        expect(standardCatServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(standardCatServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
