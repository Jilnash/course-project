package com.jilnash.courseproject.envers.revision_listener;

import com.jilnash.courseproject.context.ApplicationContextProvider;
import com.jilnash.courseproject.envers.revision_entity.AuditRevisionEntity;
import com.jilnash.courseproject.service.TaskService;
import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FieldTrackingRevisionListener implements EntityTrackingRevisionListener {

    @Override
    public void entityChanged(Class entityClass, String s, Object entityId, RevisionType revisionType, Object revisionEntity) {

        AuditRevisionEntity entity = (AuditRevisionEntity) revisionEntity;
        entity.setEntityName(entityClass.getSimpleName());
        entity.setAction(revisionType.name().toLowerCase());
        entity.setEntityId((Long) entityId);

        TaskService taskService = ApplicationContextProvider.getApplicationContext().getBean(TaskService.class);

        if (entityClass.getSimpleName().equals("Course"))
            entity.setCourseId((Long) entityId);
        else if (entityClass.getSimpleName().equals("Task"))
            entity.setCourseId(taskService.getTask((Long) entityId).getCourse().getId());

    }

    @Override
    public void newRevision(Object o) {
        AuditRevisionEntity entity = (AuditRevisionEntity) o;
        entity.setUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
