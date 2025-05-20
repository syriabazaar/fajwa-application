<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="fajwaApplicationApp.appointment.home.createOrEditLabel" data-cy="AppointmentCreateUpdateHeading">
          Create or edit a Appointment
        </h2>
        <div>
          <div class="form-group" v-if="appointment.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="appointment.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="appointment-appDateTime">App Date Time</label>
            <div class="d-flex">
              <input
                id="appointment-appDateTime"
                data-cy="appDateTime"
                type="datetime-local"
                class="form-control"
                name="appDateTime"
                :class="{ valid: !v$.appDateTime.$invalid, invalid: v$.appDateTime.$invalid }"
                :value="convertDateTimeFromServer(v$.appDateTime.$model)"
                @change="updateInstantField('appDateTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="appointment-interveiewDate">Interveiew Date</label>
            <div class="d-flex">
              <input
                id="appointment-interveiewDate"
                data-cy="interveiewDate"
                type="datetime-local"
                class="form-control"
                name="interveiewDate"
                :class="{ valid: !v$.interveiewDate.$invalid, invalid: v$.interveiewDate.$invalid }"
                :value="convertDateTimeFromServer(v$.interveiewDate.$model)"
                @change="updateInstantField('interveiewDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="appointment-nomination">Nomination</label>
            <select
              class="form-control"
              id="appointment-nomination"
              data-cy="nomination"
              name="nomination"
              v-model="appointment.nomination"
            >
              <option :value="null"></option>
              <option
                :value="
                  appointment.nomination && nominationOption.id === appointment.nomination.id ? appointment.nomination : nominationOption
                "
                v-for="nominationOption in nominations"
                :key="nominationOption.id"
              >
                {{ nominationOption.id }}
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
<script lang="ts" src="./appointment-update.component.ts"></script>
