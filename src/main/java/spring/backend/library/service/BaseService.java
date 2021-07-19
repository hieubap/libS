package spring.backend.library.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.backend.library.dto.BaseDTO;

public interface BaseService<DTO extends BaseDTO> {
  Page<DTO> search(DTO dto, Pageable pageable);

  DTO save(DTO dto);

  List<DTO> save(List<DTO> dto);

  DTO save(Long id,DTO dto);

  DTO save(Long id, Map<String,Object> dto);

  void delete(Long id);

  DTO findDetailById(Long id);

  DTO findById(Long id);

  DTO findById(Long id,boolean mapAllProperties);
}
