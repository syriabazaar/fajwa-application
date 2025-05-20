import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import NominationService from './nomination.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import EmployeeService from '@/entities/employee/employee.service';
import { type IEmployee } from '@/shared/model/employee.model';
import JobVacantService from '@/entities/job-vacant/job-vacant.service';
import { type IJobVacant } from '@/shared/model/job-vacant.model';
import { type INomination, Nomination } from '@/shared/model/nomination.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'NominationUpdate',
  setup() {
    const nominationService = inject('nominationService', () => new NominationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const nomination: Ref<INomination> = ref(new Nomination());

    const employeeService = inject('employeeService', () => new EmployeeService());

    const employees: Ref<IEmployee[]> = ref([]);

    const jobVacantService = inject('jobVacantService', () => new JobVacantService());

    const jobVacants: Ref<IJobVacant[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveNomination = async nominationId => {
      try {
        const res = await nominationService().find(nominationId);
        nomination.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.nominationId) {
      retrieveNomination(route.params.nominationId);
    }

    const initRelationships = () => {
      employeeService()
        .retrieve()
        .then(res => {
          employees.value = res.data;
        });
      jobVacantService()
        .retrieve()
        .then(res => {
          jobVacants.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      machPerc: {},
      employee: {},
      jobVacant: {},
      appointment: {},
    };
    const v$ = useVuelidate(validationRules, nomination as any);
    v$.value.$validate();

    return {
      nominationService,
      alertService,
      nomination,
      previousState,
      isSaving,
      currentLanguage,
      employees,
      jobVacants,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.nomination.id) {
        this.nominationService()
          .update(this.nomination)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(`A Nomination is updated with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.nominationService()
          .create(this.nomination)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(`A Nomination is created with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
