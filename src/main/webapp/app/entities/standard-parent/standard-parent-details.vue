<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="standardParent">
        <h2 class="jh-entity-heading" data-cy="standardParentDetailsHeading"><span>Standard Parent</span> {{ standardParent.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ standardParent.name }}</span>
          </dd>
          <dt>
            <span>Standard Type</span>
          </dt>
          <dd>
            <div v-if="standardParent.standardType">
              <router-link :to="{ name: 'StandardTypeView', params: { standardTypeId: standardParent.standardType.id } }">{{
                standardParent.standardType.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="standardParent.id"
          :to="{ name: 'StandardParentEdit', params: { standardParentId: standardParent.id } }"
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

<script lang="ts" src="./standard-parent-details.component.ts"></script>
