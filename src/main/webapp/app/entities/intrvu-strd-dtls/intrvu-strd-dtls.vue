<template>
  <div>
    <h2 id="page-heading" data-cy="IntrvuStrdDtlsHeading">
      <span id="intrvu-strd-dtls-heading">Intrvu Strd Dtls</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'IntrvuStrdDtlsCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-intrvu-strd-dtls"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Intrvu Strd Dtls</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && intrvuStrdDtls && intrvuStrdDtls.length === 0">
      <span>No Intrvu Strd Dtls found</span>
    </div>
    <div class="table-responsive" v-if="intrvuStrdDtls && intrvuStrdDtls.length > 0">
      <table class="table table-striped" aria-describedby="intrvuStrdDtls">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Standard Option</span></th>
            <th scope="row"><span>Standarditem</span></th>
            <th scope="row"><span>Appointment</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="intrvuStrdDtls in intrvuStrdDtls" :key="intrvuStrdDtls.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'IntrvuStrdDtlsView', params: { intrvuStrdDtlsId: intrvuStrdDtls.id } }">{{
                intrvuStrdDtls.id
              }}</router-link>
            </td>
            <td>{{ intrvuStrdDtls.standardOption }}</td>
            <td>
              <div v-if="intrvuStrdDtls.standarditem">
                <router-link :to="{ name: 'StandarditemView', params: { standarditemId: intrvuStrdDtls.standarditem.id } }">{{
                  intrvuStrdDtls.standarditem.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="intrvuStrdDtls.appointment">
                <router-link :to="{ name: 'AppointmentView', params: { appointmentId: intrvuStrdDtls.appointment.id } }">{{
                  intrvuStrdDtls.appointment.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'IntrvuStrdDtlsView', params: { intrvuStrdDtlsId: intrvuStrdDtls.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'IntrvuStrdDtlsEdit', params: { intrvuStrdDtlsId: intrvuStrdDtls.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(intrvuStrdDtls)"
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
        <span id="fajwaApplicationApp.intrvuStrdDtls.delete.question" data-cy="intrvuStrdDtlsDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-intrvuStrdDtls-heading">Are you sure you want to delete Intrvu Strd Dtls {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" @click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-intrvuStrdDtls"
            data-cy="entityConfirmDeleteButton"
            @click="removeIntrvuStrdDtls()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./intrvu-strd-dtls.component.ts"></script>
