<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="jobDesc">
        <h2 class="jh-entity-heading" data-cy="jobDescDetailsHeading"><span>Job Desc</span> {{ jobDesc.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Job Name</span>
          </dt>
          <dd>
            <span>{{ jobDesc.jobName }}</span>
          </dd>
          <dt>
            <span>Standard Type</span>
          </dt>
          <dd>
            <div v-if="jobDesc.standardType">
              <router-link :to="{ name: 'StandardTypeView', params: { standardTypeId: jobDesc.standardType.id } }">{{
                jobDesc.standardType.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./job-desc-details.component.ts"></script>
