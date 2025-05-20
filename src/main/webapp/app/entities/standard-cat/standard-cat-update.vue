<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="fajwaApplicationApp.standardCat.home.createOrEditLabel" data-cy="StandardCatCreateUpdateHeading">
          Create or edit a Standard Cat
        </h2>
        <div>
          <div class="form-group" v-if="standardCat.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="standardCat.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="standard-cat-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="standard-cat-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="standard-cat-standardParent">Standard Parent</label>
            <select
              class="form-control"
              id="standard-cat-standardParent"
              data-cy="standardParent"
              name="standardParent"
              v-model="standardCat.standardParent"
            >
              <option :value="null"></option>
              <option
                :value="
                  standardCat.standardParent && standardParentOption.id === standardCat.standardParent.id
                    ? standardCat.standardParent
                    : standardParentOption
                "
                v-for="standardParentOption in standardParents"
                :key="standardParentOption.id"
              >
                {{ standardParentOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="standard-cat-reqAttachment">Req Attachment</label>
            <select
              class="form-control"
              id="standard-cat-reqAttachment"
              data-cy="reqAttachment"
              name="reqAttachment"
              v-model="standardCat.reqAttachment"
            >
              <option :value="null"></option>
              <option
                :value="
                  standardCat.reqAttachment && standardReqAttachOption.id === standardCat.reqAttachment.id
                    ? standardCat.reqAttachment
                    : standardReqAttachOption
                "
                v-for="standardReqAttachOption in standardReqAttaches"
                :key="standardReqAttachOption.id"
              >
                {{ standardReqAttachOption.name }}
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
<script lang="ts" src="./standard-cat-update.component.ts"></script>
