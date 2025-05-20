package ae.gov.dubaipolice.fajwa.repository;

import ae.gov.dubaipolice.fajwa.domain.IntrvuStrdDtls;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IntrvuStrdDtls entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntrvuStrdDtlsRepository extends JpaRepository<IntrvuStrdDtls, Long> {}
