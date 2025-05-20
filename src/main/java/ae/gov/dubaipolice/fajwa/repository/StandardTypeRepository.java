package ae.gov.dubaipolice.fajwa.repository;

import ae.gov.dubaipolice.fajwa.domain.StandardType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StandardType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StandardTypeRepository extends JpaRepository<StandardType, Long>, JpaSpecificationExecutor<StandardType> {}
