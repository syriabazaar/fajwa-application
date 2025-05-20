import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import StandardCatService from './standard-cat.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import StandardParentService from '@/entities/standard-parent/standard-parent.service';
import { type IStandardParent } from '@/shared/model/standard-parent.model';
import StandardReqAttachService from '@/entities/standard-req-attach/standard-req-attach.service';
import { type IStandardReqAttach } from '@/shared/model/standard-req-attach.model';
import { type IStandardCat, StandardCat } from '@/shared/model/standard-cat.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandardCatUpdate',
  setup() {
    const standardCatService = inject('standardCatService', () => new StandardCatService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const standardCat: Ref<IStandardCat> = ref(new StandardCat());

    const standardParentService = inject('standardParentService', () => new StandardParentService());

    const standardParents: Ref<IStandardParent[]> = ref([]);

    const standardReqAttachService = inject('standardReqAttachService', () => new StandardReqAttachService());

    const standardReqAttaches: Ref<IStandardReqAttach[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      standardParentService()
        .retrieve()
        .then(res => {
          standardParents.value = res.data;
        });
      standardReqAttachService()
        .retrieve()
        .then(res => {
          standardReqAttaches.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      name: {},
      standardParent: {},
      reqAttachment: {},
    };
    const v$ = useVuelidate(validationRules, standardCat as any);
    v$.value.$validate();

    return {
      standardCatService,
      alertService,
      standardCat,
      previousState,
      isSaving,
      currentLanguage,
      standardParents,
      standardReqAttaches,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.standardCat.id) {
        this.standardCatService()
          .update(this.standardCat)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(`A StandardCat is updated with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.standardCatService()
          .create(this.standardCat)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(`A StandardCat is created with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
