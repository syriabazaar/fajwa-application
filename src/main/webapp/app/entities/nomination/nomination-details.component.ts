import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import NominationService from './nomination.service';
import { type INomination } from '@/shared/model/nomination.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'NominationDetails',
  setup() {
    const nominationService = inject('nominationService', () => new NominationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const nomination: Ref<INomination> = ref({});

    const retrieveNomination = async nominationId => {
      try {
        const res = await nominationService().find(nominationId);
        nomination.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.nominationId) {
      retrieveNomination(route.params.nominationId);
    }

    return {
      alertService,
      nomination,

      previousState,
    };
  },
});
