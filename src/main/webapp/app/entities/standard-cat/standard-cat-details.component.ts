import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import StandardCatService from './standard-cat.service';
import { type IStandardCat } from '@/shared/model/standard-cat.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandardCatDetails',
  setup() {
    const standardCatService = inject('standardCatService', () => new StandardCatService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const standardCat: Ref<IStandardCat> = ref({});

    const retrieveStandardCat = async standardCatId => {
      try {
        const res = await standardCatService().find(standardCatId);
        standardCat.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.standardCatId) {
      retrieveStandardCat(route.params.standardCatId);
    }

    return {
      alertService,
      standardCat,

      previousState,
    };
  },
});
