<template>
  <div>
    <h2 id="page-heading" data-cy="JobDescHeading">
      <span id="job-desc-heading">Job Descs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && jobDescs && jobDescs.length === 0">
      <span>No Job Descs found</span>
    </div>
    <div class="table-responsive" v-if="jobDescs && jobDescs.length > 0">
      <table class="table table-striped" aria-describedby="jobDescs">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Job Name</span></th>
            <th scope="row"><span>Standard Type</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="jobDesc in jobDescs" :key="jobDesc.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'JobDescView', params: { jobDescId: jobDesc.id } }">{{ jobDesc.id }}</router-link>
            </td>
            <td>{{ jobDesc.jobName }}</td>
            <td>
              <div v-if="jobDesc.standardType">
                <router-link :to="{ name: 'StandardTypeView', params: { standardTypeId: jobDesc.standardType.id } }">{{
                  jobDesc.standardType.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'JobDescView', params: { jobDescId: jobDesc.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script lang="ts" src="./job-desc.component.ts"></script>
