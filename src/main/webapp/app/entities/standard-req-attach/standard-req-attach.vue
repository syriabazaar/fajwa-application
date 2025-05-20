<template>
  <div>
    <h2 id="page-heading" data-cy="StandardReqAttachHeading">
      <span id="standard-req-attach-heading">Standard Req Attaches</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'StandardReqAttachCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-standard-req-attach"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Standard Req Attach</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && standardReqAttaches && standardReqAttaches.length === 0">
      <span>No Standard Req Attaches found</span>
    </div>
    <div class="table-responsive" v-if="standardReqAttaches && standardReqAttaches.length > 0">
      <table class="table table-striped" aria-describedby="standardReqAttaches">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Name</span></th>
            <th scope="row"><span>Att Desc</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="standardReqAttach in standardReqAttaches" :key="standardReqAttach.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StandardReqAttachView', params: { standardReqAttachId: standardReqAttach.id } }">{{
                standardReqAttach.id
              }}</router-link>
            </td>
            <td>{{ standardReqAttach.name }}</td>
            <td>{{ standardReqAttach.attDesc }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'StandardReqAttachView', params: { standardReqAttachId: standardReqAttach.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'StandardReqAttachEdit', params: { standardReqAttachId: standardReqAttach.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(standardReqAttach)"
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
        <span id="fajwaApplicationApp.standardReqAttach.delete.question" data-cy="standardReqAttachDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-standardReqAttach-heading">Are you sure you want to delete Standard Req Attach {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" @click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-standardReqAttach"
            data-cy="entityConfirmDeleteButton"
            @click="removeStandardReqAttach()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./standard-req-attach.component.ts"></script>
