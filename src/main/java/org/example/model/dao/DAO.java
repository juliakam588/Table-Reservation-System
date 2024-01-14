package org.example.model.dao;

import java.util.List;

public interface DAO<T> {

    T get(int id);
    List<T> getAll();
    int insert(T t);
    void update(T t);
    void delete(int id);
}