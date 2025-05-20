<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="fajwaApplicationApp.standardParent.home.createOrEditLabel" data-cy="StandardParentCreateUpdateHeading">
          Create or edit a Standard Parent
        </h2>
        <div>
          <div class="form-group" v-if="standardParent.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="standardParent.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="standard-parent-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="standard-parent-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="standard-parent-standardType">Standard Type</label>
            <select
              class="form-control"
              id="standard-parent-standardType"
              data-cy="standardType"
              name="standardType"
              v-model="standardParent.standardType"
            >
              <option :value="null"></option>
              <option
                :value="
                  standardParent.standardType && standardTypeOption.id === standardParent.standardType.id
                    ? standardParent.standardType
                    : standardTypeOption
                "
                v-for="standardTypeOption in standardTypes"
                :key="standardTypeOption.id"
              >
                {{ standardTypeOption.name }}
              </option>
            </select>
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
<script lang="ts" src="./standard-parent-update.component.ts"></script>
