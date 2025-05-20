import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import AppointmentService from './appointment.service';
import { useDateFormat } from '@/shared/composables';
import { type IAppointment } from '@/shared/model/appointment.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AppointmentDetails',
  setup() {
    const dateFormat = useDateFormat();
    const appointmentService = inject('appointmentService', () => new AppointmentService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const appointment: Ref<IAppointment> = ref({});

    const retrieveAppointment = async appointmentId => {
      try {
        const res = await appointmentService().find(appointmentId);
        appointment.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.appointmentId) {
      retrieveAppointment(route.params.appointmentId);
    }

    return {
      ...dateFormat,
      alertService,
      appointment,

      previousState,
    };
  },
});
