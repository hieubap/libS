package spring.boot.core.dao.model;

import javax.persistence.Transient;

public abstract class OwnerBaseEntity extends BaseEntity{
    @Transient
    public Boolean ignoreOwner = false;
}
