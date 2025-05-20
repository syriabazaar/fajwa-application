import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import StandarditemService from './standarditem.service';
import { type IStandarditem } from '@/shared/model/standarditem.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandarditemDetails',
  setup() {
    const standarditemService = inject('standarditemService', () => new StandarditemService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const standarditem: Ref<IStandarditem> = ref({});

    const retrieveStandarditem = async standarditemId => {
      try {
        const res = await standarditemService().find(standarditemId);
        standarditem.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.standarditemId) {
      retrieveStandarditem(route.params.standarditemId);
    }

    return {
      alertService,
      standarditem,

      previousState,
    };
  },
});
