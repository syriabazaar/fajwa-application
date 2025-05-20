import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import IntrvuStrdDtlsUpdate from './intrvu-strd-dtls-update.vue';
import IntrvuStrdDtlsService from './intrvu-strd-dtls.service';
import AlertService from '@/shared/alert/alert.service';

import StandarditemService from '@/entities/standarditem/standarditem.service';
import AppointmentService from '@/entities/appointment/appointment.service';

type IntrvuStrdDtlsUpdateComponentType = InstanceType<typeof IntrvuStrdDtlsUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const intrvuStrdDtlsSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<IntrvuStrdDtlsUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('IntrvuStrdDtls Management Update Component', () => {
    let comp: IntrvuStrdDtlsUpdateComponentType;
    let intrvuStrdDtlsServiceStub: SinonStubbedInstance<IntrvuStrdDtlsService>;

    beforeEach(() => {
      route = {};
      intrvuStrdDtlsServiceStub = sinon.createStubInstance<IntrvuStrdDtlsService>(IntrvuStrdDtlsService);
      intrvuStrdDtlsServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          intrvuStrdDtlsService: () => intrvuStrdDtlsServiceStub,
          standarditemService: () =>
            sinon.createStubInstance<StandarditemService>(StandarditemService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          appointmentService: () =>
            sinon.createStubInstance<AppointmentService>(AppointmentService, {
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
        const wrapper = shallowMount(IntrvuStrdDtlsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.intrvuStrdDtls = intrvuStrdDtlsSample;
        intrvuStrdDtlsServiceStub.update.resolves(intrvuStrdDtlsSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(intrvuStrdDtlsServiceStub.update.calledWith(intrvuStrdDtlsSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        intrvuStrdDtlsServiceStub.create.resolves(entity);
        const wrapper = shallowMount(IntrvuStrdDtlsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.intrvuStrdDtls = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(intrvuStrdDtlsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        intrvuStrdDtlsServiceStub.find.resolves(intrvuStrdDtlsSample);
        intrvuStrdDtlsServiceStub.retrieve.resolves([intrvuStrdDtlsSample]);

        // WHEN
        route = {
          params: {
            intrvuStrdDtlsId: `${intrvuStrdDtlsSample.id}`,
          },
        };
        const wrapper = shallowMount(IntrvuStrdDtlsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.intrvuStrdDtls).toMatchObject(intrvuStrdDtlsSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        intrvuStrdDtlsServiceStub.find.resolves(intrvuStrdDtlsSample);
        const wrapper = shallowMount(IntrvuStrdDtlsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
