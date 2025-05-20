<template>
  <div>
    <h2 id="page-heading" data-cy="NominationHeading">
      <span id="nomination-heading">Nominations</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'NominationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-nomination"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Nomination</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && nominations && nominations.length === 0">
      <span>No Nominations found</span>
    </div>
    <div class="table-responsive" v-if="nominations && nominations.length > 0">
      <table class="table table-striped" aria-describedby="nominations">
        <thead>
          <tr>
            <th scope="row" @click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('machPerc')">
              <span>Mach Perc</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'machPerc'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('employee.fullname')">
              <span>Employee</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'employee.fullname'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('jobVacant.id')">
              <span>Job Vacant</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'jobVacant.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="nomination in nominations" :key="nomination.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'NominationView', params: { nominationId: nomination.id } }">{{ nomination.id }}</router-link>
            </td>
            <td>{{ nomination.machPerc }}</td>
            <td>
              <div v-if="nomination.employee">
                <router-link :to="{ name: 'EmployeeView', params: { employeeId: nomination.employee.id } }">{{
                  nomination.employee.fullname
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="nomination.jobVacant">
                <router-link :to="{ name: 'JobVacantView', params: { jobVacantId: nomination.jobVacant.id } }">{{
                  nomination.jobVacant.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'NominationView', params: { nominationId: nomination.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'NominationEdit', params: { nominationId: nomination.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(nomination)"
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
        <span id="fajwaApplicationApp.nomination.delete.question" data-cy="nominationDeleteDialogHeading">Confirm delete operation</span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-nomination-heading">Are you sure you want to delete Nomination {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" @click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-nomination"
            data-cy="entityConfirmDeleteButton"
            @click="removeNomination()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
    <div v-show="nominations && nominations.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./nomination.component.ts"></script>
