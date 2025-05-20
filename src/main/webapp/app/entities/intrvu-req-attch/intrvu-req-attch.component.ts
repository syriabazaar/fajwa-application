import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';

import IntrvuReqAttchService from './intrvu-req-attch.service';
import { type IIntrvuReqAttch } from '@/shared/model/intrvu-req-attch.model';
import useDataUtils from '@/shared/data/data-utils.service';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IntrvuReqAttch',
  setup() {
    const dateFormat = useDateFormat();
    const dataUtils = useDataUtils();
    const intrvuReqAttchService = inject('intrvuReqAttchService', () => new IntrvuReqAttchService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const intrvuReqAttches: Ref<IIntrvuReqAttch[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveIntrvuReqAttchs = async () => {
      isFetching.value = true;
      try {
        const res = await intrvuReqAttchService().retrieve();
        intrvuReqAttches.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveIntrvuReqAttchs();
    };

    onMounted(async () => {
      await retrieveIntrvuReqAttchs();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IIntrvuReqAttch) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeIntrvuReqAttch = async () => {
      try {
        await intrvuReqAttchService().delete(removeId.value);
        const message = `A IntrvuReqAttch is deleted with identifier ${removeId.value}`;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveIntrvuReqAttchs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      intrvuReqAttches,
      handleSyncList,
      isFetching,
      retrieveIntrvuReqAttchs,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeIntrvuReqAttch,
      ...dataUtils,
    };
  },
});
