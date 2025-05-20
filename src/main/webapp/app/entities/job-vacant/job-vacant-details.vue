<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="jobVacant">
        <h2 class="jh-entity-heading" data-cy="jobVacantDetailsHeading"><span>Job Vacant</span> {{ jobVacant.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Department</span>
          </dt>
          <dd>
            <div v-if="jobVacant.department">
              <router-link :to="{ name: 'DepartmentView', params: { departmentId: jobVacant.department.id } }">{{
                jobVacant.department.depName
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Job Desc</span>
          </dt>
          <dd>
            <div v-if="jobVacant.jobDesc">
              <router-link :to="{ name: 'JobDescView', params: { jobDescId: jobVacant.jobDesc.id } }">{{
                jobVacant.jobDesc.jobName
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="jobVacant.id"
          :to="{ name: 'JobVacantEdit', params: { jobVacantId: jobVacant.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./job-vacant-details.component.ts"></script>
