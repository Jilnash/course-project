package com.jilnash.courseproject.envers.revision_entity;

import com.jilnash.courseproject.envers.revision_listener.FieldTrackingRevisionListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "audit_envers_info")
@RevisionEntity(FieldTrackingRevisionListener.class)
public class AuditRevisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "id")
    private Long id;

    @RevisionTimestamp
    @Column(name = "revision_date", nullable = false)
    private Date revision_date;

    private String username;

    private String action;

    private String entityName;

    private Long entityId;

    private Long courseId;

//    private String entityField;
}