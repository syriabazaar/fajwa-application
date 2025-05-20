import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import StandardReqAttachService from './standard-req-attach.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IStandardReqAttach, StandardReqAttach } from '@/shared/model/standard-req-attach.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandardReqAttachUpdate',
  setup() {
    const standardReqAttachService = inject('standardReqAttachService', () => new StandardReqAttachService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const standardReqAttach: Ref<IStandardReqAttach> = ref(new StandardReqAttach());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const validations = useValidation();
    const validationRules = {
      name: {},
      attDesc: {},
    };
    const v$ = useVuelidate(validationRules, standardReqAttach as any);
    v$.value.$validate();

    return {
      standardReqAttachService,
      alertService,
      standardReqAttach,
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
      if (this.standardReqAttach.id) {
        this.standardReqAttachService()
          .update(this.standardReqAttach)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(`A StandardReqAttach is updated with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.standardReqAttachService()
          .create(this.standardReqAttach)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(`A StandardReqAttach is created with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
