package com.example.bigwork2_14.service;

import com.example.bigwork2_14.dao.ClazzDao;
import com.example.bigwork2_14.dao.UserDao;
import com.example.bigwork2_14.pojo.Clazz;
import com.example.bigwork2_14.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    ClazzDao clazzDao;
    @Autowired
    UserDao userDao;
    public String lookOverSer(Model model){
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        List<Clazz> clazzes = clazzDao.findByUserId(currentUser.getId());
        model.addAttribute("msg",clazzes.toString());
        return "/student/lookover";
    }
    @Override
    public void doDropClazz(Integer id) {
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        List<Clazz> clazzes = currentUser.getClazzes();
        List<Clazz> newClazzes = new ArrayList<>();
        for (Clazz clazz : clazzes) {//找到除了输入的课程以外所有的已选课加入newClazzes集合中
            if(clazz.getId()!=id){
                newClazzes.add(clazz);
            }
        }
        currentUser.setClazzes(null);
        currentUser.setClazzes(newClazzes);
        userDao.save(currentUser);
    }

    @Override
    public void chooseClazz(Clazz clazz,User currentUser) {
        currentUser.getClazzes().add(clazz);
        userDao.save(currentUser);
    }

    @Override
    public void addClazz(String name) {
        Clazz clazz = new Clazz();
        clazz.setName(name);
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User)subject.getPrincipal();
        clazz.setTeacherId(currentUser.getId());
        clazz.setState(0);
        clazzDao.save(clazz);
    }
    public User findByName(String name){
        return userDao.findByName(name);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }
}
