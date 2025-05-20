<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="nomination">
        <h2 class="jh-entity-heading" data-cy="nominationDetailsHeading"><span>Nomination</span> {{ nomination.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Mach Perc</span>
          </dt>
          <dd>
            <span>{{ nomination.machPerc }}</span>
          </dd>
          <dt>
            <span>Employee</span>
          </dt>
          <dd>
            <div v-if="nomination.employee">
              <router-link :to="{ name: 'EmployeeView', params: { employeeId: nomination.employee.id } }">{{
                nomination.employee.fullname
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Job Vacant</span>
          </dt>
          <dd>
            <div v-if="nomination.jobVacant">
              <router-link :to="{ name: 'JobVacantView', params: { jobVacantId: nomination.jobVacant.id } }">{{
                nomination.jobVacant.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="nomination.id"
          :to="{ name: 'NominationEdit', params: { nominationId: nomination.id } }"
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

<script lang="ts" src="./nomination-details.component.ts"></script>
