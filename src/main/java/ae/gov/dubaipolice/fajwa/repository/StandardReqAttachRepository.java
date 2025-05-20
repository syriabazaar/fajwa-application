package ae.gov.dubaipolice.fajwa.repository;

import ae.gov.dubaipolice.fajwa.domain.StandardReqAttach;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StandardReqAttach entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StandardReqAttachRepository extends JpaRepository<StandardReqAttach, Long>, JpaSpecificationExecutor<StandardReqAttach> {}
