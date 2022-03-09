package com.example.bigwork2_14.service;

import com.example.bigwork2_14.dao.ClazzDao;
import com.example.bigwork2_14.pojo.Clazz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClazzServiceImpl implements ClazzService{
    @Autowired
    ClazzDao clazzDao;
    @Override
    public List<Clazz> findByState(Integer state) {
        return clazzDao.findByState(state);
    }

    @Override
    public Clazz getById(Integer id) {
        if(clazzDao.findById(id).orElse(null)!=null){
            return clazzDao.getById(id);
        }else {
            return null;
        }
    }
    @Override
    public List<Clazz> findAll() {
        return clazzDao.findAll();
    }
    @Override
    public Clazz findById(Integer id){
        if (clazzDao.findById(id).orElse(null)!=null){
            return clazzDao.getById(id);
        }else {
            return null;
        }
    }

    @Override
    public void save(Clazz clazz) {
        clazzDao.save(clazz);
    }
    @Override
    public List<Clazz> findByTeacherId(Integer id){
        return clazzDao.findByTeacherId(id);
    }
}
