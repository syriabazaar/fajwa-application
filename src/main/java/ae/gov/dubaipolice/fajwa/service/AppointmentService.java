package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.Appointment;
import ae.gov.dubaipolice.fajwa.repository.AppointmentRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ae.gov.dubaipolice.fajwa.domain.Appointment}.
 */
@Service
@Transactional
public class AppointmentService {

    private static final Logger LOG = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Save a appointment.
     *
     * @param appointment the entity to save.
     * @return the persisted entity.
     */
    public Appointment save(Appointment appointment) {
        LOG.debug("Request to save Appointment : {}", appointment);
        return appointmentRepository.save(appointment);
    }

    /**
     * Update a appointment.
     *
     * @param appointment the entity to save.
     * @return the persisted entity.
     */
    public Appointment update(Appointment appointment) {
        LOG.debug("Request to update Appointment : {}", appointment);
        return appointmentRepository.save(appointment);
    }

    /**
     * Partially update a appointment.
     *
     * @param appointment the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Appointment> partialUpdate(Appointment appointment) {
        LOG.debug("Request to partially update Appointment : {}", appointment);

        return appointmentRepository
            .findById(appointment.getId())
            .map(existingAppointment -> {
                if (appointment.getAppDateTime() != null) {
                    existingAppointment.setAppDateTime(appointment.getAppDateTime());
                }
                if (appointment.getInterveiewDate() != null) {
                    existingAppointment.setInterveiewDate(appointment.getInterveiewDate());
                }

                return existingAppointment;
            })
            .map(appointmentRepository::save);
    }

    /**
     * Get one appointment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Appointment> findOne(Long id) {
        LOG.debug("Request to get Appointment : {}", id);
        return appointmentRepository.findById(id);
    }

    /**
     * Delete the appointment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Appointment : {}", id);
        appointmentRepository.deleteById(id);
    }
}
