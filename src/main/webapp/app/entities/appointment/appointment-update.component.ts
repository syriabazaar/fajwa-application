import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AppointmentService from './appointment.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import NominationService from '@/entities/nomination/nomination.service';
import { type INomination } from '@/shared/model/nomination.model';
import { Appointment, type IAppointment } from '@/shared/model/appointment.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AppointmentUpdate',
  setup() {
    const appointmentService = inject('appointmentService', () => new AppointmentService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const appointment: Ref<IAppointment> = ref(new Appointment());

    const nominationService = inject('nominationService', () => new NominationService());

    const nominations: Ref<INomination[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveAppointment = async appointmentId => {
      try {
        const res = await appointmentService().find(appointmentId);
        res.appDateTime = new Date(res.appDateTime);
        res.interveiewDate = new Date(res.interveiewDate);
        appointment.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.appointmentId) {
      retrieveAppointment(route.params.appointmentId);
    }

    const initRelationships = () => {
      nominationService()
        .retrieve()
        .then(res => {
          nominations.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      appDateTime: {},
      interveiewDate: {},
      nomination: {},
      intrvuStrdDtls: {},
    };
    const v$ = useVuelidate(validationRules, appointment as any);
    v$.value.$validate();

    return {
      appointmentService,
      alertService,
      appointment,
      previousState,
      isSaving,
      currentLanguage,
      nominations,
      v$,
      ...useDateFormat({ entityRef: appointment }),
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.appointment.id) {
        this.appointmentService()
          .update(this.appointment)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(`A Appointment is updated with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.appointmentService()
          .create(this.appointment)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(`A Appointment is created with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
