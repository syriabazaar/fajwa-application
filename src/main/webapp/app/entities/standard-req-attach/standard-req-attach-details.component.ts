import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import StandardReqAttachService from './standard-req-attach.service';
import { type IStandardReqAttach } from '@/shared/model/standard-req-attach.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandardReqAttachDetails',
  setup() {
    const standardReqAttachService = inject('standardReqAttachService', () => new StandardReqAttachService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const standardReqAttach: Ref<IStandardReqAttach> = ref({});

    const retrieveStandardReqAttach = async standardReqAttachId => {
      try {
        const res = await standardReqAttachService().find(standardReqAttachId);
        standardReqAttach.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.standardReqAttachId) {
      retrieveStandardReqAttach(route.params.standardReqAttachId);
    }

    return {
      alertService,
      standardReqAttach,

      previousState,
    };
  },
});
