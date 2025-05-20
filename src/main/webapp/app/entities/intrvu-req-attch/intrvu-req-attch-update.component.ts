import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import IntrvuReqAttchService from './intrvu-req-attch.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import StandardReqAttachService from '@/entities/standard-req-attach/standard-req-attach.service';
import { type IStandardReqAttach } from '@/shared/model/standard-req-attach.model';
import EmployeeService from '@/entities/employee/employee.service';
import { type IEmployee } from '@/shared/model/employee.model';
import { type IIntrvuReqAttch, IntrvuReqAttch } from '@/shared/model/intrvu-req-attch.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IntrvuReqAttchUpdate',
  setup() {
    const intrvuReqAttchService = inject('intrvuReqAttchService', () => new IntrvuReqAttchService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const intrvuReqAttch: Ref<IIntrvuReqAttch> = ref(new IntrvuReqAttch());

    const standardReqAttachService = inject('standardReqAttachService', () => new StandardReqAttachService());

    const standardReqAttaches: Ref<IStandardReqAttach[]> = ref([]);

    const employeeService = inject('employeeService', () => new EmployeeService());

    const employees: Ref<IEmployee[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveIntrvuReqAttch = async intrvuReqAttchId => {
      try {
        const res = await intrvuReqAttchService().find(intrvuReqAttchId);
        res.addDate = new Date(res.addDate);
        intrvuReqAttch.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.intrvuReqAttchId) {
      retrieveIntrvuReqAttch(route.params.intrvuReqAttchId);
    }

    const initRelationships = () => {
      standardReqAttachService()
        .retrieve()
        .then(res => {
          standardReqAttaches.value = res.data;
        });
      employeeService()
        .retrieve()
        .then(res => {
          employees.value = res.data;
        });
    };

    initRelationships();

    const dataUtils = useDataUtils();

    const validations = useValidation();
    const validationRules = {
      addDate: {},
      attch: {},
      standardReqAttach: {},
      employee: {},
    };
    const v$ = useVuelidate(validationRules, intrvuReqAttch as any);
    v$.value.$validate();

    return {
      intrvuReqAttchService,
      alertService,
      intrvuReqAttch,
      previousState,
      isSaving,
      currentLanguage,
      standardReqAttaches,
      employees,
      ...dataUtils,
      v$,
      ...useDateFormat({ entityRef: intrvuReqAttch }),
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.intrvuReqAttch.id) {
        this.intrvuReqAttchService()
          .update(this.intrvuReqAttch)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(`A IntrvuReqAttch is updated with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.intrvuReqAttchService()
          .create(this.intrvuReqAttch)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(`A IntrvuReqAttch is created with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
