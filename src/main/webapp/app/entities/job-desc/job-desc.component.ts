import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';

import JobDescService from './job-desc.service';
import { type IJobDesc } from '@/shared/model/job-desc.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'JobDesc',
  setup() {
    const jobDescService = inject('jobDescService', () => new JobDescService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const jobDescs: Ref<IJobDesc[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveJobDescs = async () => {
      isFetching.value = true;
      try {
        const res = await jobDescService().retrieve();
        jobDescs.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveJobDescs();
    };

    onMounted(async () => {
      await retrieveJobDescs();
    });

    return {
      jobDescs,
      handleSyncList,
      isFetching,
      retrieveJobDescs,
      clear,
    };
  },
});
