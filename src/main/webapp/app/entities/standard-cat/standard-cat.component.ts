import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';

import StandardCatService from './standard-cat.service';
import { type IStandardCat } from '@/shared/model/standard-cat.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StandardCat',
  setup() {
    const standardCatService = inject('standardCatService', () => new StandardCatService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const standardCats: Ref<IStandardCat[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveStandardCats = async () => {
      isFetching.value = true;
      try {
        const res = await standardCatService().retrieve();
        standardCats.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveStandardCats();
    };

    onMounted(async () => {
      await retrieveStandardCats();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IStandardCat) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeStandardCat = async () => {
      try {
        await standardCatService().delete(removeId.value);
        const message = `A StandardCat is deleted with identifier ${removeId.value}`;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveStandardCats();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      standardCats,
      handleSyncList,
      isFetching,
      retrieveStandardCats,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeStandardCat,
    };
  },
});
