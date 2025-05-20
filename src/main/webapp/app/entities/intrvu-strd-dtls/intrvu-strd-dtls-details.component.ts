import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import IntrvuStrdDtlsService from './intrvu-strd-dtls.service';
import { type IIntrvuStrdDtls } from '@/shared/model/intrvu-strd-dtls.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IntrvuStrdDtlsDetails',
  setup() {
    const intrvuStrdDtlsService = inject('intrvuStrdDtlsService', () => new IntrvuStrdDtlsService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const intrvuStrdDtls: Ref<IIntrvuStrdDtls> = ref({});

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

    return {
      alertService,
      intrvuStrdDtls,

      previousState,
    };
  },
});
