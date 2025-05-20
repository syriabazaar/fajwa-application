import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';

import StandardParentService from './standard-parent.service';
import { type IStandardParent } from '@/shared/model/standard-parent.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandardParent',
  setup() {
    const standardParentService = inject('standardParentService', () => new StandardParentService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const standardParents: Ref<IStandardParent[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveStandardParents = async () => {
      isFetching.value = true;
      try {
        const res = await standardParentService().retrieve();
        standardParents.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveStandardParents();
    };

    onMounted(async () => {
      await retrieveStandardParents();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IStandardParent) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeStandardParent = async () => {
      try {
        await standardParentService().delete(removeId.value);
        const message = `A StandardParent is deleted with identifier ${removeId.value}`;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveStandardParents();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      standardParents,
      handleSyncList,
      isFetching,
      retrieveStandardParents,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeStandardParent,
    };
  },
});
