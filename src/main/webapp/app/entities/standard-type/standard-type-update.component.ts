import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import StandardTypeService from './standard-type.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IStandardType, StandardType } from '@/shared/model/standard-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandardTypeUpdate',
  setup() {
    const standardTypeService = inject('standardTypeService', () => new StandardTypeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const standardType: Ref<IStandardType> = ref(new StandardType());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {};

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      name: {},
      jobDescs: {},
    };
    const v$ = useVuelidate(validationRules, standardType as any);
    v$.value.$validate();

    return {
      standardTypeService,
      alertService,
      standardType,
      previousState,
      isSaving,
      currentLanguage,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.standardType.id) {
        this.standardTypeService()
          .update(this.standardType)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(`A StandardType is updated with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.standardTypeService()
          .create(this.standardType)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(`A StandardType is created with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
