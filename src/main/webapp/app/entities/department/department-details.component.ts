import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import DepartmentService from './department.service';
import { type IDepartment } from '@/shared/model/department.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DepartmentDetails',
  setup() {
    const departmentService = inject('departmentService', () => new DepartmentService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const department: Ref<IDepartment> = ref({});

    const retrieveDepartment = async departmentId => {
      try {
        const res = await departmentService().find(departmentId);
        department.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.departmentId) {
      retrieveDepartment(route.params.departmentId);
    }

    return {
      alertService,
      department,

      previousState,
    };
  },
});
