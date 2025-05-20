<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="fajwaApplicationApp.intrvuStrdDtls.home.createOrEditLabel" data-cy="IntrvuStrdDtlsCreateUpdateHeading">
          Create or edit a Intrvu Strd Dtls
        </h2>
        <div>
          <div class="form-group" v-if="intrvuStrdDtls.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="intrvuStrdDtls.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="intrvu-strd-dtls-standardOption">Standard Option</label>
            <select
              class="form-control"
              name="standardOption"
              :class="{ valid: !v$.standardOption.$invalid, invalid: v$.standardOption.$invalid }"
              v-model="v$.standardOption.$model"
              id="intrvu-strd-dtls-standardOption"
              data-cy="standardOption"
            >
              <option v-for="standardOption in standardOptionValues" :key="standardOption" :value="standardOption">
                {{ standardOption }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="intrvu-strd-dtls-standarditem">Standarditem</label>
            <select
              class="form-control"
              id="intrvu-strd-dtls-standarditem"
              data-cy="standarditem"
              name="standarditem"
              v-model="intrvuStrdDtls.standarditem"
            >
              <option :value="null"></option>
              <option
                :value="
                  intrvuStrdDtls.standarditem && standarditemOption.id === intrvuStrdDtls.standarditem.id
                    ? intrvuStrdDtls.standarditem
                    : standarditemOption
                "
                v-for="standarditemOption in standarditems"
                :key="standarditemOption.id"
              >
                {{ standarditemOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="intrvu-strd-dtls-appointment">Appointment</label>
            <select
              class="form-control"
              id="intrvu-strd-dtls-appointment"
              data-cy="appointment"
              name="appointment"
              v-model="intrvuStrdDtls.appointment"
            >
              <option :value="null"></option>
              <option
                :value="
                  intrvuStrdDtls.appointment && appointmentOption.id === intrvuStrdDtls.appointment.id
                    ? intrvuStrdDtls.appointment
                    : appointmentOption
                "
                v-for="appointmentOption in appointments"
                :key="appointmentOption.id"
              >
                {{ appointmentOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./intrvu-strd-dtls-update.component.ts"></script>
