<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="fajwaApplicationApp.standardReqAttach.home.createOrEditLabel" data-cy="StandardReqAttachCreateUpdateHeading">
          Create or edit a Standard Req Attach
        </h2>
        <div>
          <div class="form-group" v-if="standardReqAttach.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="standardReqAttach.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="standard-req-attach-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="standard-req-attach-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="standard-req-attach-attDesc">Att Desc</label>
            <input
              type="text"
              class="form-control"
              name="attDesc"
              id="standard-req-attach-attDesc"
              data-cy="attDesc"
              :class="{ valid: !v$.attDesc.$invalid, invalid: v$.attDesc.$invalid }"
              v-model="v$.attDesc.$model"
            />
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
<script lang="ts" src="./standard-req-attach-update.component.ts"></script>
