import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import IntrvuStrdDtlsService from './intrvu-strd-dtls.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import StandarditemService from '@/entities/standarditem/standarditem.service';
import { type IStandarditem } from '@/shared/model/standarditem.model';
import AppointmentService from '@/entities/appointment/appointment.service';
import { type IAppointment } from '@/shared/model/appointment.model';
import { type IIntrvuStrdDtls, IntrvuStrdDtls } from '@/shared/model/intrvu-strd-dtls.model';
import { StandardOption } from '@/shared/model/enumerations/standard-option.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IntrvuStrdDtlsUpdate',
  setup() {
    const intrvuStrdDtlsService = inject('intrvuStrdDtlsService', () => new IntrvuStrdDtlsService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const intrvuStrdDtls: Ref<IIntrvuStrdDtls> = ref(new IntrvuStrdDtls());

    const standarditemService = inject('standarditemService', () => new StandarditemService());

    const standarditems: Ref<IStandarditem[]> = ref([]);

    const appointmentService = inject('appointmentService', () => new AppointmentService());

    const appointments: Ref<IAppointment[]> = ref([]);
    const standardOptionValues: Ref<string[]> = ref(Object.keys(StandardOption));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveIntrvuStrdDtls = async intrvuStrdDtlsId => {
      try {
        const res = await intrvuStrdDtlsService().find(intrvuStrdDtlsId);
        intrvuStrdDtls.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.intrvuStrdDtlsId) {
      retrieveIntrvuStrdDtls(route.params.intrvuStrdDtlsId);
    }

    const initRelationships = () => {
      standarditemService()
        .retrieve()
        .then(res => {
          standarditems.value = res.data;
        });
      appointmentService()
        .retrieve()
        .then(res => {
          appointments.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      standardOption: {},
      standarditem: {},
      appointment: {},
    };
    const v$ = useVuelidate(validationRules, intrvuStrdDtls as any);
    v$.value.$validate();

    return {
      intrvuStrdDtlsService,
      alertService,
      intrvuStrdDtls,
      previousState,
      standardOptionValues,
      isSaving,
      currentLanguage,
      standarditems,
      appointments,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.intrvuStrdDtls.id) {
        this.intrvuStrdDtlsService()
          .update(this.intrvuStrdDtls)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(`A IntrvuStrdDtls is updated with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.intrvuStrdDtlsService()
          .create(this.intrvuStrdDtls)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(`A IntrvuStrdDtls is created with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
