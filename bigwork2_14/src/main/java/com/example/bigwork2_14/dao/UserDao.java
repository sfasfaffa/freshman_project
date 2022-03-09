package com.example.bigwork2_14.dao;

import com.example.bigwork2_14.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {
   public User findByName(String name);
   @Query(value = "select * from user ",nativeQuery = true)
   public List<User> findSql();

}
