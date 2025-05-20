import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';

import StandardReqAttachService from './standard-req-attach.service';
import { type IStandardReqAttach } from '@/shared/model/standard-req-attach.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandardReqAttach',
  setup() {
    const standardReqAttachService = inject('standardReqAttachService', () => new StandardReqAttachService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const standardReqAttaches: Ref<IStandardReqAttach[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveStandardReqAttachs = async () => {
      isFetching.value = true;
      try {
        const res = await standardReqAttachService().retrieve();
        standardReqAttaches.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveStandardReqAttachs();
    };

    onMounted(async () => {
      await retrieveStandardReqAttachs();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IStandardReqAttach) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeStandardReqAttach = async () => {
      try {
        await standardReqAttachService().delete(removeId.value);
        const message = `A StandardReqAttach is deleted with identifier ${removeId.value}`;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveStandardReqAttachs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      standardReqAttaches,
      handleSyncList,
      isFetching,
      retrieveStandardReqAttachs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeStandardReqAttach,
    };
  },
});
