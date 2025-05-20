import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StandardCatUpdate from './standard-cat-update.vue';
import StandardCatService from './standard-cat.service';
import AlertService from '@/shared/alert/alert.service';

import StandardParentService from '@/entities/standard-parent/standard-parent.service';
import StandardReqAttachService from '@/entities/standard-req-attach/standard-req-attach.service';

type StandardCatUpdateComponentType = InstanceType<typeof StandardCatUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const standardCatSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<StandardCatUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('StandardCat Management Update Component', () => {
    let comp: StandardCatUpdateComponentType;
    let standardCatServiceStub: SinonStubbedInstance<StandardCatService>;

    beforeEach(() => {
      route = {};
      standardCatServiceStub = sinon.createStubInstance<StandardCatService>(StandardCatService);
      standardCatServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          standardCatService: () => standardCatServiceStub,
          standardParentService: () =>
            sinon.createStubInstance<StandardParentService>(StandardParentService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          standardReqAttachService: () =>
            sinon.createStubInstance<StandardReqAttachService>(StandardReqAttachService, {
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
        const wrapper = shallowMount(StandardCatUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.standardCat = standardCatSample;
        standardCatServiceStub.update.resolves(standardCatSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(standardCatServiceStub.update.calledWith(standardCatSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        standardCatServiceStub.create.resolves(entity);
        const wrapper = shallowMount(StandardCatUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.standardCat = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(standardCatServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        standardCatServiceStub.find.resolves(standardCatSample);
        standardCatServiceStub.retrieve.resolves([standardCatSample]);

        // WHEN
        route = {
          params: {
            standardCatId: `${standardCatSample.id}`,
          },
        };
        const wrapper = shallowMount(StandardCatUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.standardCat).toMatchObject(standardCatSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        standardCatServiceStub.find.resolves(standardCatSample);
        const wrapper = shallowMount(StandardCatUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
