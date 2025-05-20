<template>
  <div>
    <h2 id="page-heading" data-cy="AppointmentHeading">
      <span id="appointment-heading">Appointments</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'AppointmentCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-appointment"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Appointment</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && appointments && appointments.length === 0">
      <span>No Appointments found</span>
    </div>
    <div class="table-responsive" v-if="appointments && appointments.length > 0">
      <table class="table table-striped" aria-describedby="appointments">
        <thead>
          <tr>
            <th scope="row" @click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('appDateTime')">
              <span>App Date Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'appDateTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('interveiewDate')">
              <span>Interveiew Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'interveiewDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('nomination.id')">
              <span>Nomination</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nomination.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="appointment in appointments" :key="appointment.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AppointmentView', params: { appointmentId: appointment.id } }">{{ appointment.id }}</router-link>
            </td>
            <td>{{ formatDateShort(appointment.appDateTime) || '' }}</td>
            <td>{{ formatDateShort(appointment.interveiewDate) || '' }}</td>
            <td>
              <div v-if="appointment.nomination">
                <router-link :to="{ name: 'NominationView', params: { nominationId: appointment.nomination.id } }">{{
                  appointment.nomination.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AppointmentView', params: { appointmentId: appointment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AppointmentEdit', params: { appointmentId: appointment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(appointment)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="fajwaApplicationApp.appointment.delete.question" data-cy="appointmentDeleteDialogHeading">Confirm delete operation</span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-appointment-heading">Are you sure you want to delete Appointment {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" @click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-appointment"
            data-cy="entityConfirmDeleteButton"
            @click="removeAppointment()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
    <div v-show="appointments && appointments.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./appointment.component.ts"></script>
