package ae.gov.dubaipolice.fajwa.repository;

import ae.gov.dubaipolice.fajwa.domain.Standarditem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Standarditem entity.
 */
@Repository
public interface StandarditemRepository extends JpaRepository<Standarditem, Long>, JpaSpecificationExecutor<Standarditem> {
    default Optional<Standarditem> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Standarditem> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Standarditem> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select standarditem from Standarditem standarditem left join fetch standarditem.standardCat",
        countQuery = "select count(standarditem) from Standarditem standarditem"
    )
    Page<Standarditem> findAllWithToOneRelationships(Pageable pageable);

    @Query("select standarditem from Standarditem standarditem left join fetch standarditem.standardCat")
    List<Standarditem> findAllWithToOneRelationships();

    @Query("select standarditem from Standarditem standarditem left join fetch standarditem.standardCat where standarditem.id =:id")
    Optional<Standarditem> findOneWithToOneRelationships(@Param("id") Long id);
}
