<template>
  <div>
    <h2 id="page-heading" data-cy="IntrvuReqAttchHeading">
      <span id="intrvu-req-attch-heading">Intrvu Req Attches</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'IntrvuReqAttchCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-intrvu-req-attch"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Intrvu Req Attch</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && intrvuReqAttches && intrvuReqAttches.length === 0">
      <span>No Intrvu Req Attches found</span>
    </div>
    <div class="table-responsive" v-if="intrvuReqAttches && intrvuReqAttches.length > 0">
      <table class="table table-striped" aria-describedby="intrvuReqAttches">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Add Date</span></th>
            <th scope="row"><span>Attch</span></th>
            <th scope="row"><span>Standard Req Attach</span></th>
            <th scope="row"><span>Employee</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="intrvuReqAttch in intrvuReqAttches" :key="intrvuReqAttch.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'IntrvuReqAttchView', params: { intrvuReqAttchId: intrvuReqAttch.id } }">{{
                intrvuReqAttch.id
              }}</router-link>
            </td>
            <td>{{ formatDateShort(intrvuReqAttch.addDate) || '' }}</td>
            <td>{{ intrvuReqAttch.attch }}</td>
            <td>
              <div v-if="intrvuReqAttch.standardReqAttach">
                <router-link
                  :to="{ name: 'StandardReqAttachView', params: { standardReqAttachId: intrvuReqAttch.standardReqAttach.id } }"
                  >{{ intrvuReqAttch.standardReqAttach.name }}</router-link
                >
              </div>
            </td>
            <td>
              <div v-if="intrvuReqAttch.employee">
                <router-link :to="{ name: 'EmployeeView', params: { employeeId: intrvuReqAttch.employee.id } }">{{
                  intrvuReqAttch.employee.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'IntrvuReqAttchView', params: { intrvuReqAttchId: intrvuReqAttch.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'IntrvuReqAttchEdit', params: { intrvuReqAttchId: intrvuReqAttch.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(intrvuReqAttch)"
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
        <span id="fajwaApplicationApp.intrvuReqAttch.delete.question" data-cy="intrvuReqAttchDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-intrvuReqAttch-heading">Are you sure you want to delete Intrvu Req Attch {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" @click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-intrvuReqAttch"
            data-cy="entityConfirmDeleteButton"
            @click="removeIntrvuReqAttch()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./intrvu-req-attch.component.ts"></script>
