package com.ippt.server.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractTvDbEntity extends AbstractEntity {
    @NaturalId
    @Column(name = "tvdb_id", nullable = false)
    private long tvdbId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}
