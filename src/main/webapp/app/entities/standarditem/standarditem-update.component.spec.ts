import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StandarditemUpdate from './standarditem-update.vue';
import StandarditemService from './standarditem.service';
import AlertService from '@/shared/alert/alert.service';

import StandardCatService from '@/entities/standard-cat/standard-cat.service';

type StandarditemUpdateComponentType = InstanceType<typeof StandarditemUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const standarditemSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<StandarditemUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Standarditem Management Update Component', () => {
    let comp: StandarditemUpdateComponentType;
    let standarditemServiceStub: SinonStubbedInstance<StandarditemService>;

    beforeEach(() => {
      route = {};
      standarditemServiceStub = sinon.createStubInstance<StandarditemService>(StandarditemService);
      standarditemServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          standarditemService: () => standarditemServiceStub,
          standardCatService: () =>
            sinon.createStubInstance<StandardCatService>(StandardCatService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(StandarditemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.standarditem = standarditemSample;
        standarditemServiceStub.update.resolves(standarditemSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(standarditemServiceStub.update.calledWith(standarditemSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        standarditemServiceStub.create.resolves(entity);
        const wrapper = shallowMount(StandarditemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.standarditem = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(standarditemServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        standarditemServiceStub.find.resolves(standarditemSample);
        standarditemServiceStub.retrieve.resolves([standarditemSample]);

        // WHEN
        route = {
          params: {
            standarditemId: `${standarditemSample.id}`,
          },
        };
        const wrapper = shallowMount(StandarditemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.standarditem).toMatchObject(standarditemSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        standarditemServiceStub.find.resolves(standarditemSample);
        const wrapper = shallowMount(StandarditemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
