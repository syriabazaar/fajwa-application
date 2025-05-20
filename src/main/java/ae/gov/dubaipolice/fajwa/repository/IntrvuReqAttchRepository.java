package ae.gov.dubaipolice.fajwa.repository;

import ae.gov.dubaipolice.fajwa.domain.IntrvuReqAttch;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IntrvuReqAttch entity.
 */
@Repository
public interface IntrvuReqAttchRepository extends JpaRepository<IntrvuReqAttch, Long> {
    default Optional<IntrvuReqAttch> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<IntrvuReqAttch> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<IntrvuReqAttch> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select intrvuReqAttch from IntrvuReqAttch intrvuReqAttch left join fetch intrvuReqAttch.standardReqAttach",
        countQuery = "select count(intrvuReqAttch) from IntrvuReqAttch intrvuReqAttch"
    )
    Page<IntrvuReqAttch> findAllWithToOneRelationships(Pageable pageable);

    @Query("select intrvuReqAttch from IntrvuReqAttch intrvuReqAttch left join fetch intrvuReqAttch.standardReqAttach")
    List<IntrvuReqAttch> findAllWithToOneRelationships();

    @Query(
        "select intrvuReqAttch from IntrvuReqAttch intrvuReqAttch left join fetch intrvuReqAttch.standardReqAttach where intrvuReqAttch.id =:id"
    )
    Optional<IntrvuReqAttch> findOneWithToOneRelationships(@Param("id") Long id);
}
