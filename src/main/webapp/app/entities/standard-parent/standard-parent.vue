<template>
  <div>
    <h2 id="page-heading" data-cy="StandardParentHeading">
      <span id="standard-parent-heading">Standard Parents</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'StandardParentCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-standard-parent"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Standard Parent</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && standardParents && standardParents.length === 0">
      <span>No Standard Parents found</span>
    </div>
    <div class="table-responsive" v-if="standardParents && standardParents.length > 0">
      <table class="table table-striped" aria-describedby="standardParents">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Name</span></th>
            <th scope="row"><span>Standard Type</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="standardParent in standardParents" :key="standardParent.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StandardParentView', params: { standardParentId: standardParent.id } }">{{
                standardParent.id
              }}</router-link>
            </td>
            <td>{{ standardParent.name }}</td>
            <td>
              <div v-if="standardParent.standardType">
                <router-link :to="{ name: 'StandardTypeView', params: { standardTypeId: standardParent.standardType.id } }">{{
                  standardParent.standardType.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'StandardParentView', params: { standardParentId: standardParent.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'StandardParentEdit', params: { standardParentId: standardParent.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(standardParent)"
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
        <span id="fajwaApplicationApp.standardParent.delete.question" data-cy="standardParentDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-standardParent-heading">Are you sure you want to delete Standard Parent {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" @click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-standardParent"
            data-cy="entityConfirmDeleteButton"
            @click="removeStandardParent()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./standard-parent.component.ts"></script>
