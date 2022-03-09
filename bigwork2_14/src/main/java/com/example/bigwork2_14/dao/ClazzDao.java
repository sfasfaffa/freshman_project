package com.example.bigwork2_14.dao;

import com.example.bigwork2_14.pojo.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClazzDao extends JpaRepository<Clazz,Integer> {
    public List<Clazz> findByState(Integer state);
    @Query(value = "select * from clazz where teacher_id=?",nativeQuery = true)
    public List<Clazz> findByTeacherId(Integer id);
    @Query(value = "select * from clazz inner join clazzes_users  ON clazz.id=clazzes_users.c_id where clazzes_users.u_id=?",nativeQuery = true)
    public List<Clazz> findByUserId(Integer id);
}
