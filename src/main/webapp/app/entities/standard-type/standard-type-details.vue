<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="standardType">
        <h2 class="jh-entity-heading" data-cy="standardTypeDetailsHeading"><span>Standard Type</span> {{ standardType.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ standardType.name }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="standardType.id"
          :to="{ name: 'StandardTypeEdit', params: { standardTypeId: standardType.id } }"
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

<script lang="ts" src="./standard-type-details.component.ts"></script>
