import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StandardTypeUpdate from './standard-type-update.vue';
import StandardTypeService from './standard-type.service';
import AlertService from '@/shared/alert/alert.service';

type StandardTypeUpdateComponentType = InstanceType<typeof StandardTypeUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const standardTypeSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<StandardTypeUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('StandardType Management Update Component', () => {
    let comp: StandardTypeUpdateComponentType;
    let standardTypeServiceStub: SinonStubbedInstance<StandardTypeService>;

    beforeEach(() => {
      route = {};
      standardTypeServiceStub = sinon.createStubInstance<StandardTypeService>(StandardTypeService);
      standardTypeServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          standardTypeService: () => standardTypeServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(StandardTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.standardType = standardTypeSample;
        standardTypeServiceStub.update.resolves(standardTypeSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(standardTypeServiceStub.update.calledWith(standardTypeSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        standardTypeServiceStub.create.resolves(entity);
        const wrapper = shallowMount(StandardTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.standardType = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(standardTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        standardTypeServiceStub.find.resolves(standardTypeSample);
        standardTypeServiceStub.retrieve.resolves([standardTypeSample]);

        // WHEN
        route = {
          params: {
            standardTypeId: `${standardTypeSample.id}`,
          },
        };
        const wrapper = shallowMount(StandardTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.standardType).toMatchObject(standardTypeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        standardTypeServiceStub.find.resolves(standardTypeSample);
        const wrapper = shallowMount(StandardTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
