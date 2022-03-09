package com.example.bigwork2_14;

import com.example.bigwork2_14.dao.ClazzDao;
import com.example.bigwork2_14.dao.UserDao;
import com.example.bigwork2_14.pojo.Clazz;
import com.example.bigwork2_14.pojo.User;
import com.example.bigwork2_14.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Bigwork214ApplicationTests {

    @Autowired
    UserDao userDao;
    @Autowired
    ClazzDao clazzDao;
    @Test
    public void test(){
        List<User> users= userDao.findSql();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }
    @Test
    public void test2() {
        List<Clazz> clazzes = clazzDao.findByUserId(6);
        for (Clazz clazz : clazzes) {
            System.out.println(clazz.toString());
        }
    }
}
