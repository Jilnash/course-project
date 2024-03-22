package com.jilnash.courseproject.envers.revision_listener;

import com.jilnash.courseproject.envers.revision_entity.AuditRevisionEntity;
import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;
import org.springframework.security.core.context.SecurityContextHolder;

public class FieldTrackingRevisionListener implements EntityTrackingRevisionListener {
    @Override
    public void entityChanged(Class entityClass, String s, Object entityId, RevisionType revisionType, Object revisionEntity) {

        AuditRevisionEntity entity = (AuditRevisionEntity) revisionEntity;
        entity.setEntityName(entityClass.getSimpleName());
        entity.setAction(revisionType.name().toLowerCase());
        entity.setEntityId((Long) entityId);
    }

    @Override
    public void newRevision(Object o) {
        AuditRevisionEntity entity = (AuditRevisionEntity) o;
        entity.setUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
