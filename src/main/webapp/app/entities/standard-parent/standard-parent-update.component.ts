import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import StandardParentService from './standard-parent.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import StandardTypeService from '@/entities/standard-type/standard-type.service';
import { type IStandardType } from '@/shared/model/standard-type.model';
import { type IStandardParent, StandardParent } from '@/shared/model/standard-parent.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandardParentUpdate',
  setup() {
    const standardParentService = inject('standardParentService', () => new StandardParentService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const standardParent: Ref<IStandardParent> = ref(new StandardParent());

    const standardTypeService = inject('standardTypeService', () => new StandardTypeService());

    const standardTypes: Ref<IStandardType[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      standardTypeService()
        .retrieve()
        .then(res => {
          standardTypes.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      name: {},
      standardType: {},
    };
    const v$ = useVuelidate(validationRules, standardParent as any);
    v$.value.$validate();

    return {
      standardParentService,
      alertService,
      standardParent,
      previousState,
      isSaving,
      currentLanguage,
      standardTypes,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.standardParent.id) {
        this.standardParentService()
          .update(this.standardParent)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(`A StandardParent is updated with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.standardParentService()
          .create(this.standardParent)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(`A StandardParent is created with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
