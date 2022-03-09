package com.example.bigwork2_14.service;

import com.example.bigwork2_14.pojo.Clazz;

import java.util.List;

public interface ClazzService {
    public List<Clazz> findByState(Integer state);
    public Clazz getById(Integer id);
    public List<Clazz> findAll();
    public Clazz findById(Integer id);
    public void save(Clazz clazz);
    public List<Clazz> findByTeacherId(Integer id);
}
