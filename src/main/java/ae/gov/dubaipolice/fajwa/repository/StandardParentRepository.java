package ae.gov.dubaipolice.fajwa.repository;

import ae.gov.dubaipolice.fajwa.domain.StandardParent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StandardParent entity.
 */
@Repository
public interface StandardParentRepository extends JpaRepository<StandardParent, Long>, JpaSpecificationExecutor<StandardParent> {
    default Optional<StandardParent> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<StandardParent> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<StandardParent> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select standardParent from StandardParent standardParent left join fetch standardParent.standardType",
        countQuery = "select count(standardParent) from StandardParent standardParent"
    )
    Page<StandardParent> findAllWithToOneRelationships(Pageable pageable);

    @Query("select standardParent from StandardParent standardParent left join fetch standardParent.standardType")
    List<StandardParent> findAllWithToOneRelationships();

    @Query(
        "select standardParent from StandardParent standardParent left join fetch standardParent.standardType where standardParent.id =:id"
    )
    Optional<StandardParent> findOneWithToOneRelationships(@Param("id") Long id);
}
