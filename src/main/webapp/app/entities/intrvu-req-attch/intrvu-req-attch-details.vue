<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="intrvuReqAttch">
        <h2 class="jh-entity-heading" data-cy="intrvuReqAttchDetailsHeading"><span>Intrvu Req Attch</span> {{ intrvuReqAttch.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Add Date</span>
          </dt>
          <dd>
            <span v-if="intrvuReqAttch.addDate">{{ formatDateLong(intrvuReqAttch.addDate) }}</span>
          </dd>
          <dt>
            <span>Attch</span>
          </dt>
          <dd>
            <span>{{ intrvuReqAttch.attch }}</span>
          </dd>
          <dt>
            <span>Standard Req Attach</span>
          </dt>
          <dd>
            <div v-if="intrvuReqAttch.standardReqAttach">
              <router-link :to="{ name: 'StandardReqAttachView', params: { standardReqAttachId: intrvuReqAttch.standardReqAttach.id } }">{{
                intrvuReqAttch.standardReqAttach.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Employee</span>
          </dt>
          <dd>
            <div v-if="intrvuReqAttch.employee">
              <router-link :to="{ name: 'EmployeeView', params: { employeeId: intrvuReqAttch.employee.id } }">{{
                intrvuReqAttch.employee.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="intrvuReqAttch.id"
          :to="{ name: 'IntrvuReqAttchEdit', params: { intrvuReqAttchId: intrvuReqAttch.id } }"
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

<script lang="ts" src="./intrvu-req-attch-details.component.ts"></script>
