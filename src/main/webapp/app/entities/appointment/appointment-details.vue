<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="appointment">
        <h2 class="jh-entity-heading" data-cy="appointmentDetailsHeading"><span>Appointment</span> {{ appointment.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>App Date Time</span>
          </dt>
          <dd>
            <span v-if="appointment.appDateTime">{{ formatDateLong(appointment.appDateTime) }}</span>
          </dd>
          <dt>
            <span>Interveiew Date</span>
          </dt>
          <dd>
            <span v-if="appointment.interveiewDate">{{ formatDateLong(appointment.interveiewDate) }}</span>
          </dd>
          <dt>
            <span>Nomination</span>
          </dt>
          <dd>
            <div v-if="appointment.nomination">
              <router-link :to="{ name: 'NominationView', params: { nominationId: appointment.nomination.id } }">{{
                appointment.nomination.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="appointment.id"
          :to="{ name: 'AppointmentEdit', params: { appointmentId: appointment.id } }"
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

<script lang="ts" src="./appointment-details.component.ts"></script>
