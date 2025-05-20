import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import IntrvuReqAttchService from './intrvu-req-attch.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useDateFormat } from '@/shared/composables';
import { type IIntrvuReqAttch } from '@/shared/model/intrvu-req-attch.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IntrvuReqAttchDetails',
  setup() {
    const dateFormat = useDateFormat();
    const intrvuReqAttchService = inject('intrvuReqAttchService', () => new IntrvuReqAttchService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const intrvuReqAttch: Ref<IIntrvuReqAttch> = ref({});

    const retrieveIntrvuReqAttch = async intrvuReqAttchId => {
      try {
        const res = await intrvuReqAttchService().find(intrvuReqAttchId);
        intrvuReqAttch.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.intrvuReqAttchId) {
      retrieveIntrvuReqAttch(route.params.intrvuReqAttchId);
    }

    return {
      ...dateFormat,
      alertService,
      intrvuReqAttch,

      ...dataUtils,

      previousState,
    };
  },
});
