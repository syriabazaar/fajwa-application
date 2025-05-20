import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import StandarditemService from './standarditem.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import StandardCatService from '@/entities/standard-cat/standard-cat.service';
import { type IStandardCat } from '@/shared/model/standard-cat.model';
import { type IStandarditem, Standarditem } from '@/shared/model/standarditem.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandarditemUpdate',
  setup() {
    const standarditemService = inject('standarditemService', () => new StandarditemService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const standarditem: Ref<IStandarditem> = ref(new Standarditem());

    const standardCatService = inject('standardCatService', () => new StandardCatService());

    const standardCats: Ref<IStandardCat[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      standardCatService()
        .retrieve()
        .then(res => {
          standardCats.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      name: {},
      isActive: {},
      weightPercentage: {},
      standardCat: {},
      intrvuStrdDtls: {},
    };
    const v$ = useVuelidate(validationRules, standarditem as any);
    v$.value.$validate();

    return {
      standarditemService,
      alertService,
      standarditem,
      previousState,
      isSaving,
      currentLanguage,
      standardCats,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.standarditem.id) {
        this.standarditemService()
          .update(this.standarditem)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(`A Standarditem is updated with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.standarditemService()
          .create(this.standarditem)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(`A Standarditem is created with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
