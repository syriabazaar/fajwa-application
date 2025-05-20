import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';

import IntrvuStrdDtlsService from './intrvu-strd-dtls.service';
import { type IIntrvuStrdDtls } from '@/shared/model/intrvu-strd-dtls.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IntrvuStrdDtls',
  setup() {
    const intrvuStrdDtlsService = inject('intrvuStrdDtlsService', () => new IntrvuStrdDtlsService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const intrvuStrdDtls: Ref<IIntrvuStrdDtls[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveIntrvuStrdDtlss = async () => {
      isFetching.value = true;
      try {
        const res = await intrvuStrdDtlsService().retrieve();
        intrvuStrdDtls.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveIntrvuStrdDtlss();
    };

    onMounted(async () => {
      await retrieveIntrvuStrdDtlss();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IIntrvuStrdDtls) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeIntrvuStrdDtls = async () => {
      try {
        await intrvuStrdDtlsService().delete(removeId.value);
        const message = `A IntrvuStrdDtls is deleted with identifier ${removeId.value}`;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveIntrvuStrdDtlss();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      intrvuStrdDtls,
      handleSyncList,
      isFetching,
      retrieveIntrvuStrdDtlss,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeIntrvuStrdDtls,
    };
  },
});
