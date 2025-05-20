import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StandardParentUpdate from './standard-parent-update.vue';
import StandardParentService from './standard-parent.service';
import AlertService from '@/shared/alert/alert.service';

import StandardTypeService from '@/entities/standard-type/standard-type.service';

type StandardParentUpdateComponentType = InstanceType<typeof StandardParentUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const standardParentSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<StandardParentUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('StandardParent Management Update Component', () => {
    let comp: StandardParentUpdateComponentType;
    let standardParentServiceStub: SinonStubbedInstance<StandardParentService>;

    beforeEach(() => {
      route = {};
      standardParentServiceStub = sinon.createStubInstance<StandardParentService>(StandardParentService);
      standardParentServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          standardParentService: () => standardParentServiceStub,
          standardTypeService: () =>
            sinon.createStubInstance<StandardTypeService>(StandardTypeService, {
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
        const wrapper = shallowMount(StandardParentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.standardParent = standardParentSample;
        standardParentServiceStub.update.resolves(standardParentSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(standardParentServiceStub.update.calledWith(standardParentSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        standardParentServiceStub.create.resolves(entity);
        const wrapper = shallowMount(StandardParentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.standardParent = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(standardParentServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        standardParentServiceStub.find.resolves(standardParentSample);
        standardParentServiceStub.retrieve.resolves([standardParentSample]);

        // WHEN
        route = {
          params: {
            standardParentId: `${standardParentSample.id}`,
          },
        };
        const wrapper = shallowMount(StandardParentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.standardParent).toMatchObject(standardParentSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        standardParentServiceStub.find.resolves(standardParentSample);
        const wrapper = shallowMount(StandardParentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
