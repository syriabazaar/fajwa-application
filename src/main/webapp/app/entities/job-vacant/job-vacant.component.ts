import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';

import JobVacantService from './job-vacant.service';
import { type IJobVacant } from '@/shared/model/job-vacant.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'JobVacant',
  setup() {
    const jobVacantService = inject('jobVacantService', () => new JobVacantService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const jobVacants: Ref<IJobVacant[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveJobVacants = async () => {
      isFetching.value = true;
      try {
        const res = await jobVacantService().retrieve();
        jobVacants.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveJobVacants();
    };

    onMounted(async () => {
      await retrieveJobVacants();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IJobVacant) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeJobVacant = async () => {
      try {
        await jobVacantService().delete(removeId.value);
        const message = `A JobVacant is deleted with identifier ${removeId.value}`;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveJobVacants();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      jobVacants,
      handleSyncList,
      isFetching,
      retrieveJobVacants,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeJobVacant,
    };
  },
});
