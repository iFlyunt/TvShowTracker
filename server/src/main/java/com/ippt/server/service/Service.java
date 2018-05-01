package com.ippt.server.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Service<T, ID, R extends JpaRepository<T, ID>> {
    void delete(T entity);

    void save(T entity);

    T findById(ID id);

    List<T> findAll();

    R getRepository();
}
