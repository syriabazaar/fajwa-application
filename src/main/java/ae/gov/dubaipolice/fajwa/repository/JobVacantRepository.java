package ae.gov.dubaipolice.fajwa.repository;

import ae.gov.dubaipolice.fajwa.domain.JobVacant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobVacant entity.
 */
@Repository
public interface JobVacantRepository extends JpaRepository<JobVacant, Long> {
    default Optional<JobVacant> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<JobVacant> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<JobVacant> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select jobVacant from JobVacant jobVacant left join fetch jobVacant.department left join fetch jobVacant.jobDesc",
        countQuery = "select count(jobVacant) from JobVacant jobVacant"
    )
    Page<JobVacant> findAllWithToOneRelationships(Pageable pageable);

    @Query("select jobVacant from JobVacant jobVacant left join fetch jobVacant.department left join fetch jobVacant.jobDesc")
    List<JobVacant> findAllWithToOneRelationships();

    @Query(
        "select jobVacant from JobVacant jobVacant left join fetch jobVacant.department left join fetch jobVacant.jobDesc where jobVacant.id =:id"
    )
    Optional<JobVacant> findOneWithToOneRelationships(@Param("id") Long id);
}
