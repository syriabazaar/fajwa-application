import { type Ref, defineComponent, ref } from 'vue';
import axios from 'axios';
import { useVuelidate } from '@vuelidate/core';
import { maxLength, minLength, required, sameAs } from '@vuelidate/validators';
import { useLoginModal } from '@/account/login-modal';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ResetPasswordFinish',
  validations() {
    return {
      resetAccount: {
        newPassword: {
          required,
          minLength: minLength(4),
          maxLength: maxLength(254),
        },
        confirmPassword: {
          sameAsPassword: sameAs(this.resetAccount.newPassword),
        },
      },
    };
  },
  created(): void {
    if (this.$route?.query?.key !== undefined) {
      this.key = this.$route.query.key;
    }
    this.keyMissing = !this.key;
  },
  setup() {
    const { showLogin } = useLoginModal();

    const doNotMatch: Ref<string> = ref(null);
    const success: Ref<string> = ref(null);
    const error: Ref<string> = ref(null);
    const keyMissing: Ref<boolean> = ref(false);
    const key: Ref<any> = ref(null);
    const resetAccount: Ref<any> = ref({
      newPassword: null,
      confirmPassword: null,
    });

    return {
      showLogin,
      doNotMatch,
      success,
      error,
      keyMissing,
      key,
      resetAccount,
      v$: useVuelidate(),
    };
  },
  methods: {
    finishReset() {
      this.doNotMatch = null;
      this.success = null;
      this.error = null;
      if (this.resetAccount.newPassword !== this.resetAccount.confirmPassword) {
        this.doNotMatch = 'ERROR';
      } else {
        return axios
          .post('api/account/reset-password/finish', { key: this.key, newPassword: this.resetAccount.newPassword })
          .then(() => {
            this.success = 'OK';
          })
          .catch(() => {
            this.success = null;
            this.error = 'ERROR';
          });
      }
    },
  },
});
