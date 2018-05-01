package com.ippt.server.service;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public abstract class AbstractService<T, ID, R extends JpaRepository<T, ID>>
        implements Service<T, ID, R> {
    private R repository;

    protected AbstractService(R repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Transactional
    @Override
    public void save(T entity) {
        repository.save(entity);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public R getRepository() {
        return this.repository;
    }
}
