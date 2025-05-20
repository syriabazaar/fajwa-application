import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import JobVacantService from './job-vacant.service';
import { type IJobVacant } from '@/shared/model/job-vacant.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'JobVacantDetails',
  setup() {
    const jobVacantService = inject('jobVacantService', () => new JobVacantService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const jobVacant: Ref<IJobVacant> = ref({});

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

    return {
      alertService,
      jobVacant,

      previousState,
    };
  },
});
