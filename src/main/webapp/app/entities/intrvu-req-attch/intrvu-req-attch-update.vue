<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="fajwaApplicationApp.intrvuReqAttch.home.createOrEditLabel" data-cy="IntrvuReqAttchCreateUpdateHeading">
          Create or edit a Intrvu Req Attch
        </h2>
        <div>
          <div class="form-group" v-if="intrvuReqAttch.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="intrvuReqAttch.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="intrvu-req-attch-addDate">Add Date</label>
            <div class="d-flex">
              <input
                id="intrvu-req-attch-addDate"
                data-cy="addDate"
                type="datetime-local"
                class="form-control"
                name="addDate"
                :class="{ valid: !v$.addDate.$invalid, invalid: v$.addDate.$invalid }"
                :value="convertDateTimeFromServer(v$.addDate.$model)"
                @change="updateInstantField('addDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="intrvu-req-attch-attch">Attch</label>
            <div>
              <div v-if="intrvuReqAttch.attch" class="form-text text-danger clearfix">
                <span class="pull-left">{{ intrvuReqAttch.attchContentType }}, {{ byteSize(intrvuReqAttch.attch) }}</span>
                <button
                  type="button"
                  @click="
                    intrvuReqAttch.attch = null;
                    intrvuReqAttch.attchContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_attch" class="btn btn-primary pull-right">Add blob</label>
              <input
                type="file"
                ref="file_attch"
                id="file_attch"
                style="display: none"
                data-cy="attch"
                @change="setFileData($event, intrvuReqAttch, 'attch', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="attch"
              id="intrvu-req-attch-attch"
              data-cy="attch"
              :class="{ valid: !v$.attch.$invalid, invalid: v$.attch.$invalid }"
              v-model="v$.attch.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="attchContentType"
              id="intrvu-req-attch-attchContentType"
              v-model="intrvuReqAttch.attchContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="intrvu-req-attch-standardReqAttach">Standard Req Attach</label>
            <select
              class="form-control"
              id="intrvu-req-attch-standardReqAttach"
              data-cy="standardReqAttach"
              name="standardReqAttach"
              v-model="intrvuReqAttch.standardReqAttach"
            >
              <option :value="null"></option>
              <option
                :value="
                  intrvuReqAttch.standardReqAttach && standardReqAttachOption.id === intrvuReqAttch.standardReqAttach.id
                    ? intrvuReqAttch.standardReqAttach
                    : standardReqAttachOption
                "
                v-for="standardReqAttachOption in standardReqAttaches"
                :key="standardReqAttachOption.id"
              >
                {{ standardReqAttachOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="intrvu-req-attch-employee">Employee</label>
            <select
              class="form-control"
              id="intrvu-req-attch-employee"
              data-cy="employee"
              name="employee"
              v-model="intrvuReqAttch.employee"
            >
              <option :value="null"></option>
              <option
                :value="
                  intrvuReqAttch.employee && employeeOption.id === intrvuReqAttch.employee.id ? intrvuReqAttch.employee : employeeOption
                "
                v-for="employeeOption in employees"
                :key="employeeOption.id"
              >
                {{ employeeOption.id }}
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
<script lang="ts" src="./intrvu-req-attch-update.component.ts"></script>
