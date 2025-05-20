import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import StandardTypeService from './standard-type.service';
import { type IStandardType } from '@/shared/model/standard-type.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandardTypeDetails',
  setup() {
    const standardTypeService = inject('standardTypeService', () => new StandardTypeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const standardType: Ref<IStandardType> = ref({});

    const retrieveStandardType = async standardTypeId => {
      try {
        const res = await standardTypeService().find(standardTypeId);
        standardType.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.standardTypeId) {
      retrieveStandardType(route.params.standardTypeId);
    }

    return {
      alertService,
      standardType,

      previousState,
    };
  },
});
