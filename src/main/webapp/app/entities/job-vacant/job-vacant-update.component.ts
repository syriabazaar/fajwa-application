import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import JobVacantService from './job-vacant.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import DepartmentService from '@/entities/department/department.service';
import { type IDepartment } from '@/shared/model/department.model';
import JobDescService from '@/entities/job-desc/job-desc.service';
import { type IJobDesc } from '@/shared/model/job-desc.model';
import { type IJobVacant, JobVacant } from '@/shared/model/job-vacant.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'JobVacantUpdate',
  setup() {
    const jobVacantService = inject('jobVacantService', () => new JobVacantService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const jobVacant: Ref<IJobVacant> = ref(new JobVacant());

    const departmentService = inject('departmentService', () => new DepartmentService());

    const departments: Ref<IDepartment[]> = ref([]);

    const jobDescService = inject('jobDescService', () => new JobDescService());

    const jobDescs: Ref<IJobDesc[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveJobVacant = async jobVacantId => {
      try {
        const res = await jobVacantService().find(jobVacantId);
        jobVacant.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.jobVacantId) {
      retrieveJobVacant(route.params.jobVacantId);
    }

    const initRelationships = () => {
      departmentService()
        .retrieve()
        .then(res => {
          departments.value = res.data;
        });
      jobDescService()
        .retrieve()
        .then(res => {
          jobDescs.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      department: {},
      jobDesc: {},
      nominations: {},
    };
    const v$ = useVuelidate(validationRules, jobVacant as any);
    v$.value.$validate();

    return {
      jobVacantService,
      alertService,
      jobVacant,
      previousState,
      isSaving,
      currentLanguage,
      departments,
      jobDescs,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.jobVacant.id) {
        this.jobVacantService()
          .update(this.jobVacant)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(`A JobVacant is updated with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.jobVacantService()
          .create(this.jobVacant)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(`A JobVacant is created with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
