<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="department">
        <h2 class="jh-entity-heading" data-cy="departmentDetailsHeading"><span>Department</span> {{ department.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Dep Name</span>
          </dt>
          <dd>
            <span>{{ department.depName }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="department.id"
          :to="{ name: 'DepartmentEdit', params: { departmentId: department.id } }"
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

<script lang="ts" src="./department-details.component.ts"></script>
