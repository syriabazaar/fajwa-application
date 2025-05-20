<template>
  <div>
    <h2 id="page-heading" data-cy="JobVacantHeading">
      <span id="job-vacant-heading">Job Vacants</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'JobVacantCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-job-vacant"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Job Vacant</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && jobVacants && jobVacants.length === 0">
      <span>No Job Vacants found</span>
    </div>
    <div class="table-responsive" v-if="jobVacants && jobVacants.length > 0">
      <table class="table table-striped" aria-describedby="jobVacants">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Department</span></th>
            <th scope="row"><span>Job Desc</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="jobVacant in jobVacants" :key="jobVacant.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'JobVacantView', params: { jobVacantId: jobVacant.id } }">{{ jobVacant.id }}</router-link>
            </td>
            <td>
              <div v-if="jobVacant.department">
                <router-link :to="{ name: 'DepartmentView', params: { departmentId: jobVacant.department.id } }">{{
                  jobVacant.department.depName
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="jobVacant.jobDesc">
                <router-link :to="{ name: 'JobDescView', params: { jobDescId: jobVacant.jobDesc.id } }">{{
                  jobVacant.jobDesc.jobName
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'JobVacantView', params: { jobVacantId: jobVacant.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'JobVacantEdit', params: { jobVacantId: jobVacant.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(jobVacant)"
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
        <span id="fajwaApplicationApp.jobVacant.delete.question" data-cy="jobVacantDeleteDialogHeading">Confirm delete operation</span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-jobVacant-heading">Are you sure you want to delete Job Vacant {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" @click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-jobVacant"
            data-cy="entityConfirmDeleteButton"
            @click="removeJobVacant()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./job-vacant.component.ts"></script>
