package spring.backend.library.dao.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public abstract class BaseEntity {

  @Column(name = "create_at",nullable = false,updatable = false)
  @CreatedDate
  private ZonedDateTime createdAt;

  @Column(name = "update_at",nullable = false)
  @LastModifiedDate
  private ZonedDateTime updatedAt;

  @Column(name = "created_by")
  @CreatedBy
  private Long createdBy;

  @Column(name = "updated_by")
  @LastModifiedBy
  private Long updatedBy;

  @Column(name = "deleted", nullable = false)
  private Short deleted = 0;

  @Transient
  private Boolean mapAllProperties = false;

  public void setMapAllProperties (Boolean value){
    this.mapAllProperties = value;
  }
  public abstract void setId(Long id);

  public abstract Long getId();

  public void setAuditProperties(ZonedDateTime createAt, Long createdBy, ZonedDateTime updateAt,
      Long updatedBy) {
    this.createdAt = createAt;
    this.createdBy = createdBy;
    this.updatedAt = updateAt;
    this.updatedBy = updatedBy;
  }
}
