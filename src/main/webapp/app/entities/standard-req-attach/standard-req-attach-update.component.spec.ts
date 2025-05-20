import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StandardReqAttachUpdate from './standard-req-attach-update.vue';
import StandardReqAttachService from './standard-req-attach.service';
import AlertService from '@/shared/alert/alert.service';

type StandardReqAttachUpdateComponentType = InstanceType<typeof StandardReqAttachUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const standardReqAttachSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<StandardReqAttachUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('StandardReqAttach Management Update Component', () => {
    let comp: StandardReqAttachUpdateComponentType;
    let standardReqAttachServiceStub: SinonStubbedInstance<StandardReqAttachService>;

    beforeEach(() => {
      route = {};
      standardReqAttachServiceStub = sinon.createStubInstance<StandardReqAttachService>(StandardReqAttachService);
      standardReqAttachServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          standardReqAttachService: () => standardReqAttachServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(StandardReqAttachUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.standardReqAttach = standardReqAttachSample;
        standardReqAttachServiceStub.update.resolves(standardReqAttachSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(standardReqAttachServiceStub.update.calledWith(standardReqAttachSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        standardReqAttachServiceStub.create.resolves(entity);
        const wrapper = shallowMount(StandardReqAttachUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.standardReqAttach = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(standardReqAttachServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        standardReqAttachServiceStub.find.resolves(standardReqAttachSample);
        standardReqAttachServiceStub.retrieve.resolves([standardReqAttachSample]);

        // WHEN
        route = {
          params: {
            standardReqAttachId: `${standardReqAttachSample.id}`,
          },
        };
        const wrapper = shallowMount(StandardReqAttachUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.standardReqAttach).toMatchObject(standardReqAttachSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        standardReqAttachServiceStub.find.resolves(standardReqAttachSample);
        const wrapper = shallowMount(StandardReqAttachUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
