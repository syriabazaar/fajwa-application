import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import JobDescService from './job-desc.service';
import { type IJobDesc } from '@/shared/model/job-desc.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'JobDescDetails',
  setup() {
    const jobDescService = inject('jobDescService', () => new JobDescService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const jobDesc: Ref<IJobDesc> = ref({});

    const retrieveJobDesc = async jobDescId => {
      try {
        const res = await jobDescService().find(jobDescId);
        jobDesc.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.jobDescId) {
      retrieveJobDesc(route.params.jobDescId);
    }

    return {
      alertService,
      jobDesc,

      previousState,
    };
  },
});
