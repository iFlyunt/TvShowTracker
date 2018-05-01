package com.ippt.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "subscriber")
public class Subscriber extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(nullable = false)
    private String username;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.REMOVE)
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.REMOVE)
    private List<WatchedEpisode> watchedEpisodes;
}
