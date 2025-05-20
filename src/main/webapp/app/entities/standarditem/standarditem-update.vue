<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="fajwaApplicationApp.standarditem.home.createOrEditLabel" data-cy="StandarditemCreateUpdateHeading">
          Create or edit a Standarditem
        </h2>
        <div>
          <div class="form-group" v-if="standarditem.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="standarditem.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="standarditem-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="standarditem-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="standarditem-isActive">Is Active</label>
            <input
              type="checkbox"
              class="form-check"
              name="isActive"
              id="standarditem-isActive"
              data-cy="isActive"
              :class="{ valid: !v$.isActive.$invalid, invalid: v$.isActive.$invalid }"
              v-model="v$.isActive.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="standarditem-weightPercentage">Weight Percentage</label>
            <input
              type="number"
              class="form-control"
              name="weightPercentage"
              id="standarditem-weightPercentage"
              data-cy="weightPercentage"
              :class="{ valid: !v$.weightPercentage.$invalid, invalid: v$.weightPercentage.$invalid }"
              v-model.number="v$.weightPercentage.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="standarditem-standardCat">Standard Cat</label>
            <select
              class="form-control"
              id="standarditem-standardCat"
              data-cy="standardCat"
              name="standardCat"
              v-model="standarditem.standardCat"
            >
              <option :value="null"></option>
              <option
                :value="
                  standarditem.standardCat && standardCatOption.id === standarditem.standardCat.id
                    ? standarditem.standardCat
                    : standardCatOption
                "
                v-for="standardCatOption in standardCats"
                :key="standardCatOption.id"
              >
                {{ standardCatOption.name }}
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
<script lang="ts" src="./standarditem-update.component.ts"></script>
