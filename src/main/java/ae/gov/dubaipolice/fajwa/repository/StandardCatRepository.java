package ae.gov.dubaipolice.fajwa.repository;

import ae.gov.dubaipolice.fajwa.domain.StandardCat;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StandardCat entity.
 */
@Repository
public interface StandardCatRepository extends JpaRepository<StandardCat, Long>, JpaSpecificationExecutor<StandardCat> {
    default Optional<StandardCat> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<StandardCat> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<StandardCat> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select standardCat from StandardCat standardCat left join fetch standardCat.standardParent left join fetch standardCat.reqAttachment",
        countQuery = "select count(standardCat) from StandardCat standardCat"
    )
    Page<StandardCat> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select standardCat from StandardCat standardCat left join fetch standardCat.standardParent left join fetch standardCat.reqAttachment"
    )
    List<StandardCat> findAllWithToOneRelationships();

    @Query(
        "select standardCat from StandardCat standardCat left join fetch standardCat.standardParent left join fetch standardCat.reqAttachment where standardCat.id =:id"
    )
    Optional<StandardCat> findOneWithToOneRelationships(@Param("id") Long id);
}
