<template>
  <div>
    <h2 id="page-heading" data-cy="StandarditemHeading">
      <span id="standarditem-heading">Standarditems</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'StandarditemCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-standarditem"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Standarditem</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && standarditems && standarditems.length === 0">
      <span>No Standarditems found</span>
    </div>
    <div class="table-responsive" v-if="standarditems && standarditems.length > 0">
      <table class="table table-striped" aria-describedby="standarditems">
        <thead>
          <tr>
            <th scope="row" @click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('name')">
              <span>Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('isActive')">
              <span>Is Active</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isActive'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('weightPercentage')">
              <span>Weight Percentage</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'weightPercentage'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('standardCat.name')">
              <span>Standard Cat</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'standardCat.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="standarditem in standarditems" :key="standarditem.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StandarditemView', params: { standarditemId: standarditem.id } }">{{
                standarditem.id
              }}</router-link>
            </td>
            <td>{{ standarditem.name }}</td>
            <td>{{ standarditem.isActive }}</td>
            <td>{{ standarditem.weightPercentage }}</td>
            <td>
              <div v-if="standarditem.standardCat">
                <router-link :to="{ name: 'StandardCatView', params: { standardCatId: standarditem.standardCat.id } }">{{
                  standarditem.standardCat.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StandarditemView', params: { standarditemId: standarditem.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StandarditemEdit', params: { standarditemId: standarditem.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(standarditem)"
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
        <span id="fajwaApplicationApp.standarditem.delete.question" data-cy="standarditemDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-standarditem-heading">Are you sure you want to delete Standarditem {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" @click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-standarditem"
            data-cy="entityConfirmDeleteButton"
            @click="removeStandarditem()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
    <div v-show="standarditems && standarditems.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./standarditem.component.ts"></script>
