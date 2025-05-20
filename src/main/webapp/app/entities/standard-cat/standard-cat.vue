<template>
  <div>
    <h2 id="page-heading" data-cy="StandardCatHeading">
      <span id="standard-cat-heading">Standard Cats</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'StandardCatCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-standard-cat"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Standard Cat</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && standardCats && standardCats.length === 0">
      <span>No Standard Cats found</span>
    </div>
    <div class="table-responsive" v-if="standardCats && standardCats.length > 0">
      <table class="table table-striped" aria-describedby="standardCats">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Name</span></th>
            <th scope="row"><span>Standard Parent</span></th>
            <th scope="row"><span>Req Attachment</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="standardCat in standardCats" :key="standardCat.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StandardCatView', params: { standardCatId: standardCat.id } }">{{ standardCat.id }}</router-link>
            </td>
            <td>{{ standardCat.name }}</td>
            <td>
              <div v-if="standardCat.standardParent">
                <router-link :to="{ name: 'StandardParentView', params: { standardParentId: standardCat.standardParent.id } }">{{
                  standardCat.standardParent.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="standardCat.reqAttachment">
                <router-link :to="{ name: 'StandardReqAttachView', params: { standardReqAttachId: standardCat.reqAttachment.id } }">{{
                  standardCat.reqAttachment.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StandardCatView', params: { standardCatId: standardCat.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StandardCatEdit', params: { standardCatId: standardCat.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(standardCat)"
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
        <span id="fajwaApplicationApp.standardCat.delete.question" data-cy="standardCatDeleteDialogHeading">Confirm delete operation</span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-standardCat-heading">Are you sure you want to delete Standard Cat {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" @click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-standardCat"
            data-cy="entityConfirmDeleteButton"
            @click="removeStandardCat()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./standard-cat.component.ts"></script>
