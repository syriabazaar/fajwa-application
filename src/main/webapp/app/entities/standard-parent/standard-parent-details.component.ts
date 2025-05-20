import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import StandardParentService from './standard-parent.service';
import { type IStandardParent } from '@/shared/model/standard-parent.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandardParentDetails',
  setup() {
    const standardParentService = inject('standardParentService', () => new StandardParentService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const standardParent: Ref<IStandardParent> = ref({});

    const retrieveStandardParent = async standardParentId => {
      try {
        const res = await standardParentService().find(standardParentId);
        standardParent.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.standardParentId) {
      retrieveStandardParent(route.params.standardParentId);
    }

    return {
      alertService,
      standardParent,

      previousState,
    };
  },
});
