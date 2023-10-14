package com.EzmarJava.Webshop.model;

import jakarta.persistence.*;

import java.util.Date;

@MappedSuperclass
public abstract class AbstractTimeEntity
{
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        createdAt = new Date();
    }
}
