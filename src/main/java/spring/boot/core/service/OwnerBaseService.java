package spring.boot.core.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.boot.core.dao.model.BaseEntity;
import spring.boot.core.dao.repository.BaseRepository;
import spring.boot.core.dao.repository.OwnerBaseRepository;
import spring.boot.core.dto.BaseDTO;
import spring.boot.core.exception.BaseException;

import java.util.Objects;

public class OwnerBaseService<
        Entity extends BaseEntity,
        DTO extends BaseDTO,
        Repository extends OwnerBaseRepository<Entity,DTO,Long>>
        extends AbstractBaseService<Entity, DTO, Repository>{

    @Override
    protected Repository getRepository() {
        return null;
    }

    @Override
    @Cacheable
    public Page<DTO> search(DTO dto, Pageable pageable) {
        dto.setCreatedBy(getCurrentUserId());
        return getRepository().search(dto,pageable).map(this::mapToDTO);
    }

    @Override
    public DTO mapToDTO(Entity entity) {
        if(entity.getId() != null && !Objects.equals(entity.getCreatedBy(), getCurrentUserId())){
            throw new BaseException(1000, "Dữ liệu không thuộc về bạn");
        }
        return super.mapToDTO(entity);
    }

    @Override
    protected void beforeSave(Entity entity, DTO dto) {
        if(entity.getId() != null && !Objects.equals(entity.getCreatedBy(), getCurrentUserId())){
            throw new BaseException(1000, "Dữ liệu không thuộc về bạn");
        }
        super.beforeSave(entity, dto);
    }

    @Override
    protected void beforeDelete(Long id) {
        if(!getRepository().ownerOfId(id, getCurrentUserId())){
            throw new BaseException(1000, "Dữ liệu không thuộc về bạn");
        }
        super.beforeDelete(id);
    }
}
