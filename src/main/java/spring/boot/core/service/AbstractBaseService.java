package spring.boot.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.CastUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import spring.boot.core.config.userdetail.UserPrincipal;
import spring.boot.core.dao.model.BaseEntity;
import spring.boot.core.dao.repository.BaseRepository;
import spring.boot.core.dto.BaseDTO;
import spring.boot.core.exception.BaseException;
import spring.boot.core.exception.DataException;
import spring.boot.core.map.MapperService;
import spring.boot.core.storage.StorageService;
import spring.boot.core.utils.MapperUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
public abstract class AbstractBaseService<
        Entity extends BaseEntity,
        DTO extends BaseDTO,
        Repository extends BaseRepository<Entity,DTO,Long>>
  extends MapperService<Entity,DTO> implements BaseService<DTO> {

  protected abstract Repository getRepository();

  @Autowired
  private StorageService storageService;
  final protected StorageService getStorageService() {
    return storageService;
  }

  protected List<DTO> save(List<Entity> entities, List<DTO> dtos) {
    entities = getRepository().saveAll(entities);

    int size = entities.size();

    for (int i = 0; i < size; i++) {
      DTO dto = dtos.get(i);
      Entity entity = entities.get(i);

      mapToDTO(entity, dto);
    }

    return dtos;
  }

  protected DTO save(Entity entity,DTO dto){
    getRepository().save(entity);

    mapToDTO(entity,dto);

    return dto;
  }

  @Override
  @Cacheable
  public Page<DTO> search(DTO dto, Pageable pageable) {
    return getRepository().search(dto,pageable).map(this::mapToDTO);
  }

  @Override
  public DTO save(DTO dto) {
    if (dto == null){
      throw new DataException.NotExistData();
    }

    Entity entity;
    if (dto.getId() != null){
      entity = getById(dto.getId());
      mapToEntity(dto,entity);
      entity.setId(dto.getId());
    }
    else {
      entity = mapToEntity(dto);
    }

    return save(entity,dto);
  }

  @Override
  public List<DTO> save(List<DTO> dtos) {
    if (dtos == null || dtos.isEmpty()) {
      throw new DataException.NotExistData();
    }

    List<Entity> entities = new ArrayList<>();

    dtos.forEach(dto -> {
      Entity model;
      if (dto.getId() != null) {
        model = getById(dto.getId());
        mapToEntity(dto, model);
        model.setId(dto.getId());
      } else {
        model = mapToEntity(dto);
      }
      entities.add(model);
    });


    return save(entities, dtos);
  }

  @Override
  public DTO save(Long id, DTO dto) {
    if (dto == null){
      throw new DataException.NotExistData();
    }
    if (id == null || id.compareTo(0L) <= 0){
      throw new DataException.NotFoundEntityById(id,getName());
    }

    Entity model = getById(id);
    dto.setId(id);
    mapToEntity(dto,model);
    model.setId(id);

    return save(model,dto);
  }

  @Override
  public DTO save(Long id, Map<String, Object> map) {
    if (id == null || id.compareTo(0L) <= 0) {
      throw new DataException.NotFoundEntityById(id, getName());
    }

    if (map == null) {
      throw new BaseException(400,"json is null");
    }

    Entity model = getById(id);
//    model.setMapAllProperties(true);

    map = mergeMap(map, MapperUtils.convertValue(mapToDTO(model)));

    DTO dto = MapperUtils.convertValue(map, getDtoClass());

    dto.setId(id);
    mapToEntity(dto, model);
    model.setId(id);

    return save(model, dto);
  }

  @Override
  public DTO findDetailById(Long id) {
    Entity entity = getRepository().findById(id).get();
    entity.setMapAllProperties(true);
    return mapToDTO(entity);
  }

  @Override
  public DTO findById(Long id) {
    Entity entity = getById(id);
    entity.setMapAllProperties(false);
    return mapToDTO(entity);
  }

  @Override
  public DTO findById(Long id, boolean mapAllProperties) {
    Entity entity = getById(id);
    entity.setMapAllProperties(mapAllProperties);
    return mapToDTO(entity);
  }

  @Override
  public void delete(Long id) {
    getRepository().deleteById(id);
  }

  @Override
  public Boolean existedById(Long id){
    return getRepository().existsById(id);
  }

  public Entity getById(Long id){
    return getRepository().findById(id)
        .orElseThrow(() -> new DataException.NotFoundEntityById(id, getName()));
  }

  protected Map<String, Object> mergeMap(Map<String, Object> from, Map<String, Object> to) {
    from.forEach((key, newValue) -> {
      Object oldValue = to.get(key);
      if ((oldValue instanceof Map) && (newValue instanceof Map)) {
        to.put(key, mergeMap(CastUtils.cast(newValue), CastUtils.cast(oldValue)));
      } else {
        to.put(key, newValue);
      }
    });
    return to;
  }

  @Override
  public Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()
            || authentication instanceof AnonymousAuthenticationToken) {
      return null;
    }
    UserPrincipal userDetail = (UserPrincipal) authentication.getPrincipal();
    return userDetail.getId();
  }

  @Override
  public UserPrincipal getTokenInfo() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()
            || authentication instanceof AnonymousAuthenticationToken) {
      return null;
    }
    return (UserPrincipal) authentication.getPrincipal();
  }
}
