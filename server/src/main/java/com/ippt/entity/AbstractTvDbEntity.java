package com.ippt.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

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
