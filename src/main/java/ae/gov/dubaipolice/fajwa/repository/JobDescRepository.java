package ae.gov.dubaipolice.fajwa.repository;

import ae.gov.dubaipolice.fajwa.domain.JobDesc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobDesc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobDescRepository extends JpaRepository<JobDesc, Long> {}
