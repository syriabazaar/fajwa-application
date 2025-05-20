<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="intrvuStrdDtls">
        <h2 class="jh-entity-heading" data-cy="intrvuStrdDtlsDetailsHeading"><span>Intrvu Strd Dtls</span> {{ intrvuStrdDtls.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Standard Option</span>
          </dt>
          <dd>
            <span>{{ intrvuStrdDtls.standardOption }}</span>
          </dd>
          <dt>
            <span>Standarditem</span>
          </dt>
          <dd>
            <div v-if="intrvuStrdDtls.standarditem">
              <router-link :to="{ name: 'StandarditemView', params: { standarditemId: intrvuStrdDtls.standarditem.id } }">{{
                intrvuStrdDtls.standarditem.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Appointment</span>
          </dt>
          <dd>
            <div v-if="intrvuStrdDtls.appointment">
              <router-link :to="{ name: 'AppointmentView', params: { appointmentId: intrvuStrdDtls.appointment.id } }">{{
                intrvuStrdDtls.appointment.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="intrvuStrdDtls.id"
          :to="{ name: 'IntrvuStrdDtlsEdit', params: { intrvuStrdDtlsId: intrvuStrdDtls.id } }"
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

<script lang="ts" src="./intrvu-strd-dtls-details.component.ts"></script>
