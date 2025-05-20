<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="standarditem">
        <h2 class="jh-entity-heading" data-cy="standarditemDetailsHeading"><span>Standarditem</span> {{ standarditem.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ standarditem.name }}</span>
          </dd>
          <dt>
            <span>Is Active</span>
          </dt>
          <dd>
            <span>{{ standarditem.isActive }}</span>
          </dd>
          <dt>
            <span>Weight Percentage</span>
          </dt>
          <dd>
            <span>{{ standarditem.weightPercentage }}</span>
          </dd>
          <dt>
            <span>Standard Cat</span>
          </dt>
          <dd>
            <div v-if="standarditem.standardCat">
              <router-link :to="{ name: 'StandardCatView', params: { standardCatId: standarditem.standardCat.id } }">{{
                standarditem.standardCat.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="standarditem.id"
          :to="{ name: 'StandarditemEdit', params: { standarditemId: standarditem.id } }"
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

<script lang="ts" src="./standarditem-details.component.ts"></script>
