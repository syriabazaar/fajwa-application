package ae.gov.dubaipolice.fajwa.web.rest;

import ae.gov.dubaipolice.fajwa.domain.Appointment;
import ae.gov.dubaipolice.fajwa.repository.AppointmentRepository;
import ae.gov.dubaipolice.fajwa.service.AppointmentQueryService;
import ae.gov.dubaipolice.fajwa.service.AppointmentService;
import ae.gov.dubaipolice.fajwa.service.criteria.AppointmentCriteria;
import ae.gov.dubaipolice.fajwa.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ae.gov.dubaipolice.fajwa.domain.Appointment}.
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(AppointmentResource.class);

    private static final String ENTITY_NAME = "appointment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppointmentService appointmentService;

    private final AppointmentRepository appointmentRepository;

    private final AppointmentQueryService appointmentQueryService;

    public AppointmentResource(
        AppointmentService appointmentService,
        AppointmentRepository appointmentRepository,
        AppointmentQueryService appointmentQueryService
    ) {
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
        this.appointmentQueryService = appointmentQueryService;
    }

    /**
     * {@code POST  /appointments} : Create a new appointment.
     *
     * @param appointment the appointment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appointment, or with status {@code 400 (Bad Request)} if the appointment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) throws URISyntaxException {
        LOG.debug("REST request to save Appointment : {}", appointment);
        if (appointment.getId() != null) {
            throw new BadRequestAlertException("A new appointment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appointment = appointmentService.save(appointment);
        return ResponseEntity.created(new URI("/api/appointments/" + appointment.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, appointment.getId().toString()))
            .body(appointment);
    }

    /**
     * {@code PUT  /appointments/:id} : Updates an existing appointment.
     *
     * @param id the id of the appointment to save.
     * @param appointment the appointment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointment,
     * or with status {@code 400 (Bad Request)} if the appointment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appointment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Appointment appointment
    ) throws URISyntaxException {
        LOG.debug("REST request to update Appointment : {}, {}", id, appointment);
        if (appointment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appointment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appointmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appointment = appointmentService.update(appointment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appointment.getId().toString()))
            .body(appointment);
    }

    /**
     * {@code PATCH  /appointments/:id} : Partial updates given fields of an existing appointment, field will ignore if it is null
     *
     * @param id the id of the appointment to save.
     * @param appointment the appointment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointment,
     * or with status {@code 400 (Bad Request)} if the appointment is not valid,
     * or with status {@code 404 (Not Found)} if the appointment is not found,
     * or with status {@code 500 (Internal Server Error)} if the appointment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Appointment> partialUpdateAppointment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Appointment appointment
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Appointment partially : {}, {}", id, appointment);
        if (appointment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appointment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appointmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Appointment> result = appointmentService.partialUpdate(appointment);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appointment.getId().toString())
        );
    }

    /**
     * {@code GET  /appointments} : get all the appointments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appointments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Appointment>> getAllAppointments(
        AppointmentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Appointments by criteria: {}", criteria);

        Page<Appointment> page = appointmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appointments/count} : count all the appointments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAppointments(AppointmentCriteria criteria) {
        LOG.debug("REST request to count Appointments by criteria: {}", criteria);
        return ResponseEntity.ok().body(appointmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /appointments/:id} : get the "id" appointment.
     *
     * @param id the id of the appointment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appointment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Appointment : {}", id);
        Optional<Appointment> appointment = appointmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appointment);
    }

    /**
     * {@code DELETE  /appointments/:id} : delete the "id" appointment.
     *
     * @param id the id of the appointment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Appointment : {}", id);
        appointmentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
