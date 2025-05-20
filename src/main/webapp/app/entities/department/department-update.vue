<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="fajwaApplicationApp.department.home.createOrEditLabel" data-cy="DepartmentCreateUpdateHeading">
          Create or edit a Department
        </h2>
        <div>
          <div class="form-group" v-if="department.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="department.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="department-depName">Dep Name</label>
            <input
              type="text"
              class="form-control"
              name="depName"
              id="department-depName"
              data-cy="depName"
              :class="{ valid: !v$.depName.$invalid, invalid: v$.depName.$invalid }"
              v-model="v$.depName.$model"
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
<script lang="ts" src="./department-update.component.ts"></script>
