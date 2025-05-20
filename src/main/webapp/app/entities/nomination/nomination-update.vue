<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="fajwaApplicationApp.nomination.home.createOrEditLabel" data-cy="NominationCreateUpdateHeading">
          Create or edit a Nomination
        </h2>
        <div>
          <div class="form-group" v-if="nomination.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="nomination.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="nomination-machPerc">Mach Perc</label>
            <input
              type="number"
              class="form-control"
              name="machPerc"
              id="nomination-machPerc"
              data-cy="machPerc"
              :class="{ valid: !v$.machPerc.$invalid, invalid: v$.machPerc.$invalid }"
              v-model.number="v$.machPerc.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="nomination-employee">Employee</label>
            <select class="form-control" id="nomination-employee" data-cy="employee" name="employee" v-model="nomination.employee">
              <option :value="null"></option>
              <option
                :value="nomination.employee && employeeOption.id === nomination.employee.id ? nomination.employee : employeeOption"
                v-for="employeeOption in employees"
                :key="employeeOption.id"
              >
                {{ employeeOption.fullname }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="nomination-jobVacant">Job Vacant</label>
            <select class="form-control" id="nomination-jobVacant" data-cy="jobVacant" name="jobVacant" v-model="nomination.jobVacant">
              <option :value="null"></option>
              <option
                :value="nomination.jobVacant && jobVacantOption.id === nomination.jobVacant.id ? nomination.jobVacant : jobVacantOption"
                v-for="jobVacantOption in jobVacants"
                :key="jobVacantOption.id"
              >
                {{ jobVacantOption.id }}
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
<script lang="ts" src="./nomination-update.component.ts"></script>
