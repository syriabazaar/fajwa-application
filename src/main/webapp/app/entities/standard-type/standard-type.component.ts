import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';

import StandardTypeService from './standard-type.service';
import { type IStandardType } from '@/shared/model/standard-type.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandardType',
  setup() {
    const standardTypeService = inject('standardTypeService', () => new StandardTypeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const standardTypes: Ref<IStandardType[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveStandardTypes = async () => {
      isFetching.value = true;
      try {
        const res = await standardTypeService().retrieve();
        standardTypes.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveStandardTypes();
    };

    onMounted(async () => {
      await retrieveStandardTypes();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IStandardType) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeStandardType = async () => {
      try {
        await standardTypeService().delete(removeId.value);
        const message = `A StandardType is deleted with identifier ${removeId.value}`;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveStandardTypes();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      standardTypes,
      handleSyncList,
      isFetching,
      retrieveStandardTypes,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeStandardType,
    };
  },
});
