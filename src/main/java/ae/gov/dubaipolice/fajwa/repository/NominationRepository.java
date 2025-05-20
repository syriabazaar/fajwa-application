package ae.gov.dubaipolice.fajwa.repository;

import ae.gov.dubaipolice.fajwa.domain.Nomination;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nomination entity.
 */
@Repository
public interface NominationRepository extends JpaRepository<Nomination, Long>, JpaSpecificationExecutor<Nomination> {
    default Optional<Nomination> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Nomination> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Nomination> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select nomination from Nomination nomination left join fetch nomination.employee",
        countQuery = "select count(nomination) from Nomination nomination"
    )
    Page<Nomination> findAllWithToOneRelationships(Pageable pageable);

    @Query("select nomination from Nomination nomination left join fetch nomination.employee")
    List<Nomination> findAllWithToOneRelationships();

    @Query("select nomination from Nomination nomination left join fetch nomination.employee where nomination.id =:id")
    Optional<Nomination> findOneWithToOneRelationships(@Param("id") Long id);
}
