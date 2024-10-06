package spring.boot.core.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import spring.boot.core.dao.model.BaseEntity;
import spring.boot.core.dto.BaseDTO;

import java.util.Optional;

public interface OwnerBaseRepository<Entity extends BaseEntity,DTO extends BaseDTO,ID extends Long>
        extends BaseRepository<Entity, DTO, ID>{
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.createdBy = :#{#dto.createdBy}")
    Page<Entity> search(DTO dto, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.createdBy = :#{#createdBy} and e.id = ?1")
    Optional<Entity> findById(ID id, Long createdBy);

    @Transactional(readOnly = true)
    @Query("select case when count(e) > 0 then true else false end " +
            " from #{#entityName} e where e.createdBy = :#{#createdBy} and e.id = ?1")
    Boolean ownerOfId(ID id, Long createdBy);
}
