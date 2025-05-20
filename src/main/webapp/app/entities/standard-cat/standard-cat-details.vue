<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="standardCat">
        <h2 class="jh-entity-heading" data-cy="standardCatDetailsHeading"><span>Standard Cat</span> {{ standardCat.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ standardCat.name }}</span>
          </dd>
          <dt>
            <span>Standard Parent</span>
          </dt>
          <dd>
            <div v-if="standardCat.standardParent">
              <router-link :to="{ name: 'StandardParentView', params: { standardParentId: standardCat.standardParent.id } }">{{
                standardCat.standardParent.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Req Attachment</span>
          </dt>
          <dd>
            <div v-if="standardCat.reqAttachment">
              <router-link :to="{ name: 'StandardReqAttachView', params: { standardReqAttachId: standardCat.reqAttachment.id } }">{{
                standardCat.reqAttachment.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="standardCat.id"
          :to="{ name: 'StandardCatEdit', params: { standardCatId: standardCat.id } }"
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

<script lang="ts" src="./standard-cat-details.component.ts"></script>
